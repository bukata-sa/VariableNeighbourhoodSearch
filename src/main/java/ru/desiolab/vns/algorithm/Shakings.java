package ru.desiolab.vns.algorithm;

import ru.desiolab.vns.dto.Cluster;
import ru.desiolab.vns.dto.Solution;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class Shakings {

    public static final List<Operation> ALL = List.of(new RandomSplit(), new RandomMerge());

    public static final class RandomSplit implements Operation {

        @Override
        public Solution perform(Solution solution) {
            List<Cluster> candidateClusters = solution.clusters().stream()
                    .filter(cluster -> cluster.machines().size() > 1)
                    .filter(cluster -> cluster.parts().size() > 1)
                    .collect(Collectors.toList());
            if (candidateClusters.isEmpty()) {
                return solution;
            }
            Cluster cluster = candidateClusters.get(new Random().nextInt(candidateClusters.size()));
            solution.clusters().remove(cluster);
            List<Cluster> newClusters = splitCluster(cluster);
            solution.clusters().addAll(newClusters);
            return solution;
        }

        private List<Cluster> splitCluster(Cluster cluster) {
            int machineIndex = new Random().nextInt(cluster.machines().size());
            int partIndex = new Random().nextInt(cluster.parts().size());
            List<List<Integer>> machinesSublist = splitListByIndex(cluster.machines(), machineIndex);
            List<List<Integer>> partsSublist = splitListByIndex(cluster.parts(), partIndex);
            return List.of(
                    new Cluster(machinesSublist.get(0), partsSublist.get(0)),
                    new Cluster(machinesSublist.get(1), partsSublist.get(1))
            );
        }

        private List<List<Integer>> splitListByIndex(List<Integer> parts, int partIndex) {
            List<Integer> first = parts.subList(0, partIndex);
            List<Integer> second = parts.subList(partIndex, parts.size());
            return List.of(first, second);
        }
    }

    public static final class RandomMerge implements Operation {

        @Override
        public Solution perform(Solution solution) {
            if (solution.clusters().size() <= 1) {
                return solution;
            }
            Integer firstClusterIndex = null;
            Integer secondClusterIndex = null;
            while (isNull(firstClusterIndex)) {
                int i = new Random().nextInt(solution.clusters().size());
                int j = new Random().nextInt(solution.clusters().size());
                if (i != j) {
                    firstClusterIndex = i;
                    secondClusterIndex = j;
                }
            }
            Cluster firstCluster = solution.clusters().get(firstClusterIndex);
            Cluster secondCluster = solution.clusters().get(secondClusterIndex);
            solution.clusters().remove(firstCluster);
            solution.clusters().remove(secondCluster);
            Cluster merged = merge(firstCluster, secondCluster);
            solution.clusters().add(merged);
            return solution;
        }

        private Cluster merge(Cluster firstCluster, Cluster secondCluster) {
            List<Integer> machines = Stream.of(firstCluster.machines(), secondCluster.machines())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            List<Integer> parts = Stream.of(firstCluster.parts(), secondCluster.parts())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            return new Cluster(machines, parts);
        }
    }
}

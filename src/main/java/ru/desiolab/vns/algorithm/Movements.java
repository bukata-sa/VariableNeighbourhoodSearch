package ru.desiolab.vns.algorithm;

import ru.desiolab.vns.dto.Cluster;
import ru.desiolab.vns.dto.Solution;

import java.util.List;
import java.util.stream.Collectors;

public class Movements {

    public static final List<Operation> ALL = List.of(new MoveAllMachines(), new MoveAllParts());

    public static final class MoveAllMachines implements Operation {

        @Override
        public Solution perform(Solution solution) {
            List<Cluster> candidateClusters = solution.clusters().stream()
                    .filter(cluster -> cluster.machines().size() > 1)
                    .collect(Collectors.toList());
            if (candidateClusters.size() <= 1) {
                return solution;
            }
            Solution bestSolution = solution.copy();
            for (Cluster fromCluster : candidateClusters) {
                for (Cluster toCluster : candidateClusters) {
                    if (fromCluster.equals(toCluster)) {
                        continue;
                    }
                    for (int machineIndex = 0; machineIndex < fromCluster.machines().size(); machineIndex++) {
                        Solution newSolution = bestSolution.copy();
                        int i = newSolution.clusters().indexOf(fromCluster);
                        int j = newSolution.clusters().indexOf(toCluster);
                        Cluster newFromCluster = newSolution.clusters().get(i);
                        Cluster newToCluster = newSolution.clusters().get(j);
                        moveMachine(newFromCluster, newToCluster, machineIndex);
                        if (newSolution.cost() > bestSolution.cost()) {
                            bestSolution = newSolution;
                            return perform(bestSolution);
                        }
                    }
                }
            }
            return bestSolution;
        }

        private void moveMachine(Cluster fromCluster, Cluster toCluster, int machineIndex) {
            Integer machine = fromCluster.machines().get(machineIndex);
            fromCluster.machines().remove(machineIndex);
            toCluster.machines().add(machine);
        }
    }

    public static final class MoveAllParts implements Operation {

        @Override
        public Solution perform(Solution solution) {
            List<Cluster> candidateClusters = solution.clusters().stream()
                    .filter(cluster -> cluster.parts().size() > 1)
                    .collect(Collectors.toList());
            if (candidateClusters.size() <= 1) {
                return solution;
            }
            Solution bestSolution = solution.copy();
            for (Cluster fromCluster : candidateClusters) {
                for (Cluster toCluster : candidateClusters) {
                    if (fromCluster.equals(toCluster)) {
                        continue;
                    }
                    for (int partIndex = 0; partIndex < fromCluster.parts().size(); partIndex++) {
                        Solution newSolution = bestSolution.copy();
                        int i = newSolution.clusters().indexOf(fromCluster);
                        int j = newSolution.clusters().indexOf(toCluster);
                        Cluster newFromCluster = newSolution.clusters().get(i);
                        Cluster newToCluster = newSolution.clusters().get(j);
                        movePart(newFromCluster, newToCluster, partIndex);
                        if (newSolution.cost() > bestSolution.cost()) {
                            bestSolution = newSolution;
                            return perform(bestSolution);
                        }
                    }
                }
            }
            return bestSolution;
        }

        private void movePart(Cluster fromCluster, Cluster toCluster, int partIndex) {
            Integer part = fromCluster.parts().get(partIndex);
            fromCluster.parts().remove(partIndex);
            toCluster.parts().add(part);
        }
    }
}

package ru.desiolab.vns.dto;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Accessors(fluent = true)
public class Solution implements Cloneable {

    @Getter
    private final List<Cluster> clusters;
    private final int[][] machineToPart;
    private final int total;

    public static Solution initial(TaskInstance taskInstance) {
        List<Integer> allMachinesNumbers = IntStream.range(0, taskInstance.machinesNumber()).boxed().collect(Collectors.toList());
        List<Integer> allPartsNumbers = IntStream.range(0, taskInstance.partsNumber()).boxed().collect(Collectors.toList());
        Cluster cluster = new Cluster(allMachinesNumbers, allPartsNumbers);
        return new Solution(Collections.singletonList(cluster), taskInstance.machineToPart());
    }

    public Solution(List<Cluster> clusters, int[][] machineToPart) {
        this.clusters = new ArrayList<>(clusters);
        this.machineToPart = machineToPart;
        this.total = Arrays.stream(machineToPart)
                .flatMapToInt(Arrays::stream)
                .sum();
    }

    public Solution(List<Cluster> clusters, int[][] machineToPart, int total) {
        this.clusters = new ArrayList<>(clusters);
        this.machineToPart = machineToPart;
        this.total = total;
    }

    public Solution copy() {
        List<Cluster> clusters = this.clusters.stream()
                .map(Cluster::clone)
                .collect(Collectors.toList());
        return new Solution(clusters, machineToPart, total);
    }

    //TODO PERFORMANCE PROBLEMS!!! use array
    public Double cost() {
        double onesInClusters = 0.0;
        int zeroInClusters = 0;
        for (Cluster cluster : clusters) {
            for (Integer machine : cluster.machines()) {
                for (Integer part : cluster.parts()) {
                    if (machineToPart[machine][part] == 1) {
                        onesInClusters++;
                    } else {
                        zeroInClusters++;
                    }
                }
            }
        }
        return onesInClusters / (total + zeroInClusters);
    }
}

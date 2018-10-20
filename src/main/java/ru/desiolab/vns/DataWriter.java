package ru.desiolab.vns;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.desiolab.vns.dto.Cluster;
import ru.desiolab.vns.dto.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class DataWriter {

    public static void write(String instanceName, Solution solution) throws IOException {
        HashMap<Integer, Integer> machinesToCluster = new HashMap<>();
        HashMap<Integer, Integer> partsToCluster = new HashMap<>();
        for (int clusterIndex = 0; clusterIndex < solution.clusters().size(); clusterIndex++) {
            Cluster cluster = solution.clusters().get(clusterIndex);
            for (Integer machine : cluster.machines()) {
                machinesToCluster.put(machine + 1, clusterIndex + 1);
            }
            for (Integer part : cluster.parts()) {
                partsToCluster.put(part + 1, clusterIndex + 1);
            }
        }
        StringBuilder machinesAnswer = new StringBuilder();
        for (Map.Entry<Integer, Integer> machineToCluster : machinesToCluster.entrySet()) {
            machinesAnswer.append(machineToCluster.getKey())
                    .append("_")
                    .append(machineToCluster.getValue())
                    .append(" ");
        }
        StringBuilder partsAnswer = new StringBuilder();
        for (Map.Entry<Integer, Integer> partToCluster : partsToCluster.entrySet()) {
            partsAnswer.append(partToCluster.getKey())
                    .append("_")
                    .append(partToCluster.getValue())
                    .append(" ");
        }
        Files.write(Path.of("C:\\Projects\\VariableNeighbourhoodSearch\\results\\" + instanceName + ".sol"),
                List.of(machinesAnswer.toString(), partsAnswer.toString()));
    }
}

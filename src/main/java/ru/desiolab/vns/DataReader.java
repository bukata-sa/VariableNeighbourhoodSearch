package ru.desiolab.vns;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.desiolab.vns.dto.TaskInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class DataReader {

    public static TaskInstance readFrom(String filepath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filepath));
            String[] instanceInfo = lines.get(0).split(" ");
            int machinesNumber = Integer.parseInt(instanceInfo[0]);
            int partsNumber = Integer.parseInt(instanceInfo[1]);
            int[][] machineToParts = new int[machinesNumber][partsNumber];
            for (int i = 1; i < lines.size(); i++) {
                List<String> currentLine = Arrays.asList(lines.get(i).split(" "));
                List<Integer> parts = currentLine.subList(1, currentLine.size()).stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                int machine = Integer.parseInt(currentLine.get(0));
                for (Integer part : parts) {
                    machineToParts[machine - 1][part - 1] = 1;
                }
            }
            return new TaskInstance(machinesNumber, partsNumber, machineToParts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

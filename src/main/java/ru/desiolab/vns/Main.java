package ru.desiolab.vns;

import ru.desiolab.vns.algorithm.GeneralVnsAlgorithm;
import ru.desiolab.vns.dto.Solution;
import ru.desiolab.vns.dto.TaskInstance;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String instanceName = "37x53";
        String path = "C:\\Projects\\VariableNeighbourhoodSearch\\src\\main\\resources\\" + instanceName + ".txt";
        TaskInstance taskInstance = DataReader.readFrom(path);

        GeneralVnsAlgorithm algorithm = new GeneralVnsAlgorithm(Solution.initial(taskInstance));
        long timeMillis = System.currentTimeMillis();
        Solution solution = algorithm.execute(2500);
        System.out.println("time: " + (System.currentTimeMillis() - timeMillis));
        System.out.println(solution.cost());
        DataWriter.write(instanceName, solution);
    }
}

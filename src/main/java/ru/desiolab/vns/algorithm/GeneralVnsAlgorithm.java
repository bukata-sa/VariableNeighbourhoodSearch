package ru.desiolab.vns.algorithm;


import ru.desiolab.vns.dto.Solution;

import java.util.List;

public class GeneralVnsAlgorithm {

    private final Solution initialSolution;
    private final List<Operation> shakingOperations;
    private final List<Operation> movementOperations;

    public GeneralVnsAlgorithm(Solution initialSolution) {
        this.initialSolution = initialSolution;
        this.shakingOperations = Shakings.ALL;
        this.movementOperations = Movements.ALL;
    }

    public Solution execute(int attempts) {
        Solution bestSolution = null;
        Double bestCost = 0.0;
        for (int i = 0; i < attempts; i++) {
            Solution solution = improve();
            Double cost = solution.cost();
            if (cost > bestCost) {
                bestCost = cost;
                bestSolution = solution;
                System.out.println("improve: " + bestCost);
            }
        }
        return bestSolution;
    }

    private Solution improve() {
        Solution bestSolution = initialSolution;
        Double bestCost = initialSolution.cost();
        while (true) {
            Solution currentSolution = shakeAndMove(bestSolution);
            Double cost = currentSolution.cost();
            if (cost > bestCost) {
                bestCost = currentSolution.cost();
                bestSolution = currentSolution;
            } else {
                break;
            }
        }
        return bestSolution;
    }

    private Solution shakeAndMove(Solution solution) {
        Double bestCost = solution.cost();
        Solution bestSolution = solution.copy();
        for (int i = 0; i < shakingOperations.size(); i++) {
            Operation shakingOperation = shakingOperations.get(i);
            Solution currentSolution = bestSolution.copy();
            currentSolution = shakingOperation.perform(currentSolution);
            currentSolution = move(currentSolution);
            Double cost = currentSolution.cost();
            if (cost > bestCost) {
                bestCost = cost;
                bestSolution = currentSolution;
                // next call in loop: i++, but we want to start with 0, so "i = -1"
                i = -1;
            }
        }
        return bestSolution;
    }

    private Solution move(Solution solution) {
        Solution bestSolution = solution.copy();
        Double bestCost = solution.cost();
        for (int i = 0; i < movementOperations.size(); i++) {
            Operation movementOperation = movementOperations.get(i);
            Solution currentSolution = bestSolution.copy();
            currentSolution = movementOperation.perform(currentSolution);
            Double currentCost = currentSolution.cost();
            if (currentCost > bestCost) {
                bestCost = currentCost;
                bestSolution = currentSolution;
                // next call in loop: i++, but we want to start with 0, so "i = -1"
                i = -1;
            }
        }
        return bestSolution;
    }
}

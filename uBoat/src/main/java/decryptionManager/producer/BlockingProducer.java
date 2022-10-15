package decryptionManager.producer;

import engine.dto.ConfigurationDTO;
import common.Combinations;
import common.Permutations;
import decryptionManager.Difficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BlockingProducer extends AbstractProducer {
    private ConfigurationDTO initialMachineCode;
    public BlockingProducer(BlockingQueue<ConfigurationDTO> queue, int missionSize, long total, Difficulty difficulty,
                            int totalReflectors, int totalRotors, ConfigurationDTO initialMachineCode) {
        super(queue, missionSize, total, difficulty, totalReflectors, totalRotors);
        this.initialMachineCode = initialMachineCode;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " is about to initialize the blocking queue.");
            if (difficulty.equals(Difficulty.EASY)) {
                easyDifficulty();
            } else if (difficulty.equals(Difficulty.MEDIUM)) {
                mediumDifficulty();
            } else if (difficulty.equals(Difficulty.HARD)) {
                hardDifficulty();
            } else if (difficulty.equals(Difficulty.IMPOSSIBLE)) {
                impossibleDifficulty();
            } else { // Log
                System.out.println("Exception: weird difficulty level entered.");
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " has ended, blocking queue has all values.");
        } catch (InterruptedException e) {
            System.out.println("Was interrupted!");
        }

    }
    private void easyDifficulty() throws InterruptedException {
        for (long incrementStartingPositions = 0; incrementStartingPositions < total; incrementStartingPositions++) {
            queue.put(initialMachineCode);
            incrementStartingPositions();
        }
    }

    private void incrementStartingPositions() {
        initialMachineCode = initialMachineCode.deepClone();
        for (long i = 0; i < missionSize; i++) {
            initialMachineCode.incrementStartingPositions();
        }
    }

    private void mediumDifficulty() throws InterruptedException {
        for (long incrementSelectedReflector = 0; incrementSelectedReflector < totalReflectors; incrementSelectedReflector++) {
            incrementSelectedReflector();
            for (long incrementStartingPositions = 0; incrementStartingPositions < total / totalReflectors; incrementStartingPositions++) {
                queue.put(initialMachineCode);
                incrementStartingPositions();
            }
        }
    }

    private void incrementSelectedReflector() {
        initialMachineCode = initialMachineCode.deepClone();
        initialMachineCode.incrementSelectedReflector(totalReflectors);
    }

    private void hardDifficulty() throws InterruptedException {
        int allRotorsPermutationsSize = Difficulty.factorial(initialMachineCode.getRotorsIDInorder().size());
        long limit = (total / totalReflectors) / allRotorsPermutationsSize;

        hardAndImpossibleDifficulties(allRotorsPermutationsSize, limit);
    }

    private void hardAndImpossibleDifficulties(int allRotorsPermutationsSize, long limit) throws InterruptedException {
        List<List<Integer>> allRotorsPermutations = Permutations.getAllPermutationsIterative(initialMachineCode.getRotorsIDInorder(), new ArrayList<>());
        for (int rotorsCurrentPermutationIndex = 0; rotorsCurrentPermutationIndex < allRotorsPermutationsSize; rotorsCurrentPermutationIndex++) {
            updateRotorsPermutation(allRotorsPermutations, rotorsCurrentPermutationIndex); //, logger);
            for (long incrementSelectedReflector = 0; incrementSelectedReflector < totalReflectors; incrementSelectedReflector++) {
                incrementSelectedReflector();
                for (long incrementStartingPositions = 0; incrementStartingPositions < limit; incrementStartingPositions++) {
                    queue.put(initialMachineCode);
                    incrementStartingPositions();
                }
            }
        }
    }

    private void updateRotorsPermutation(List<List<Integer>> allRotorsPermutations, int idx) { //, Logger logger) {
        /*logger.info("Changing permutation to " + allRotorsPermutations.get(idx));*/

        initialMachineCode = initialMachineCode.deepClone();
        initialMachineCode.setRotorsIDInorder(allRotorsPermutations.get(idx));
    }

    private void impossibleDifficulty() throws InterruptedException {
        List<List<Integer>> allRotorsCombinations = Combinations.generateAllCombinations(totalRotors, initialMachineCode.getRotorsIDInorder().size());
        int allRotorsCombinationsSize = allRotorsCombinations.size();
        int allRotorsPermutationsSize = Difficulty.factorial(initialMachineCode.getRotorsIDInorder().size());
        long limit = ((total / totalReflectors) / allRotorsPermutationsSize) / allRotorsCombinationsSize;

        for (int currentCombinationIndex = 0; currentCombinationIndex < allRotorsCombinationsSize; currentCombinationIndex++) {
            updateRotorsCombination(allRotorsCombinations, currentCombinationIndex);
            hardAndImpossibleDifficulties(allRotorsPermutationsSize, limit);
        }
    }

    private void updateRotorsCombination(List<List<Integer>> allRotorsCombinations, int idx) { //, Logger logger) {
        /*logger.info("Changing permutation to " + allRotorsPermutations.get(idx));*/

        initialMachineCode = initialMachineCode.deepClone();
        initialMachineCode.setRotorsIDInorder(allRotorsCombinations.get(idx));
    }
}
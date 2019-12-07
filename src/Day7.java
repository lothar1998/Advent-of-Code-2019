import java.io.*;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/input7.txt")));
        String[] loadedStrings = bufferedReader.readLine().split(",");

        System.out.println(findMaxOutput(loadedStrings));
    }

    private static int findMaxOutput(String[] loadedStrings){
        int max = Integer.MIN_VALUE;

        Integer[] set = {0, 1, 2, 3, 4};
        List<List<Integer>> permutations = generateAllPermutationsOfSet(set);

        for(List<Integer> permutation: permutations){
            Integer[] loadedData0 = Day5.parseCommandsToInteger(loadedStrings);
            Integer[] loadedData1 = Day5.parseCommandsToInteger(loadedStrings);
            Integer[] loadedData2 = Day5.parseCommandsToInteger(loadedStrings);
            Integer[] loadedData3 = Day5.parseCommandsToInteger(loadedStrings);
            Integer[] loadedData4 = Day5.parseCommandsToInteger(loadedStrings);
            int result = 0;

            result = executeOptCode(loadedData0, result, permutation.get(0));
            result = executeOptCode(loadedData1, result, permutation.get(1));
            result = executeOptCode(loadedData2, result, permutation.get(2));
            result = executeOptCode(loadedData3, result, permutation.get(3));
            result = executeOptCode(loadedData4, result, permutation.get(4));


            if(result > max)
                max = result;
        }
        return max;
    }

    private static List<List<Integer>> generateAllPermutationsOfSet(Integer[] set){
        List<List<Integer>> result = new ArrayList<>();

        generatePermutationsOfSet(set, set.length, result);

        return result;
    }

    private static void generatePermutationsOfSet(Integer[] set, int n, List<List<Integer>> result){
        if(n==1) {
            Integer[] temp = new Integer[set.length];
            System.arraycopy(set, 0, temp, 0, set.length);
            result.add(Arrays.asList(temp));
        }
        else {
            for(int i = 0; i < n - 1; i++){
                generatePermutationsOfSet(set, n - 1, result);
                if(n % 2 == 0)
                    swap(set, i, n - 1);
                else
                    swap(set, 0 , n - 1);
            }
            generatePermutationsOfSet(set, n - 1, result);
        }
    }

    private static void swap(Integer[] input, int a, int b) {
        Integer tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    private static int executeOptCode(Integer[] optCode, Integer input, Integer phase){
        int i = 0;
        int currentInstruction;
        int typeOfOperation;

        Integer[] modes;
        int arg1;
        int arg2;

        Integer[] inputArgs = {phase, input};
        int inputArgsIndex = 0;

        do{
            currentInstruction = optCode[i];
            Integer[] splittedCurrentInstruction = Day5.getDigitsFromNumber(currentInstruction);
            typeOfOperation = splittedCurrentInstruction[splittedCurrentInstruction.length - 1];

            switch (typeOfOperation){
                case 1: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 3);
                    arg1 = Day5.getValue(optCode, i + 1, modes[2]);
                    arg2 = Day5.getValue(optCode, i + 2, modes[1]);
                    Day5.saveValue(optCode, i + 3, arg1 + arg2);
                    i += 4;
                    break;
                case 2: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 3);
                    arg1 = Day5.getValue(optCode, i + 1, modes[2]);
                    arg2 = Day5.getValue(optCode, i + 2, modes[1]);
                    Day5.saveValue(optCode, i + 3, arg1 * arg2);
                    i += 4;
                    break;
                case 3: arg1 = inputArgs[inputArgsIndex++];
                    Day5.saveValue(optCode, i + 1, arg1);
                    i += 2;
                    break;
                case 4: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 1);
                    arg1 = Day5.getValue(optCode, i + 1, modes[0]);
                    return arg1;
                case 5: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 2);
                    arg1 = Day5.getValue(optCode, i + 1, modes[1]);
                    if (arg1 != 0)
                        i = Day5.getValue(optCode, i + 2, modes[0]);
                    else
                        i += 3;
                    break;
                case 6: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 2);
                    arg1 = Day5.getValue(optCode, i + 1, modes[1]);
                    if (arg1 == 0)
                        i = Day5.getValue(optCode, i + 2, modes[0]);
                    else
                        i += 3;
                    break;
                case 7: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 3);
                    arg1 = Day5.getValue(optCode, i + 1, modes[2]);
                    arg2 = Day5.getValue(optCode, i + 2, modes[1]);
                    if(arg1 < arg2)
                        Day5.saveValue(optCode, i + 3, 1);
                    else
                        Day5.saveValue(optCode, i + 3, 0);

                    i += 4;
                    break;
                case 8: modes = Day5.modeOfInstructionParameters(splittedCurrentInstruction, 3);
                    arg1 = Day5.getValue(optCode, i + 1, modes[2]);
                    arg2 = Day5.getValue(optCode, i + 2, modes[1]);
                    if(arg1 == arg2)
                        Day5.saveValue(optCode, i + 3, 1);
                    else
                        Day5.saveValue(optCode, i + 3, 0);

                    i += 4;
                    break;
                default:
                    i = optCode.length;
                    break;
            }
        }while (i < optCode.length);

        return 0;
    }


}

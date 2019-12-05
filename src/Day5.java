import java.io.*;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) throws IOException {
        File file = new File("data/input5.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] commands = bufferedReader.readLine().split(",");
        Integer[] optCode = parseCommandsToInteger(commands);
        executeOptCode(optCode);
    }

    private static void executeOptCode(Integer[] optCode){
        int i = 0;
        int currentInstruction;
        int typeOfOperation;

        Integer[] modes;
        int arg1;
        int arg2;

        Scanner in = new Scanner(System.in);

        do{
            currentInstruction = optCode[i];
            Integer[] splittedCurrentInstruction = getDigitsFromNumber(currentInstruction);
            typeOfOperation = splittedCurrentInstruction[splittedCurrentInstruction.length - 1];

            switch (typeOfOperation){
                case 1: modes = modeOfInstructionParameters(splittedCurrentInstruction, 3);
                        arg1 = getValue(optCode, i + 1, modes[2]);
                        arg2 = getValue(optCode, i + 2, modes[1]);
                        saveValue(optCode, i + 3, arg1 + arg2);
                        i += 4;
                    break;
                case 2: modes = modeOfInstructionParameters(splittedCurrentInstruction, 3);
                        arg1 = getValue(optCode, i + 1, modes[2]);
                        arg2 = getValue(optCode, i + 2, modes[1]);
                        saveValue(optCode, i + 3, arg1 * arg2);
                        i += 4;
                    break;
                case 3: arg1 = in.nextInt();
                        saveValue(optCode, i + 1, arg1);
                        i += 2;
                    break;
                case 4: modes = modeOfInstructionParameters(splittedCurrentInstruction, 1);
                        arg1 = getValue(optCode, i + 1, modes[0]);
                        System.out.println("result: " + arg1);
                        i += 2;
                    break;
                case 5: modes = modeOfInstructionParameters(splittedCurrentInstruction, 2);
                        arg1 = getValue(optCode, i + 1, modes[1]);
                        if (arg1 != 0)
                            i = getValue(optCode, i + 2, modes[0]);
                        else
                            i += 3;
                    break;
                case 6: modes = modeOfInstructionParameters(splittedCurrentInstruction, 2);
                        arg1 = getValue(optCode, i + 1, modes[1]);
                        if (arg1 == 0)
                            i = getValue(optCode, i + 2, modes[0]);
                        else
                            i += 3;
                    break;
                case 7: modes = modeOfInstructionParameters(splittedCurrentInstruction, 3);
                        arg1 = getValue(optCode, i + 1, modes[2]);
                        arg2 = getValue(optCode, i + 2, modes[1]);
                        if(arg1 < arg2)
                            saveValue(optCode, i + 3, 1);
                        else
                            saveValue(optCode, i + 3, 0);

                        i += 4;
                    break;
                case 8: modes = modeOfInstructionParameters(splittedCurrentInstruction, 3);
                        arg1 = getValue(optCode, i + 1, modes[2]);
                        arg2 = getValue(optCode, i + 2, modes[1]);
                        if(arg1 == arg2)
                            saveValue(optCode, i + 3, 1);
                        else
                            saveValue(optCode, i + 3, 0);

                        i += 4;
                    break;
                default:
                    i = optCode.length;
                    break;
            }
        }while (i < optCode.length);
    }

    private static Integer[] modeOfInstructionParameters(Integer[] splittedInstruction, int expectedNumberOfParameters){
        Integer[] modesOfInstructionParameters = new Integer[expectedNumberOfParameters];


        int numberOfParameters;

        if(splittedInstruction.length == 1)
            numberOfParameters = 0;
        else
            numberOfParameters = splittedInstruction.length - 2;

        int i = 0;

        if(expectedNumberOfParameters > numberOfParameters) {
            for (; i < expectedNumberOfParameters - numberOfParameters; i++)
                modesOfInstructionParameters[i] = 0;
        }

        for(int j = 0; i < expectedNumberOfParameters; i++, j++)
            modesOfInstructionParameters[i] = splittedInstruction[j];

        return modesOfInstructionParameters;
    }

    private static int getValue(Integer[] optCode, int index, int mode){
        if(mode == 0)
            return optCode[optCode[index]];
        else
            return optCode[index];
    }

    private static void saveValue(Integer[] optCode, int index, int value){
        optCode[optCode[index]] = value;
    }


    private static Integer[] parseCommandsToInteger(String[] commands){
        Integer[] simpleCommands = new Integer[commands.length];

        for(int i = 0; i < commands.length; i++)
            simpleCommands[i] = Integer.parseInt(commands[i]);

        return simpleCommands;
    }

    private static Integer[] getDigitsFromNumber(int number){
        int n = getNumbersOfDigitsInInteger(number);

        int temp;
        Integer[] result = new Integer[n];
        for(int i = n - 1; i >= 0; i--){
            temp = number / (int)Math.pow(10, i);
            result[n - 1 - i] = temp;
            number -= temp * (int)Math.pow(10, i);
        }

        return result;
    }

    private static int getNumbersOfDigitsInInteger(int number){
        int n = 0;
        do{
            if((number / (int)Math.pow(10, n)) <= 0)
                break;
            n++;
        }while (true);
        return n;
    }
}

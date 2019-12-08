import java.io.*;

public class Day8 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/input8.txt")));
        String loadedData = bufferedReader.readLine();

        Integer[] digits = splitIntoSeparatedDigits(loadedData);

        int width = 25;
        int height = 6;

        System.out.println("Part 1: " + computeAnswerPart1(digits, width, height));
        printPicture(computeAnswerPart2(digits, width, height), width, height);
    }

    private static int computeAnswerPart1(Integer[] digits, int width, int height){


        int minNumberOfZeros = Integer.MAX_VALUE;
        int begin = 0;
        int end = 0;
        int temp;

        for(int i = 0 ; i < digits.length; i+= width*height)
            if((temp = computeNumberOf(0, digits, i, i + width * height)) < minNumberOfZeros){
                minNumberOfZeros = temp;
                begin = i;
                end = i + width * height;
            }

        int numberOfOnes = computeNumberOf(1, digits, begin, end);
        int numberOfTwo = computeNumberOf(2, digits, begin, end);

        return numberOfOnes * numberOfTwo;
    }

    private static Integer[] splitIntoSeparatedDigits(String loadedData){
        Integer[] result = new Integer[loadedData.length()];

        for(int i = 0; i < loadedData.length(); i++){
            result[i] = Integer.parseInt(loadedData.substring(i,i+1));
        }

        return result;
    }

    private static int computeNumberOf(int valueToCompute, Integer[] array, int begin, int end){
        int number = 0;
        for(int i = begin; i < end; i++){
            if(array[i].equals(valueToCompute))
                number++;
        }

        return number;
    }

    private static Integer[] computeAnswerPart2(Integer[] digits, int width, int height){
        Integer[] picture = new Integer[width * height];
        for(int i = 0; i < width * height; i++){
            for(int j = i; j < digits.length; j += width * height)
                if(digits[j] == 2)
                    continue;
                else{
                    picture[i] = digits[j];
                    break;
                }
        }

        return picture;
    }

    private static void printPicture(Integer[] picture, int width, int height){
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++) {
                int value = picture[(w + (h * width))];
                if (value == 0)
                    System.out.print(" ");
                else
                    System.out.print("x");
            }
            System.out.println();
        }
    }
}

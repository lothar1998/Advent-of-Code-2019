import java.util.Arrays;

public class Day4 {
    public static void main(String[] args) {
        int begin = 264360;
        int end = 746325;

        System.out.println("Elf's passwords: " + countElfPasswords(begin, end));
        System.out.println("Elf's passwords2: " + countElfPasswords2(begin, end));
    }

    public static int countElfPasswords(int begin, int end){
        int i = 0;
        for(int k = begin; k <= end; k++)
            if(isAscendingOrder(k) && isTwoAdjacentDigitsAreTheSame(k))
                i++;

            return i;
    }

    public static int countElfPasswords2(int begin, int end){
        int i = 0;
        for(int k = begin; k <= end; k++)
            if(isAscendingOrder(k) && isTwoAdjacentDigitsAreTheSameButNotInGroup(k))
                i++;

        return i;
    }

    private static boolean isAscendingOrder(int number){
        Integer[] digits = getDigitsFromNumber(number);

        boolean result = true;

        for(int i = 0; i < digits.length - 1; i++)
            if(digits[i + 1] < digits[i]){
                result = false;
                break;
            }

        return result;
    }

    private static boolean isTwoAdjacentDigitsAreTheSame(int number){
        Integer[] digits = getDigitsFromNumber(number);

        boolean result = false;

        for(int i = 0; i < digits.length - 1; i++)
            if(digits[i].equals(digits[i+1])){
                result = true;
                break;
            }

        return result;
    }

    private static boolean isTwoAdjacentDigitsAreTheSameButNotInGroup(int number){
        Integer[] digits = getDigitsFromNumber(number);

        boolean result = false;

        Integer[] arrayOfDigits = new Integer[10];
        Arrays.fill(arrayOfDigits, 0);

        for(Integer i:digits)
            ++arrayOfDigits[i];

        for(int i = 0; i < arrayOfDigits.length; i++)
            if(arrayOfDigits[i] == 2){
                int firstIndex = 0;
                int secondIndex = 0;
                int j;

                for(j = 0; j < digits.length; j++)
                    if(digits[j] == i) {
                        firstIndex = j;
                        break;
                    }

                j++;

                for(; j < digits.length; j++)
                    if(digits[j] == i) {
                        secondIndex = j;
                        break;
                    }

                if(secondIndex-firstIndex == 1)
                    result = true;
            }

        return result;
    }

    private static Integer[] getDigitsFromNumber(int number){

        int n = 0;
        do{
            if((number / (int)Math.pow(10, n)) <= 0)
                break;
            n++;
        }while (true);

        int temp;
        Integer[] result = new Integer[n];
        for(int i = n - 1; i >= 0; i--){
            temp = number / (int)Math.pow(10, i);
            result[n - 1 - i] = temp;
            number -= temp * (int)Math.pow(10, i);
        }

        return result;
    }
}

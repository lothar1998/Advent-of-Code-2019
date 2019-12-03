import java.io.*;

public class Day2 {
    public static void main(String[] args) throws IOException {
        File file = new File("data/input2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String textRead = reader.readLine();
        String[] splittedText = textRead.split(",");
        Integer[] orders = new Integer[splittedText.length];

        restoreBaseCode(orders, splittedText);
        System.out.println("answer: " + runIntCodeProgram(12, 2, orders));

        findInitValuesOfIntCodeProgram(orders, splittedText);
        
        
    }

    private static void findInitValuesOfIntCodeProgram(Integer[] inputCode, String[] baseCode){
        int n, v = 0;

        boolean interrupt = false;

        for(n = 0; n < 100; n++) {
            for (v = 0; v < 100; v++) {
                restoreBaseCode(inputCode, baseCode);

                if (runIntCodeProgram(n, v, inputCode) == 19690720) {
                    interrupt = true;
                    break;
                }
            }
            if (interrupt)
                break;
        }

        System.out.println("n: " + n + ", v: " + v + ", answer = " + (100 * n + v));
    }

    private static int runIntCodeProgram(int n, int v, Integer[] inputCode){
        inputCode[1] = n;
        inputCode[2] = v;

        int i = 0;

        while(inputCode[i] != 99){
            int order = inputCode[i];
            int arg1 = inputCode[inputCode[i + 1]];
            int arg2 = inputCode[inputCode[i + 2]];
            int placeToStoreResult = inputCode[i + 3];

            if(order == 1){
                inputCode[placeToStoreResult] = arg1 + arg2;
            }
            else if (order == 2){
                inputCode[placeToStoreResult] = arg1 * arg2;
            }

            i+=4;
        }

        return inputCode[0];
    }
    
    private static void restoreBaseCode(Integer[] inputCode, String[] baseCode){
        for(int i = 0; i < baseCode.length; i++){
            inputCode[i] = Integer.parseInt(baseCode[i]);
        }
    }
}

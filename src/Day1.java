import java.io.*;

public class Day1 {
    public static void main(String[] args) throws IOException {
        File file = new File("data/input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String text;
        int sum = 0;
        int sum2 = 0;
        while((text = br.readLine()) != null){
            sum += requiredFuel1(Integer.parseInt(text));
            sum2 += requiredFuel2(Integer.parseInt(text));
        }

        System.out.println("v1: " + sum);
        System.out.println("v2: " + sum2);
    }

    private static int requiredFuel1(int mass){
        return (int)Math.floor((double)mass/3) - 2;
    }

    private static int requiredFuel2(int start){
        int current = (int)Math.floor((double)start/3) - 2;

        if(current <= 0)
            return 0;

        return  current + requiredFuel2(current);
    }
}

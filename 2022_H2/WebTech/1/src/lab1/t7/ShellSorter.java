package lab1.t7;


import java.util.Arrays;
import java.util.Scanner;

public class ShellSorter {

    public static void main(String[] args) {
        System.out.println("Enter N:");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        double[] inputArray = new double[N];

        for (int i = 0; i < N; i++) {
            System.out.println("Enter " + i);
            inputArray[i] = sc.nextDouble();
        }
        sortArray(inputArray);
        System.out.println(Arrays.toString(inputArray));
    }

    private static void sortArray(double[] array) {
        int i = 0;
        while (i < array.length - 1) {
            if (array[i] <= array[i + 1]) {
                i++;
            }
            else {
                double tmp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = tmp;
                if (i > 0){
                    i--;
                }
            }
        }
    }

}

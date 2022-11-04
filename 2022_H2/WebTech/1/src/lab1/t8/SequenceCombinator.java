package lab1.t8;

import java.util.Scanner;

public class SequenceCombinator {

    public static void main(String[] args) {
        System.out.println("Enter N:");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        double[] inputArray = new double[N];
        for (int i = 0; i < N; i++) {
            System.out.println("Enter " + i);
            inputArray[i] = sc.nextDouble();
        }

        System.out.println("Enter M:");
        int M = sc.nextInt();
        double[] inputArray2 = new double[M];
        for (int i = 0; i < M; i++) {
            System.out.println("Enter " + i);
            inputArray2[i] = sc.nextDouble();
        }
        makeNewSequence(inputArray, inputArray2);
    }

    private static void makeNewSequence(double[] a, double[] b) {
        int i = 0;
        for (double el : b) {
            while (i < a.length) {
                if (el <= a[i]) {
                    break;
                }
                System.out.print(a[i] + " ");
                i++;
            }
            System.out.print(el + "from the sec seq ");
        }

        while (i < a.length) {
            System.out.print( a[i] + " ");
            i++;
        }
    }
}

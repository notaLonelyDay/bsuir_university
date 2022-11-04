package lab1.t5;


import java.util.Scanner;

import static java.lang.Math.max;

public class IncreasingSequence {

    public static void main(String[] args) {
        System.out.println("Enter N:");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] inputArray = new int[N];
        for (int i = 0; i < N; i++) {
            System.out.println("Enter " + i);
            inputArray[i] = sc.nextInt();
        }
        System.out.println("" + calculateCount(inputArray, N));
    }


    private static int calculateCount(int[] inputArray, int N) {
        int[] arr = new int[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            arr[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (inputArray[j] < inputArray[i]) {
                    arr[i] = max(arr[i], 1 + arr[j]);
                }
            }
        }

        int res = arr[0];
        for (int i = 0; i < inputArray.length; i++) {
            res = max(res, arr[i]);
        }
        return inputArray.length - res;
    }

}

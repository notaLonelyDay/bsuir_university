package lab1.t3;

import java.util.Scanner;

public class FunctionCalculator {

    private static void outputTable(double a, double b, double h) {
        double f;

        System.out.println("i          f(i)");
            for (double i = a; i < b; i = i + h) {
                f = Math.tan(i);
                System.out.printf("%.2f  :  %.5f%n", i, f);
            }
            f = Math.tan(b);
        System.out.printf("%.2f  :  %.5f%n", b, f);
    }

    public static void main(String[] args) {
        System.out.println("Enter a:");
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        System.out.println("Enter b:");
        double b = sc.nextDouble();
        System.out.println("Enter h:");
        double h = sc.nextDouble();

        if (a > b) {
            System.out.println("invalid input");
        }
        else {
            outputTable(a, b, h);
        }
    }
}

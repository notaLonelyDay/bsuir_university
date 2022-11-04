package lab1.t1;

import java.util.Scanner;

public class ExpressionCalculator {

    private static double calculateExpression(final double x, final double y){
        return (1 + Math.pow(Math.sin(x + y), 2)) /
                 (2 + Math.abs(x - ((2 * x) / (1 + x * x * y * y)))) + x;
    }


    public static void main(String[] args) {
        System.out.println("Enter x:");
        Scanner sc = new Scanner(System.in);
        double x = sc.nextDouble();
        System.out.println("Enter y:");
        double y = sc.nextDouble();

        System.out.println("Result is " + calculateExpression(x, y));
    }
}

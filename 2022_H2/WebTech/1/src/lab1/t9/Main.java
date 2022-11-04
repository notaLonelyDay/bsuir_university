package lab1.t9;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int MAX_WEIGHT = 800;

    public static void main (String[] args) {
        System.out.println("enter amount of balls:");
        Scanner sc = new Scanner(System.in);
        int countBalls = sc.nextInt();

        Random random = new Random();
        Basket basket = new Basket();

        for (int i = 0; i < countBalls; i++) {
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            basket.addBall(new Ball(weight, BallColors.getRandomColor()));
        }

        for (int i = 0; i < countBalls; i++) {
            System.out.println(basket.listOfBalls.get(i).getColor() + " " + basket.listOfBalls.get(i).getWeight());
        }

        System.out.println("count of blue balls " + basket.countBlueBalls());
        System.out.println("total weight of balls " + basket.totalWeightBalls());
    }
}

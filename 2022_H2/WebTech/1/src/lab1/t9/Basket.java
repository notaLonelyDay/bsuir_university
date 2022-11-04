package lab1.t9;

import java.util.ArrayList;
import java.util.List;


public class Basket {
    public List<Ball> listOfBalls = new ArrayList<Ball>();

    public void addBall(Ball ball) {
        listOfBalls.add(ball);
    }

    public int countBlueBalls() {
        int result = 0;
        for (Ball ball:
             listOfBalls) {
            if (ball.getColor() == BallColors.BLUE) {
                result++;
            }
        }
        return result;
    }

    public double totalWeightBalls(){
        double result = 0;

        for (Ball ball:
                listOfBalls) {
            result = result + ball.getWeight();
            }
        return result;
    }

}

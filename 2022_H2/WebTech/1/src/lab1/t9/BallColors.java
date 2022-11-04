package lab1.t9;

import java.util.Random;

public enum BallColors {
        BLACK,
        BLUE,
        RED;

        public static BallColors getRandomColor(){
                Random random = new Random();

                return values()[random.nextInt(values().length)];
        }
}

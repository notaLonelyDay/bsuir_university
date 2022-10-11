package lab1.t9;

public record Ball(double weight, BallColors color) {

    public double getWeight() {
        return weight;
    }

    public BallColors getColor() {
        return color;
    }

}

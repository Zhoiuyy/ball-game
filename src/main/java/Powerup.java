import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.Random;

public class Powerup extends Sprite {
    private Vector2 velocity;
    private static final double SPEED = 3;
    private Vector2 direction;
    private Point destination;

    public Powerup(Point point, Point destination) {
        super(point, "res/powerup.png");
        this.destination = destination;
        //double m = Math.sqrt(Math.pow(point.x-destination.x,2)+Math.pow(point.y-destination.y,2));
        double m = magnitude(point,destination);
        this.direction = new Vector2(point.x-destination.x, point.y-destination.y).div(m);
        this.velocity = direction.mul(SPEED);
    }
    public double magnitude(Point p, Point d){
        double m = Math.sqrt(Math.pow(p.x-d.x,2)+Math.pow(p.y-d.y,2));
        return m;
    }

    @Override
    public void update() {
        super.move(velocity);
        if (magnitude(super.getRect().centre(),destination)<5){
        //if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()){

            Random rand = new Random();
            destination = new Point(Window.getWidth() * rand.nextDouble(),
                    Window.getHeight()* rand.nextDouble());
            double m = magnitude(super.getRect().centre(),destination);
            this.direction = new Vector2(super.getRect().centre().x-destination.x, super.getRect().centre().y-destination.y).div(m);
            this.velocity = direction.mul(SPEED);
        }
        super.draw();

    }
}

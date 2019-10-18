/**
 * this class can draw a powerup
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.Random;

public class Powerup extends Sprite {
    private Vector2 velocity;
    private static final double SPEED = 3;
    private Vector2 direction;
    private Point destination;

    /**
     *
     * @param point the point to draw the powerup
     * @param destination the destination that the powerup will move to
     */
    public Powerup(Point point, Point destination) {
        super(point, "res/powerup.png");
        this.destination = destination;
        double m = magnitude(point,destination);
        this.direction = direction(point,destination,m);
        this.velocity = direction.mul(SPEED);
    }



    private double magnitude(Point p, Point d){
        double m = Math.sqrt(Math.pow(p.x-d.x,2)+Math.pow(p.y-d.y,2));
        return m;
    }


    private Vector2 direction(Point point, Point destination, double m){
        double xDiff = destination.x-point.x;
        double yDiff = destination.y-point.y;
        Vector2 direction = new Vector2().add(Vector2.right.mul(xDiff)).add(Vector2.down.mul(yDiff)).div(m);
        return direction;
    }

    /**
     * update the powerup
     */
    @Override
    public void update() {
        super.move(velocity);

        if (magnitude(super.getRect().centre(),destination)<5){
            Random rand = new Random();
            destination = new Point(Window.getWidth() * rand.nextDouble(),
                    Window.getHeight()* rand.nextDouble());
            double m = magnitude(super.getRect().centre(),destination);
            this.direction =  direction(super.getRect().centre(),destination,m);
            this.velocity = direction.mul(SPEED);
        }

        super.draw();
    }

}

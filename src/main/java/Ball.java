/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Side;
import bagel.util.Rectangle;


public class Ball extends Sprite {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;
    /*
    public Ball(Point point,String direction){
        super(point, "res/ball.png");
        if(direction.equals("left")) {
            velocity = Vector2.left.add(Vector2.up).mul(1);
        }else if(direction.equals("right")){
            velocity = Vector2.right.add(Vector2.up).mul(1);
        }
    }

     */
    public Ball(Point point, Vector2 direction) {
        super(point, "res/ball.png");
        velocity = direction.mul(SPEED);
    }

    public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }

    public void bounce(Peg peg){
        Side side = peg.getRect().intersectedAt(peg.getRect().centre(),velocity);
        if(side == Side.TOP || side == Side.BOTTOM){
            this.velocity = new Vector2(velocity.x, -velocity.y);
        }
        if(side == Side.LEFT || side == Side.RIGHT){
            this.velocity = new Vector2(-velocity.x, velocity.y);
        }
    }
}

/**
 * this class can draw a ball
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Side;

public class Ball extends Sprite {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;

    /**
     *
     * @param point the point to draw a ball
     * @param direction the direction that ball will move to
     */
    public Ball(Point point, Vector2 direction) {
        super(point, "res/ball.png");
        velocity = direction.mul(SPEED);
    }

    /**
     *
     * @param ball instance a new ball in the ball's position
     * @param imageSrc the picture
     */
    public Ball(Ball ball, String imageSrc) {
        super(ball.getRect().centre(), imageSrc);
        this.velocity = ball.velocity;
    }

    /**
     * check if the ball out of screen
     * @return if the ball out of screen
     */
    public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    /**
     * update the ball
     */
    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }

    /**
     * check the side of intersection, then change the ball's velocity according to the side
     * @param peg the ball strike this peg
     */
    public void bounce(Peg peg){
        Side side = peg.getRect().intersectedAt(peg.getRect().topLeft(),velocity);
        //Side side = peg.getRect().intersectedAt(super.getRect().centre(), velocity);

        if(side == Side.TOP || side == Side.BOTTOM){
            this.velocity = new Vector2(velocity.x, -velocity.y);
        }
        if(side == Side.LEFT || side == Side.RIGHT){
            this.velocity = new Vector2(-velocity.x, velocity.y);
        }
    }
}

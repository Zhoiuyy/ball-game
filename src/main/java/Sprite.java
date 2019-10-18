/**
 *  abstract class
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Sprite {
    private Image image;
    private Rectangle rect;

    /**
     *
     * @param point the point to draw
     * @param imageSrc picture
     */
    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
    }

    /**
     * get rectangle
     * @return the rectangle of the object
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * check if intersect with other
     * @param other other sprite
     * @return if intersect with other
     */
    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    /**
     *
     * @param dx
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**
     * get if it's can be destroyed
     * @return true
     */
    public boolean destroyable(){
        return true;
    }

    /**
     * draw it
     */
    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    /**
     * abstract method of update
     */
    public abstract void update();

}

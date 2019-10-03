/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Sprite {
    private Image image;
    private Rectangle rect;
    private boolean exist;

    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
        exist = true;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }



    public void destroy(){
        this.exist = false;
    }

    public void destroy(boolean exist){
        this.exist = exist;
    }
    public boolean getExist(){
        return exist;
    }

    public void draw() {
        if(exist) {
            image.draw(rect.centre().x, rect.centre().y);
        }
    }
    public abstract void update();

}

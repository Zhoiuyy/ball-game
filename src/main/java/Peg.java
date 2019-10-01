/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.util.Point;

public class Peg extends Sprite {
    public Peg(Point point, String imageSrc) {
        super(point, "res/" + imageSrc + ".png");
    }

    @Override
    public void update() {
        super.draw();
    }


}

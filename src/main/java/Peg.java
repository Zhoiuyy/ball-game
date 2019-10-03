/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.util.Point;

public class Peg extends Sprite {
    private String shape = "normal";

    public Peg(Point point, String imageSrc) {
        super(point, "res/" + imageSrc + ".png");
        if(imageSrc.contains("v")){
            shape = "vertical";
        }else if(imageSrc.contains("h")){
            shape = "horizontal";
        }
    }

    public String getShape(){
        return shape;
    }

    @Override
    public void update() {
            super.draw();
    }


}

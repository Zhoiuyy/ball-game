/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.util.Point;

public class Peg extends Sprite {
    private String shape ;
    //private String colour;

    public Peg(Point point, String imageSrc) {
        super(point, "res/" + imageSrc + ".png");
        shape = imageSrc.contains("v") ? "vertical" : ((imageSrc.contains("h")) ? "horizontal" : "normal");
        //colour = "blue";
    }

    public String getShape(){
        return shape;
    }
/*
    public String getColour(){
        return colour;
    }

    public void setColour(String colour){
        this.colour = colour;
    }

 */

    @Override
    public void update() {
            super.draw();
    }


}

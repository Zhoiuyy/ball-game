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
    }

    public String getShape(){
        return shape;
    }

    public Peg changeColour(String colour){
        Point p = super.getRect().centre();
        if(colour.equals("blue")) {
            if (shape.equals("normal")) {
                return new Peg(p, "peg");
            } else {
                return new Peg(p, shape + "-peg");
            }
        }else if(colour.equals("green")) {
            if (shape.equals("normal")) {
                return new GreenPeg(p);
            } else {
               return new GreenPeg(p, "green-" + shape + "-peg");
            }
        }else if(colour.equals("red")){
            if (shape.equals("normal")) {
                return new RedPeg(p);
            } else {
                return new RedPeg(p, "red-" + shape + "-peg");
            }
        }
        return this;
    }


    @Override
    public void update() {
            super.draw();
    }


}

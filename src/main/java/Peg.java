/**
 * to get a peg
 */

import bagel.util.Point;

public class Peg extends Sprite {
    private String shape ;

    /**
     *
     * @param point the point to draw peg
     * @param imageSrc the picture
     */
    public Peg(Point point, String imageSrc) {
        super(point, "res/" + imageSrc + ".png");
        shape = imageSrc.contains("v") ? "vertical" : ((imageSrc.contains("h")) ? "horizontal" : "normal");
    }

    /**
     * change the colour of the peg
     * @param colour the colour that peg want change to
     * @return the peg after changing colour
     */
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

    /**
     * draw the peg
     */
    @Override
    public void update() {
            super.draw();
    }


}

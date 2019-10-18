/**
 * this class can instance a red peg
 */

import bagel.util.Point;

public class RedPeg extends Peg {

    /**
     *
     * @param point the point to draw
     * @param imageSrc picture
     */
    public RedPeg(Point point, String imageSrc){
        super(point, imageSrc);
    }


    /**
     *
     * @param point the point to draw
     */
    public RedPeg(Point point){
        super(point,"red-peg");
    }

}

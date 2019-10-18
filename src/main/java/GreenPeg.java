/**
 * this class to instance a green peg
 */

import bagel.util.Point;

public class GreenPeg extends Peg {
    /**
     *
     * @param point the point to draw green peg
     * @param imageSrc the picture
     */
    public GreenPeg(Point point, String imageSrc){
        super(point, imageSrc);
    }

    /**
     *
     * @param point the point to draw green peg
     */
    public GreenPeg(Point point){
        super(point,"green-peg");
    }


}

/**
 * this class to instance a grey peg
 */

import bagel.util.Point;

public class GreyPeg extends Peg {

    /**
     *
     * @param point the point to draw grey peg
     * @param imageSrc the picture
     */
    public GreyPeg(Point point, String imageSrc){
        super(point, imageSrc);
    }

    /**
     * make the grey peg indestructible
     * @return false as grey peg can not be destroyed
     */
    @Override
    public boolean destroyable(){
        super.update();
        return false;
    }

}

import bagel.util.Point;

public class GreyPeg extends Peg {

    public GreyPeg(Point point, String imageSrc){
        super(point, imageSrc);
        //super.setColour("grey");
    }



    @Override
    public void destroy() {
        super.destroy(true);
        super.update();
    }
}

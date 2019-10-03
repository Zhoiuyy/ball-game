import bagel.util.Point;

public class GreyPeg extends Peg {

    public GreyPeg(Point point, String imageSrc){
        super(point, imageSrc);
    }



    @Override
    public void destroy() {
        super.destroy(true);
        super.update();
    }
}

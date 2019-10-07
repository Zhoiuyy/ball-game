import bagel.util.Point;

public class RedPeg extends Peg {


    public RedPeg(Point point, String imageSrc){
        super(point, imageSrc);
        //super.setColour("red");
    }

    public RedPeg(Point point){
        super(point,"red-peg");
       // super.setColour("red");
    }
    /*
    public String getColour(){
        return colour;
    }

     */

/*
    @Override
    public void destroy() {
        super.destroy();
    }

 */

}

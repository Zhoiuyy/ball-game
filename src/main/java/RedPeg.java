import bagel.util.Point;

public class RedPeg extends Peg {
    private Integer redPegNumber;
    /*
    public RedPeg(Peg peg){
            super(peg.getRect().centre(), "red-" + peg.getShape() + "-peg");

    }
     */

    public RedPeg(Point point, String imageSrc){
        super(point, imageSrc);
        if(redPegNumber == null ){
            redPegNumber = 0;
        }else{
            redPegNumber +=1;
        }
    }

    public RedPeg(Point point){
        super(point,"red-peg");
        if(redPegNumber == null ){
            redPegNumber = 0;
        }else{
            redPegNumber +=1;
        }
    }

    public Integer getRedPegNumber(){
        return redPegNumber;
    }

    @Override
    public void destroy() {
        super.destroy();
        redPegNumber = -1;
    }

}


import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.math.*;

public class Board {
    private ArrayList<Peg> pegs = new ArrayList<Peg>();
    private Ball ball;
    private int redPegNumber;
    private int shots;
    //private static final Point BALL_POSITION = new Point(512, 32);
    private static final Point BALL_POSITION = new Point(Window.getWidth()/2, 32);
    private static final Point BUCKET_POSITION = new Point(Window.getWidth()/2, Window.getHeight()-24);

    public Board(String file){
       readCsv(file);
       Random rand = new Random();
       int size = pegs.size();
       redPegNumber =  Math.round(size/5);
       for(int i = 0; i <  redPegNumber; ++i){
           int index = rand.nextInt(size);
           if(pegs.get(index).getClass() != Peg.class){
               i--;
               continue;
           }
           Point p = pegs.get(index).getRect().centre();
           String shape =  pegs.get(index).getShape();
           pegs.remove(index);
           if(shape == "normal") {
               pegs.add(index, new RedPeg(p));
           }else{
               pegs.add(index, new RedPeg(p, "red-" + shape + "-peg"));
           }
       }
    }

    public int getRedPegNumber(){
        return redPegNumber;
    }

    public void readCsv(String file){
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] columns = text.replace("blue_", "").replace("_","-").split(",");
                Point p = new Point(Double.parseDouble(columns[1]),Double.parseDouble(columns[2]));
                if(columns[0].contains("grey")){
                    pegs.add(new GreyPeg(p, columns[0]));
                }else{
                    pegs.add(new Peg(p, columns[0]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Input input) {

        // Check all non-deleted pegs for intersection with the ball
        for (int i = 0; i < pegs.size(); ++i) {
            if (pegs.get(i).getExist()) {
                if (ball != null && ball.intersects(pegs.get(i))) {
                    pegs.get(i).destroy();
                    if(pegs.get(i).getClass() == RedPeg.class){
                        redPegNumber--;
                    }
                    /*
                    if(pegs.get(i).getColour() == "red"){
                        redPegNumber --;
                    }

                     */
                    ball.bounce(pegs.get(i));
                } else {
                    pegs.get(i).update();
                }
            }
        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && ball == null) {
            ball = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION));
        }

        if (ball != null) {
            ball.update();

            // Delete the ball when it leaves the screen
            if (ball.outOfScreen()) {
                ball = null;
            }
        }
    }


}

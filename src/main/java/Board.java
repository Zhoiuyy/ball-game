
import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.io.*;
public class Board {
    //private Peg[] pegs = new Peg[];
    private ArrayList<Peg> pegs = new ArrayList<Peg>();
    private Ball ball;
    private static final Point BALL_POSITION = new Point(512, 32);
    private int shots;
    private static final Point BUCKET_POSITION = new Point(512, 744);


    public Board(String file){
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] columns = text.replace("blue_", "").replaceAll("_","-").split(",");
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
            if (pegs.get(i) != null) {
                if (ball != null && ball.intersects(pegs.get(i))) {
                    pegs.get(i).destroy();
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

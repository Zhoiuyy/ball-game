
import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.math.*;

public class Board {
    private ArrayList<Peg> pegs = new ArrayList<Peg>();
    //private Ball ball;
    private ArrayList<Ball> balls = new ArrayList<Ball>();

    private int redPegNumber;
    private int greenPegNumber;
    public static int shots;
    private static final Point BALL_POSITION = new Point(Window.getWidth() / 2, 32);
    private static final Point BUCKET_POSITION = new Point(Window.getWidth() / 2, Window.getHeight() - 24);

    public Board(String file) {
        readCsv(file);
        greenPegNumber = 0;
        redPegNumber = Math.round(pegs.size() / 5);
        for (int i = 0; i < redPegNumber; ++i) {
            int index = randomBluePeg();
            pegs.set(index,pegs.get(index).changeColour("red"));
        }
    }

    public int randomBluePeg() {
        Random rand = new Random();
        int size = pegs.size();
        int index = rand.nextInt(size);
        while (pegs.get(index).getClass() != Peg.class || pegs.get(index).getExist() == false) {
            index = rand.nextInt(size);
        }
        return index;
    }

    public int getRedPegNumber() {
        return redPegNumber;
    }

    public void setShots(int shots) {
        Board.shots = shots;
    }

    public boolean outOfShots(Input input) {
        if (input.wasPressed(MouseButtons.LEFT) &&  balls.isEmpty() && shots < 0) {
            return true;
        }
        return false;
    }

    public void getGreenPeg() {
        if ( balls.isEmpty() && greenPegNumber < 1) {
            int index = randomBluePeg();
            pegs.set(index,pegs.get(index).changeColour("green"));
            greenPegNumber = 1;
        }
    }

    public void greenReturnBlue() {
        for (int i = 0; i < pegs.size(); ++i) {
            if(pegs.get(i).getClass().equals(GreenPeg.class) && pegs.get(i).getExist()){
                pegs.set(i,pegs.get(i).changeColour("blue"));
            }
        }
        greenPegNumber = 0;
    }





    public void readCsv(String file){
        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] columns = text.split(",");
                String[] image = columns[0].split("_");
                columns[0] = (image.length == 3) ? (image[0] + "-" + image[2] + "-" + image[1]) : columns[0];
                columns[0] = columns[0].replace("_","-").replace("blue-", "");
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
        getGreenPeg();
        for (int i = 0; i < pegs.size(); ++i) {
            if(balls.isEmpty()){
                pegs.get(i).update();
            }else{
                for(int j = 0; j < balls.size(); ++j) {
                    if (pegs.get(i).getExist() && balls.get(j).intersects(pegs.get(i))) {
                        pegs.get(i).destroy();
                        if (pegs.get(i).getClass().equals(RedPeg.class)) {
                            redPegNumber--;
                        }
                        if (pegs.get(i).getClass().equals(GreenPeg.class)) {
                            balls.add(new Ball(pegs.get(i).getRect().centre(), Vector2.left.add(Vector2.up)));
                            balls.add(new Ball(pegs.get(i).getRect().centre(), Vector2.right.add(Vector2.up)));
                        }
                        balls.get(j).bounce(pegs.get(i));
                    } else {
                        pegs.get(i).update();
                    }
                }
            }
        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && balls.isEmpty()) {
            if(shots > 0) {
                //ball = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION));
                balls.add(new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION)));
            }
            Board.shots--;
        }

        if (!balls.isEmpty()) {
            for (int i = 0; i < balls.size(); ++i) {
                balls.get(i).update();
                if (balls.get(i).outOfScreen()) {
                    balls.remove(i);
                }
            }
            // Delete the ball when it leaves the screen , and green peg become blue again
            if (balls.isEmpty()) {
                greenReturnBlue();
            }
        }
    }


}

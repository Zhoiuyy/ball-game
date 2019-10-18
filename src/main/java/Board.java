
/**
 * the board include all kinds of sprite
 * @author Ying Zhou
 */

import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.io.*;
import java.util.Random;



public class Board {
    private ArrayList<Peg> pegs = new ArrayList<Peg>();
    private Powerup powerup;
    private Bucket bucket;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private boolean isContactBucket;

    private int redPegNumber;
    private int greenPegNumber;
    private boolean turnInitialized;
    private static final Point BALL_POSITION = new Point(Window.getWidth() / 2, 32);
    private static final Point BUCKET_POSITION = new Point(Window.getWidth() / 2, Window.getHeight() - 24);
    /**
     * number of shots
     */
    public static int shots;

    /**
     * initialize a board
     * @param file the file is going to be read
     */
    public Board(String file) {
        readCsv(file);
        greenPegNumber = 0;
        isContactBucket = false;
        turnInitialized = false;
        bucket = new Bucket(BUCKET_POSITION,Vector2.left);
        redPegNumber = Math.round(pegs.size() / 5);
        for (int i = 0; i < redPegNumber; ++i) {
            int index = randomBluePeg();
            pegs.set(index,pegs.get(index).changeColour("red"));
        }
    }


    /**
     * get the number of red pegs
     * @return  the the number of red pegs
     */
    public int getRedPegNumber() {
        return redPegNumber;
    }

    /**
     *
     * set the shot number
     * @param shots the shot number will be set to this number
     */
    public void setShots(int shots) {
        Board.shots = shots;
    }

    /**
     * check if there's shots left
     * @param input
     * @return if no more shots left
     */
    public boolean outOfShots(Input input) {
        if (input.wasPressed(MouseButtons.LEFT) &&  balls.isEmpty() && shots < 0) {
            return true;
        }
        return false;
    }

    // to get a random blue peg from the existing pegs , and return its index
    private int randomBluePeg() {
        Random rand = new Random();
        int size = pegs.size();
        int index = rand.nextInt(size);
        while (pegs.get(index).getClass() != Peg.class){ // || pegs.get(index).getExist() == false) {
            index = rand.nextInt(size);
        }
        return index;
    }

    // instance a green peg
    private void getGreenPeg() {
        if ( greenPegNumber < 1) {
            int index = randomBluePeg();
            pegs.set(index,pegs.get(index).changeColour("green"));
            greenPegNumber = 1;
        }
    }

    // check the presence of green peg , if yes , make that green peg back to blue
    private void greenReturnBlue() {
        for (int i = 0; i < pegs.size(); ++i) {
            if(pegs.get(i).getClass().equals(GreenPeg.class)){
                pegs.set(i,pegs.get(i).changeColour("blue"));
            }
        }
        greenPegNumber = 0;
    }

    // initialize a  power up
    private void getPowerup(){
        Random rand = new Random();
        int chance = rand.nextInt(10);
        if (powerup == null && chance == 0) {
            Point p = new Point(Window.getWidth() * rand.nextDouble(),
                    Window.getHeight()* rand.nextDouble());
            Point d = new Point(Window.getWidth() * rand.nextDouble(),
                    Window.getHeight()* rand.nextDouble());
            powerup = new Powerup(p,d);
        }
    }

    // return true if a new trun start
    private boolean isNewTurn(){
        if (balls.isEmpty() && turnInitialized == false ){
            return true;
        }
        return false;
   }

    //check if the peg is green or red.
    //if green, instance two balls, if red, redPegNumber will decrease.
    private void checkPeg(Peg peg){
       if (peg.getClass().equals(GreenPeg.class)) {
           balls.add(new Ball(peg.getRect().centre(), Vector2.left.add(Vector2.up)));
           balls.add(new Ball(peg.getRect().centre(), Vector2.right.add(Vector2.up)));
       }
       if (peg.getClass().equals(RedPeg.class)) {
           redPegNumber --;
       }
   }

    // destroy pegs within 70 pixels of the point
    private void fireBounce(Point point){
        for(int i = 0; i < pegs.size(); ++i){
            Point d = pegs.get(i).getRect().centre();
            if(Math.sqrt(Math.pow(point.x-d.x,2)+Math.pow(point.y-d.y,2)) <= 70 && pegs.get(i).destroyable()){
                checkPeg(pegs.get(i));
                pegs.remove(i);
                i--;
            }
        }
   }

    //check if the ball intersects a powerup or bucket.
    //if powerup, instance a fireball to replace the ball
    //if bucket, mark the bucket is contacted
    private void checkBall(int index){
        if(powerup != null) {
            if (balls.get(index).intersects(powerup)) {
                balls.set(index, new FireBall(balls.get(index)));
                powerup = null;
            }
        }
        if(balls.get(index).intersects(bucket)){
            isContactBucket = true;
        }
    }

    //read the csv file, initialize the pegs according to the csv
    private void readCsv(String file){
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

    /**
     * upadate the board
     * @param input
     */
    public void update(Input input) {
        // set a new trun
        if (isNewTurn()) {
            getGreenPeg();
            getPowerup();
            turnInitialized = true;
        }

        // Check all non-deleted pegs for intersection with the all non-delete ball
        for (int i = 0; i < pegs.size(); ++i) {
            pegs.get(i).update();
            for(int j = 0; j < balls.size(); ++j) {
                if (balls.get(j).intersects(pegs.get(i))) {
                    balls.get(j).bounce(pegs.get(i));

                    if(balls.get(j).getClass().equals(FireBall.class)){
                        fireBounce(pegs.get(i).getRect().centre());
                        balls.set(j, new Ball(balls.get(j),"res/ball.png"));
                        i = 0;
                        break;
                    }
                    checkPeg(pegs.get(i));
                    if(pegs.get(i).destroyable()){
                        pegs.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }


        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && balls.isEmpty()) {
            if(shots > 0) {
                balls.add(new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION)));
            }
            Board.shots--;
        }


        if (!balls.isEmpty()) {
            for (int i = 0; i < balls.size(); ++i) {
                checkBall(i);
                balls.get(i).update();
                // Delete the ball when it leaves the screen
                if (balls.get(i).outOfScreen()) {
                    if(isContactBucket){
                        shots++;
                        isContactBucket = false;
                    }
                    balls.remove(i);
                    i--;
                }
            }
            // When all balls leave the screen, new turn will start
            if (balls.isEmpty()) {
                greenReturnBlue();
                powerup = null;
                turnInitialized = false;
            }
        }

        bucket.update();

        if(powerup != null) {
            powerup.update();
        }

    }


}

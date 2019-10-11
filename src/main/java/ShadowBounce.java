/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;

public class ShadowBounce extends AbstractGame {
    //private  Board[] boards = new Board[2];
    private  ArrayList<Board> boards = new ArrayList<Board>();
    //private int shots;

    //private Peg[] pegs = new Peg[50];
    //private Ball ball;
    //private static final Point BALL_POSITION = new Point(512, 32);
    //private static final double PEG_OFFSET = 100;

    public ShadowBounce() {
        for (int i = 0; i < 5; i++) {
            boards.add(new Board("res/" + i + ".csv"));
        }
        boards.get(0).setShots(100);
    }


    @Override
    protected void update(Input input) {


        if(boards.size()>0) {
            boards.get(0).update(input);
            if(boards.get(0).getRedPegNumber() == 0){
                boards.remove(0);
            }
        }
        if(boards.size()==0 || boards.get(0).outOfShots(input)) {
            Window.close();
        }

    }



    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}

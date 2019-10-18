
import bagel.*;
import java.util.ArrayList;

public class ShadowBounce extends AbstractGame {
    private  ArrayList<Board> boards = new ArrayList<Board>();

    public ShadowBounce() {
        for (int i = 0; i < 5; i++) {
            boards.add(new Board("res/" + i + ".csv"));
        }
        boards.get(0).setShots(20);
    }


    @Override
    protected void update(Input input) {

        //update the first board (boards[0]), and remove that board when all red pegs are destroyed
        //now we can update the next board as it's the first board now
        if(boards.size()>0) {
            boards.get(0).update(input);
            if(boards.get(0).getRedPegNumber() == 0){
                boards.remove(0);
            }
        }

        // the game will stop if all boards are removed or run out of the shots
        if(boards.size()==0 || boards.get(0).outOfShots(input)) {
            Window.close();
        }

    }


    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}

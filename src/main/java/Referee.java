import java.util.Random;

public class Referee {
    public boolean checkLose;
    private Field field;
    private Cell cell;

    public Referee(Field field) {
        this.field = field;
    }


    public boolean checkLose(Field field) {
        //für jede cell des feldes muss gelten : nicht bombe und gleichzeitig uncovered (cell.isBomb && cell.isUncovered) --> true (also ist das spiel vorbei)
        return false;
    }

    public boolean checkWin(Field field) {
        //für alle cells des feldes muss gelten : es darf nicht nicht bombe sein und covered (!cell.isBomb && cell.isCovered) --> false
        return false;
    }
}

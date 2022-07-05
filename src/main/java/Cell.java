
import java.util.Arrays;

public class Cell {
    private boolean covered;
    private final Position position;
    private final Field field;
    private boolean bomb;
    //private boolean flagged;

    public Cell(Position position, Field field) {
        this.position = position;
        this.field = field;
    }

    public Cell[] getNeighbours() {
        return this.field.getAllCells().stream().filter((c -> c.getPosition().isNextTo(this.position))).toArray(Cell[]::new);
    }

    public Cell[] getNonDiagonalNeighbours() {
        return this.field.getAllCells().stream().filter((c -> c.getPosition().isNonDiagonallyNextTo(this.position))).toArray(Cell[]::new);
    }

    public void markCovered() {
        this.covered = true;
    }
/*
    public void markFlagged() {
        this.flagged = true;
    }

    public void markUnflagged() {
        this.flagged = false;
    }
*/
    public boolean checkUncover(){
        if(!this.isBomb())
            return true;
        else
            System.out.println("BOOM");
            return false;
    }


    public void markUncovered() {
    this.covered = false;

        for ( Cell c : getNonDiagonalNeighbours())
            if (!c.isBomb() && !c.isUncovered())
                c.markUncovered(); //TODO:show benni
    }

    public void markBomb() {
        this.bomb = true;
    }

    public long bombCount() {
        var neighbours = this.getNeighbours();
        var bombNeighboursCount = Arrays.stream(neighbours).filter(Cell::isBomb).count();
        return bombNeighboursCount;
    }

    public Position getPosition() {
        return this.position;
    }

    boolean isBomb() {
        return this.bomb;
    }

    boolean notBomb() {
        return !this.bomb;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public boolean isUncovered() {return !this.covered;}
/*
    public boolean isFlagged() {
        return this.flagged;
    }

    public boolean isUnflagged() {
        return !this.flagged;
    }
 */
}

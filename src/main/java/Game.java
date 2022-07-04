import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.Scanner;
import java.util.Random;

/*
public class Game {
    private int size = 10;
    private Field field = new Field(size);

    public void init() {
        System.out.println("\n\n================Welcome to Minesweeper ! ================\n");
        while (size > 0) {

        }
    }

}





public record Game(Field field) {
    public void proceed() {
        var CoveredNextRound =
                field.getAllCells()
                        .stream()
                        .filter(Cell::isCoveredNextRound)
                        .toList();
        var UncoveredNextRound = field.getAllCells()
                .stream()
                .filter(Predicate.not(Cell::isCoveredNextRound))
                .toList();

        //FlaggedNextRound.forEach(Cell::markFlagged);
        CoveredNextRound.forEach(Cell::markCovered);
        UncoveredNextRound.forEach(Cell::markUncovered);
    }
}

*/

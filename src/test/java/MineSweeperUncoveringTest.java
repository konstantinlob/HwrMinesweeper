import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MineSweeperUncoveringTest {

    private Field gameField;

    @BeforeEach
    void setUp() {
        gameField = new Field(10);
    }

    private void markCellsAsBomb(Position[] positions) {
        for (Position position : positions) {
            Cell cell = gameField.getCellAt(position);
            cell.markBomb();
        }
    }

    @Nested
    class EmptyFieldTest {

    }
}

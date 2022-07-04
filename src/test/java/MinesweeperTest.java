
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinesweeperTest {
    private int FIELD_SIZE;
    private Field field;
    private Position[] positions;
    private Cell[] cells;


    @Nested
    class NeigbourTests {
        @Test
        void getneighbours_AllNeighbourPositionsAreNextToPosition() {
            // given
            Position cellPosition = new Position(5, 5);
            Position[] neighbours = new Position[8];
            int index = 0;
            for (int x = 4; x <= 6; x++) {
                for (int y = 4; y <= 6; y++) {
                    boolean isCellItself = x == 5 && y == 5;
                    if (!isCellItself) {
                        Position p = new Position(x, y);
                        neighbours[index] = p;
                        index++;
                    }
                }
            }
            for (Position neighbour : neighbours) {
                // when
                boolean neighbourIsNextToPosition = cellPosition.isNextTo(neighbour);
                // then
                assertThat(neighbourIsNextToPosition).isTrue();
            }
        }

        @Test
        void getNeighbours_cellAtCenterPosition_cellHasEightNeighboursWhichAreNextToCenterPosition() {

            // given
            Field gameField = new Field(10);
            Position centerPosition = new Position(5, 5);
            Cell cell = gameField.getCellAt(centerPosition);
            // when
            Cell[] neighbours = cell.getNeighbours();
            // then
            assertThat(neighbours).hasSize(8);
            for (Cell neighbour : neighbours) {
                assertThat(neighbour.getPosition().isNextTo(centerPosition)).isTrue();
            }
        }

        @Test
        void getNonDiagonalNeighbours_cellAtCenterPosition_cellHasFourNonDiagonalNeighboursNextToPosition() {

            // given
            Field gameField = new Field(10);
            Position centerPosition = new Position(5, 5);
            Cell cell = gameField.getCellAt(centerPosition);
            // when
            Cell[] neighbours = cell.getNonDiagonalNeighbours();
            // then
            assertThat(neighbours).hasSize(4);
            for (Cell neighbour : neighbours) {
                assertThat(neighbour.getPosition().isNonDiagonallyNextTo(centerPosition)).isTrue();
            }
        }


    }

    @Nested
    class CoveringTests {
        @Test
        void IsCovered_singleCoveredCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markCovered();
            assertThat(cell.isCovered()).isTrue();
        }

        @Test
        void IsNotCovered_singleCoveredCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);

            assertThat(cell.isCovered()).isFalse();
        }
    }

    @Nested
    class UncoveringTests{
        @Test
        void IsUncovered_singleUncoveredCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markCovered();
            cell.markUncovered();
            assertThat(cell.isUncovered()).isTrue();
        }

        @Test
        void IsBomb_checkUncover(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markCovered();
            cell.markBomb();

            assertThat(cell.checkUncover()).isFalse();
        }

        @Test
        void IsntBomb_checkUncover(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markCovered();

            assertThat(cell.checkUncover()).isTrue();
        }
    }

    @Nested
    class FlagTests{

        @Test
        void IsFlagged_singleFlaggedCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markFlagged();
            assertThat(cell.isFlagged()).isTrue();
        }

        @Test
        void IsUnflagged_singleFlaggedCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markFlagged();
            cell.markUnflagged();
            assertThat(cell.isUnflagged()).isTrue();
        }
    }

    @Nested
    class Bombtests{

        @Test
        @Disabled
        void BombCount_threeBombsAroundCenterCell () {
            //idfk>
            //TODO: remove
            Field field = new Field(10);
            Position cellPosition = new Position(1, 1);
            Cell cell = field.getCellAt(cellPosition);
            initializePositions();
            initializeCells();
            markCellsAsBomb(new Position[]{
                    new Position(0, 0),
                    new Position(0, 1),
                    new Position(0, 2)
            });
            assertThat(cell.bombCount()).isEqualTo(3);
        }

        @Test
        void isBomb_singleMarkedBombCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markBomb();
            assertThat(cell.isBomb()).isTrue();
        }

        @Test
        void notBomb_singleMarkedBombCell(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Cell cell = field.getCellAt(cellPosition);
            cell.markBomb();
            assertThat(cell.notBomb()).isFalse();
        }

        @Test
        void isSingleBomb_BombCount(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Position bombPosition = new Position(4,4);

            Cell cell = field.getCellAt(cellPosition);
            Cell bombCell = field.getCellAt(bombPosition);
            bombCell.markBomb();
            assertThat(cell.bombCount()).isEqualTo(1);
        }

        @Test
        void isPluralBomb_BombCount(){
            Field field = new Field(10);
            Position cellPosition = new Position(5, 5);
            Position bombPosition1 = new Position(4,4);
            Position bombPosition2 = new Position(4,5);
            Position bombPosition3 = new Position(4,6);

            Cell cell = field.getCellAt(cellPosition);
            Cell bombCell1 = field.getCellAt(bombPosition1);
            Cell bombCell2 = field.getCellAt(bombPosition2);
            Cell bombCell3 = field.getCellAt(bombPosition3);
            bombCell1.markBomb();
            bombCell2.markBomb();
            bombCell3.markBomb();

            assertThat(cell.bombCount()).isEqualTo(3);
        }
    }

    @Nested
    class PositionTests {
        @Test
        void IsNextToPosition_PositionIsNextToposition (){
            Position cellPosition = new Position(1, 1);
            Position[] neighbours = new Position[8];
            int index = 0;
            for (int x = 0; x <= 2; x++) {
                for (int y = 0; y <= 2; y++) {
                    boolean isCellItself = x == 1 && y == 1;
                    if (!isCellItself) {
                        Position p = new Position(x, y);
                        neighbours[index] = p;
                        index++;
                    }
                }
            }
            for (Position neighbour : neighbours) {
                // when
                boolean neighbourIsNextToPosition = cellPosition.isNextTo(neighbour);
                // then
                assertThat(neighbourIsNextToPosition).isTrue();
            }
        }

        @Test
        void IsNextToPosition_PositionIsntNextToposition (){

            Position centerCell = new Position(1, 1);
            Position neighbour = new Position(3, 3);

            boolean neighbourIsNextToPosition = centerCell.isNextTo(neighbour);
            assertThat(neighbourIsNextToPosition).isFalse();
        }
    }

    private void markCellsAsBomb(Position[] positions) {
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cell.markBomb();
        }

    }

    private void initializeCells() {
        cells = new Cell[FIELD_SIZE * FIELD_SIZE];
        int index = 0;
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cells[index] = cell;
            cell.markCovered();
            index++;
        }
    }

    private void initializePositions() {
        positions = new Position[FIELD_SIZE * FIELD_SIZE];
        int index = 0;
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int y = 0; y < FIELD_SIZE; y++) {
                Position p = new Position(x, y);
                positions[index] = p;
                index++;
            }
        }
    }

}

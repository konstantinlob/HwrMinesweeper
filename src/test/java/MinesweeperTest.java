
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinesweeperTest {
    private int fieldSize;
    private Command command;
    private Position[] positions;

    @Nested
    class CommandTests{
        @Test
        void validCommand_commandValidizer(){
            fieldSize = 10;
            command = new Command("8 6");
            boolean boo = command.validizeUncover(fieldSize);

            Assertions.assertThat(boo).isTrue();
        }

        @Test
        void validCreateCommand_commandValidizer_commandProcessor(){
            command = new Command("86 1");
            boolean boo = command.validizeFieldDifficultyFieldSize();
            command.processFieldDifficultyFieldSize();
            fieldSize= command.getSize();
            int difficulty= command.getDifficulty();
            Assertions.assertThat(fieldSize).isEqualTo(86);
            Assertions.assertThat(difficulty).isEqualTo(1);
            Assertions.assertThat(boo).isTrue();
        }

        @Test
        void invalidCommand_commandValidizer(){
            fieldSize = 10;
            command = new Command("86");
            boolean boo = command.validizeUncover(fieldSize);

            Assertions.assertThat(boo).isFalse();
        }

        @Test
        void invalidCreateCommand_commandValidizer(){
            command = new Command("100 4");
            boolean boo = command.validizeFieldDifficultyFieldSize();

            Assertions.assertThat(boo).isFalse();
        }

        @Test
        void validCommand_processCommand(){
            Field field = new Field(10);
            Position cellPosition = new Position(8,6);
            Cell cell = field.getCellAt(cellPosition);
            cell.markCovered();
            command = new Command("8 6");
            command.processUncover();

            Assertions.assertThat(command.getX()).isEqualTo(8);
            Assertions.assertThat(command.getY()).isEqualTo(6);
        }
    }

    @Nested
    class RefereeTests{
        @Test
        void winningField_checkWin(){
            fieldSize = 4;
            Field gameField = new Field(fieldSize);
            Referee referee = new Referee(gameField);
            Position bombPosition = new Position(2,2);
            Cell bombCell = gameField.getCellAt(bombPosition);
            bombCell.markBomb();
            bombCell.markCovered();
            boolean gameWin = referee.checkWin(gameField);
            Assertions.assertThat(gameWin).isTrue();
        }
        @Test
        void nonWinningField_checkWin(){
            fieldSize = 4;
            Field gameField = new Field(fieldSize);
            Referee referee = new Referee(gameField);
            Position bombPosition = new Position(2,2);
            Position nonBombPosition = new Position(3, 2);
            Cell bombCell = gameField.getCellAt(bombPosition);
            Cell nonBombCell = gameField.getCellAt(nonBombPosition);
            bombCell.markBomb();
            bombCell.markCovered();
            nonBombCell.markCovered();
            boolean gameWin = referee.checkWin(gameField);

            Assertions.assertThat(gameWin).isFalse();
        }

        @Test
        void nonWinningField_withUncoveredBomb_checkWin(){ //sonst kann ein field gewinnen wenn es nur eine uncovered bomb hat und nichts anderes
            fieldSize = 4;
            Field gameField = new Field(fieldSize);
            Referee referee = new Referee(gameField);
            Position bombPosition = new Position(2,2);
            Cell bombCell = gameField.getCellAt(bombPosition);
            bombCell.markBomb();

            boolean gameWin = referee.checkWin(gameField);

            Assertions.assertThat(gameWin).isFalse();
        }

        @Test
        void losingField_checkLose(){
            fieldSize = 4;
            Field gameField = new Field(fieldSize);
            Referee referee = new Referee(gameField);
            Position bombPosition = new Position(2,2);
            Cell bombCell = gameField.getCellAt(bombPosition);
            bombCell.markBomb();
            boolean gameLose = referee.checkLose(gameField);

            Assertions.assertThat(gameLose).isTrue();
        }

        @Test
        void nonLosingField_checkLose(){
            fieldSize = 4;
            Field gameField = new Field(fieldSize);
            Referee referee = new Referee(gameField);
            Position bombPosition = new Position(2,2);
            Cell bombCell = gameField.getCellAt(bombPosition);
            bombCell.markBomb();
            bombCell.markCovered();
            boolean gameLose = referee.checkLose(gameField);

            Assertions.assertThat(gameLose).isFalse();
        }
    }

    @Nested
    class NeighbourTests {
        @Test
        void getNeighbours_AllNeighbourPositionsAreNextToPosition() {
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
        void pluralCell_markUncover_diagonalUncoverShouldNotHappen(){
            Field gameField = new Field(2);
            Position startPosition = new Position(1, 1);
            Position cornerPosition = new Position(0,0);
            Cell startingCell = gameField.getCellAt(startPosition);
            Cell cornerCell = gameField.getCellAt(cornerPosition);

            startingCell.markCovered();
            cornerCell.markCovered();

            startingCell.markUncovered();

            assertThat(startingCell.isUncovered()).isTrue();
            assertThat(cornerCell.isUncovered()).isFalse();
        }

        @Test
        void pluralCell_markUncover_RecursionShouldHappen(){
            Field gameField = new Field(2);
            Position startPosition = new Position(1, 1);
            Position sidePosition = new Position(0,1);
            Position topPosition = new Position(1,0);
            Cell startingCell = gameField.getCellAt(startPosition);
            Cell sideCell = gameField.getCellAt(sidePosition);
            Cell topCell = gameField.getCellAt(topPosition);

            startingCell.markCovered();
            sideCell.markCovered();
            topCell.markCovered();

            startingCell.markUncovered();

            assertThat(startingCell.isUncovered()).isTrue();
            assertThat(sideCell.isUncovered()).isTrue();
            assertThat(topCell.isUncovered()).isTrue();
        }
    }

    @Nested
    class BombTests{
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

        @Test
        void randomBombPlacer_difficultyOne(){
            int fieldSize=10;
            Field field = new Field(fieldSize);
            int difficulty = 1;
            initializePositions();
            initializePositions();
            field.placerandombombs(10, difficulty);
            int placedbombs = field.getPlacedBombs();
            assertThat(placedbombs).isEqualTo(fieldSize*fieldSize/8);
        }

        @Test
        void randomBombPlacer_difficultyTwo(){
            int fieldSize=10;
            Field field = new Field(fieldSize);
            int difficulty = 2;
            initializePositions();
            initializePositions();
            field.placerandombombs(10, difficulty);
            int placedbombs = field.getPlacedBombs();
            assertThat(placedbombs).isEqualTo(fieldSize*fieldSize/6);
        }

        @Test
        void randomBombPlacer_difficultyThree(){
            int fieldSize=10;
            Field field = new Field(fieldSize);
            int difficulty = 3;
            initializePositions();
            initializePositions();
            field.placerandombombs(10, difficulty);
            int placedbombs = field.getPlacedBombs();
            assertThat(placedbombs).isEqualTo(fieldSize*fieldSize/3);
        }
    }

    @Nested
    class PositionTests {
        @Test
        void IsNextToPosition_PositionIsNextToPosition (){
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
        void IsNextToPosition_PositionIsntNextToPosition (){
            Position centerCell = new Position(1, 1);
            Position neighbour = new Position(3, 3);

            boolean neighbourIsNextToPosition = centerCell.isNextTo(neighbour);
            assertThat(neighbourIsNextToPosition).isFalse();
        }
    }

    private void initializePositions() {
        positions = new Position[fieldSize * fieldSize];
        int index = 0;
        for (int x = 0; x < fieldSize; x++) {
            for (int y = 0; y < fieldSize; y++) {
                Position p = new Position(x, y);
                positions[index] = p;
                index++;
            }
        }
    }
}

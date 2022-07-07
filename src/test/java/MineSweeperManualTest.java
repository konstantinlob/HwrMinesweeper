import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static java.lang.System.out;

class MineSweeperManualTest {

    private Field field;
    private Position[] positions;
    private Cell[] cells;
    private Command command;
    private Command createCommand;
    private Referee referee;
    private int fieldSize;
    private int difficulty;
    
    @Test 
    void PlayTest() {
        boolean playAgain;
        Scanner input = new Scanner(System.in);

        do {
            out.println("\n\n ========Welcome to Minesweeper ! ========\n um anzufangen die Feldgroesse(0-99) und den spielmodus (1-3) in dem format 'groesse schwierigkeit'eingeben.");

            String create;
            Scanner createInput = new Scanner(System.in);

            do {
                create = createInput.nextLine();
                out.println(create);
                createCommand = new Command(create);
            } while(!createCommand.validizeFieldDifficultyFieldSize());

            createCommand.processFieldDifficultyFieldSize();
            fieldSize =createCommand.getSize();
            field = new Field(fieldSize);
            difficulty = createCommand.getDifficulty();
            referee = new Referee(field);
            initializePositions();
            initializeCells();
            field.placerandombombs(fieldSize, difficulty);

            out.println("\n Das richtige Format zum aufdecken ist X Y. Probiere es noch mal!");
            do {
                gameRound();
            }
            while (!referee.checkWin(field) && !referee.checkLose(field));

            if (referee.checkWin(field)) {
                out.println("\n\n ===== WELL DONE YOU WON!!! =====\n");
            }
            if (referee.checkLose(field)) {
                out.println("\n\n ========= you lost loser =========\n");
                try {
                    String string = buildConsoleOutput();
                    out.println(string);
                    Thread.sleep(300);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            }
            out.println("\n do you want to play again? if yes press 'y', if no then press a random key");
            String command = input.nextLine();
            if(command=="y"){
                playAgain = true;
            } else {
                playAgain = false;
            }
        } while (playAgain == false);
    }


    private void gameRound()  {
        try {
            String string = buildConsoleOutput();
            out.println(string);
            Thread.sleep(3000);
        } catch (InterruptedException i){
            i.printStackTrace();
        }

        String com;
        Scanner input = new Scanner(System.in);

        do {
            com = input.nextLine();
        out.println(com);
            command = new Command(com);
        } while(!command.validizeUncover(fieldSize));

        command.processUncover();

        Position cellPosition = new Position(command.getY(),command.getX());
        Cell cell = field.getCellAt(cellPosition);
        cell.markUncovered();
    }

    private String buildConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        builder.append("   ");
        for (int i = 0; i < fieldSize; i++){
            builder.append("|"+i);
            if(i<10){builder.append(" ");}
        }

        for (int i = 0; i < cells.length; i++) {
                boolean newRow = i % fieldSize == 0;
            if (newRow) {
                builder.append("\n"+(i / fieldSize));
                boolean rowOverNine = i > 9* fieldSize == true;
                if(rowOverNine){
                    builder.append(" ");
                } else {
                    builder.append("  ");
                }
            }

            Cell cell = cells[i];
            boolean isCovered = cell.isCovered();
            builder.append("|");
            if (isCovered){
                    builder.append('?');
            }  else {
                if (cell.isBomb()){
                    builder.append("X");
                } else {
                    builder.append(cell.bombCount());
                }
            }
            builder.append(" ");
        }
        builder.append('\n');
        return builder.toString();
    }

    private void initializeCells() {
        cells = new Cell[fieldSize * fieldSize];
        int index = 0;
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cells[index] = cell;
            cell.markCovered();
            index++;
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

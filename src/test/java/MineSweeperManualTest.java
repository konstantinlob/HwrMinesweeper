import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static java.lang.System.out;

class MineSweeperManualTest {
    private static int FIELD_SIZE = 10;

    //private Game game;
    private Field field;
    private Position[] positions;
    private Cell[] cells;
    
    @Test 
    void PlayTest(){
        field = new Field(FIELD_SIZE);
        //game = new Game(field);

        initializePositions();
        initializeCells();

        out.println("\n\n =====Welcome to Minesweeper ! =====\n");
        
        while(!checkwin()) {
            gameRound();
            //Thread.sleep(2000);
        }
    }

    private boolean checkwin() {
        for(Cell c : cells){
            if(!c.isBomb()){
                if(c.isCovered()){
                    return false;
                }
            }
        }
        return true;
    }

    private void gameRound() {
        String command; // Can be local.
        Scanner input = new Scanner(System.in);
        String string = buildConsoleOutput();

        out.println(string);
        while(!commandvalidizer()) {
            command = input.next();
            commandvalidizer();
            commandprocesser(command);
        }
    }

    private boolean commandvalidizer(){ //Damit das Programm nicht faild und wir testen können.
        return false;
    }

    private boolean commandvalidizer(String command) {              //Methode mit input String Variable
        if(command.matches("\\d*\s\\d*")){                    //Command muss dem Muster entsprechen damit wir dann splitten können an der Stelle
            String[] splitted = command.split(" ");           //splittet an dem Leerzeichen
            System.out.println("Nicht im richtigen Format eingegeben!!! Das richtige Format ist X Y. Probiere es noch mal!");
            if(splitted[0].length() <= String.valueOf(FIELD_SIZE-1).length() && splitted[1].length() <= String.valueOf(FIELD_SIZE-1).length()){
                return true;
            }
        }
        return false;
    }

    private void commandprocesser(String command) {
    }

    private void markCellsAsBomb(Position[] positions) {
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cell.markBomb();
        }
    }

    private String buildConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ");
        for (int i = 0; i < FIELD_SIZE; i++){
            builder.append(i);
        }
        builder.append('\n');


        for (int i = 0; i < cells.length; i++) {
            boolean newRow = i % FIELD_SIZE == 0;
            if (newRow) {
                builder.append("\n");
                builder.append(i/FIELD_SIZE + " ");

            }
            Cell cell = cells[i];
            boolean isCovered = cell.isCovered();
            boolean isFlagged = cell.isFlagged();

            if (isCovered){
                if(!isFlagged) {
                    builder.append('C'); //Grey Field Unicode U+2800
                } else {
                    builder.append('F'/*"U+1F6A9"*/); //flag unicode
                }
            }  else {
                if (cell.isBomb()){
                    builder.append("X");
                } else {
                    builder.append(cell.bombCount()); //leerer unicode
                }
            }
        }
        builder.append('\n');
        return builder.toString();
    }

    private void initializeCells() {
        cells = new Cell[FIELD_SIZE * FIELD_SIZE];
        int index = 0;
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cells[index] = cell;
            //cell.markCovered();
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

    @Test
        //@Disabled("manual test")
    void manualTest() throws InterruptedException {
        field = new Field(FIELD_SIZE);
        //game = new Game(field);
        initializePositions();
        initializeCells();
        markCellsAsBomb(new Position[]{
                new Position(3, 0),
                new Position(3, 2),
                new Position(3, 3),
                new Position(3, 5),
                new Position(3, 7),
                new Position(3, 8),
                new Position(3, 9)
        });
        out.println("\n\n================Welcome to Minesweeper ! ================\n");
        while(true) {
            String string = buildConsoleOutput();
            //game.proceed();
            out.println(string);
            Thread.sleep(3000);

/*
            if(checkWin()) {
                displayHidden();
                System.out.println("\n================You WON!!!================");
                break;
            }
 */
        }
    }
}

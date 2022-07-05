import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static java.lang.System.out;

class MineSweeperManualTest {


    //private Game game;
    private Field field;
    private Position[] positions;
    private Cell[] cells;
    private Command command;
    private Referee referee;
    private int fieldsize = 10;
    
    @Test 
    void PlayTest() {
        field = new Field(fieldsize);
        referee = new Referee(field);
        initializePositions();
        initializeCells();
        field.placerandombombs(fieldsize);

        out.println("\n\n =====Welcome to Minesweeper ! =====\n");
        
        do {
            gameRound();
        }
        while(!referee.checkWin(field) || !referee.checkLose);

        if(referee.checkWin(field)){
            out.println("\n\n ===== WELL DONE YOU WON!!! =====\n");
        }
        else {
            out.println("\n\n ===== you lost loser =====\n");
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

    private void gameRound()  {

        try {
            String string = buildConsoleOutput();
            out.println(string);
            Thread.sleep(3000);
        } catch (InterruptedException i){
            i.printStackTrace();
        }

        String com; // Can be local.
        Scanner input = new Scanner(System.in);

        do {
            com = input.next();
            command = new Command(com);
        } while(!command.validize(fieldsize));

        command.process();
        int commandx = command.getX();
        int commandy = command.getX();
        Position cellPosition = new Position(commandx,commandy);
        Cell cell = field.getCellAt(cellPosition);
        cell.markUncovered();



    }

    private String buildConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ");
        for (int i = 0; i < fieldsize; i++){
            builder.append(i);
        }
        builder.append('\n');


        for (int i = 0; i < cells.length; i++) {
            boolean newRow = i % fieldsize == 0;
            if (newRow) {
                builder.append("\n");
                builder.append(i/fieldsize + " ");

            }
            Cell cell = cells[i];
            boolean isCovered = cell.isCovered();
            //boolean isFlagged = cell.isFlagged();

            if (isCovered){
                    builder.append('?'); //Grey Field Unicode U+2800
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
        cells = new Cell[fieldsize * fieldsize];
        int index = 0;
        for (Position position : positions) {
            Cell cell = field.getCellAt(position);
            cells[index] = cell;
            //cell.markCovered();
            index++;
        }
    }

    private void initializePositions() {
        positions = new Position[fieldsize * fieldsize];
        int index = 0;
        for (int x = 0; x < fieldsize; x++) {
            for (int y = 0; y < fieldsize; y++) {
                Position p = new Position(x, y);
                positions[index] = p;
                index++;
            }
        }
    }

    @Test
        //@Disabled("manual test")
    void manualTest() throws InterruptedException {
        field = new Field(fieldsize);
        //game = new Game(field);
        initializePositions();
        initializeCells();
        field.placerandombombs(fieldsize);
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

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static java.lang.System.out;

class MineSweeperManualTest {

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
        while(!referee.checkWin(field) &&  !referee.checkLose(field));

        if(referee.checkWin(field)){
            out.println("\n\n ===== WELL DONE YOU WON!!! =====\n");
        }
        if(referee.checkLose(field)){
            out.println("\n\n ===== you lost loser =====\n");
        }
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
        } while(!command.validize(fieldsize));

        command.process();

        Position cellPosition = new Position(command.getY(),command.getX());
        Cell cell = field.getCellAt(cellPosition);
        cell.markUncovered();
    }

    private String buildConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        builder.append("  ");
        for (int i = 0; i < fieldsize; i++){
            builder.append(i);
        }
        //builder.append('\n');
        for (int i = 0; i < cells.length; i++) {
                boolean newRow = i % fieldsize == 0;
            if (newRow) {
                builder.append("\n");
                builder.append((i / fieldsize));
                builder.append(" ");
            }
            Cell cell = cells[i];
            boolean isCovered = cell.isCovered();

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
            cell.markCovered();
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
        }
    }
}

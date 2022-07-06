import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static java.lang.System.out;

class MineSweeperManualTest {

    private Field field;
    private Position[] positions;
    private Cell[] cells;
    private Command command;
    private Referee referee;
    private int fieldsize= 25;
    
    @Test 
    void PlayTest() {
        boolean playAgain;
        Scanner input = new Scanner(System.in);

        do {
            out.println("\n\n ========Welcome to Minesweeper ! ========\n Das richtige Format zum aufdecken ist X Y. Probiere es noch mal!");

            field = new Field(fieldsize);
            referee = new Referee(field);
            initializePositions();
            initializeCells();
            field.placerandombombs(fieldsize);

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
        } while(!command.validize(fieldsize));

        command.process();

        Position cellPosition = new Position(command.getY(),command.getX());
        Cell cell = field.getCellAt(cellPosition);
        cell.markUncovered();
    }

    private String buildConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        builder.append("   ");
        for (int i = 0; i < fieldsize; i++){
            builder.append("|"+i);
            if(i<10){builder.append(" ");}
        }
        builder.append("|");
        for (int i = 0; i < cells.length; i++) {
                boolean newRow = i % fieldsize == 0;
            if (newRow) {
                builder.append("\n"+(i / fieldsize));
                boolean rowOverNine = i > 9*fieldsize == true;
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

public class Command {

    private final String command;
    private int x;
    private int y;
    private int size;
    private int difficulty;

    public Command(String command) {
        this.command = command;
    }

    public boolean validizeFieldDifficultyFieldSize() {
        if(command.matches("[1-9][0-9]\\s[1-3]")){                    //Command muss dem Muster entsprechen damit wir dann splitten können an der Stelle
            String[] splitted = command.split(" ");           //splittet an dem Leerzeichen
            return true;
        }
        System.out.println("Nicht im richtigen Format eingegeben!!! Das richtige Format ist 'groesse schwierigkeit' Probiere es noch mal!");
        return false;
    }

    public void processFieldDifficultyFieldSize() {
        String[] splitted = command.split(" ");
        size = Integer.parseInt(splitted[0]);
        difficulty = Integer.parseInt(splitted[1]);
    }

    public void processUncover() {
        String[] splitted = command.split(" ");
        x = Integer.parseInt(splitted[0]);
        y = Integer.parseInt(splitted[1]);
    }

    public int getX() {
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getDifficulty() {return this.difficulty;}
    public int getSize() {return this.size;}
    public boolean validizeUncover(int fieldsize) {
        if(command.matches("\\d*\s\\d*")){                    //Command muss dem Muster entsprechen damit wir dann splitten können an der Stelle
            String[] splitted = command.split(" ");           //splittet an dem Leerzeichen
            if(splitted[0].length() <= String.valueOf(fieldsize-1).length() && splitted[1].length() <= String.valueOf(fieldsize-1).length()){
                return true;
            }
        } else {
            System.out.println("Nicht im richtigen Format eingegeben!!! Das richtige Format ist X Y. Probiere es noch mal!");
        }
        return false;
    }


    }

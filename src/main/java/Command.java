public class Command {

    private final String command;
    private Field field;
    private int x;
    private int y;
    public Command(String command) {
        this.command = command;
    }

    public void process() {
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

    public boolean validize(int fieldsize) {
        if(command.matches("\\d*\s\\d*")){                    //Command muss dem Muster entsprechen damit wir dann splitten können an der Stelle
            String[] splitted = command.split(" ");           //splittet an dem Leerzeichen
            System.out.println("Richtiges Format");
            if(splitted[0].length() <= String.valueOf(fieldsize-1).length() && splitted[1].length() <= String.valueOf(fieldsize-1).length()){
                System.out.println("Returning true");
                return true;
            } else {
                System.out.println("Out of Bounds!");
            }

        } else {
            System.out.println("Nicht im richtigen Format eingegeben!!! Das richtige Format ist X Y. Probiere es noch mal!");
        }
        return false;
    }
}

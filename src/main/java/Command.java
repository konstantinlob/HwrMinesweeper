public class Command {

    private final String command;
    private Field field;

    public Command(String command) {
        this.command = command;
    }

    public void process(Field field) {
        String[] splitted = command.split(" ");
        int X = Integer.parseInt(splitted[0]);
        int Y = Integer.parseInt(splitted[1]);

        Position cellPosition = new Position(X,Y);
        Cell cell = field.getCellAt(cellPosition);
        cell.markUncovered();
    }

    public boolean validize(int fieldsize) {
        if(command.matches("\\d*\s\\d*")){                    //Command muss dem Muster entsprechen damit wir dann splitten können an der Stelle
            String[] splitted = command.split(" ");           //splittet an dem Leerzeichen
            System.out.println("Richtiges Format");
            if(splitted[0].length() <= String.valueOf(fieldsize-1).length() && splitted[1].length() <= String.valueOf(fieldsize-1).length()){
                return true;
            }
        }
        System.out.println("Nicht im richtigen Format eingegeben!!! Das richtige Format ist X Y. Probiere es noch mal!");
        return false;
    }
}

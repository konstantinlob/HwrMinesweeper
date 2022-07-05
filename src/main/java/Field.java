import java.util.*;

public class Field {

    private final Map<Position, Cell> playingField;

    private Position[] positions;

    public Field(int size) {
        this.playingField = createField(size);
    }

    private Map<Position, Cell> createField(int size) {
        var field = new HashMap<Position, Cell>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                var position = new Position(x, y);
                var cell = new Cell(position, this);
                field.put(position, cell);

            }
        }

        return field;
    }

    public void placerandombombs(int size){
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Random rn = new Random();
                int answer = rn.nextInt(10) + 1;
                if(answer == 5 || answer == 2){
                Position cellPosition = new Position(x,y);
                Cell cell = getCellAt(cellPosition);
                cell.markBomb();
            }
            }
        }

    }
    public Cell getCellAt(Position centerPosition) {
        return playingField.get(centerPosition);
    }

    public Set<Cell> getAllCells() {
        return new HashSet<>(playingField.values());
    }
}

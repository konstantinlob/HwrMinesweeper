import java.util.*;

public class Field {

    private final Map<Position, Cell> playingField;

    private Position[] positions;

    public Field(int size) {
        this.playingField = createField(size);
    }

    private Map<Position, Cell> createField(int size) {
        var field = new HashMap<Position, Cell>();
        var horizontalsize = size ;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                var position = new Position(x, y);
                var cell = new Cell(position, this);
                field.put(position, cell);

            }
        }

        return field;
    }

    /*
    private void placeBombs() {
        int var = 0;
        while(var!=10) {
            Random random = new Random();
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            cell.markBomb();
        }
    }
    */

    public Cell getCellAt(Position centerPosition) {
        return playingField.get(centerPosition);
    }

    public Set<Cell> getAllCells() {
        return new HashSet<>(playingField.values());
    }
}

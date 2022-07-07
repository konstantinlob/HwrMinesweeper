import java.util.*;

public class Field {
    int placedBombCount;

    private final Map<Position, Cell> playingField;

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

    public void placerandombombs(int size, int difficulty){
        int bombCount=0;
        placedBombCount=0;
        if (difficulty==1){ bombCount= (size*size)/8;}
        if (difficulty==2){ bombCount= (size*size)/6 ;}
        if (difficulty==3){ bombCount= (size*size)/3;}
      Random rn = new Random();
        while(bombCount > 0){
            int x = rn.nextInt(size);
            int y = rn.nextInt(size);
            Position cellPosition = new Position(x, y);
            Cell cell = getCellAt(cellPosition);
            if(!cell.isBomb()){
                cell.markBomb();
                bombCount -= 1;
                placedBombCount+=1;
            }
        }
    }
    public Cell getCellAt(Position centerPosition) {
        return playingField.get(centerPosition);
    }

    public Set<Cell> getAllCells() {
        return new HashSet<>(playingField.values());
    }
    public int getPlacedBombs(){return this.placedBombCount;}
}

public record Position(int x, int y) {

    public boolean isNextTo(Position neighbour) {
        var distanceX = Math.abs(this.x - neighbour.x());
        var distanceY = Math.abs(this.y - neighbour.y());
        return (distanceX <= 1 && distanceY <= 1)
                && !(distanceX == 0 && distanceY == 0);
    }

    public boolean isNonDiagonallyNextTo(Position neighbour) {
        var distanceX = Math.abs(this.x - neighbour.x());
        var distanceY = Math.abs(this.y - neighbour.y());
        return (distanceX <= 1 && distanceY <= 1)
                && !(distanceX == 0 && distanceY == 0)
                && !(distanceX == 1 && distanceY == 1);
    }
}

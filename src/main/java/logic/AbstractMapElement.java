package logic;

public abstract class AbstractMapElement {
    protected Vector2d position;
    protected AbstractWorldMap map;
    protected AbstractMapElement(AbstractWorldMap map,Vector2d position){
        this.map = map;
        this.position = position;
    }
    public Vector2d getPosition() {
        return position;
    }
    public abstract String getSource();

}

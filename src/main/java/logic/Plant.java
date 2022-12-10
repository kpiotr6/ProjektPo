package logic;

public class Plant extends AbstractMapElement{

    public Plant(Vector2d position) {
        super(position);
    }
    public String getSource(){
        return "src/main/resources/grass.png";
    }
}

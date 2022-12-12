package logic;

public class Plant extends AbstractMapElement{
    public Plant(AbstractWorldMap map,Vector2d positon) {
        super(map,positon);
    }
    public String getSource(){
        return "src/main/resources/grass.png";
    }
}

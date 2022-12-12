package logic;

public abstract class Plantator {
    protected AbstractWorldMap map;
    public Plantator(AbstractWorldMap map){
        this.map = map;
    }
    public abstract void plant();
}

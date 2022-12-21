package logic;

import java.util.HashMap;

public class ToxicPlantator extends Plantator {
    public ToxicPlantator(AbstractWorldMap map) {
        super(map);
    }
    private HashMap<Vector2d,Integer> died = new HashMap<>();
    @Override
    public void plant(){

    }

    @Override
    public boolean isSpecialPlant() {
        return false;
    }

    @Override
    public boolean isSpecialField(Vector2d position) {
        return false;
    }

    @Override
    public void plantEaten(Vector2d position) {

    }
}

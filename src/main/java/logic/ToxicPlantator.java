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
}

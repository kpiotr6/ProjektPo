package logic;

import java.util.*;

public class ToxicPlantator extends Plantator {
    private HashMap<Vector2d,Integer> died = new HashMap<>();
    private int realStandardFields;
    private int realSpecialFields;
    public ToxicPlantator(AbstractWorldMap map) {
        super(map);
    }
    private Comparator<Map.Entry<Vector2d,Integer>> fieldComparator = new Comparator<Map.Entry<Vector2d, Integer>>() {
        @Override
        public int compare(Map.Entry<Vector2d, Integer> o1, Map.Entry<Vector2d, Integer> o2) {
            return o1.getValue() - o2.getValue();
        }
    };
    @Override
    public void plant(){
        List<Vector2d> standardPositions = listStandardFields();
        HashSet<Vector2d> set = new HashSet<Vector2d>(standardPositions);
        for(int i=0;i<map.config.getNewPlants();i++){
            if(normalPlantsNum+specialPlantsNum == map.height* map.width){
                return;
            }
            boolean temp;
            if(this.normalPlantsNum == realStandardFields){
                temp = true;
            }
            else {
                temp = isSpecialPlant();
            }

            if(temp){
                Vector2d tmpVector = new Vector2d(this.map.generateNumber(0, map.width-1),this.map.generateNumber(0, map.height-1));
                while(this.map.plantAt(tmpVector)!=null || set.contains(tmpVector)){
                    tmpVector = new Vector2d(this.map.generateNumber(0, map.width-1),this.map.generateNumber(0, map.height-1));
                }
                this.map.addPlant(new Plant(this.map, tmpVector), tmpVector);
                this.specialPlantsNum += 1;
            }
            else{
                Vector2d tmpVector = standardPositions.get(this.map.generateNumber(0,standardPositions.size()-1));
                while(this.map.plantAt(tmpVector)!=null){
                    standardPositions.remove(tmpVector);
                    tmpVector = standardPositions.get(this.map.generateNumber(0,standardPositions.size()-1));
                }
                this.map.addPlant(new Plant(this.map, tmpVector), tmpVector);
                this.normalPlantsNum += 1;
            }
        }
    }

    @Override
    public boolean isSpecialPlant() {
        return map.generateNumber(0,realSpecialFields*4+realStandardFields)<=realStandardFields*4;
    }

    private List<Vector2d> listStandardFields(){
        int standardFields = this.map.width*this.map.height-this.specialFields;
        List<Vector2d> fieldList;
        if(died.size()<=standardFields){
            fieldList = new LinkedList<Vector2d>(died.keySet());
            realStandardFields = fieldList.size();
            realSpecialFields = map.width*map.height-realStandardFields;
            return fieldList;
        }
        List<Map.Entry<Vector2d,Integer>> entryList = new ArrayList<Map.Entry<Vector2d,Integer>>(died.entrySet());
        entryList.sort(fieldComparator);
        entryList.subList(0,standardFields);
        fieldList = new LinkedList<>();
        for (Map.Entry<Vector2d,Integer> e : entryList) {
            fieldList.add(e.getKey());
        }
        fieldList = fieldList.subList(0,standardFields);
        realStandardFields = standardFields;
        realSpecialFields = map.width*map.height-realStandardFields;
        return fieldList;

    }
}

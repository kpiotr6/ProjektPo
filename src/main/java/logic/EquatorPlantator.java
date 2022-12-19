package logic;

public class EquatorPlantator extends Plantator{

    private int specialPlantsNum = 0;
    private int normalPlantsNum = 0;
    private int numOfZones;
    private Vector2d equatorStart;
    private Vector2d equatorEnd;

    public EquatorPlantator(AbstractWorldMap map) {
        super(map);
        this.numOfZones = Math.max(1, (int)Math.floor(this.specialFields / this.map.width));
        this.equatorStart = new Vector2d(0, ((int)(this.map.height / 2))-(int)(numOfZones/2));
        this.equatorEnd = new Vector2d(this.map.width-1,((int)(this.map.height / 2))+(int)(numOfZones/2)-1);
        if(this.equatorEnd.y < 0 ) this.equatorEnd = new Vector2d(this.equatorEnd.x, 0);
    }

    @Override
    public void plant() {
        for (int i = 0; i < this.map.config.getNewPlants(); i++) {
            if(normalPlantsNum + specialPlantsNum == this.map.width * this.map.height) return;
            if(this.isSpecialPlant() && this.specialPlantsNum <= (this.map.width-1) * numOfZones){
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                int potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);

                int cycle = 0;
                while(this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
                    potentialNewGrassFieldX = (potentialNewGrassFieldX + 1)%this.map.width;
                    cycle += 1;
                    if(cycle == this.map.width - 1){
                        potentialNewGrassFieldY =(potentialNewGrassFieldY + 1)%(equatorEnd.y - equatorStart.y + 1);
                        cycle = 0;
                    }
                }
                this.specialPlantsNum += 1;
            }else{
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                int potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);

                if(potentialNewGrassFieldY == this.equatorStart.y || potentialNewGrassFieldY == this.equatorEnd.y){
                    potentialNewGrassFieldY = this.equatorStart.y + 1;
                }
                while (this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
                    potentialNewGrassFieldX += 1;
                    if (potentialNewGrassFieldX == this.map.width-1){
                        potentialNewGrassFieldX = 0;
                        potentialNewGrassFieldY = (potentialNewGrassFieldY + 1)%this.map.height;
                    }
                    if(potentialNewGrassFieldY == this.equatorStart.y || potentialNewGrassFieldY == this.equatorEnd.y){
                        potentialNewGrassFieldY = this.equatorStart.y + 1;
                    }
                }
                this.normalPlantsNum += 1;
            }
        }
    }

    @Override
    public boolean isSpecialPlant() {
        return this.map.generateNumber(0,9) < 8;
    }
}

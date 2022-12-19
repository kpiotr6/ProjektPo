package logic;

public class EquatorPlantator extends Plantator{

    private int specialPlants = 0;
    private int normalPlants = 0;
    private Vector2d equatorStart;
    private Vector2d equatorEnd;

    public EquatorPlantator(AbstractWorldMap map) {
        super(map);
        if(this.map.width % 2 == 0){
            this.equatorStart = new Vector2d(0, ((int)this.map.height / 2)-1);
            this.equatorEnd = new Vector2d(this.map.width-1,((int)this.map.height / 2)-1);
        }else{
            this.equatorStart = new Vector2d(0, (int)Math.floor(this.map.height / 2));
            this.equatorEnd = new Vector2d(this.map.width-1,(int)Math.floor(this.map.height / 2));
        }
    }

    @Override
    public void plant() {
        for (int i = 0; i < this.map.config.getNewPlants(); i++) {
            if(normalPlants + specialPlants == this.map.width * this.map.height) return;
            if(this.map.generateNumber(0, 9) < 2 && this.specialPlants <= this.map.width-1){
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
                this.specialPlants += 1;
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
                this.normalPlants += 1;
            }
        }
    }
}

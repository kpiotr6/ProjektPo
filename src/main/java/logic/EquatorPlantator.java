package logic;

public class EquatorPlantator extends Plantator{
    private int numOfZones;
    private Vector2d equatorStart;
    private Vector2d equatorEnd;
    private int totalWeights;
    private int specialWeights;

    public EquatorPlantator(AbstractWorldMap map) {
        super(map);
        this.numOfZones = Math.max(1, (int)Math.floor(this.specialFields / this.map.width));
        this.equatorStart = new Vector2d(0, ((int)(this.map.height / 2))-(int)(numOfZones/2));
        if(this.numOfZones % 2 == 0){
            this.equatorEnd = new Vector2d(this.map.width-1,((int)(this.map.height / 2))+(int)(numOfZones/2)-1);
        }else{
            this.equatorEnd = new Vector2d(this.map.width-1,((int)(this.map.height / 2))+(int)(numOfZones/2));
        }

        if(this.equatorEnd.y < 0 ) this.equatorEnd = new Vector2d(this.equatorEnd.x, 0);
        this.totalWeights = this.map.width * this.map.height + (this.map.width) * numOfZones * 4;
        this.specialWeights = (this.map.width) * numOfZones * 5;
    }
    @Override
    public void startPlants(){
        for (int i = 0; i < this.map.config.getStartPlantNumber(); i++) {
            if(normalPlantsNum + specialPlantsNum == this.map.width * this.map.height) return;
            boolean temp =  this.isSpecialPlant();
            if(this.normalPlantsNum == this.map.width * this.map.height - (this.map.width) * numOfZones){
                temp = true;
            }
            if(temp && this.specialPlantsNum < (this.map.width) * numOfZones){
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                //System.out.println("lol:"+equatorStart.y+" "+equatorEnd.y);
                int potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);

                while(this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
                    potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                    potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);
                }
                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
                this.specialPlantsNum += 1;
            }else{
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                int potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);

                while (this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null || (potentialNewGrassFieldY >= this.equatorStart.y && potentialNewGrassFieldY <= this.equatorEnd.y)){
                    potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                    potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);
                }
                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
                this.normalPlantsNum += 1;
            }
        }
    }
    @Override
    public void plant() {
        for (int i = 0; i < this.map.config.getNewPlants(); i++) {
            if(normalPlantsNum + specialPlantsNum == this.map.width * this.map.height) return;
            boolean temp =  this.isSpecialPlant();
            if(this.normalPlantsNum == this.map.width * this.map.height - (this.map.width) * numOfZones){
                temp = true;
            }
            if(temp && this.specialPlantsNum < (this.map.width) * numOfZones){
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                //System.out.println("lol:"+equatorStart.y+" "+equatorEnd.y);
                int potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);

                while(this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
                    potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                    potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);
                }
                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
                this.specialPlantsNum += 1;
            }else{
                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                int potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);

                while (this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null || (potentialNewGrassFieldY >= this.equatorStart.y && potentialNewGrassFieldY <= this.equatorEnd.y)){
                    potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
                    potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);
                }
                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
                this.normalPlantsNum += 1;
            }
        }
    }

    @Override
    public boolean isSpecialPlant() {
        //return this.map.generateNumber(0,9) < 8;
        return this.map.generateNumber(0,5) <= 4;
        //return this.map.generateNumber(0,totalWeights-1) < this.specialWeights-1;

    }

    public boolean isSpecialField(Vector2d position){
        return position.y >= this.equatorStart.y && position.y <= this.equatorEnd.y;
    }

    public void plantEaten(Vector2d position){
        if(isSpecialField(position)){
            this.specialPlantsNum -= 1;
        }else{
            this.normalPlantsNum -= 1;
        }
    }
}

//    @Override
//    public void plant() {
//        System.out.println(this.equatorStart.y + " " + this.equatorEnd.y);
//        for (int i = 0; i < this.map.config.getNewPlants(); i++) {
//            if(normalPlantsNum + specialPlantsNum == this.map.width * this.map.height) return;
//            boolean temp =  this.isSpecialPlant();
//            if(this.normalPlantsNum == this.map.width * this.map.height - (this.map.width) * numOfZones){
//                temp = true;
//            }
//            if(temp && this.specialPlantsNum < (this.map.width) * numOfZones){
//                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
//                int potentialNewGrassFieldY = this.map.generateNumber(this.equatorStart.y, this.equatorEnd.y);
//
//                int cycle = 0;
//                while(this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
//                    potentialNewGrassFieldX = (potentialNewGrassFieldX + 1)%this.map.width;
//                    cycle += 1;
//                    if(cycle == this.map.width-1){
//                        potentialNewGrassFieldY = (potentialNewGrassFieldY + 1);
//                        if (potentialNewGrassFieldY > equatorEnd.y) potentialNewGrassFieldY = equatorStart.y;
//                        cycle = 0;
//                    }
//                }
//                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
//                this.specialPlantsNum += 1;
//            }else{
//                int potentialNewGrassFieldX = this.map.generateNumber(0, map.width-1);
//                int potentialNewGrassFieldY = this.map.generateNumber(0, map.height-1);
//
//                if(potentialNewGrassFieldY >= this.equatorStart.y && potentialNewGrassFieldY <= this.equatorEnd.y){
//                    potentialNewGrassFieldY = this.equatorEnd.y + 1;
//                }
//                while (this.map.plantAt(new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)) != null){
//                    potentialNewGrassFieldX += 1;
//                    if (potentialNewGrassFieldX == this.map.width){
//                        potentialNewGrassFieldX = 0;
//                        potentialNewGrassFieldY = (potentialNewGrassFieldY + 1)%this.map.height;
//                    }
//                    if(potentialNewGrassFieldY >= this.equatorStart.y && potentialNewGrassFieldY <= this.equatorEnd.y){
//                        potentialNewGrassFieldY = this.equatorEnd.y + 1;
//                    }
//                }
//                this.map.addPlant(new Plant(this.map, new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY)), new Vector2d(potentialNewGrassFieldX, potentialNewGrassFieldY));
//                this.normalPlantsNum += 1;
//            }
//        }
//    }
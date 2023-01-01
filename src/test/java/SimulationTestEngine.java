import logic.*;
import logic.enums.MapDirection;

public class SimulationTestEngine {
    private AbstractWorldMap map;
    private int params;
    public int year;

    public SimulationTestEngine(AbstractWorldMap map, Animal[] animals, int params){
        int index = 0;
        for ( Animal animal : animals) {
            map.initAnimal(animal);
            index += 1;
        }
        this.map = map;
        this.params = params;
        this.year = 0;
    }

    public void run(){
        while (this.map.getAnimalCount() > 0){
            System.out.print(this.map);
            if(params == 1){
                this.map.kill();
                this.map.move();
            }if(params == 2){
                this.map.kill();
                this.map.move();
                if(this.map.getAnimals().get(new Vector2d(2,2)).size() == 2){
                    this.map.multiplicate();
                    return;
                }
            }if(params == 3){
                this.map.move();
                this.map.kill();
                this.map.grow();
                this.year += 1;
            }
            if(params == 4) {
                this.map.grow();
                this.map.consume();
                return;
            }if(params == 5){
                this.map.grow();
                return;
            }

        }
    }
}
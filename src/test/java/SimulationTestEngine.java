import logic.*;
import logic.enums.MapDirection;

public class SimulationTestEngine {
    private AbstractWorldMap map;

    public SimulationTestEngine(AbstractWorldMap map, Animal[] animals){
        int index = 0;
        for ( Animal animal : animals) {
            map.initAnimal(animal);
            index += 1;
        }
        this.map = map;
    }

    public void run(){
        while (this.map.getAnimalCount() > 0){
            System.out.print(this.map);
            this.map.kill();
            this.map.move();
        }
    }
}
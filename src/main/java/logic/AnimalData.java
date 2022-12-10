package logic;

public class AnimalData {
    private final int[] genome;
    private final int activeated;
    private final int energy;
    private final int eaten;
    private final int children;
    private final int lives;
    private final int died;

    public AnimalData(int[] genome, int activeated, int energy, int eaten, int children, int lives, int died) {
        this.genome = genome;
        this.activeated = activeated;
        this.energy = energy;
        this.eaten = eaten;
        this.children = children;
        this.lives = lives;
        this.died = died;
    }
}

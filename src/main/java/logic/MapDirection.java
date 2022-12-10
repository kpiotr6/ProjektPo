package logic;


public enum MapDirection {
    N(0),
    NE(1),
    E(2),
    SE(3),
    S(4),
    SW(5),
    W(6),
    NW(7);

    private static final MapDirection[] directions  = MapDirection.values();
    private int order;
    private MapDirection(int i){
        this.order = i;
    }
    Vector2d toUnitVector(){
        return switch (this){
            case N -> new Vector2d(0,1);
            case NE -> new Vector2d(1,1);
            case E -> new Vector2d(1,0);
            case SE -> new Vector2d(1,-1);
            case S -> new Vector2d(0,-1);
            case SW -> new Vector2d(-1,-1);
            case W -> new Vector2d(-1,0);
            case NW -> new Vector2d(-1,1);
        };
    }
    public MapDirection turn(int i){
        int newOrder = (this.order + i)%this.directions.length;
        return(directions[newOrder]);
    }
    public static MapDirection fromNumber(int i){
        return directions[i];
    }
}
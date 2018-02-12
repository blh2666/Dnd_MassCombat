import java.util.ArrayList;

public class Tile {

    private Unit unit;
    public int distance;
    private ArrayList<Tile> Connections;
    public enum terrain{open,difficult,restricted}
    private terrain type;
    public boolean visited;
    public Tile Predecessor;
    private int x;
    private int y;

    public Tile(terrain type, int x,int y){
        this.unit = null;
        this.distance = 0;
        this.Connections = new ArrayList<>();
        this.type = type;
        this.x = x;
        this.y = y;

    }

    public void addConnections(Tile other){
        this.Connections.add(other);
    }

    public boolean hasEnemy(String factionName){
        if (unit == null){
            return false;
        }
        else if (unit.getAffiliation().equals(factionName)){
            return false;
        }
        else{
            return true;
        }
    }

    public Unit getUnit(){
        return unit;
    }

    public void setUnit(Unit unit){
        this.unit = unit;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public terrain getType() {
        return type;
    }

    public boolean hasEnemy(Unit other){
        if (unit == null){
            return false;
        }
        else{
            return unit.isEnemy(other);
        }
    }

    public boolean hasUnit(){
        if (unit == null){
            return false;
        }
        else{

            return true;
        }
    }

    public void setType(terrain type){
        this.type = type;
    }

    @Override
    public String toString() {
        if (unit == null){
            if (type == terrain.open){
                return "__";
            }
            else if (type == terrain.difficult){
                return "==";
            }
            else{
                return "XX";
            }
        }
        else{
            return unit.getInitials();
        }
    }
}

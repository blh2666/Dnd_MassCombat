
import java.util.ArrayList;

public class Faction {
    private String name;
    private ArrayList<Unit> units;
    public enum orientation{top,bottom,left,right,center}
    private orientation start;
    private Unit.State state;

    public Faction(String name, Unit.State state){
        units = new ArrayList<>();
        this.name = name;
        this.start =start;
        this.state = state;
    }

    public void addUnits(int numUnits, String name, int maxHP, int AC, int hitMod, int damageDice, int damageDiceNum, int damageMod, int numAttacks, int range ,int move){
        for (int i = 0; i< numUnits; i++){
            Unit newUnit = new Unit(this.name.charAt(0)+name+String.valueOf(i),maxHP,AC,hitMod,damageDice,damageDiceNum,damageMod,numAttacks,range,move);
            newUnit.setState(state);
            newUnit.setAffiliation(this.name);
            units.add(newUnit);
        }
    }

    public String getName(){
        return name;
    }

    public orientation getStart(){
        return start;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void damageUnit(int damage){

        for (Unit unit:
             units) {
            if (unit.getState() != Unit.State.Dead){
                System.out.println(unit.takeDamage(damage));
                break;
            }
        }
    }
}

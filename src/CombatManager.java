import java.util.*;

public class CombatManager {
    private ArrayList<Unit> units;
    private Map map;
    private ArrayList<Faction> factions;

    public CombatManager(ArrayList<Faction> factions,Map map){

        this.map = map;
        this.factions = factions;
        units = new ArrayList<>();

        for (Faction faction:
             factions) {
            units.addAll(faction.getUnits());
        }

        Collections.shuffle(units);


    }

    public void UnitAttacks(Unit attacker, Unit defender){
        ArrayList<String> results = attacker.attack(defender);

        for (String attack:
             results) {
            System.out.println(attack);
        }

        if (defender.getState().equals(Unit.State.Dead)){

            //units.remove(defender);
            map.setUnit(defender.getX(),defender.getY(),null);
        }

    }

    public void removeDeadCombatants(){
        for (int i = 0; i < units.size(); i++){
            Unit unit = units.get(i);
            if (unit.getState() == Unit.State.Dead){
                System.out.println(unit.getName()+ " was killed");
                units.remove(unit);
                i -= 1;
            }
        }
    }

    public boolean IsGoing(){
        int units = 0;
        for (Faction faction:
             factions) {
            for (Unit unit:
                 faction.getUnits()) {
                if (unit.getState() != Unit.State.Dead){
                    units += 1;
                }

            }
            if (units == 0){
                return false;
            }
            else {
                units = 0;
            }
        }
        return true;
    }

    public void startCombat(){

        System.out.println("Battlefield overview");
        System.out.println(map.toString());
        Scanner Reader = new Scanner(System.in);
        int round = 1;
        Iterator<Unit> initiative = units.iterator();
        Unit activeUnit;
        Random random = new Random();

        while (IsGoing()) {

            for (int initiativeOrder = 0; initiativeOrder <units.size(); initiativeOrder++){
                activeUnit = units.get(initiativeOrder);

                if (activeUnit.getState().equals(Unit.State.Defending)) {

                    ArrayList<Unit> enemies = map.hasEnemies(activeUnit);

                    if (enemies.size() > 0) {

                        UnitAttacks(activeUnit, enemies.get(random.nextInt(enemies.size())));
                    }

                } else if (activeUnit.getState().equals(Unit.State.Attacking)) {

                    ArrayList<Unit> enemies = map.hasEnemies(activeUnit);

                    if (enemies.size() > 0) {

                        UnitAttacks(activeUnit, enemies.get(random.nextInt(enemies.size())));
                    }
                    else{
                        int[] coord = map.closestEnemy(activeUnit);
                        if (coord != null) {
                            coord = map.PathTowards(activeUnit, coord);
                            map.Move(coord[0], coord[1], activeUnit);

                            enemies = map.hasEnemies(activeUnit);

                            if (enemies.size() > 0) {

                                UnitAttacks(activeUnit, enemies.get(random.nextInt(enemies.size())));
                            }
                        }
                    }

                } else if (activeUnit.getState().equals(Unit.State.Fleeing)) {


                }
            }
            removeDeadCombatants();
            System.out.println("Battlefield overview");
            System.out.println(map.toString());
            System.out.println("round "+String.valueOf(round)+" has concluded, input anything to continue");
            Reader.next();
            round++;


        }

    }
}

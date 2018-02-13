import java.util.ArrayList;
import java.util.Random;

public class Unit {

    private String name;
    private int MaxHP;
    private int currHP;
    private int AC;
    private int hitMod;
    private int damageDice;
    private int damageDiceNum;
    private int damageMod;
    private int numAttacks;
    private int range;
    private Random rand;
    private String affiliation;
    private int x;
    private int y;
    public enum State{Attacking,Defending,Fleeing,Dead}
    private State state;
    private int move;

    public Unit(String name, int maxHP, int AC, int hitMod, int damageDice, int damageDiceNum, int damageMod, int numAttacks, int range ,int move){
        this.name = name;
        this.MaxHP = maxHP;
        this.currHP = maxHP;
        this.AC = AC;
        this.hitMod = hitMod;
        this.damageDice = damageDice;
        this.damageDiceNum = damageDiceNum;
        this.damageMod = damageMod;
        this.numAttacks = numAttacks;
        this.range = range;
        this.rand = new Random();
        this.move = move;
    }

    public ArrayList<String> attack(Unit other) {
        ArrayList<String> outcomes = new ArrayList<>();

        for (int n = 0; n < numAttacks; n++) {

            int attackRoll = rand.nextInt(20) + 1;
            //System.out.println(attackRoll);
            int totalDamage = 0;

            if (attackRoll == 1) {
                outcomes.add( this.name + " attacks " + other.name + " and critically fails");
            } else if (attackRoll == 20) {

                for (int i = 0; i < damageDiceNum * 2; i++) {
                    totalDamage += rand.nextInt(damageDice) + 1;
                }
                totalDamage += damageMod;

                other.takeDamage(totalDamage);

                outcomes.add(  this.name + " attacks " + other.name + " and crits, dealing " + String.valueOf(totalDamage) + " damage");
            } else if (attackRoll + hitMod > other.AC) {

                for (int i = 0; i < damageDiceNum; i++) {
                    totalDamage += rand.nextInt(damageDice) + 1;
                }
                totalDamage += damageMod;

                other.takeDamage(totalDamage);

                outcomes.add(this.name + " attacks " + other.name + " and hits, dealing " + String.valueOf(totalDamage) + " damage");
            } else {
                outcomes.add(this.name + " attacks " + other.name + " and misses");
            }
        }
        return outcomes;
    }

    public String takeDamage(int damage){
        currHP -= damage;

        if (currHP < 0){
            state = State.Dead;
        }/*
        else if (currHP < MaxHP*0.25){
            state = State.Fleeing;
        }*/

        return this.name+" takes "+Integer.toString(damage)+" damage and is "+state;

    }

    public void setAffiliation(String FactionName){
        this.affiliation = FactionName;
    }

    public String getAffiliation(){
        return affiliation;
    }

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getRange(){
        return range;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public String getInitials(){
        return name.substring(0,2);
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public boolean isEnemy(Unit other){
        if (other.affiliation != this.affiliation){
            return true;
        }
        else{
            return false;
        }
    }

    public int getMove(){
        return move;
    }

    public String getName() {
        return name;
    }
}

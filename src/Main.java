import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main (String[] args){

        Scanner Reader = new Scanner(System.in);

        Boolean settingUp = true;
        Hashtable<String,int[]> unitTypes = new Hashtable<>();
        String[] inputs;
        int[] values;
        Map map = new Map(1,1,null);
        ArrayList factions = new ArrayList();
        //while (settingUp) {

            System.out.println("Enter combat specification file location: ");
            String filename = Reader.next();

            try(Scanner in = new Scanner(new File(filename))){
                String line = in.nextLine();
                line = in.nextLine();
                line = in.nextLine();

                while (!line.substring(0,1).equals("#")){
                    System.out.println("unit input");
                    inputs = line.split(" ");
                    values = new int[9];
                    values[0] = Integer.valueOf(inputs[1]);
                    values[1] = Integer.valueOf(inputs[2]);
                    values[2] = Integer.valueOf(inputs[3]);
                    values[3] = Integer.valueOf(inputs[4]);
                    values[4] = Integer.valueOf(inputs[5]);
                    values[5] = Integer.valueOf(inputs[6]);
                    values[6] = Integer.valueOf(inputs[7]);
                    values[7] = Integer.valueOf(inputs[8]);
                    values[8] = Integer.valueOf(inputs[9]);
                    unitTypes.put(inputs[0],values);
                    line = in.nextLine();
                }
                System.out.println("past unit input");
                in.nextLine();

                line = in.nextLine();
                line = in.nextLine();

                inputs = line.split(" ");

                Faction faction1 = new Faction(null,null);

                if (inputs[1].equals("Attacking")){
                    faction1 = new Faction(inputs[0],Unit.State.Attacking);
                }
                else if (inputs[1].equals("Defending")){
                    faction1 = new Faction(inputs[0],Unit.State.Defending);
                }
                else{
                    System.out.println("Unknown state");
                }

                System.out.println("past faction 1 input");

                in.nextLine();
                in.nextLine();
                in.nextLine();
                line = in.nextLine();

                while (!line.substring(0,1).equals("#")){

                    inputs = line.split(" ");
                    int num = Integer.valueOf(inputs[1]);

                    int[] unitType = unitTypes.get(inputs[0]);

                    faction1.addUnits(num,inputs[0],unitType[0],unitType[1],unitType[2],unitType[3],unitType[4],unitType[5],unitType[6],unitType[7],unitType[8]);
                    line = in.nextLine();
                }

                System.out.println("past faction1 troop input");
                in.nextLine();
                line = in.nextLine();
                line = in.nextLine();

                inputs = line.split(" ");

                Faction faction2 = new Faction(null,null);

                if (inputs[1].equals("Attacking")){
                    faction2 = new Faction(inputs[0],Unit.State.Attacking);
                }
                else if (inputs[1].equals("Defending")){
                    faction2 = new Faction(inputs[0],Unit.State.Defending);
                }
                else{
                    System.out.println("Unknown state");
                }

                in.nextLine();
                in.nextLine();
                in.nextLine();
                line = in.nextLine();

                while (!line.substring(0,1).equals("#")){

                    inputs = line.split(" ");
                    int num = Integer.valueOf(inputs[1]);

                    int[] unitType = unitTypes.get(inputs[0]);

                    faction2.addUnits(num,inputs[0],unitType[0],unitType[1],unitType[2],unitType[3],unitType[4],unitType[5],unitType[6],unitType[7],unitType[8]);
                    line = in.nextLine();
                }

                factions = new ArrayList<>();
                factions.add(faction1);
                factions.add(faction2);

                in.nextLine();
                in.nextLine();
                line = in.nextLine();

                inputs = line.split(" ");
                map = new Map(Integer.valueOf(inputs[0]),Integer.valueOf(inputs[1]),factions);

                map.placeUnits(Integer.valueOf(inputs[2]));

                in.nextLine();
                in.nextLine();
                in.nextLine();

                while (in.hasNext()){
                    line = in.nextLine();
                    inputs = line.split(" ");
                    values = new int[4];
                    values[0] = Integer.valueOf(inputs[1]);
                    values[1] = Integer.valueOf(inputs[2]);
                    values[2] = Integer.valueOf(inputs[3]);
                    values[3] = Integer.valueOf(inputs[4]);

                    if (inputs[0].equals("open")){
                        map.setTerrain(Tile.terrain.open,values[0],values[1],values[2],values[3]);
                    }
                    else if (inputs[0].equals("difficult")){
                        map.setTerrain(Tile.terrain.difficult,values[0],values[1],values[2],values[3]);
                    }
                    else if (inputs[0].equals("restricted")){
                        map.setTerrain(Tile.terrain.restricted,values[0],values[1],values[2],values[3]);
                    }
                }

                settingUp = false;

            }catch (java.io.FileNotFoundException r){
                System.out.println("File not found \n Please enter again\n");

            }

        /*
        Faction JoeFaction = new Faction("dark protecters", Unit.State.Defending);
        JoeFaction.addUnits(20,"guard",10,14,4,8,1,2,1,60,30);

        Faction GregFaction = new Faction("bloody claws", Unit.State.Attacking);
        GregFaction.addUnits(10,"werewolf",58,12,4,4,2,2,3,5,30);

        ArrayList<Faction> factions = new ArrayList<>();
        factions.add(JoeFaction);
        factions.add(GregFaction);
        Map map = new Map(20,20,factions);

        map.setTerrain(Tile.terrain.restricted,0,3,18,6);

        map.placeUnits(15);
        */

        CombatManager Manager = new CombatManager(factions,map);

        Manager.startCombat();
    }
}

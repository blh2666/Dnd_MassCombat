import java.util.ArrayList;

public class Map {

    private Tile[][] map;
    private ArrayList<Faction> factions;
    private int xMax;
    private int yMax;

    public Map(int x, int y, ArrayList<Faction> factions) {
        xMax = x;
        yMax = y;
        this.map = new Tile[x][y];
        this.factions = factions;

        //Here it constructs the individual tiles
        for (int i = 0;i < xMax; i++ ){
            for (int j = 0; j < yMax; j++){

                map[i][j] = new Tile(Tile.terrain.open,i,j);

            }
        }

        //Here it adds the neighbors
        for (int i = 0;i < xMax; i++){
            for (int j = 0; j < yMax; j++){

                if (i > 0){
                    map[i][j].addConnections(map[i-1][j]);
                }
                if (j > 0){
                    map[i][j].addConnections(map[i][j-1]);
                }
                if (i < xMax-1){
                    map[i][j].addConnections(map[i+1][j]);
                }
                if (j < yMax-1){
                    map[i][j].addConnections(map[i][j+1]);
                }

            }
        }
    }

    public boolean placeUnits(int troopLength){
        Faction faction1 =factions.get(0);

        int offset = (xMax-troopLength)/2;

        int x = 0;
        int y = 0;
        for (Unit unit:
             faction1.getUnits()) {
            setUnit(x+offset,y,unit);

            x++;

            if (x > troopLength){
                x= 0;
                y++;
            }

        }

        Faction faction2 = factions.get(1);

        x = 0;
        y = yMax-1;

        for (Unit unit:
                faction2.getUnits()) {
            setUnit(x+offset,y,unit);

            x++;

            if (x > troopLength){
                x= 0;
                y--;
            }

        }

        return true;
    }

    public void setTerrain(Tile.terrain type,int startX, int startY, int EndX, int EndY){
        int xDuration = EndX-startX;
        int yDuration = EndY-startY;

        for (int i = 0;i <= xDuration; i++){
            for (int j = 0;j <= yDuration;j++){
                map[startX+i][startY+j].setType(type);
            }
        }
    }

    public void setUnit(int x, int y, Unit unit){
        map[x][y].setUnit(unit);
        if (unit != null) {
            unit.setX(x);
            unit.setY(y);
        }
    }

    public void Move(int x,int y,Unit unit){
        map[unit.getX()][unit.getY()].setUnit(null);

        map[x][y].setUnit(unit);
        unit.setX(x);
        unit.setY(y);

    }

    public int[] closestEnemy(Unit unit){
        int startX = unit.getX();
        int startY = unit.getY();

        String faction = unit.getAffiliation();

        int CurrX = 1;
        int CurrY = 0;

        int[] found = new int[2];

        while (CurrX <= xMax+yMax){

            while (CurrX > 0) {

                if (startX+CurrX < xMax && startY + CurrY < yMax) {

                    if (map[startX + CurrX][startY + CurrY].hasEnemy(faction)) {

                        found[0] = startX+CurrX;
                        found[1] = startY+CurrY;
                        return found;
                    }
                }

                CurrX--;
                CurrY++;
            }

            while (CurrY > 0){

                if (startX+CurrX < xMax && startY - CurrY >= 0) {

                    if (map[startX + CurrX][startY - CurrY].hasEnemy(faction)) {

                        found[0] = startX+CurrX;
                        found[1] = startY-CurrY;
                        return found;
                    }

                }

                CurrX++;
                CurrY--;

            }

            while (CurrX > 0){

                if (startX - CurrX >= 0 && startY - CurrY >= 0) {

                    if (map[startX - CurrX][startY - CurrY].hasEnemy(faction)) {

                        found[0] = startX-CurrX;
                        found[1] = startY-CurrY;
                        return found;
                    }

                }

                CurrX--;
                CurrY++;

            }

            while (CurrY > 0){

                if (startX-CurrX >= 0 && startY + CurrY < yMax) {

                    if (map[startX - CurrX][startY + CurrY].hasEnemy(faction)) {

                        found[0] = startX-CurrX;
                        found[1] = startY+CurrY;
                        return found;
                    }

                }

                CurrX++;
                CurrY--;

            }

            CurrX++;

        }
        return null;
    }

    public ArrayList<Unit> hasEnemies(Unit unit) {
        int range = (unit.getRange())/5;

        int startX = unit.getX();
        int startY = unit.getY();

        String faction = unit.getAffiliation();

        int CurrX = 1;
        int CurrY = 0;

        ArrayList<Unit> enemies = new ArrayList<>();

        while (CurrX <= range){

            while (CurrX > 0) {

                if (startX+CurrX < xMax && startY + CurrY < yMax) {

                    if (map[startX + CurrX][startY + CurrY].hasEnemy(faction)) {

                        enemies.add(map[startX + CurrX][startY + CurrY].getUnit());
                    }
                }

                CurrX--;
                CurrY++;
            }

            while (CurrY > 0){

                if (startX+CurrX < xMax && startY - CurrY >= 0) {

                    if (map[startX + CurrX][startY - CurrY].hasEnemy(faction)) {

                        enemies.add(map[startX + CurrX][startY - CurrY].getUnit());
                    }

                }

                CurrX++;
                CurrY--;

            }

            while (CurrX > 0){

                if (startX - CurrX >= 0 && startY - CurrY >= 0) {

                    if (map[startX - CurrX][startY - CurrY].hasEnemy(faction)) {

                        enemies.add(map[startX - CurrX][startY - CurrY].getUnit());
                    }

                }

                CurrX--;
                CurrY++;

            }

            while (CurrY > 0){

                if (startX-CurrX >= 0 && startY + CurrY < yMax) {

                    if (map[startX - CurrX][startY + CurrY].hasEnemy(faction)) {

                        enemies.add(map[startX - CurrX][startY + CurrY].getUnit());
                    }

                }

                CurrX++;
                CurrY--;

            }

            CurrX++;

        }

        return enemies;
    }

    public int[] PathTowards(Unit unit, int[] destination){
        ArrayList<Tile> toVisit = new ArrayList<>();

        for (int x = 0; x < xMax; x++){
            for (int y = 0; y < yMax; y++){
                map[x][y].Predecessor = null;
                map[x][y].distance = 999999999;
                toVisit.add(map[x][y]);
            }
        }

        map[unit.getX()][unit.getY()].distance = 0;

        while (!toVisit.isEmpty()){

            int smallestIndex = 0;
            int smallestDistance = 999999999;
            for (int i = 0; i < toVisit.size(); i++){
                if (toVisit.get(i).distance < smallestDistance){
                    smallestIndex = i;
                    smallestDistance = toVisit.get(i).distance;
                }
            }

            Tile smallest = toVisit.remove(smallestIndex);
            int smallX = smallest.getX();
            int smallY = smallest.getY();

            if (smallX+1 < xMax){
                if (map[smallX+1][smallY].getType() == Tile.terrain.open &&
                        !map[smallX+1][smallY].hasUnit()){
                    if (map[smallX+1][smallY].distance > smallest.distance+5){
                        map[smallX+1][smallY].distance = smallestDistance+5;
                        map[smallX+1][smallY].Predecessor = smallest;
                    }
                }
                else if (map[smallX+1][smallY].getType() == Tile.terrain.difficult &&
                        !map[smallX+1][smallY].hasUnit()){
                    if (map[smallX+1][smallY].distance > smallest.distance+10){
                        map[smallX+1][smallY].distance = smallestDistance+10;
                        map[smallX+1][smallY].Predecessor = smallest;
                    }
                }
                else if (map[smallX+1][smallY].hasUnit()){
                    if (map[smallX+1][smallY].distance > smallest.distance+1000){
                        map[smallX+1][smallY].distance = smallestDistance+1000;
                        map[smallX+1][smallY].Predecessor = smallest;
                    }
                }
            }
            if (smallX-1 > 0){
                if (map[smallX-1][smallY].getType() == Tile.terrain.open &&
                        !map[smallX-1][smallY].hasUnit()){
                    if (map[smallX-1][smallY].distance > smallest.distance+5){
                        map[smallX-1][smallY].distance = smallestDistance+5;
                        map[smallX-1][smallY].Predecessor = smallest;
                    }
                }
                else if (map[smallX-1][smallY].getType() == Tile.terrain.difficult &&
                        !map[smallX-1][smallY].hasUnit()){
                    if (map[smallX-1][smallY].distance > smallest.distance+10){
                        map[smallX-1][smallY].distance = smallestDistance+10;
                        map[smallX-1][smallY].Predecessor = smallest;
                    }
                }
                else if (map[smallX-1][smallY].hasUnit()){
                    if (map[smallX-1][smallY].distance > smallest.distance+1000){
                        map[smallX-1][smallY].distance = smallestDistance+1000;
                        map[smallX-1][smallY].Predecessor = smallest;
                    }
                }
            }
            if (smallY+1 < yMax){
                if (map[smallX][smallY+1].getType() == Tile.terrain.open &&
                        !map[smallX][smallY+1].hasUnit()){
                    if (map[smallX][smallY+1].distance > smallest.distance+5){
                        map[smallX][smallY+1].distance = smallestDistance+5;
                        map[smallX][smallY+1].Predecessor = smallest;
                    }
                }
                else if (map[smallX][smallY+1].getType() == Tile.terrain.difficult &&
                        !map[smallX][smallY+1].hasUnit()){
                    if (map[smallX][smallY+1].distance > smallest.distance+10){
                        map[smallX][smallY+1].distance = smallestDistance+10;
                        map[smallX][smallY+1].Predecessor = smallest;
                    }
                }
                else if (map[smallX][smallY+1].hasUnit()){
                    if (map[smallX][smallY+1].distance > smallest.distance+1000){
                        map[smallX][smallY+1].distance = smallestDistance+1000;
                        map[smallX][smallY+1].Predecessor = smallest;
                    }
                }
            }
            if (smallY-1 >= 0){
                if (map[smallX][smallY-1].getType() == Tile.terrain.open &&
                        !map[smallX][smallY-1].hasUnit()){
                    if (map[smallX][smallY-1].distance > smallest.distance+5){
                        map[smallX][smallY-1].distance = smallestDistance+5;
                        map[smallX][smallY-1].Predecessor = smallest;
                    }
                }
                else if (map[smallX][smallY-1].getType() == Tile.terrain.difficult &&
                        !map[smallX][smallY-1].hasUnit()){
                    if (map[smallX][smallY-1].distance > smallest.distance+10){
                        map[smallX][smallY-1].distance = smallestDistance+10;
                        map[smallX][smallY-1].Predecessor = smallest;
                    }
                }
                else if (map[smallX][smallY-1].hasUnit()){
                    if (map[smallX][smallY-1].distance > smallest.distance+1000){
                        map[smallX][smallY-1].distance = smallestDistance+1000;
                        map[smallX][smallY-1].Predecessor = smallest;
                    }
                }
            }

        }

        Tile closest;

        closest = map[destination[0]][destination[1]];

        while (closest.distance > unit.getMove() || closest.getUnit() != null){
            if (closest.Predecessor == null){
                break;
            }
            closest = closest.Predecessor;
        }

        destination[0] = closest.getX();
        destination[1] = closest.getY();

        return destination;
    }

    @Override
    public String toString() {
        String grid = new String();

        for (int j = 0; j < yMax; j++) {
            for (int i = 0; i < xMax; i++){

                grid += map[i][j].toString();

            }
            grid += "\n";
        }

        return grid;
    }
}

import java.awt.Graphics;
import java.util.ArrayList;

public class towerManager {
    private ArrayList<Tower> towers;
    private int money = 0;
    
    public towerManager() {
        towers = new ArrayList<>();
    }
    
    public boolean placeTower(Tower tower, int x, int y) {
        if (money >= tower.getCost()) {
            towers.add(tower);
            tower.setPosition(x, y);
            money -= tower.getCost();
            return true;
        }
        else {
            System.out.println("Not enough money to place tower.");
            return false;
        }
    }
    
    public void updateTowers(ArrayList<Enemy> enemies) {
        for (Tower tower : towers) {
            tower.update(enemies);
        }
    }

    public void addMoney(int amount) {
        money += amount;
    }
    
    public int getMoney() {
        return money;
    }
    
    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public void drawTowers(Graphics g) {
        for (Tower tower : towers) {
            tower.draw(g);
        }
    }
}

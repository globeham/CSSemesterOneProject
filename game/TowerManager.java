/**
 * TowerManager
 * 
 * Manages tower placement, updates, and projectile management.
 * Handles player money, tower collections, and coordinate updates for all active towers.
 * Manages projectiles spawned by towers and validates tower placement before purchase.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.Graphics;
import java.util.ArrayList;

public class TowerManager {
    private ArrayList<Tower> towers;
    private ArrayList<Projectile> projectiles;
    private int money = 0;
    
    public TowerManager() {
        towers = new ArrayList<>();
        projectiles = new ArrayList<>();
    }
    
    // places tower
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
    
    // updates game cycle
    public void updateTowers(ArrayList<Enemy> enemies) {
        for (Tower tower : towers) {
            tower.update(enemies, this);
        }
        
        // Update projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.update();
            if (!p.isActive()) {
                projectiles.remove(i);
            }
        }
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    // adds money
    public void addMoney(int amount) {
        money += amount;
    }
    
    // getters
    public int getMoney() {
        return money;
    }
    
    public ArrayList<Tower> getTowers() {
        return towers;
    }

    // draws all towers
    public void drawTowers(Graphics g) {
        for (Tower tower : towers) {
            tower.draw(g);
        }
        
        // Draw projectiles
        for (Projectile p : projectiles) {
            p.draw(g);
        }
    }
    
    // adds money
    public void addMoneyFromEnemy(Enemy enemy) {
        money += enemy.getReward();
    }
}

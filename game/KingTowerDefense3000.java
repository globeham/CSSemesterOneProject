import javax.swing.*;
import java.awt.*;

public class KingTowerDefense3000 extends JFrame {
    private gameMap map;
    private GamePanel gamePanel;
    private EnemyManager enemyManager;
    private Timer gameTimer;
    private towerManager towerManager = new towerManager();
    private int numberOfWaves = 5;
    
    public KingTowerDefense3000() {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        map = new gameMap(1000, 800);

        towerManager = new towerManager();
        towerManager.addMoney(200);

        String routeCode = "RRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDRRRRRRRR";
        map.setPath(routeCode, 0, 185);

        enemyManager = new EnemyManager(map.getPath());
        
        gamePanel = new GamePanel(map, enemyManager, towerManager); 
        
        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                placeTower(e.getX(), e.getY());
            }
        });
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);

        startGameLoop();
        enemyManager.spawnWave();
    }

    private void placeTower(int x, int y) {
        int adjustedY = y - getInsets().top;

        int minDistanceFromPath = 50; 
        if (map.isTooCloseToPath(x, adjustedY, minDistanceFromPath)) {
            System.out.println("Too close to path — place tower further away.");
            return;
        }
        
        Tower tower = new Tower(30, 100, 10, 50, Color.BLUE);
        
        if (towerManager.placeTower(tower, x, adjustedY)) {
            System.out.println("Tower placed at (" + x + ", " + adjustedY + ")! Money: " + towerManager.getMoney());
            gamePanel.repaint();
        } 
        else {
            System.out.println("Not enough money! Need: " + tower.getCost() + ", Have: " + towerManager.getMoney());
        }
    }


    private void startGameLoop() {
        Timer gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }
    
    private void updateGame() {
        enemyManager.updateEnemies();
        
        if (enemyManager.isWaveComplete() && enemyManager.getCurrentWave() < numberOfWaves) {
            enemyManager.spawnWave();
        }
            
            gamePanel.repaint();
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
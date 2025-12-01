import javax.swing.*;
import java.awt.*;

public class KingTowerDefense3000 extends JFrame {
    private gameMap map;
    private GamePanel gamePanel;
    private EnemyManager enemyManager;
    private Timer gameTimer;
    
    public KingTowerDefense3000() {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        map = new gameMap(800, 600);

        /* 
        String routeCode = "";
        for (int i = 0; i < 100; i++) {
            int rand = (int)(Math.random()* 100) + 1;
            if (rand <= 25) {
                routeCode += "U";
            }
            else if (rand <= 50) {
                routeCode += "D";
            }
            else if (rand <= 75) {
                routeCode += "R";
            }
            else {
                routeCode += "L";
            }
        }
    */

        String routeCode = "DDDDDRRRRRRUUUUUULLLLLDDDDDDRRRRUUULLDDDRRUULLDDRRUU";
        //String routeCode = "DDDRRRRRRUUULLLLLLDDDDRRRRRRUUUULLLLLLDDDDRRRRRUUUU";
        map.setPath(routeCode, 100, 100);

        enemyManager = new EnemyManager(map.getPath());
        
        gamePanel = new GamePanel(map);
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void startGameLoop() {
        Timer gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }
    
    private void updateGame() {
        enemyManager.updateEnemies();
        
        
        if (enemyManager.isWaveComplete()) {
            int nextWave = enemyManager.getCurrentWave() + 1;
            enemyManager.spawnWave();
        }
        
        gamePanel.repaint();
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
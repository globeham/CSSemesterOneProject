import javax.swing.*;

public class KingTowerDefense3000 extends JFrame {
    private GameMap map;
    private GamePanel gamePanel;
    private EnemyManager enemyManager;
    private Timer gameTimer;
    private TowerManager TowerManager = new TowerManager();
    private int numberOfWaves = 5;
    
    public KingTowerDefense3000() {
        setTitle("Tower Defense Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        map = new GameMap(1000, 800);

        TowerManager = new TowerManager();
        TowerManager.addMoney(200);

        String routeCode = "RRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDRRRRRRRR";
        map.setPath(routeCode, 0, 185);

        enemyManager = new EnemyManager(map.getPath());
        
        gamePanel = new GamePanel(map, enemyManager, TowerManager); 
        
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
        
        // Create tower options with images
        String[] towerNames = {"Knight", "Devil", "King", "Ninja", "Wizard"};
        String[] towerImages = {
            "images/knight.gif",
            "images/devil.gif",
            "images/king.gif",
            "images/ninja.gif",
            "images/wizard.gif"
        };
        
        Icon[] icons = new Icon[5];
        for (int i = 0; i < 5; i++) {
            try {
                icons[i] = new ImageIcon(towerImages[i]);
            } catch (Exception e) {
                icons[i] = null;
            }
        }
        
        // Create button array with icons and labels
        Object[] buttons = new Object[5];
        buttons[0] = new JButton("Knight");
        buttons[1] = new JButton("Devil");
        buttons[2] = new JButton("King");
        buttons[3] = new JButton("Ninja");
        buttons[4] = new JButton("Wizard");
        
        for (int i = 0; i < 5; i++) {
            if (icons[i] != null) {
                ((JButton)buttons[i]).setIcon(icons[i]);
            }
            ((JButton)buttons[i]).setFocusPainted(false);
        }
        
        int choice = JOptionPane.showOptionDialog(this,
                "Choose a tower to place:",
                "Tower Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                buttons,
                buttons[0]);
        
        if (choice == -1) {
            return; // User cancelled
        }
        
        Tower tower = null;
        switch (choice) {
            case 0: // Knight
                tower = new Knight(0, 0, 0, 0, null);
                break;
            case 1: // Devil
                tower = new Devil(0, 0, 0, 0, null);
                break;
            case 2: // King
                tower = new King(0, 0, 0, 0, null);
                break;
            case 3: // Ninja
                tower = new Ninja(0, 0, 0, 0, null);
                break;
            case 4: // Wizard
                tower = new Wizard(0, 0, 0, 0, null);
                break;
        }
        
        if (tower != null && TowerManager.placeTower(tower, x, adjustedY)) {
            System.out.println("Tower placed at (" + x + ", " + adjustedY + ")! Money: " + TowerManager.getMoney());
            gamePanel.repaint();
        } 
        else if (tower != null) {
            System.out.println("Not enough money! Need: " + tower.getCost() + ", Have: " + TowerManager.getMoney());
        }
    }


    private void startGameLoop() {
        Timer gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }
    
    private void updateGame() {

        enemyManager.updateEnemies(TowerManager);
        TowerManager.updateTowers(enemyManager.getEnemies());
        
        if (enemyManager.isWaveComplete() && enemyManager.getCurrentWave() < numberOfWaves) {
            enemyManager.spawnWave();
        }
            
            gamePanel.repaint();
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
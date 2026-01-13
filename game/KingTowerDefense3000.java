import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KingTowerDefense3000 extends JFrame {
    private GameMap map;
    private GamePanel gamePanel;
    private EnemyManager enemyManager;
    private Timer gameTimer;
    private TowerManager TowerManager = new TowerManager();
    private javax.swing.JButton startWaveButton;
    private javax.swing.JLabel waveLabel;
    private javax.swing.JPanel controlPanel;
    private int numberOfWaves = 5;
    
    // Map definitions
    private MenuPanel.MapInfo[] availableMaps = {
        new MenuPanel.MapInfo("Map 1 - Classic", 
            "RRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDRRRRRRRR",
            0, 185, "images/kingtowerdefense map1.png"),
        new MenuPanel.MapInfo("Map 2 - Spiral", 
            "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRUUUUUUUUUUUUUUUULLLLLLLLLLLLLLDDDDDDDDDDDRRRRRRRRRRRRR",
            162, 0, "images/map2.png"),
        new MenuPanel.MapInfo("Map 3 - Zigzag", 
            "RRRRRRRRRRRRRRUUUUUUUUUUUUUUURRRRRRRRRRRRRDDDDDDDDDDDDDDRRRRRRRRRRRRRRUUUUUUUUUUUUUU",
            50, 200, "images/kingtowerdefense map1.png"),
        new MenuPanel.MapInfo("Map 4 - S-Curve", 
            "RRRRRRRRRRRRRRRRRRRRRRRRRRDDDDDDDDUUUUUUUUUUUUUUUUUURRRRRRRRRRRRRRRRRRRRRRDDDDDDDD",
            0, 100, "images/kingtowerdefense map1.png")
    };
    
    public KingTowerDefense3000() {
        setTitle("King Tower Defense 3000");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        // Show menu screen first
        showMenu();
        
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    private void showMenu() {
        // Create action listeners for each map
        java.awt.event.ActionListener[] mapSelectors = new java.awt.event.ActionListener[availableMaps.length];
        for (int i = 0; i < availableMaps.length; i++) {
            final int mapIndex = i;
            mapSelectors[i] = e -> startGame(mapIndex);
        }
        
        MenuPanel menuPanel = new MenuPanel(availableMaps, mapSelectors);
        menuPanel.setPreferredSize(new Dimension(1000, 800));
        
        // Clear any existing content and show menu
        getContentPane().removeAll();
        add(menuPanel);
        setTitle("King Tower Defense 3000 - Select Map");
    }
    
    private void startGame(int mapIndex) {
        MenuPanel.MapInfo selectedMap = availableMaps[mapIndex];
        
        map = new GameMap(1000, 800);

        TowerManager = new TowerManager();
        TowerManager.addMoney(200);

        map.setPath(selectedMap.routeCode, selectedMap.startX, selectedMap.startY);

        enemyManager = new EnemyManager(map.getPath());
        
        gamePanel = new GamePanel(map, enemyManager, TowerManager, selectedMap.imageFile); 
        
        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                placeTower(e.getX(), e.getY());
            }
        });
        
        // Clear menu and show game
        getContentPane().removeAll();
        add(gamePanel);
        
        // Control panel with Start Wave button
        controlPanel = new JPanel();
        waveLabel = new JLabel("Wave: " + enemyManager.getCurrentWave() + "/" + numberOfWaves);
        startWaveButton = new JButton("Start Wave");
        startWaveButton.addActionListener(e -> {
            if (enemyManager.getCurrentWave() < numberOfWaves && enemyManager.isWaveComplete()) {
                enemyManager.spawnWave();
                startWaveButton.setEnabled(false);
                updateWaveLabel();
                gamePanel.repaint();
            }
        });
        controlPanel.add(waveLabel);
        controlPanel.add(startWaveButton);
        
        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.addActionListener(e -> {
            if (gameTimer != null) {
                gameTimer.stop();
            }
            showMenu();
            pack();
            setLocationRelativeTo(null);
        });
        controlPanel.add(backToMenuButton);
        
        add(controlPanel, java.awt.BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setTitle("King Tower Defense 3000 - " + selectedMap.name);
        
        startGameLoop();
    }

    private void placeTower(int x, int y) {
        // No need to adjust y anymore since we're working in panel coordinates
        int minDistanceFromPath = 50; 
        if (map.isTooCloseToPath(x, y, minDistanceFromPath)) {
            System.out.println("Too close to path — place tower further away.");
            return;
        }
        
        // Build a small dialog with buttons that place the tower immediately on click
        final int fx = x;
        final int fy = y;
        String[] towerImages = {"images/knight.png", "images/devil.png", "images/king.png", "images/ninja.png", "images/wizard.png"};
        JPanel panel = new JPanel(new java.awt.GridLayout(1, 5, 8, 8));

        JDialog dialog = new JDialog(this, "Choose a tower to place:", true);
        for (int i = 0; i < 5; i++) {
            String name;
            Tower created;
            switch (i) {
                case 0: name = "Knight"; created = new Knight(0,0,0,0,null); break;
                case 1: name = "Devil"; created = new Devil(0,0,0,0,null); break;
                case 2: name = "King"; created = new King(0,0,0,0,null); break;
                case 3: name = "Ninja"; created = new Ninja(0,0,0,0,null); break;
                default: name = "Wizard"; created = new Wizard(0,0,0,0,null); break;
            }

            JButton b = new JButton(name);
            try {
                b.setIcon(new ImageIcon(towerImages[i]));
            } catch (Exception ex) {
                // ignore icon load errors
            }
            b.setFocusPainted(false);
            Tower toPlace = created;
            b.addActionListener(ev -> {
                if (TowerManager.placeTower(toPlace, fx, fy)) {
                    gamePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough money to place tower.");
                }
                dialog.dispose();
            });
            panel.add(b);
        }

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void startGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }
    
    private void updateGame() {
        enemyManager.updateEnemies(TowerManager);
        TowerManager.updateTowers(enemyManager.getEnemies());

        // If the wave is complete, enable Start Wave button (unless we've finished all waves)
        if (enemyManager.isWaveComplete()) {
            if (enemyManager.getCurrentWave() < numberOfWaves) {
                startWaveButton.setEnabled(true);
            } else {
                startWaveButton.setEnabled(false);
                startWaveButton.setText("All Waves Spawned");
            }
        }

        updateWaveLabel();
        gamePanel.repaint();
    }

    private void updateWaveLabel() {
        if (waveLabel != null && enemyManager != null) {
            waveLabel.setText("Wave: " + enemyManager.getCurrentWave() + "/" + numberOfWaves);
        }
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
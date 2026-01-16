import javax.swing.*;
import java.awt.*;

/*
KingTowerDefense3000 is the main game class that manages the game state
*/

public class KingTowerDefense3000 extends JFrame {
    private GameMap map;
    private GamePanel gamePanel;
    private EnemyManager enemyManager;
    private Timer gameTimer;
    private TowerManager TowerManager = new TowerManager();
    private javax.swing.JButton startWaveButton;
    private javax.swing.JLabel waveLabel;
    private javax.swing.JPanel controlPanel;
    private JPanel glassPane;
    private int numberOfWaves = 1000;
    
    // Map definitions
    private MenuPanel.MapInfo[] availableMaps = {
        new MenuPanel.MapInfo("Map 1 - Classic", 
            "RRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUUUUUUURRRRRRRRRRRRRDDDDDDDDDDDDDDDDDDDRRRRRRRR",
            0, 185, "images/kingtowerdefense map1.png"),
        new MenuPanel.MapInfo("Map 2 - Spiral", 
            "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDRRRRRRRRRRRRUUUUUUUUUUUULLLLLLLLLLLLDDDDDDDDDDDDRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLLLLLLLLUUUUUUUUUUURRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR",
            162, 0, "images/map2.png"),
    };
    
    // constructor for the main game class
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
        
        // Hide glass pane if it exists
        if (glassPane != null) {
            setGlassPane(new JPanel());
            glassPane = null;
        }
        
        // Clear any existing content and show menu
        getContentPane().removeAll();
        add(menuPanel);
        setTitle("King Tower Defense 3000 - Select Map");
    }
    
    // starts game cycle
    private void startGame(int mapIndex) {
        MenuPanel.MapInfo selectedMap = availableMaps[mapIndex];
        
        map = new GameMap(1000, 800);

        // Initialize TowerManager with starting money
        TowerManager = new TowerManager();
        TowerManager.addMoney(200);

        map.setPath(selectedMap.routeCode, selectedMap.startX, selectedMap.startY);

        enemyManager = new EnemyManager(map.getPath());
        
        gamePanel = new GamePanel(map, enemyManager, TowerManager, selectedMap.imageFile); 
        
        // Clear menu and show game
        getContentPane().removeAll();
        add(gamePanel);
        
        // Control panel with Start Wave button and tower buttons
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
        
        // Add tower buttons for dragging
        String[] towerNames = {"Knight($100)", "Devil($500)", "King($500)", "Ninja($200)", "Wizard($300)"};
        String[] towerImages = {"images/knight.png", "images/devil.png", "images/king.png", "images/ninja.png", "images/wizard.png"};
        for (int i = 0; i < 5; i++) {
            JButton b = new JButton(towerNames[i]);
            try {
                b.setIcon(new ImageIcon(towerImages[i]));
            } catch (Exception ex) {
                // ignore
            }
            final int towerIndex = i;
            b.addActionListener(ev -> {
                gamePanel.setDraggingTower(towerIndex);
            });
            controlPanel.add(b);
        }
        
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
        
        // Set up glass pane for drag handling
        glassPane = new JPanel();
        glassPane.setOpaque(false);
        setGlassPane(glassPane);
        glassPane.setVisible(false);
        

            glassPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    if (gamePanel.isDragging()) {
                        gamePanel.updateDragPosition(e.getX(), e.getY());
                    }
                }
            });

            glassPane.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (gamePanel.isDragging()) {
                        gamePanel.tryPlaceTower(e.getX(), e.getY());
                    }
                }
            });
        
        add(controlPanel, java.awt.BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setTitle("King Tower Defense 3000 - " + selectedMap.name);
        
        startGameLoop();
    }

    // shows glass pane for dragging towers
    public void showGlassPane(boolean show) {
        if (glassPane != null) {
            glassPane.setVisible(show);
        }
    }

    private void startGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }
    
    // updates game state 
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

    // updates menu wave label
    private void updateWaveLabel() {
        if (waveLabel != null && enemyManager != null) {
            waveLabel.setText("Wave: " + enemyManager.getCurrentWave() + "/" + numberOfWaves);
        }
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
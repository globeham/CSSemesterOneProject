import javax.swing.*;

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
        add(controlPanel, java.awt.BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);

        startGameLoop();
    }

    private void placeTower(int x, int y) {
        int adjustedY = y - getInsets().top;

        int minDistanceFromPath = 50; 
        if (map.isTooCloseToPath(x, adjustedY, minDistanceFromPath)) {
            System.out.println("Too close to path — place tower further away.");
            return;
        }
        
        // Build a small dialog with buttons that place the tower immediately on click
        final int fx = x;
        final int fy = adjustedY;
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
        Timer gameTimer = new Timer(16, e -> updateGame());
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
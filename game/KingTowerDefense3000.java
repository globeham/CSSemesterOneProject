import javax.swing.*;
import java.awt.*;

public class KingTowerDefense3000 extends JFrame {
    private gameMap map;
    private GamePanel gamePanel;
    
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
        
        gamePanel = new GamePanel(map);
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new KingTowerDefense3000();
    }
}
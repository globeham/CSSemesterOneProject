import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private gameMap map;
    
    public GamePanel(gameMap map) {
        this.map = map;
        this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        this.setBackground(Color.GREEN);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        map.drawPath(g);
        
        g.setColor(Color.RED);
        for (point2D point : map.getPath()) {
            g.fillOval(point.getX() - 5, point.getY() - 5, 10, 10);
        }
    }
}
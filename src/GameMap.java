import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class GameMap extends JPanel {
    final int tileSize = 50;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    private ArrayList<point2D> path;

    
    public GameMap() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.yellow);
        this.setDoubleBuffered(true);
    }

    public ArrayList<point2D> createPath(String routeCode, int startX, int startY) {
        ArrayList<point2D> path = new ArrayList<point2D>();
        // looping through routeCode
        for (int i = 0; i < routeCode.length(); i++) {
            // adding path depending on direction indicated in route code
            if (routeCode.substring(i, i + 1).equals("D")) {
                path.add(new point2D(startX, startY + 100));
                startY += 100;
            }
            if (routeCode.substring(i, i + 1).equals("U")) {
                path.add(new point2D(startX, startY - 100));
                startY -= 100;
            }
            if (routeCode.substring(i, i + 1).equals("L")) {
                path.add(new point2D(startX - 100, startY));
                startX -= 100;
            }
            if (routeCode.substring(i, i + 1).equals("R")) {
                path.add(new point2D(startX + 100, startY));
                startX += 100;
            }
        }
        return path;
    }

    

}
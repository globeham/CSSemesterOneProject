import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class gameMap {
    private int width;
    private int height;
    private ArrayList<point2D> path;
    
    public gameMap(int initWidth, int initHeight) {
        this.width = initWidth;
        this.height = initHeight;
        this.path = new ArrayList<point2D>();
    }
    
    // Creates path from routeCode and sets it as the map's path
    public void setPath(String routeCode, int startX, int startY) {
        this.path = createPath(routeCode, startX, startY);
    }
    
    // Creates and returns a path from the given routeCode
    public ArrayList<point2D> createPath(String routeCode, int startX, int startY) {
        ArrayList<point2D> newPath = new ArrayList<point2D>();
        
        // Add starting point
        newPath.add(new point2D(startX, startY));
        
        int currentX = startX;
        int currentY = startY;
        
        // Loop through routeCode
        for (int i = 0; i < routeCode.length(); i++) {
            char direction = routeCode.charAt(i);
            
            switch (direction) {
                case 'D':
                currentY += 90;
                    break;
                case 'U':
                    currentY -= 90;
                    break;
                case 'L':
                    currentX -= 90;
                    break;
                case 'R':
                    currentX += 90;
                    break;
            }
            newPath.add(new point2D(currentX, currentY));
        }
        return newPath;
    }
    
    // Getters
    public ArrayList<point2D> getPath() {
        return path;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    // Draw the path on the map
    public void drawPath(Graphics g) {
        if (path.size() < 2) return;
        
        g.setColor(Color.YELLOW);
        for (int i = 0; i < path.size() - 1; i++) {
            point2D current = path.get(i);
            point2D next = path.get(i + 1);
            Graphics2D g2d = (Graphics2D) g;
            BasicStroke thickStroke = new BasicStroke(10.0f); 
            g2d.setStroke(thickStroke);
            g2d.drawLine(current.getX(), current.getY(), 
            next.getX(), next.getY());
        }
    }
}
/*
Class provides a gameMap constructor with path and background image
*/
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
                currentY += 20;
                    break;
                case 'U':
                    currentY -= 20;
                    break;
                case 'L':
                    currentX -= 20;
                    break;
                case 'R':
                    currentX += 20;
                    break;
            }
            newPath.add(new point2D(currentX, currentY));
        }
        return newPath;
    }

    // returns the shortest distance from (x,y) to the path (segments)
    public double distanceToPath(int x, int y) {
        if (path == null || path.size() == 0) return Double.MAX_VALUE;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < path.size() - 1; i++) {
            point2D a = path.get(i);
            point2D b = path.get(i + 1);
            double d = pointToSegmentDistance(x, y, a.getX(), a.getY(), b.getX(), b.getY());
            if (d < minDist) minDist = d;
        }
        return minDist;
    }

    // convenience: true if (x,y) is within minDistance of the path
    public boolean isTooCloseToPath(int x, int y, int minDistance) {
        return distanceToPath(x, y) < minDistance;
    }

    // distance from point (px,py) to segment (x1,y1)-(x2,y2)
    private double pointToSegmentDistance(double px, double py,
                                          double x1, double y1,
                                          double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (dx == 0 && dy == 0) {
            dx = px - x1;
            dy = py - y1;
            return Math.hypot(dx, dy);
        }
        double t = ((px - x1) * dx + (py - y1) * dy) / (dx*dx + dy*dy);
        t = Math.max(0, Math.min(1, t));
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;
        return Math.hypot(px - projX, py - projY);
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
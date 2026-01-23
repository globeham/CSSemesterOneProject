/**
 * GameMap
 * 
 * Defines and manages game map properties including dimensions and enemy paths.
 * Parses route codes to create paths for enemies to follow based on directional 
 * instructions (U, D, L, R). Handles map scaling for different display resolutions.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private ArrayList<Point2D> path;
    private ArrayList<Point2D> originalPath;
    private int originalWidth;
    private int originalHeight;
    
    // constructor for game map
    public GameMap(int initWidth, int initHeight) {
        this.width = initWidth;
        this.height = initHeight;
        this.path = new ArrayList<Point2D>();
        this.originalPath = new ArrayList<Point2D>();
        this.originalWidth = initWidth;
        this.originalHeight = initHeight;
    }
    
    // sets path to given route code
    public void setPath(String routeCode, int startX, int startY) {
        this.originalPath = createPath(routeCode, startX, startY);
        this.path = new ArrayList<Point2D>(originalPath);
    }
    
    // Creates and returns a path from the given routeCode
    public ArrayList<Point2D> createPath(String routeCode, int startX, int startY) {
        ArrayList<Point2D> newPath = new ArrayList<Point2D>();
        
        // Add starting point
        newPath.add(new Point2D(startX, startY));
        
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
            newPath.add(new Point2D(currentX, currentY));
        }
        return newPath;
    }

   
    // scales the path to fit the current display size
    public void setDisplaySize(int displayW, int displayH) {
        this.width = displayW;
        this.height = displayH;
        if (originalPath == null || originalPath.size() == 0) {
            this.path = new ArrayList<Point2D>();
            return;
        }
        double sx = (double) displayW / (double) Math.max(1, originalWidth);
        double sy = (double) displayH / (double) Math.max(1, originalHeight);
        ArrayList<Point2D> scaled = new ArrayList<Point2D>(originalPath.size());
        for (Point2D p : originalPath) {
            int x = (int) Math.round(p.getX() * sx);
            int y = (int) Math.round(p.getY() * sy);
            scaled.add(new Point2D(x, y));
        }
        this.path = scaled;
    }

    // returns the shortest distance from (x,y) to the path in display coordinates
    public double distanceToPath(int x, int y) {
        if (path == null || path.size() == 0) return Double.MAX_VALUE;
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < path.size() - 1; i++) {
            Point2D a = path.get(i);
            Point2D b = path.get(i + 1);
            double d = pointToSegmentDistance(x, y, a.getX(), a.getY(), b.getX(), b.getY());
            if (d < minDist) minDist = d;
        }
        return minDist;
    }

    // true if (x,y) is within minDistance of the path
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
    public ArrayList<Point2D> getPath() {
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
        if (path == null || path.size() < 2) return;
        
        g.setColor(Color.YELLOW);
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke thickStroke = new BasicStroke(10.0f); 
        g2d.setStroke(thickStroke);
        for (int i = 0; i < path.size() - 1; i++) {
            Point2D current = path.get(i);
            Point2D next = path.get(i + 1);
            g2d.drawLine(current.getX(), current.getY(), next.getX(), next.getY());
        }
    }
}
import java.awt.*;
import javax.swing.*;
import java.ArrayList;

public class map {
    private int width;
    private int height;
    private ArrayList<point2D> path;

    public map(int initWidth, int initHeight, ArrayList<point2D> initPath) {
        this.width = initWidth;
        this.height = initHeight;
        this.path = initPath;
    }


    // precon: routeCode is made up of letters U, D, L, R, indicating, up, down, left, and right
    // method will create a path from the given routeCode
    public ArrayList<point2D> createPath(String routeCode, int startX, int startY) {
        ArrayList<point2D> path = new ArrayList<point2D>();
        // looping through routeCode
        for (int i = 0; i < routeCode.length(); i++) {
            // adding path depending on direction indicated in route code
            if (routeCode.substring(i, i + 1).equals("D")) {
                path.add(startX, startY + 100);
                startY += 100;
            }
            if (routeCode.substring(i, i + 1).equals("U")) {
                path.add(startX, startY - 100);
                startY -= 100;
            }
            if (routeCode.substring(i, i + 1).equals("L")) {
                path.add(startX - 100, startY);
                startX -= 100;
            }
            if (routeCode.substring(i, i + 1).equals("R")) {
                path.add(startX + 100, startY);
                startX += 100;
            }
        }
        return path;
    }
}
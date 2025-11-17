import java.awt.*;
import javax.swing.*;
import java.ArrayList;

public class map {
    private int width;
    private int height;
    private path;

    public map(int initWidth, int initHeight, ArrayList<point2D> initPath) {
        this.width = initWidth;
        this.height = initHeight;
        this.path = initHeight;
    }


    // precon: routeCode is made up of letters U, D, L, R, indicating, up, down, left, and right
    // method will create a path from the given routeCode
    private ArrayList<point2D> createPath(String routeCode) {
        for (int i = 0; i < routeCode.length(); i++) {

        }
    }
}
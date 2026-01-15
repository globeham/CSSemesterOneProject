/* 
Helper class with point methods
*/

public class Point2D {
    // x and y variables
    private int x;
    private int y;

    // constructor for a coordinate point
    public Point2D(int initX, int initY) {
        this.x = initX;
        this.y = initY;
    }

    // getters
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
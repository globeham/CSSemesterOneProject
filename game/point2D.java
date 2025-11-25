/* 
Helper class with point methods
basically used to make paths and decide where enemies go i think
*/

public class point2D {
    // x and y variables
    private int x;
    private int y;

    // constructor for a coordinate point
    public point2D(int initX, int initY) {
        this.x = initX;
        this.y = initY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
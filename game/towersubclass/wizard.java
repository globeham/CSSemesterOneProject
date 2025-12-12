public class wizard extends Tower {
    public wizard() {
        super(30, 100, 15, 75, Color.MAGENTA); // speed, radius, damage, cost, color
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for wizard tower if needed
    }
}
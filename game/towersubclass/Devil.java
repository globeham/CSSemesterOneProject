/*
devil subclass of Tower
*/

public class Devil extends Tower {
    public Devil(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75, image); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/devil.gif"));
        } catch (IOException e) {
            System.out.println("Could not load devil image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for devil tower if needed
    }
}
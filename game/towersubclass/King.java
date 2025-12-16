/*
King subclass of Tower
*/

public class King extends Tower {
    public King(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75, image); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/king.gif"));
        } catch (IOException e) {
            System.out.println("Could not load king image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for king tower if needed
    }
}
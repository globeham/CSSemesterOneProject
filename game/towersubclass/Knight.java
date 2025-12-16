/*
Knight subclass of Tower
*/

public class Knight extends Tower {
    public Knight(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75, image); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/knight.gif"));
        } catch (IOException e) {
            System.out.println("Could not load knight image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for knight tower if needed
    }
}
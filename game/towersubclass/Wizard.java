/*
wizard subclass of Tower
*/

public class Wizard extends Tower {
    public Wizard(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75, image); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/wizard.gif"));
        } catch (IOException e) {
            System.out.println("Could not load wizard image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for wizard tower if needed
    }
}
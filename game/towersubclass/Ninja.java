/*
ninja subclass of Tower
*/

public class Ninja extends Tower {
    public Ninja(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(30, 100, 15, 75, image); // speed, radius, damage, cost, color
        try {
            this.image = ImageIO.read(new File("images/ninja.gif"));
        } catch (IOException e) {
            System.out.println("Could not load ninja image: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        // Additional drawing for ninja tower if needed
    }
}
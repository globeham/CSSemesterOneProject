/*
devil subclass of Tower
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Devil extends Tower {
    private BufferedImage image;
    // constructor for devil tower
    public Devil(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(24, 140, 18, 500);
        try {
            this.image = ImageIO.read(new File("images/devil.png"));
            setImage(this.image);
            BufferedImage redOrbImage = ImageIO.read(new File("images/Red Orb.png"));
            setProjectileImage(redOrbImage);
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
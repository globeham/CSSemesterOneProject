/*
devil subclass of Tower
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Devil extends Tower {
    private BufferedImage image;
    public Devil(int speed, int radius, int damage, int cost, BufferedImage image) {
        super(24, 140, 18, 400); // cost: 400
        try {
            this.image = ImageIO.read(new File("images/devil.png"));
            setImage(this.image);
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
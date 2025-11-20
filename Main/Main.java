import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("King Tower Defense 3000");

        GameMap gameMap = new GameMap();
        frame.add(gameMap);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
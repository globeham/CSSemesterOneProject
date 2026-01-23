/**
 * MenuPanel
 * 
 * JPanel that displays the main menu and handles map selection functionality.
 * Contains nested MapInfo class to store map data including route, dimensions, 
 * and background image. Manages action listeners for map selection buttons.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPanel extends JPanel {
    private JButton[] mapButtons;
    private ActionListener[] actionListeners;

    public static class MapInfo {
        public String name;
        public String routeCode;
        public int startX;
        public int startY;
        public String imageFile;
        
        // constructor for map info
        public MapInfo(String name, String routeCode, int startX, int startY, String imageFile) {
            this.name = name;
            this.routeCode = routeCode;
            this.startX = startX;
            this.startY = startY;
            this.imageFile = imageFile;
        }
    }
    // constructor for menu panel
    public MenuPanel(MapInfo[] maps, ActionListener[] listeners) {
        this.actionListeners = listeners;
        this.mapButtons = new JButton[maps.length];
        
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("King Tower Defense 3000");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 40, 10);
        add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Select a Map");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 30, 10);
        add(subtitleLabel, gbc);
        
        // Map buttons in a 2x2 grid
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        for (int i = 0; i < maps.length; i++) {
            JButton button = new JButton(maps[i].name);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(200, 80));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            
            int row = i / 2 + 2;
            int col = i % 2;
            gbc.gridx = col;
            gbc.gridy = row;
            
            final int index = i;
            button.addActionListener(e -> {
                if (actionListeners != null && index < actionListeners.length) {
                    actionListeners[index].actionPerformed(e);
                }
            });
            
            mapButtons[i] = button;
            add(button, gbc);
            
        }
    }
}

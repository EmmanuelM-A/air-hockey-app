package ema.ui;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.awt.*;

/**
 * This class handles the creation of the menu buttons.
 */
public class MenuButtons extends JButton {
    /**
     * The buttons action listener.
     */
    private ActionListener listener;

    /**
     * Button's descritpion.
     */
    private JLabel description;

    /**
     * Curved radius of the button.
     */
    private final int RADIUS = 50;

    /**
     * Creates a styled menu button with the following arguments.
     * @param title The title of the button.
     * @param listener The button's action listner.
     * @param descriptionText The button's description, null if there is none.
     */
    public MenuButtons(String title, ActionListener listener, String descriptionText) {
        super(title);
        this.listener = listener;
        this.description = new JLabel(descriptionText);
        description.setFont(new Font("Cambria", Font.BOLD, 13));
        description.setForeground(Color.WHITE);

        super.addActionListener(listener);
        super.setOpaque(false);
        super.setFont(new Font("Cambria", Font.BOLD, 20));
        super.setBackground(new Color( 173, 216, 230));
        super.setForeground(Color.WHITE);

        description.setVisible(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.WHITE);
                setForeground(new Color( 173, 216, 230));
                description.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color( 173, 216, 230));
                setForeground(Color.WHITE);
                description.setVisible(false);
            }
        });
    }

    /**
     * Gets the button's description.
     * @return The button description.
     */
    public JLabel getDescription() {
        return this.description;
    }

    /**
     * Gets the button's action listener.
     * @return The button actionlistener.
     */
    public ActionListener getListener() {
        return this.listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);

        FontMetrics fm = g2.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }
}

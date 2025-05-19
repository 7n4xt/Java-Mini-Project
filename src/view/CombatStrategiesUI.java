package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe utilitaire pour afficher des conseils stratégiques et des visuels
 * d'aide au combat
 */
public class CombatStrategiesUI {

    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 20);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 16);

    /**
     * Affiche une boîte de dialogue avec des conseils stratégiques
     * 
     * @param parent       Le composant parent
     * @param staminaLevel Niveau actuel d'endurance
     */
    public static void showCombatAdvice(Component parent, int staminaLevel) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(parent),
                "Conseils de Combat", true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = new JLabel("Stratégies de Combat", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Contenu qui varie selon le niveau d'endurance
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(30, 30, 30));

        // Icône associée au niveau de stamina
        String iconPath;
        String mainAdvice;

        if (staminaLevel < 4) {
            iconPath = "🔴"; // Emoji point rouge pour niveau critique
            mainAdvice = "<html><b>Endurance Critique!</b><br><br>"
                    + "• Défendez-vous pour économiser de l'énergie<br>"
                    + "• Utilisez des objets de récupération<br>"
                    + "• Tentez de fuir si nécessaire<br>"
                    + "• Évitez les attaques qui coûtent beaucoup d'énergie</html>";
        } else if (staminaLevel < 8) {
            iconPath = "🟠"; // Emoji point orange pour niveau moyen
            mainAdvice = "<html><b>Endurance Modérée</b><br><br>"
                    + "• Alternez entre attaque et défense<br>"
                    + "• Gardez un œil sur votre barre d'endurance<br>"
                    + "• Utilisez des objets si disponibles<br>"
                    + "• Préparez-vous à une défense si l'endurance baisse encore</html>";
        } else {
            iconPath = "🟢"; // Emoji point vert pour bon niveau
            mainAdvice = "<html><b>Bonne Endurance</b><br><br>"
                    + "• Attaquez sans hésiter pour maximiser les dégâts<br>"
                    + "• Utilisez des techniques spéciales si disponibles<br>"
                    + "• Profitez de ce bon niveau d'énergie pour des attaques puissantes<br>"
                    + "• Gardez tout de même des objets de soin en réserve</html>";
        }

        JLabel iconLabel = new JLabel(iconPath);
        iconLabel.setFont(new Font("Dialog", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel adviceLabel = new JLabel(mainAdvice);
        adviceLabel.setForeground(Color.WHITE);
        adviceLabel.setFont(TEXT_FONT);
        adviceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(adviceLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Rappel général des mécaniques de combat
        JLabel reminderLabel = new JLabel("<html><b>Rappel:</b><br>"
                + "• Attaquer coûte 3 points d'endurance<br>"
                + "• Se défendre coûte 1 point d'endurance<br>"
                + "• Fuir coûte 10 points d'endurance si échec<br>"
                + "• Victoire = +5 points d'endurance récupérés</html>");
        reminderLabel.setForeground(new Color(200, 200, 200));
        reminderLabel.setFont(new Font(TEXT_FONT.getName(), Font.PLAIN, 14));
        reminderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(reminderLabel);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Retour au combat");
        closeButton.setBackground(ACCENT_COLOR);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(parent);

        // Effet de fondu à l'affichage
        Timer fadeInTimer = new Timer(50, null);
        dialog.setOpacity(0.0f);
        dialog.setVisible(true);

        float[] opacity = { 0.0f };
        fadeInTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity[0] += 0.1f;
                if (opacity[0] > 1.0f) {
                    opacity[0] = 1.0f;
                    fadeInTimer.stop();
                }
                dialog.setOpacity(opacity[0]);
            }
        });
        fadeInTimer.start();
    }

    /**
     * Crée un effet visuel de flash pour un composant
     * 
     * @param component  Composant à faire clignoter
     * @param startColor Couleur initiale
     * @param endColor   Couleur finale
     * @param flashes    Nombre de clignotements
     */
    public static void createFlashEffect(JComponent component, Color startColor,
            Color endColor, int flashes) {
        Timer flashTimer = new Timer(300, null);
        final int[] count = { 0 };
        final Color originalColor = component.getForeground();

        flashTimer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                component.setForeground(endColor);
            } else {
                component.setForeground(startColor);
            }
            count[0]++;

            if (count[0] >= flashes * 2) {
                flashTimer.stop();
                component.setForeground(originalColor);
            }
        });

        flashTimer.start();
    }

    /**
     * Affiche un message d'alerte temporaire sur un combat
     * 
     * @param parent  Le composant parent
     * @param message Le message à afficher
     */
    public static void showCombatAlert(Component parent, String message) {
        JWindow alertWindow = new JWindow();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        panel.setBackground(new Color(30, 30, 30, 220));

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(TEXT_FONT);
        panel.add(label);

        alertWindow.add(panel);
        alertWindow.pack();

        // Positionner l'alerte en haut de l'écran du parent
        Point p = parent.getLocationOnScreen();
        alertWindow.setLocation(
                p.x + (parent.getWidth() - alertWindow.getWidth()) / 2,
                p.y + 50);

        // Afficher pendant 3 secondes puis disparaître
        alertWindow.setVisible(true);
        Timer timer = new Timer(3000, e -> {
            alertWindow.dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
}

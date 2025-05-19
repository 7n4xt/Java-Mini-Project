package view;

import model.Personnage;
import javax.swing.*;
import java.awt.*;

/**
 * Système de conseils stratégiques pour le combat
 */
public class CombatHelper {

    /**
     * Affiche une fenêtre de conseils stratégiques
     * 
     * @param parent     Le composant parent
     * @param personnage Le personnage dont on veut afficher les statistiques
     */
    public static void showStrategies(JDialog parent, Personnage personnage) {
        // Créer une nouvelle fenêtre StaminaTipsWindow
        StaminaTipsWindow tipsWindow = new StaminaTipsWindow(parent, personnage);
        tipsWindow.setVisible(true);
    }

    /**
     * Met à jour l'affichage d'une barre de vie
     * 
     * @param lifeBar     La barre de progression à mettre à jour
     * @param currentLife La vie actuelle
     * @param maxLife     La vie maximale
     */
    public static void updateLifeBar(JProgressBar lifeBar, int currentLife, int maxLife) {
        lifeBar.setValue(currentLife);
        lifeBar.setString("Vie: " + currentLife + "/" + maxLife);

        // Mettre à jour la couleur en fonction du niveau de vie
        if (currentLife <= maxLife * 0.25) {
            lifeBar.setForeground(new Color(200, 50, 50)); // Rouge critique
        } else if (currentLife <= maxLife * 0.5) {
            lifeBar.setForeground(new Color(200, 150, 50)); // Orange blessé
        } else {
            lifeBar.setForeground(new Color(50, 150, 50)); // Vert normal
        }
    }

    /**
     * Met à jour l'affichage d'une barre d'endurance
     * 
     * @param staminaBar     La barre de progression à mettre à jour
     * @param currentStamina L'endurance actuelle
     * @param maxStamina     L'endurance maximale
     */
    public static void updateStaminaBar(JProgressBar staminaBar, int currentStamina, int maxStamina) {
        staminaBar.setValue(currentStamina);
        staminaBar.setString("Endurance: " + currentStamina + "/" + maxStamina);

        // Mettre à jour la couleur en fonction du niveau d'endurance
        if (currentStamina <= maxStamina * 0.2) {
            staminaBar.setForeground(new Color(200, 50, 50)); // Rouge critique
        } else if (currentStamina <= maxStamina * 0.5) {
            staminaBar.setForeground(new Color(200, 150, 50)); // Orange fatigué
        } else {
            staminaBar.setForeground(new Color(50, 100, 150)); // Bleu normal
        }
    }

    /**
     * Affiche une alerte concernant l'endurance basse
     * 
     * @param parent     Le composant parent
     * @param staminaBar La barre d'endurance
     */
    public static void showStaminaAlert(JDialog parent, JProgressBar staminaBar) {
        JToolTip tip = new JToolTip();
        tip.setTipText("<html><b>Conseils de combat:</b><br>" +
                "- La défense coûte moins d'endurance que l'attaque<br>" +
                "- Les rations restaurent l'endurance<br>" +
                "- Après une victoire, votre endurance se régénère<br>" +
                "- Sans endurance après 3 défenses, c'est Game Over!</html>");

        Point p = new Point(staminaBar.getLocationOnScreen());
        p.translate(staminaBar.getWidth() / 2, -80);

        final JDialog alertDialog = new JDialog(parent, false);
        alertDialog.setUndecorated(true);
        alertDialog.add(tip);
        alertDialog.pack();
        alertDialog.setLocation(p);
        alertDialog.setVisible(true);

        // Fermer le popup après quelques secondes
        Timer tipTimer = new Timer(5000, e -> alertDialog.dispose());
        tipTimer.setRepeats(false);
        tipTimer.start();
    }

    /**
     * Fait clignoter une barre de progression pour alerter le joueur
     * 
     * @param bar        La barre à faire clignoter
     * @param startColor La couleur initiale
     * @param endColor   La couleur finale
     * @param flashes    Le nombre de clignotements
     */
    public static void createFlashEffect(JProgressBar bar, Color startColor, Color endColor, int flashes) {
        Timer flashTimer = new Timer(300, null);
        final int[] count = { 0 };

        flashTimer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                bar.setForeground(startColor);
            } else {
                bar.setForeground(endColor);
            }
            count[0]++;

            if (count[0] >= flashes * 2) {
                flashTimer.stop();
                bar.setForeground(endColor); // Revenir à la couleur finale
            }
        });

        flashTimer.start();
    }

    /**
     * Affiche un conseil rapide pendant le combat
     * 
     * @param parent          Le composant parent
     * @param message         Le message à afficher
     * @param durationSeconds La durée d'affichage en secondes
     */
    public static void showQuickTip(JDialog parent, String message, int durationSeconds) {
        JDialog tipDialog = new JDialog(parent, false);
        tipDialog.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50, 220));
        panel.setBorder(BorderFactory.createLineBorder(new Color(201, 121, 66), 2));
        panel.setLayout(new BorderLayout());

        JLabel tipLabel = new JLabel("<html><body style='width: 250px; padding: 10px'>" + message + "</body></html>");
        tipLabel.setForeground(new Color(255, 241, 224));
        tipLabel.setFont(new Font("Yu Mincho", Font.BOLD, 14));
        panel.add(tipLabel, BorderLayout.CENTER);

        tipDialog.add(panel);
        tipDialog.pack();

        // Positionner au centre de la fenêtre parent
        tipDialog.setLocationRelativeTo(parent);

        // Afficher et fermer après la durée spécifiée
        tipDialog.setVisible(true);
        Timer closeTimer = new Timer(durationSeconds * 1000, e -> tipDialog.dispose());
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
}

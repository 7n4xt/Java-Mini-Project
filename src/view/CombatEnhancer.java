package view;

import model.Enemy;
import model.Personnage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * Interface graphique pour les combats avec support amélioré pour l'endurance
 */
public class CombatEnhancer {

    /**
     * Ajoute un bouton d'aide stratégique aux combats
     * 
     * @param combatUI   L'interface de combat à améliorer
     * @param mainPanel  Le panneau principal de l'interface
     * @param personnage Le personnage du joueur
     */
    public static void addStrategyButton(JDialog combatUI, JPanel mainPanel, Personnage personnage) {
        // Créer un bouton d'aide stratégique
        JButton tipsButton = new JButton("Stratégies");
        tipsButton.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        tipsButton.setForeground(new Color(255, 241, 224));
        tipsButton.setBackground(new Color(50, 70, 120)); // Couleur bleue pour le distinguer
        tipsButton.setBorderPainted(false);
        tipsButton.setFocusPainted(false);
        tipsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ajouter une action pour afficher les conseils
        tipsButton.addActionListener(e -> showCombatStrategies(combatUI, personnage));
        tipsButton.setToolTipText("Afficher des conseils sur la gestion de l'endurance et les stratégies de combat");

        // Ajouter un panneau pour le bouton
        JPanel extraButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        extraButtonPanel.setOpaque(false);
        extraButtonPanel.add(tipsButton);
        mainPanel.add(extraButtonPanel, BorderLayout.NORTH);
    }

    /**
     * Affiche des conseils stratégiques pour le combat
     * 
     * @param parent     Le composant parent
     * @param personnage Le personnage du joueur
     */
    public static void showCombatStrategies(Component parent, Personnage personnage) {
        // Utiliser StaminaTipsWindow pour afficher les conseils stratégiques
        JDialog parentDialog = (JDialog) SwingUtilities.getWindowAncestor(parent);
        StaminaTipsWindow tipsWindow = new StaminaTipsWindow(parentDialog, personnage);
        tipsWindow.setVisible(true);
    }

    /**
     * Met à jour l'affichage des barres de vie
     * 
     * @param vieJoueur La barre de vie du joueur
     * @param vieEnnemi La barre de vie de l'ennemi
     * @param joueur    Le personnage du joueur
     * @param ennemi    L'ennemi
     */
    public static void updateLifeBars(JProgressBar vieJoueur, JProgressBar vieEnnemi,
            Personnage joueur, Enemy ennemi) {
        if (vieJoueur != null && joueur != null) {
            vieJoueur.setValue(joueur.getStatistique("vie"));
            vieJoueur.setString("Vie: " + joueur.getStatistique("vie") + "/" + vieJoueur.getMaximum());
        }

        if (vieEnnemi != null && ennemi != null) {
            vieEnnemi.setValue(ennemi.getEndurance());
            vieEnnemi.setString("Vie: " + ennemi.getEndurance() + "/" + ennemi.getEnduranceMax());
        }
    }

    /**
     * Ajoute un message d'alerte au log de combat
     * 
     * @param combatLog Le log de combat
     * @param message   Le message à ajouter
     */
    public static void addAlertToCombatLog(JTextArea combatLog, String message) {
        // Ajouter des espaces pour une meilleure lisibilité
        if (combatLog.getText().trim().length() > 0) {
            combatLog.append("\n\n⚠️ " + message);
        } else {
            combatLog.append("⚠️ " + message);
        }

        // Défiler automatiquement vers le bas
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
    }

    /**
     * Affiche une notification de récompense après un combat victorieux
     * 
     * @param parent     Le composant parent
     * @param ennemi     L'ennemi vaincu
     * @param recompense L'objet de récompense
     */
    public static void showRewardNotification(Component parent, Enemy ennemi, String recompense) {
        String icon = Personnage.getItemIcon(recompense);
        JOptionPane.showMessageDialog(parent,
                "Vous avez vaincu " + ennemi.getNom() + " et obtenu:\n\n" +
                        icon + " " + recompense + "\n\n" +
                        "Cet objet a été ajouté à votre inventaire.",
                "Récompense obtenue",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Crée un effet de flash sur un composant pour attirer l'attention
     * 
     * @param component  Le composant à faire clignoter
     * @param flashColor La couleur du clignotement
     * @param baseColor  La couleur de base
     * @param flashCount Le nombre de clignotements
     */
    public static void createFlashEffect(JComponent component, Color flashColor, Color baseColor, int flashCount) {
        Timer flashTimer = new Timer(300, null);
        final int[] count = { 0 };

        flashTimer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                component.setForeground(flashColor);
            } else {
                component.setForeground(baseColor);
            }
            count[0]++;

            if (count[0] >= flashCount * 2) {
                flashTimer.stop();
                component.setForeground(baseColor);
            }
        });

        flashTimer.start();
    }
}

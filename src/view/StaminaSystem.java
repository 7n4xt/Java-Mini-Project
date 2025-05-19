package view;

import model.Personnage;
import javax.swing.*;
import java.awt.*;

/**
 * Système de gestion de la stamina/endurance pour les combats
 */
public class StaminaSystem {
    // Constantes pour les couleurs de la barre de stamina
    private static final Color STAMINA_HIGH = new Color(50, 100, 150); // Bleu - stamina normale
    private static final Color STAMINA_MEDIUM = new Color(200, 150, 50); // Orange - stamina moyenne
    private static final Color STAMINA_LOW = new Color(200, 50, 50); // Rouge - stamina basse
    private static final Color STAMINA_CRITICAL = new Color(255, 50, 50); // Rouge vif - stamina critique

    // État du système
    private JProgressBar staminaBar;
    private Personnage personnage;
    private JComponent parentComponent;
    private boolean alertShown = false;
    private Timer flashTimer;

    /**
     * Crée un nouveau système de gestion de stamina
     * 
     * @param staminaBar La barre de progression qui affiche la stamina
     * @param personnage Le personnage dont on suit la stamina
     * @param parent     Le composant parent pour afficher les notifications
     */
    public StaminaSystem(JProgressBar staminaBar, Personnage personnage, JComponent parent) {
        this.staminaBar = staminaBar;
        this.personnage = personnage;
        this.parentComponent = parent;
    }

    /**
     * Met à jour l'affichage de la barre de stamina
     */
    public void updateStaminaBar() {
        int currentStamina = personnage.getStatistique("endurance");
        int maxStamina = 15; // Valeur maximale d'endurance

        staminaBar.setValue(currentStamina);
        staminaBar.setString("Endurance: " + currentStamina + "/" + maxStamina);

        // Changer la couleur en fonction du niveau d'endurance
        if (currentStamina <= 3) {
            staminaBar.setForeground(STAMINA_LOW); // Rouge si faible

            // Animation pour alerter le joueur que la stamina est critique
            if (currentStamina > 0) {
                flashStaminaWarning();
            }

            // Montrer un message d'avertissement si c'est la première fois qu'on atteint ce
            // niveau
            if (!alertShown && currentStamina <= 3) {
                showStaminaAlert();
            }
        } else if (currentStamina <= 7) {
            staminaBar.setForeground(STAMINA_MEDIUM); // Orange si moyen
            stopFlashing(); // Arrêter le clignotement si actif
            alertShown = false; // Réinitialiser pour afficher à nouveau l'alerte si la stamina baisse encore
        } else {
            staminaBar.setForeground(STAMINA_HIGH); // Bleu si bon niveau
            stopFlashing(); // Arrêter le clignotement si actif
            alertShown = false; // Réinitialiser pour afficher à nouveau l'alerte si la stamina baisse encore
        }
    }

    /**
     * Arrête le clignotement de la barre de stamina
     */
    private void stopFlashing() {
        if (flashTimer != null && flashTimer.isRunning()) {
            flashTimer.stop();
            staminaBar.setForeground(STAMINA_LOW);
        }
    }

    /**
     * Affiche une alerte concernant la stamina basse
     */
    private void showStaminaAlert() {
        // Afficher un message d'alerte
        JOptionPane.showMessageDialog(parentComponent,
                "Votre endurance est critique!\n\n" +
                        "Conseils de combat:\n" +
                        "- La défense coûte moins d'endurance que l'attaque\n" +
                        "- Les rations restaurent l'endurance\n" +
                        "- Après une victoire, votre endurance se régénère un peu\n" +
                        "- Utilisez vos objets stratégiquement",
                "Alerte d'endurance",
                JOptionPane.WARNING_MESSAGE);

        alertShown = true;
    }

    /**
     * Crée un effet de clignotement pour la barre de stamina
     */
    private void flashStaminaWarning() {
        // Arrêter le timer existant si actif
        stopFlashing();

        flashTimer = new Timer(300, null);
        final int[] count = { 0 };

        flashTimer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                staminaBar.setForeground(STAMINA_CRITICAL); // Rouge plus vif
            } else {
                staminaBar.setForeground(STAMINA_LOW); // Rouge normal
            }
            count[0]++;

            if (count[0] >= 6) { // 3 flashs
                flashTimer.stop();
                staminaBar.setForeground(STAMINA_LOW); // Revenir à la couleur normale
            }
        });

        flashTimer.start();
    }

    /**
     * Réduit la stamina du joueur
     * 
     * @param quantité Quantité de stamina à réduire
     */
    public void réduireStamina(int quantité) {
        personnage.utiliserEndurance(quantité);
        updateStaminaBar();
    }

    /**
     * Restaure la stamina du joueur
     * 
     * @param quantité Quantité de stamina à restaurer
     */
    public void restaurerStamina(int quantité) {
        personnage.restaurerEndurance(quantité);
        updateStaminaBar();
    }

    /**
     * Vérifie si le joueur a assez de stamina pour une action coûteuse
     * 
     * @param coût Le coût de l'action en stamina
     * @return true si le joueur peut effectuer l'action, false sinon
     */
    public boolean peutEffectuerAction(int coût) {
        return personnage.getStatistique("endurance") >= coût;
    }
}

// Removed package declaration to match the default package

import view.MenuUI;
import javax.swing.*;

/**
 * Classe principale pour lancer le jeu "L'Épée du Samouraï"
 */
public class Main {

    /**
     * Point d'entrée de l'application
     */
    public static void main(String[] args) {
        System.out.println("Lancement de L'Épée du Samouraï...");

        // Lancer directement l'interface graphique avec le menu principal
        SwingUtilities.invokeLater(() -> {
            MenuUI menuUI = new MenuUI();
            menuUI.setVisible(true);
        });
    }
}
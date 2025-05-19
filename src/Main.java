// Removed package declaration to match the default package

import controller.ScenarioLoader;
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

        // Appliquer les optimisations de rendu de texte globales
        try {
            // Importer la classe TextRenderingFixer
            Class.forName("view.TextRenderingFixer");
            // Appliquer les paramètres globaux de rendu de texte
            view.TextRenderingFixer.applyGlobalTextSettings();
            System.out.println("Optimisations de rendu de texte appliquées");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'application des optimisations de rendu: " + e.getMessage());
        }

        // Précharger les scénarios pour de meilleures performances
        preloadScenarios();

        // Lancer directement l'interface graphique avec le menu principal
        SwingUtilities.invokeLater(() -> {
            MenuUI menuUI = new MenuUI();
            menuUI.setVisible(true);
        });
    }

    /**
     * Précharge les scénarios en arrière-plan pour réduire les temps de chargement
     */
    private static void preloadScenarios() {
        new Thread(() -> {
            try {
                // Précharger les scénarios dans un thread séparé
                System.out.println("Préchargement des chapitres...");

                // Charger le scénario de démonstration
                try {
                    ScenarioLoader.creerScenarioDemonstration();
                    System.out.println("Scénario de démonstration préchargé avec succès.");
                } catch (Exception e) {
                    System.err.println("Erreur lors du préchargement du scénario de démonstration: " + e.getMessage());
                }

                // Charger le scénario du chapitre 2
                try {
                    ScenarioLoader.creerScenarioChapitre2();
                    System.out.println("Scénario du chapitre 2 préchargé avec succès.");
                } catch (Exception e) {
                    System.err.println("Erreur lors du préchargement du scénario du chapitre 2: " + e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Préchargement terminé.");
            } catch (Exception e) {
                System.err.println("Erreur générale lors du préchargement des scénarios: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}
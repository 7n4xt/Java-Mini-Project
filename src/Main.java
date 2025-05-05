// Removed package declaration to match the default package

import controller.GameController;
import controller.ScenarioLoader;
import model.Scenario;
import view.MenuUI;
import view.SamuraiSwingUI;
import view.SwingUI;
import view.TextUI;

import javax.swing.*;
import java.util.Scanner;

/**
 * Classe principale pour lancer le jeu "L'Épée du Samouraï"
 */
public class Main {

    /**
     * Point d'entrée de l'application
     */
    public static void main(String[] args) {
        System.out.println("Lancement de L'Épée du Samouraï...");

        // Demander le type d'interface (toujours demandé en console)
        boolean useGraphicalUI = demanderInterfaceGraphique();

        if (useGraphicalUI) {
            // Lancer l'interface graphique avec le nouveau menu
            SwingUtilities.invokeLater(() -> {
                MenuUI menuUI = new MenuUI();
                menuUI.setVisible(true);
            });
        } else {
            // Interface texte en ligne de commande (inchangée)
            String nomJoueur = demanderNomJoueur();
            Scenario scenario = ScenarioLoader.creerScenarioDemonstration();
            GameController gameController = new GameController(scenario, nomJoueur);
            TextUI textUI = new TextUI(gameController);
            textUI.demarrer();
        }
    }

    /**
     * Demande au joueur de saisir son nom
     */
    private static String demanderNomJoueur() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom de votre samouraï: ");
        String nom = scanner.nextLine().trim();

        // Si le joueur n'entre pas de nom, utiliser un nom par défaut
        if (nom.isEmpty()) {
            nom = "Musashi";
        }

        return nom;
    }

    /**
     * Demande à l'utilisateur s'il souhaite utiliser l'interface graphique ou
     * console
     * 
     * @return true pour l'interface graphique, false pour l'interface texte
     */
    private static boolean demanderInterfaceGraphique() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quelle interface souhaitez-vous utiliser?");
        System.out.println("1) Interface graphique (style Samouraï, recommandée)");
        System.out.println("2) Interface texte (ligne de commande)");

        while (true) {
            System.out.print("Votre choix (1 ou 2): ");
            String choix = scanner.nextLine().trim();

            if (choix.equals("1")) {
                return true;
            } else if (choix.equals("2")) {
                return false;
            } else {
                System.out.println("Choix invalide. Veuillez entrer 1 ou 2.");
            }
        }
    }
}
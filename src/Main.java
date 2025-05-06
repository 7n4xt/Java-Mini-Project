// Removed package declaration to match the default package

import controller.GameController;
import controller.ScenarioLoader;
import model.Scenario;
import view.InterfaceSelectionDialog;
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

        // Utiliser la boîte de dialogue graphique pour choisir l'interface
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
     * en utilisant une boîte de dialogue graphique
     * 
     * @return true pour l'interface graphique, false pour l'interface texte
     */
    private static boolean demanderInterfaceGraphique() {
        // Utiliser la boîte de dialogue theme samouraï au lieu de la console
        try {
            // Tentative d'utiliser l'interface graphique pour la sélection
            InterfaceSelectionDialog dialog = new InterfaceSelectionDialog();
            return dialog.showDialogAndWaitForChoice();
        } catch (Exception e) {
            // En cas d'erreur (par exemple, si l'environnement graphique n'est pas
            // disponible),
            // revenir à la version console comme fallback
            System.out.println("Impossible d'afficher l'interface graphique de sélection.");

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
}
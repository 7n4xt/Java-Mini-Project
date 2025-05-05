package fr.samourai.adventure;

import fr.samourai.adventure.controller.GameController;
import fr.samourai.adventure.controller.ScenarioLoader;
import fr.samourai.adventure.model.Scenario;
import fr.samourai.adventure.view.SwingUI;
import fr.samourai.adventure.view.TextUI;

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
        System.out.println("Chargement du scénario...");

        // Création d'un scénario de démonstration
        Scenario scenario = ScenarioLoader.creerScenarioDemonstration();

        // Demander le nom du joueur
        String nomJoueur = demanderNomJoueur();

        // Initialisation du contrôleur de jeu
        GameController gameController = new GameController(scenario, nomJoueur);

        // Choix de l'interface (graphique ou texte)
        boolean useGraphicalUI = demanderInterfaceGraphique();

        if (useGraphicalUI) {
            // Interface graphique avec Swing
            SwingUI swingUI = new SwingUI(gameController);
            SwingUtilities.invokeLater(() -> {
                swingUI.setVisible(true);
            });
        } else {
            // Interface texte en ligne de commande
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
        System.out.println("1) Interface graphique (recommandée)");
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
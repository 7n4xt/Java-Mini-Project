package view;

import controller.GameController;
import model.Chapitre;
import model.Choix;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Interface utilisateur en ligne de commande pour le jeu.
 * Permet de tester rapidement les fonctionnalités sans interface graphique.
 */
public class TextUI {
    private GameController gameController;
    private Scanner scanner;

    /**
     * Constructeur de l'interface texte
     * 
     * @param gameController Le contrôleur de jeu
     */
    public TextUI(GameController gameController) {
        this.gameController = gameController;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Lance l'interface texte et commence le jeu
     */
    public void demarrer() {
        afficherBienvenue();
        gameController.demarrerPartie();

        while (true) {
            Chapitre chapitreActuel = gameController.getChapitreActuel();
            afficherChapitre(chapitreActuel);

            // Si le chapitre est un chapitre de fin
            if (chapitreActuel.estFin()) {
                System.out.println("\nFIN DE L'AVENTURE");
                System.out.print("\nVoulez-vous recommencer? (o/n): ");
                String reponse = scanner.nextLine().trim().toLowerCase();
                if (reponse.equals("o")) {
                    gameController.demarrerPartie();
                } else {
                    System.out.println("Merci d'avoir joué à L'Épée du Samouraï!");
                    break;
                }
            } else {
                afficherChoix(chapitreActuel.getChoixPossibles());
                traiterChoixUtilisateur(chapitreActuel.getChoixPossibles().size());
            }
        }
    }

    /**
     * Affiche le message de bienvenue
     */
    private void afficherBienvenue() {
        System.out.println("================================================");
        System.out.println("        L'ÉPÉE DU SAMOURAÏ");
        System.out.println("   Un livre dont VOUS êtes le héros");
        System.out.println("================================================");
        System.out.println(gameController.getScenario().getDescription());
        System.out.println("================================================");
        System.out.println();
    }

    /**
     * Affiche le contenu d'un chapitre
     * 
     * @param chapitre Le chapitre à afficher
     */
    private void afficherChapitre(Chapitre chapitre) {
        System.out.println("\n------------------------------------------");
        System.out.println("CHAPITRE " + chapitre.getId() + ": " + chapitre.getTitre());
        System.out.println("------------------------------------------");
        System.out.println(chapitre.getTexte());

        // Affichage des statistiques du personnage
        System.out.println("\n--- Statistiques de votre Samouraï ---");
        System.out.println("Nom: " + gameController.getPersonnage().getNom());
        Map<String, Integer> stats = gameController.getPersonnage().getStatistiques();
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            System.out.println(stat.getKey() + ": " + stat.getValue());
        }

        // Affichage de l'inventaire
        List<String> inventaire = gameController.getPersonnage().getInventaire();
        System.out.println("\nInventaire: " + (inventaire.isEmpty() ? "vide" : String.join(", ", inventaire)));
    }

    /**
     * Affiche les choix disponibles
     * 
     * @param choix Liste des choix disponibles
     */
    private void afficherChoix(List<Choix> choix) {
        System.out.println("\nQue souhaitez-vous faire ?");
        for (int i = 0; i < choix.size(); i++) {
            System.out.println((i + 1) + ") " + choix.get(i).getTexte());
        }
        System.out.println("0) Sauvegarder la partie");
        System.out.println("-1) Charger une partie");
        System.out.println("-2) Quitter le jeu");
    }

    /**
     * Traite le choix de l'utilisateur
     * 
     * @param nbChoixPossibles Nombre de choix disponibles
     */
    private void traiterChoixUtilisateur(int nbChoixPossibles) {
        while (true) {
            System.out.print("\nVotre choix: ");
            try {
                int choix = Integer.parseInt(scanner.nextLine().trim());

                if (choix >= 1 && choix <= nbChoixPossibles) {
                    // Le joueur a fait un choix de jeu
                    gameController.faireChoix(choix - 1);
                    break;
                } else if (choix == 0) {
                    // Sauvegarder
                    System.out.print("Entrez le nom du fichier de sauvegarde: ");
                    String nomFichier = scanner.nextLine().trim();
                    boolean succes = gameController.sauvegarderPartie(nomFichier);
                    System.out.println(succes ? "Partie sauvegardée avec succès!" : "Erreur lors de la sauvegarde");
                } else if (choix == -1) {
                    // Charger
                    System.out.print("Entrez le nom du fichier de sauvegarde: ");
                    String nomFichier = scanner.nextLine().trim();
                    boolean succes = gameController.chargerPartie(nomFichier);
                    if (succes) {
                        System.out.println("Partie chargée avec succès!");
                        break; // Sort de la boucle pour afficher le nouveau chapitre
                    } else {
                        System.out.println("Erreur lors du chargement");
                    }
                } else if (choix == -2) {
                    // Quitter
                    System.out.println("Merci d'avoir joué à L'Épée du Samouraï!");
                    System.exit(0);
                } else {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un numéro valide.");
            }
        }
    }
}
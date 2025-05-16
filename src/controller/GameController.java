package controller;

import model.Chapitre;
import model.Choix;
import model.Personnage;
import model.Scenario;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur principal du jeu, gère la progression de l'histoire et
 * l'interaction utilisateur.
 */
public class GameController {
    private Scenario scenario;
    private Personnage personnage;
    private Chapitre chapitreActuel;
    private Map<Integer, Boolean> chapitresVisites;
    private Map<Integer, Integer> chapitresDefaite; // Map associant chapitre -> chapitre de défaite

    /**
         * Constructeur du contrôleur de jeu avec classe ou type de personnage
         * @param scenario Le scénario du jeu
         * @param nomJoueur Le nom du joueur
         * @param personnageType La classe ou le type du personnage (Samouraï, Ninja, Samouraï Shogun, etc.)
         */
        public GameController(Scenario scenario, String nomJoueur, String personnageType) {
            this.scenario = scenario;
            this.chapitresVisites = new HashMap<>();
            personnage = new Personnage(nomJoueur);
    
            // Ajuster les statistiques selon la classe ou le type du personnage
            switch (personnageType) {
                case "Samouraï":
                    personnage.modifierStatistique("HABILETÉ", 2);
                    personnage.modifierStatistique("ENDURANCE", 2);
                    personnage.ajouterInventaire("Katana");
                    break;
                case "Ninja":
                    personnage.modifierStatistique("HABILETÉ", 3);
                    personnage.modifierStatistique("CHANCE", 1);
                    personnage.ajouterInventaire("Shuriken");
                    personnage.ajouterInventaire("Fumigène");
                    break;
                case "Ronin":
                    personnage.modifierStatistique("HABILETÉ", 2);
                    personnage.modifierStatistique("ENDURANCE", 1);
                    personnage.modifierStatistique("CHANCE", 1);
                    personnage.ajouterInventaire("Daisho");
                    break;
                case "Moine":
                    personnage.modifierStatistique("ENDURANCE", 3);
                    personnage.modifierStatistique("CHANCE", 2);
                    personnage.ajouterInventaire("Bâton Bo");
                    break;
                case "Samouraï Shogun":
                    personnage.modifierStatistique("HABILETÉ", 2);
                    personnage.modifierStatistique("ENDURANCE", 3);
                    personnage.modifierStatistique("HONNEUR", 3);
                    personnage.ajouterInventaire("Daisho légendaire");
                    personnage.ajouterInventaire("Armure d'apparat");
                    personnage.ajouterInventaire("Bannière de clan");
                    break;
                case "Samouraï Daimyo":
                    personnage.modifierStatistique("HABILETÉ", 1);
                    personnage.modifierStatistique("ENDURANCE", 2);
                    personnage.modifierStatistique("CHANCE", 2);
                    personnage.modifierStatistique("HONNEUR", 2);
                    personnage.ajouterInventaire("Katana de famille");
                    personnage.ajouterInventaire("Armure laquée");
                    personnage.ajouterInventaire("Étendard noble");
                    break;
                case "Samouraï Hatamoto":
                    personnage.modifierStatistique("HABILETÉ", 3);
                    personnage.modifierStatistique("ENDURANCE", 2);
                    personnage.modifierStatistique("HONNEUR", 1);
                    personnage.ajouterInventaire("Katana d'élite");
                    personnage.ajouterInventaire("Armure légère");
                    personnage.ajouterInventaire("Étendard du Shogun");
                    break;
                case "Samouraï Kensai":
                    personnage.modifierStatistique("HABILETÉ", 4);
                    personnage.modifierStatistique("ENDURANCE", 1);
                    personnage.modifierStatistique("CHANCE", 1);
                    personnage.ajouterInventaire("Nodachi parfait");
                    personnage.ajouterInventaire("Kimono de cérémonie");
                    personnage.ajouterInventaire("Parchemin de techniques");
                    break;
                default:
                    // Statistiques par défaut
                    personnage.modifierStatistique("HABILETÉ", 2);
                    personnage.modifierStatistique("ENDURANCE", 2);
                    personnage.ajouterInventaire("Katana");
                    break;
            }
    
            demarrerPartie();
        }

    /**
     * Constructeur du contrôleur de jeu (version simple, sans classe de personnage)
     * @param scenario Le scénario du jeu
     * @param nomJoueur Le nom du joueur
     */
    public GameController(Scenario scenario, String nomJoueur) {
        this.scenario = scenario;
        this.personnage = new Personnage(nomJoueur);
        this.chapitresVisites = new HashMap<>();
        demarrerPartie();
    }

    /**
     * Démarre ou redémarre une partie
     */
    public void demarrerPartie() {
        // Assurez-vous que chapitresVisites est initialisé avant de l'utiliser
        if (chapitresVisites == null) {
            chapitresVisites = new HashMap<>();
        } else {
            chapitresVisites.clear();
        }
        
        // Réinitialiser le chapitre courant au premier chapitre du scénario
        chapitreActuel = scenario.getChapitres().get(1);
    }

    /**
     * Traite le choix fait par le joueur et change de chapitre
     * 
     * @param indexChoix L'index du choix sélectionné
     * @return Le chapitre suivant, ou null si le choix est invalide
     */
    public Chapitre faireChoix(int indexChoix) {
        if (indexChoix < 0 || indexChoix >= chapitreActuel.getChoixPossibles().size()) {
            return null;
        }

        Choix choixSelectionne = chapitreActuel.getChoixPossibles().get(indexChoix);
        int idChapitreSuivant = choixSelectionne.getChapitreDestinationId();
        chapitreActuel = scenario.getChapitre(idChapitreSuivant);

        marquerChapitreVisite(chapitreActuel.getId());
        return chapitreActuel;
    }

    /**
     * Marque un chapitre comme visité
     */
    private void marquerChapitreVisite(int idChapitre) {
        chapitresVisites.put(idChapitre, true);
    }

    /**
     * Vérifie si un chapitre a déjà été visité
     */
    public boolean chapitreDejaVisite(int idChapitre) {
        return chapitresVisites.getOrDefault(idChapitre, false);
    }

    /**
     * Sauvegarde la partie actuelle dans un fichier
     * 
     * @param cheminFichier Le chemin du fichier de sauvegarde
     * @return true si la sauvegarde a réussi, false sinon
     */
    public boolean sauvegarderPartie(String cheminFichier) {
        try (FileWriter writer = new FileWriter(cheminFichier)) {
            writer.write(personnage.getNom() + "\n");
            writer.write(chapitreActuel.getId() + "\n");
            // Sauvegarde des statistiques et de l'inventaire pourrait être ajoutée ici
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            return false;
        }
    }

    /**
     * Charge une partie sauvegardée depuis un fichier
     * 
     * @param cheminFichier Le chemin du fichier de sauvegarde
     * @return true si le chargement a réussi, false sinon
     */
    public boolean chargerPartie(String cheminFichier) {
        try {
            String[] lignes = Files.readString(Paths.get(cheminFichier)).split("\n");

            if (lignes.length >= 2) {
                String nomPersonnage = lignes[0];
                int idChapitre = Integer.parseInt(lignes[1]);

                this.personnage.setNom(nomPersonnage);
                this.chapitreActuel = scenario.getChapitre(idChapitre);

                // Chargement des statistiques et de l'inventaire pourrait être ajouté ici
                return true;
            }
            return false;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtient le chapitre de défaite correspondant au chapitre actuel
     * 
     * @param idChapitre ID du chapitre actuel
     * @return Le chapitre de défaite ou null si non défini
     */
    public Chapitre getChapitreDefaite(int idChapitre) {
        Integer idChapitreDefaite = chapitresDefaite.get(idChapitre);
        if (idChapitreDefaite != null) {
            return scenario.getChapitre(idChapitreDefaite);
        }
        return null;
    }

    // Getters
    public Scenario getScenario() {
        return scenario;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public Chapitre getChapitreActuel() {
        return chapitreActuel;
    }
}
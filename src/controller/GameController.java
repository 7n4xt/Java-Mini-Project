package src.controller;

import src.model.Chapitre;
import src.model.Choix;
import src.model.Personnage;
import src.model.Scenario;

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

    /**
     * Constructeur du contrôleur de jeu
     * 
     * @param scenario      Le scénario du jeu
     * @param nomPersonnage Nom du personnage joueur
     */
    public GameController(Scenario scenario, String nomPersonnage) {
        this.scenario = scenario;
        this.personnage = new Personnage(nomPersonnage);
        this.chapitreActuel = scenario.getChapitreInitial();
        this.chapitresVisites = new HashMap<>();
    }

    /**
     * Démarre une nouvelle partie
     */
    public void demarrerPartie() {
        this.chapitreActuel = scenario.getChapitreInitial();
        this.chapitresVisites.clear();
        marquerChapitreVisite(chapitreActuel.getId());
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
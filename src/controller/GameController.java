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
import model.Combat;

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
     * 
     * @param scenario       Le scénario du jeu
     * @param nomJoueur      Le nom du joueur
     * @param personnageType La classe ou le type du personnage (Samouraï, Ninja,
     *                       Samouraï Shogun, etc.)
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
     * 
     * @param scenario  Le scénario du jeu
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
        chapitreActuel = scenario.getChapitres().get(idChapitreSuivant);

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
                this.chapitreActuel = scenario.getChapitres().get(idChapitre);

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
     * Change le scénario courant (pour passer au chapitre suivant)
     * @param nouveauScenario Le nouveau scénario à utiliser
     */
    public void changerScenario(Scenario nouveauScenario) {
        if (nouveauScenario == null) {
            System.err.println("ERREUR: Tentative de changer vers un scénario null");
            return;
        }
        
        System.out.println("DEBUG: Changing scenario to: " + nouveauScenario.getTitre());
        
        try {
            // Sauvegarder le personnage actuel et ses statistiques
            Personnage personnageActuel = this.personnage;
            
            // Changer de scénario
            this.scenario = nouveauScenario;
            
            // Réutiliser le même personnage avec ses statistiques
            this.personnage = personnageActuel;
            
            // Réinitialiser les chapitres visités pour ce nouveau scénario
            if (chapitresVisites == null) {
                chapitresVisites = new HashMap<>();
            } else {
                chapitresVisites.clear();
            }
            
            // Démarrer au premier chapitre du nouveau scénario
            Map<Integer, Chapitre> chapitres = scenario.getChapitres();
            if (chapitres != null && !chapitres.isEmpty()) {
                chapitreActuel = chapitres.get(1); // Le premier chapitre a l'id 1
                if (chapitreActuel == null) {
                    // Si l'id 1 n'existe pas, prendre le premier chapitre disponible
                    chapitreActuel = chapitres.values().iterator().next();
                }
                System.out.println("DEBUG: New starting chapter is: " + chapitreActuel.getTitre());
            } else {
                System.err.println("ERREUR: Le scénario n'a pas de chapitres ou la Map est null!");
            }
        } catch (Exception e) {
            System.err.println("ERREUR lors du changement de scénario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtient le chapitre de défaite pour un id de chapitre donné
     * @param chapitreId L'id du chapitre
     * @return Le chapitre de défaite ou null si non trouvé
     */
    public Chapitre getChapitreDefaite(int chapitreId) {
        // Trouver le chapitre actuel
        Chapitre chapitre = scenario.getChapitreById(chapitreId);
        
        if (chapitre != null && chapitre.hasChapitreDefaite()) {
            return chapitre.getChapitreDefaite();
        }
        
        return null;
    }

    /**
     * Exécute un tour de combat
     * @param combat Le combat en cours
     * @param action L'action du joueur ("attaquer" ou "defendre")
     * @return true si le joueur a gagné le combat, false sinon
     */
    public boolean executerCombat(Combat combat, String action) {
        Personnage joueur = getPersonnage();
        
        // Déterminer les modificateurs en fonction de l'action
        int modJoueur = 0;
        int modEnnemi = 0;
        
        // Bonus pour le Chapitre 2 - facilité les combats
        boolean estChapitre2 = scenario.getTitre().contains("Chapitre 2");
        if (estChapitre2) {
            modJoueur += 2;  // +2 en bonus d'attaque général dans le chapitre 2
            modEnnemi -= 1;  // -1 en malus pour tous les ennemis du chapitre 2
        }
        
        if ("defendre".equalsIgnoreCase(action)) {
            modJoueur = -1; // Malus d'attaque quand on se défend
            modEnnemi = -2; // Mais l'ennemi a un plus gros malus
        }
        
        // Calculer les scores de combat
        int scoreJoueur = lancerDes() + joueur.getStatistique("HABILETÉ") + modJoueur;
        int scoreEnnemi = lancerDes() + combat.getEnnemiHabilete() + modEnnemi;
        
        // Avantage supplémentaire au chapitre 2
        if (estChapitre2 && scoreJoueur < scoreEnnemi && Math.random() < 0.3) {
            // 30% de chance d'inverser un échec en succès dans le chapitre 2
            System.out.println("Votre expérience de combat vous permet d'esquiver à la dernière seconde!");
            int temp = scoreJoueur;
            scoreJoueur = scoreEnnemi;
            scoreEnnemi = temp;
        }
        
        // Déterminer les dégâts
        int degatsJoueur = 0;
        int degatsEnnemi = 0;
        
        if (scoreJoueur > scoreEnnemi) {
            // Le joueur touche l'ennemi
            degatsEnnemi = 4; // Augmenté de 2 à 4
            
            // Bonus de dégâts au chapitre 2
            if (estChapitre2) {
                degatsEnnemi += 2; // Augmenté de 1 à 2
            }
            
            if ("attaquer".equalsIgnoreCase(action)) {
                // Bonus de dégâts en attaquant
                degatsEnnemi += 2; // Augmenté de 1 à 2
            }
            
            // Dégâts critiques (25% de chance)
            if (Math.random() < 0.25) {
                degatsEnnemi += 2;
                System.out.println("COUP CRITIQUE! +" + 2 + " points de dégâts!");
            }
            
            combat.setEnnemiEndurance(combat.getEnnemiEndurance() - degatsEnnemi);
            System.out.println("Le joueur inflige " + degatsEnnemi + " points de dégâts!");
        } else if (scoreEnnemi > scoreJoueur) {
            // L'ennemi touche le joueur
            degatsJoueur = 2;
            
            // Réduction des dégâts au chapitre 2
            if (estChapitre2) {
                degatsJoueur = Math.max(1, degatsJoueur - 1);
            }
            
            if ("attaquer".equalsIgnoreCase(action)) {
                // Malus en défense quand on attaque
                degatsJoueur += 1;
            }
            joueur.modifierStatistique("ENDURANCE", -degatsJoueur);
        }
        
        // Vérifier si le combat est terminé
        if (combat.getEnnemiEndurance() <= 0) {
            // Le joueur a gagné
            return true;
        } else if (joueur.getStatistique("ENDURANCE") <= 0) {
            // Le joueur a perdu
            return false;
        }
        
        // Le combat continue
        return false;
    }
    
    /**
     * Tente de fuir un combat
     * @param combat Le combat dont on veut fuir
     * @return true si la fuite est réussie, false sinon
     */
    public boolean fuirCombat(Combat combat) {
        boolean estChapitre2 = scenario.getTitre().contains("Chapitre 2");
        int modificateurFuite = estChapitre2 ? -2 : 0;  // Plus facile de fuir au chapitre 2
        
        // 50% de chances de réussir à fuir (plus au chapitre 2)
        if (lancerDes() + modificateurFuite >= 7) {
            return true;
        } else {
            // En cas d'échec, le joueur subit des dégâts (réduits au chapitre 2)
            int degats = estChapitre2 ? 0 : 1;  // Pas de dégâts en fuyant au chapitre 2
            getPersonnage().modifierStatistique("ENDURANCE", -degats);
            return false;
        }
    }
    
    /**
     * Lance 2 dés à 6 faces et renvoie la somme
     * @return un nombre entre 2 et 12
     */
    private int lancerDes() {
        return (int)(Math.random() * 6) + 1 + (int)(Math.random() * 6) + 1;
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
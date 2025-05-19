package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente un scénario complet du livre-jeu, contenant tous les chapitres.
 */
public class Scenario {
    private String titre;
    private String description;
    private Map<Integer, Chapitre> chapitres;
    private int chapitreInitialId;

    /**
     * Constructeur de Scenario
     * 
     * @param titre       Le titre du scénario
     * @param description Une brève description du scénario
     */
    public Scenario(String titre, String description) {
        this.titre = titre;
        this.description = description;
        this.chapitres = new HashMap<>();
        this.chapitreInitialId = 1;
    }

    /**
     * Constructeur avec seulement le titre (pour compatibilité)
     * @param titre Le titre du scénario
     */
    public Scenario(String titre) {
        this(titre, "");
    }

    /**
     * Ajoute un chapitre au scénario
     * @param chapitre Le chapitre à ajouter
     */
    public void addChapitre(Chapitre chapitre) {
        chapitres.put(chapitre.getId(), chapitre);
    }
    
    /**
     * Méthode alternative pour ajouter un chapitre (pour compatibilité)
     * @param chapitre Le chapitre à ajouter
     */
    public void ajouterChapitre(Chapitre chapitre) {
        chapitres.put(chapitre.getId(), chapitre);
    }

    /**
     * Récupère le chapitre initial
     */
    public Chapitre getChapitreInitial() {
        return chapitres.get(chapitreInitialId);
    }

    /**
     * Récupère tous les chapitres du scénario
     * @return Une map des chapitres avec leurs IDs comme clés
     */
    public Map<Integer, Chapitre> getChapitres() {
        return chapitres;
    }

    /**
     * Récupère un chapitre par son identifiant
     * @param id L'identifiant du chapitre à récupérer
     * @return Le chapitre correspondant ou null s'il n'existe pas
     */
    public Chapitre getChapitre(int id) {
        return chapitres.get(id);
    }

    /**
     * Récupère un chapitre par son identifiant
     * @param id Identifiant du chapitre à récupérer
     * @return Le chapitre demandé ou null s'il n'existe pas
     */
    public Chapitre getChapitreById(int id) {
        return chapitres.get(id);
    }

    // Getters et setters
    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public void setChapitreInitialId(int chapitreInitialId) {
        this.chapitreInitialId = chapitreInitialId;
    }
}
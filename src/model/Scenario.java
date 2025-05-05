package src.model;

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
     * Ajoute un chapitre au scénario
     */
    public void ajouterChapitre(Chapitre chapitre) {
        chapitres.put(chapitre.getId(), chapitre);
    }

    /**
     * Récupère un chapitre par son identifiant
     */
    public Chapitre getChapitre(int id) {
        return chapitres.get(id);
    }

    /**
     * Récupère le chapitre initial
     */
    public Chapitre getChapitreInitial() {
        return chapitres.get(chapitreInitialId);
    }

    // Getters et setters
    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public Map<Integer, Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitreInitialId(int chapitreInitialId) {
        this.chapitreInitialId = chapitreInitialId;
    }
}
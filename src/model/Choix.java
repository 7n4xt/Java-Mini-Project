package model;

/**
 * Représente un choix possible dans un chapitre, avec le texte du choix
 * et la référence vers le chapitre suivant.
 */
public class Choix {
    private String texte;
    private int chapitreDestinationId;

    /**
     * Constructeur de Choix
     * 
     * @param texte                 Le texte décrivant le choix
     * @param chapitreDestinationId L'identifiant du chapitre de destination
     */
    public Choix(String texte, int chapitreDestinationId) {
        this.texte = texte;
        this.chapitreDestinationId = chapitreDestinationId;
    }

    // Getters
    public String getTexte() {
        return texte;
    }

    public int getChapitreDestinationId() {
        return chapitreDestinationId;
    }

    @Override
    public String toString() {
        return texte + " (mène au chapitre " + chapitreDestinationId + ")";
    }
}
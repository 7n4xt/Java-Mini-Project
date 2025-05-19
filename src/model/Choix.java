package model;

/**
 * Représente un choix possible dans un chapitre, avec le texte du choix
 * et la référence vers le chapitre suivant.
 */
public class Choix {
    private String texte;
    private int chapitreDestinationId;
    private boolean declencheCombat;

    /**
     * Constructeur de Choix
     * 
     * @param texte                 Le texte décrivant le choix
     * @param chapitre2 L'identifiant du chapitre de destination
     */
    public Choix(String texte, Chapitre chapitre2) {
        this.texte = texte;
        this.chapitreDestinationId = chapitre2.getId();
        this.declencheCombat = false;
    }

    /**
     * Constructeur de Choix avec option de combat
     * 
     * @param texte                 Le texte décrivant le choix
     * @param chapitreDestinationId L'identifiant du chapitre de destination
     * @param declencheCombat       Si ce choix déclenche un combat
     */
    public Choix(String texte, int chapitreDestinationId, boolean declencheCombat) {
        this.texte = texte;
        this.chapitreDestinationId = chapitreDestinationId;
        this.declencheCombat = declencheCombat;
    }

    // Getters
    public String getTexte() {
        return texte;
    }

    public int getChapitreDestinationId() {
        return chapitreDestinationId;
    }

    public boolean declencheCombat() {
        return declencheCombat;
    }

    @Override
    public String toString() {
        return texte + " (mène au chapitre " + chapitreDestinationId + ")";
    }
}
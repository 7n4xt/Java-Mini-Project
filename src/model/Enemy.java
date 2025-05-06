package model;

/**
 * Représente un ennemi que le joueur peut combattre
 */
public class Enemy {
    private String nom;
    private int habileté;
    private int endurance;
    private String description;

    /**
     * Constructeur d'ennemi
     * 
     * @param nom         Le nom de l'ennemi
     * @param habileté    Points d'habileté de l'ennemi
     * @param endurance   Points d'endurance de l'ennemi
     * @param description Description de l'ennemi
     */
    public Enemy(String nom, int habileté, int endurance, String description) {
        this.nom = nom;
        this.habileté = habileté;
        this.endurance = endurance;
        this.description = description;
    }

    /**
     * Calcule la force d'attaque de l'ennemi (habileté + 2d6)
     * 
     * @return La force d'attaque
     */
    public int calculerForceAttaque() {
        // Lancer 2 dés à 6 faces
        int dé1 = (int) (Math.random() * 6) + 1;
        int dé2 = (int) (Math.random() * 6) + 1;
        return habileté + dé1 + dé2;
    }

    /**
     * Inflige des dégâts à l'ennemi
     * 
     * @param dégâts Nombre de points d'endurance à retirer
     */
    public void infligerDégâts(int dégâts) {
        this.endurance = Math.max(0, this.endurance - dégâts);
    }

    /**
     * Vérifie si l'ennemi est vaincu (endurance à 0)
     * 
     * @return true si l'ennemi est vaincu
     */
    public boolean estVaincu() {
        return endurance <= 0;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public int getHabileté() {
        return habileté;
    }

    public void setHabileté(int habileté) {
        this.habileté = habileté;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public String getDescription() {
        return description;
    }
}


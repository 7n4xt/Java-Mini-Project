package model;

/**
 * Représente un ennemi dans le jeu
 */
public class Enemy {
    private String nom;
    private int habilete;
    private int endurance;
    private int enduranceMax;
    private String description;

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param nom         Le nom de l'ennemi
     * @param habilete    La valeur d'habileté de l'ennemi
     * @param endurance   La valeur d'endurance de l'ennemi
     * @param description La description de l'ennemi
     */
    public Enemy(String nom, int habilete, int endurance, String description) {
        this.nom = nom;
        this.habilete = habilete;
        this.endurance = endurance;
        this.enduranceMax = endurance;
        this.description = description;
    }

    /**
     * Constructeur sans description
     * 
     * @param nom         Le nom de l'ennemi
     * @param habilete    La valeur d'habileté de l'ennemi
     * @param endurance   La valeur d'endurance de l'ennemi
     */
    public Enemy(String nom, int habilete, int endurance) {
        this(nom, habilete, endurance, "Un adversaire qui se dresse sur votre chemin.");
    }

    /**
     * Calcule la force d'attaque de l'ennemi (habileté + dés)
     * 
     * @return La force d'attaque calculée
     */
    public int calculerForceAttaque() {
        return habilete + (int) (Math.random() * 6) + 1 + (int) (Math.random() * 6) + 1;
    }

    /**
     * Inflige des dégâts à l'ennemi
     * 
     * @param degats Le nombre de points d'endurance à retirer
     */
    public void infligerDégâts(int degats) {
        // Amplifier les dégâts reçus par l'ennemi (multiplicateur 2x)
        int degatsAmplifies = degats * 2;
        this.endurance = Math.max(0, this.endurance - degatsAmplifies);
        System.out.println("L'ennemi subit " + degatsAmplifies + " points de dégâts! (Amplification x2)");
    }

    /**
     * Vérifie si l'ennemi est vaincu
     * 
     * @return true si l'endurance de l'ennemi est à 0
     */
    public boolean estVaincu() {
        return endurance <= 0;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getHabilete() {
        return habilete;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getEnduranceMax() {
        return enduranceMax;
    }

    public String getDescription() {
        return description;
    }
}


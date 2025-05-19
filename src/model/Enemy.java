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
    private String itemRecompense; // Objet donné en récompense après avoir vaincu l'ennemi

    /**
     * Constructeur avec tous les paramètres
     * 
     * @param nom            Le nom de l'ennemi
     * @param habilete       La valeur d'habileté de l'ennemi
     * @param endurance      La valeur d'endurance de l'ennemi
     * @param description    La description de l'ennemi
     * @param itemRecompense L'objet donné en récompense après avoir vaincu l'ennemi
     */
    public Enemy(String nom, int habilete, int endurance, String description, String itemRecompense) {
        this.nom = nom;
        this.habilete = habilete + 2; // Augmente l'habileté de base pour rendre les ennemis plus forts
        this.endurance = endurance + 5; // Augmente l'endurance de base pour rendre les ennemis plus résistants
        this.enduranceMax = this.endurance;
        this.description = description;
        this.itemRecompense = itemRecompense;
    }

    /**
     * Constructeur avec description mais sans item de récompense spécifique
     */
    public Enemy(String nom, int habilete, int endurance, String description) {
        this(nom, habilete, endurance, description, null);
    }

    /**
     * Constructeur sans description
     * 
     * @param nom       Le nom de l'ennemi
     * @param habilete  La valeur d'habileté de l'ennemi
     * @param endurance La valeur d'endurance de l'ennemi
     */
    public Enemy(String nom, int habilete, int endurance) {
        this(nom, habilete, endurance, "Un adversaire qui se dresse sur votre chemin.", null);
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
     * @param arme   L'arme utilisée (peut être null si aucune arme spécifique)
     */
    public void infligerDégâts(int degats, String arme) {
        // Amplifier les dégâts en fonction de l'arme
        int multiplicateur = 2; // Multiplicateur de base

        // Bonus supplémentaire pour le katana de famille
        if (arme != null && arme.toLowerCase().contains("katana")) {
            multiplicateur = 4; // Le katana inflige 4x les dégâts de base
            System.out.println("COUP DE KATANA CRITIQUE! Multiplicateur x4");
        }

        int degatsAmplifies = degats * multiplicateur;
        this.endurance = Math.max(0, this.endurance - degatsAmplifies);
        System.out.println(
                "L'ennemi subit " + degatsAmplifies + " points de dégâts! (Amplification x" + multiplicateur + ")");
    }

    /**
     * Surcharge pour compatibilité avec le code existant
     */
    public void infligerDégâts(int degats) {
        infligerDégâts(degats, null);
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

    /**
     * Retourne l'objet de récompense après avoir vaincu l'ennemi
     * 
     * @return Le nom de l'objet à donner en récompense, ou null si pas de
     *         récompense
     */
    public String getItemRecompense() {
        return itemRecompense;
    }

    /**
     * Définit un objet en récompense
     * 
     * @param item Le nom de l'objet à donner en récompense
     */
    public void setItemRecompense(String item) {
        this.itemRecompense = item;
    }
}

package model;

/**
 * Représente un combat entre le joueur et un ennemi
 */
public class Combat {
    private Personnage joueur;
    private String ennemiNom;
    private int ennemiHabilete;
    private int ennemiEndurance;
    private int ennemiEnduranceMax; // Pour suivre l'endurance maximale
    private boolean ennemiSpecial; // Pour les ennemis spéciaux comme les ninjas
    
    /**
     * Crée un combat entre le joueur et un ennemi spécifié
     * @param joueur Le personnage du joueur
     * @param enemy L'ennemi à combattre
     */
    public Combat(Personnage joueur, Enemy enemy) {
        this.joueur = joueur;
        this.ennemiNom = enemy.getNom();
        this.ennemiHabilete = enemy.getHabilete();
        this.ennemiEndurance = enemy.getEndurance();
        this.ennemiEnduranceMax = enemy.getEndurance();
        
        // Déterminer si c'est un ennemi spécial
        this.ennemiSpecial = ennemiNom.toLowerCase().contains("ninja");
    }
    
    // Getters et Setters
    public Personnage getJoueur() {
        return joueur;
    }
    
    public String getEnnemiNom() {
        return ennemiNom;
    }
    
    public int getEnnemiHabilete() {
        return ennemiHabilete;
    }
    
    public int getEnnemiEndurance() {
        return ennemiEndurance;
    }
    
    public void setEnnemiEndurance(int ennemiEndurance) {
        this.ennemiEndurance = Math.max(0, ennemiEndurance);
    }
    
    public int getEnnemiEnduranceMax() {
        return ennemiEnduranceMax;
    }
    
    public boolean estEnnemiSpecial() {
        return ennemiSpecial;
    }
    
    /**
     * Vérifie si l'ennemi est un ninja
     */
    public boolean estNinja() {
        return ennemiNom.toLowerCase().contains("ninja");
    }
    
    @Override
    public String toString() {
        return "Combat contre " + ennemiNom + 
               " (HABILETÉ: " + ennemiHabilete + 
               ", ENDURANCE: " + ennemiEndurance + "/" + ennemiEnduranceMax + ")";
    }
}

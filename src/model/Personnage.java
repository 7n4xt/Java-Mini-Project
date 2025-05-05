package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente le personnage du joueur avec ses statistiques et son inventaire.
 */
public class Personnage {
    private String nom;
    private Map<String, Integer> statistiques;
    private List<String> inventaire;
    private static final Map<String, String> ITEM_ICONS = new HashMap<>();

    static {
        ITEM_ICONS.put("Katana de famille", "⚔️");
        ITEM_ICONS.put("Armure légère", "🛡️");
        ITEM_ICONS.put("Nécessaire de méditation", "🎐");
        ITEM_ICONS.put("Rations de voyage", "🍙");
        ITEM_ICONS.put("Gourde d'eau", "🏺");
        ITEM_ICONS.put("Shuriken", "✴️");
        ITEM_ICONS.put("Parchemin magique", "📜");
        ITEM_ICONS.put("Potion de soin", "🧪");
        ITEM_ICONS.put("Amulette sacrée", "🔮");
        ITEM_ICONS.put("Kunai", "🗡️");
    }

    /**
     * Constructeur de Personnage
     * 
     * @param nom Le nom du personnage
     */
    public Personnage(String nom) {
        this.nom = nom;
        this.statistiques = new HashMap<>();
        this.inventaire = new ArrayList<>();

        // Initialisation des statistiques de base pour notre samouraï
        statistiques.put("honneur", 10);
        statistiques.put("force", 8);
        statistiques.put("agilité", 7);
        statistiques.put("sagesse", 6);
        statistiques.put("vie", 20);
    }

    /**
     * Ajoute un objet à l'inventaire
     */
    public void ajouterObjet(String objet) {
        inventaire.add(objet);
    }

    /**
     * Retire un objet de l'inventaire
     * 
     * @return true si l'objet a été retiré, false s'il n'était pas présent
     */
    public boolean retirerObjet(String objet) {
        return inventaire.remove(objet);
    }

    /**
     * Vérifie si le personnage possède un objet
     */
    public boolean possèdeObjet(String objet) {
        return inventaire.contains(objet);
    }

    /**
     * Modifie la valeur d'une statistique
     */
    public void modifierStatistique(String nom, int delta) {
        int valeurActuelle = statistiques.getOrDefault(nom, 0);
        statistiques.put(nom, valeurActuelle + delta);
    }

    /**
     * Récupère la valeur d'une statistique
     */
    public int getStatistique(String nom) {
        return statistiques.getOrDefault(nom, 0);
    }

    /**
     * Récupère l'icône associée à un objet
     */
    public static String getItemIcon(String item) {
        return ITEM_ICONS.getOrDefault(item, "📦");
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<String> getInventaire() {
        return new ArrayList<>(inventaire);
    }

    public Map<String, Integer> getStatistiques() {
        return new HashMap<>(statistiques);
    }
}
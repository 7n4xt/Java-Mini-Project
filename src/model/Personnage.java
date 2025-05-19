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
        statistiques.put("habileté", 7);
        statistiques.put("force", 8);
        statistiques.put("agilité", 7);
        statistiques.put("sagesse", 6);
        statistiques.put("vie", 20);
        statistiques.put("endurance", 15); // Nouvelle statistique d'endurance pour les actions de combat

        // Ajouter uniquement les trois objets de départ spécifiés
        inventaire.add("Katana de famille");
        inventaire.add("Rations de voyage");
        inventaire.add("Potion de soin");
    }

    /**
     * Ajoute un objet à l'inventaire
     * 
     * @param item Nom de l'objet à ajouter
     */
    public void ajouterInventaire(String item) {
        if (!inventaire.contains(item)) {
            inventaire.add(item);
        }
    }

    /**
     * Retire un objet de l'inventaire
     * 
     * @param item L'objet à retirer
     * @return true si l'objet a été retiré, false s'il n'était pas présent
     */
    public boolean retirerInventaire(String item) {
        return inventaire.remove(item);
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
     * 
     * @param nom    Nom de la statistique
     * @param valeur Valeur à ajouter (peut être négative)
     */
    public void modifierStatistique(String nom, int valeur) {
        if (statistiques.containsKey(nom)) {
            int nouvelleValeur = statistiques.get(nom) + valeur;
            // Valeur minimum 0 pour éviter les statistiques négatives
            statistiques.put(nom, Math.max(0, nouvelleValeur));
        } else {
            // Si la statistique n'existe pas encore, la créer
            statistiques.put(nom, Math.max(0, valeur));
        }
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

    /**
     * Réduit l'endurance du personnage lors d'une action
     * 
     * @param cout Le coût en endurance de l'action
     * @return true si l'action est possible, false si pas assez d'endurance
     */
    public boolean utiliserEndurance(int cout) {
        int enduranceActuelle = getStatistique("endurance");
        if (enduranceActuelle >= cout) {
            modifierStatistique("endurance", -cout);
            return true;
        }
        return false;
    }

    /**
     * Restaure de l'endurance au personnage
     * 
     * @param valeur La quantité d'endurance à restaurer
     */
    public void restaurerEndurance(int valeur) {
        int enduranceMax = 15; // Valeur maximale d'endurance
        int enduranceActuelle = getStatistique("endurance");
        int nouvelleEndurance = Math.min(enduranceActuelle + valeur, enduranceMax);
        statistiques.put("endurance", nouvelleEndurance);
    }

    /**
     * Vérifie si le personnage possède un katana
     * 
     * @return true si le personnage a un katana dans son inventaire
     */
    public boolean possedeDualKatana() {
        for (String item : inventaire) {
            if (item.toLowerCase().contains("katana")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère l'arme principale du personnage
     * 
     * @return Le nom de l'arme principale ou null si aucune arme
     */
    public String getArmePrincipale() {
        // Priorité au katana de famille
        if (inventaire.contains("Katana de famille")) {
            return "Katana de famille";
        }

        // Puis autres armes
        for (String item : inventaire) {
            if (item.contains("Katana") || item.contains("Épée") || item.contains("Sabre")) {
                return item;
            }
        }

        return null;
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

    public void ajouterObjet(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ajouterObjet'");
    }
}
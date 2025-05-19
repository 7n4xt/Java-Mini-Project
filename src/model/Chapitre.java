package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un chapitre dans l'aventure
 */
public class Chapitre {
    private int id;
    private String titre;
    private String texte;
    private List<Choix> choixPossibles;
    private boolean estFin;
    private Enemy enemy; // Pour les chapitres avec combat
    private Chapitre chapitreDefaite; // Chapitre à montrer en cas de défaite au combat
    private List<Chapitre> chapitresSucces; // Liste des chapitres de succès pour les combats

    /**
     * Constructeur standard pour un chapitre
     * 
     * @param id      L'identifiant unique du chapitre
     * @param titre   Le titre du chapitre
     * @param texte   Le contenu textuel du chapitre
     * @param estFin  Indique si c'est un chapitre de fin
     */
    public Chapitre(int id, String titre, String texte, boolean estFin) {
        this.id = id;
        this.titre = titre;
        this.texte = texte;
        this.choixPossibles = new ArrayList<>();
        this.estFin = estFin;
        this.chapitresSucces = new ArrayList<>();
    }
    
    /**
     * Constructeur simple sans préciser si c'est un chapitre de fin
     */
    public Chapitre(int id, String titre, String texte) {
        this(id, titre, texte, false);
    }

    /**
     * Ajoute un choix possible pour ce chapitre
     * 
     * @param choix Le choix à ajouter
     */
    public void ajouterChoix(Choix choix) {
        choixPossibles.add(choix);
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getTexte() {
        return texte;
    }

    public List<Choix> getChoixPossibles() {
        return choixPossibles;
    }

    public boolean estFin() {
        return estFin;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean hasEnemy() {
        return enemy != null;
    }

    /**
     * Définit le chapitre à montrer en cas de défaite au combat
     * @param chapitreDefaite Le chapitre de défaite
     */
    public void setChapitreDefaite(Chapitre chapitreDefaite) {
        this.chapitreDefaite = chapitreDefaite;
    }
    
    /**
     * @return Le chapitre de défaite
     */
    public Chapitre getChapitreDefaite() {
        return chapitreDefaite;
    }
    
    /**
     * @return true si un chapitre de défaite est défini
     */
    public boolean hasChapitreDefaite() {
        return chapitreDefaite != null;
    }
    
    /**
     * Ajoute un chapitre de succès pour les combats
     * @param chapitre Le chapitre à montrer en cas de succès au combat
     */
    public void ajouterChapitreSucces(Chapitre chapitre) {
        chapitresSucces.add(chapitre);
    }
    
    /**
     * @return La liste des chapitres de succès
     */
    public List<Chapitre> getChapitresSucces() {
        return chapitresSucces;
    }
    
    /**
     * @return true si des chapitres de succès sont définis
     */
    public boolean hasChapitresSucces() {
        return !chapitresSucces.isEmpty();
    }

    @Override
    public String toString() {
        return "Chapitre " + id + ": " + titre + " (Fin: " + estFin + ")";
    }

    /**
     * @return L'identifiant du chapitre (alias pour getId() pour compatibilité)
     */
    public int getNumero() {
        return id;
    }
}
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un chapitre du livre-jeu avec son texte et les choix possibles.
 */
public class Chapitre {
    private int id;
    private String titre;
    private String texte;
    private List<Choix> choixPossibles;
    private boolean estFin;
    private Enemy enemy; // Ennemi du chapitre, s'il y en a un

    /**
     * Constructeur pour un chapitre standard
     */
    public Chapitre(int id, String titre, String texte) {
        this.id = id;
        this.titre = titre;
        this.texte = texte;
        this.choixPossibles = new ArrayList<>();
        this.estFin = false;
        this.enemy = null;
    }

    /**
     * Constructeur pour un chapitre de fin
     */
    public Chapitre(int id, String titre, String texte, boolean estFin) {
        this(id, titre, texte);
        this.estFin = estFin;
    }

    /**
     * Ajoute un choix possible à ce chapitre
     */
    public void ajouterChoix(Choix choix) {
        choixPossibles.add(choix);
    }

    /**
     * Définit un ennemi pour ce chapitre
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Vérifie si le chapitre contient un ennemi
     */
    public boolean hasEnemy() {
        return enemy != null;
    }

    /**
     * Récupère l'ennemi du chapitre
     */
    public Enemy getEnemy() {
        return enemy;
    }

    // Getters et setters
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

    public void setEstFin(boolean estFin) {
        this.estFin = estFin;
    }

    @Override
    public String toString() {
        return "Chapitre " + id + ": " + titre;
    }
}
package fr.samourai.adventure.model;

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

    /**
     * Constructeur pour un chapitre standard
     */
    public Chapitre(int id, String titre, String texte) {
        this.id = id;
        this.titre = titre;
        this.texte = texte;
        this.choixPossibles = new ArrayList<>();
        this.estFin = false;
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
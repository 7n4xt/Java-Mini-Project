package view;

import model.Enemy;
import model.Personnage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Classe adaptateur qui permet d'utiliser EnhancedCombatUI partout où CombatUI
 * est attendu
 * Implémente le pattern Adapter pour convertir l'interface EnhancedCombatUI en
 * CombatUI
 */
public class CombatUIAdapter extends JDialog {

    private final EnhancedCombatUI enhancedUI;

    /**
     * Constructeur qui crée un EnhancedCombatUI en arrière-plan
     */
    public CombatUIAdapter(JFrame parent, Personnage joueur, Enemy ennemi) {
        super(parent, "Combat", true);
        this.enhancedUI = new EnhancedCombatUI(parent, joueur, ennemi);
    }

    /**
     * Redirige l'appel vers l'interface améliorée
     */
    public boolean commencerCombat() {
        return enhancedUI.commencerCombat();
    }

    /**
     * Méthode pour accéder directement à l'instance d'EnhancedCombatUI
     */
    public EnhancedCombatUI getEnhancedUI() {
        return enhancedUI;
    }
}

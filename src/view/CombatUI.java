package view;

import model.Enemy;
import model.Personnage;

import javax.swing.*;

/**
 * Interface graphique pour les combats
 * Cette classe est maintenant un simple délégateur vers EnhancedCombatUI
 * pour assurer la rétrocompatibilité avec le code existant
 */
public class CombatUI {
    private EnhancedCombatUI enhancedUI;

    /**
     * Constructeur qui crée une instance d'EnhancedCombatUI
     */
    public CombatUI(JFrame parent, Personnage joueur, Enemy ennemi) {
        this.enhancedUI = new EnhancedCombatUI(parent, joueur, ennemi);
    }

    /**
     * Lance le combat et attend la fin
     * 
     * @return true si le joueur est victorieux, false sinon
     */
    public boolean commencerCombat() {
        return enhancedUI.commencerCombat();
    }
}

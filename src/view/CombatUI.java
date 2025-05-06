package view;

import model.Enemy;
import model.Personnage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * Interface graphique pour les combats
 */
public class CombatUI extends JDialog implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Color BUTTON_COLOR = new Color(80, 60, 30);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 16);

    private Personnage joueur;
    private Enemy ennemi;
    private JTextArea combatLog;
    private JProgressBar vieJoueur;
    private JProgressBar vieEnnemi;
    private JProgressBar staminaJoueur;
    private JButton attaquerButton;
    private JButton défendreButton;
    private JButton inventaireButton;

    private boolean combatTerminé = false;
    private boolean victoire = false;
    private final CountDownLatch latch = new CountDownLatch(1);

    /**
     * Constructeur de l'interface de combat
     * 
     * @param parent La fenêtre parente
     * @param joueur Le personnage du joueur
     * @param ennemi L'ennemi à combattre
     */
    public CombatUI(JFrame parent, Personnage joueur, Enemy ennemi) {
        super(parent, "Combat - " + ennemi.getNom(), true);
        this.joueur = joueur;
        this.ennemi = ennemi;

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initComponents();
    }

    /**
     * Initialise les composants de l'interface
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panneau supérieur avec les barres de vie
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setOpaque(false);

        // Panneau du joueur
        JPanel joueurPanel = new JPanel(new BorderLayout(5, 5));
        joueurPanel.setOpaque(false);

        JLabel joueurLabel = new JLabel(joueur.getNom(), JLabel.LEFT);
        joueurLabel.setFont(TITLE_FONT);
        joueurLabel.setForeground(TEXT_COLOR);

        vieJoueur = new JProgressBar(0, joueur.getStatistique("vie"));
        vieJoueur.setValue(joueur.getStatistique("vie"));
        vieJoueur.setStringPainted(true);
        vieJoueur.setString("vie actuel");
        vieJoueur.setForeground(new Color(50, 150, 50));

        staminaJoueur = new JProgressBar(0, 100);
        staminaJoueur.setValue(100); // Commencer à 100%
        staminaJoueur.setStringPainted(true);
        staminaJoueur.setString("Stamina");
        staminaJoueur.setForeground(new Color(50, 100, 150));

        joueurPanel.add(joueurLabel, BorderLayout.NORTH);
        joueurPanel.add(vieJoueur, BorderLayout.CENTER);
        joueurPanel.add(staminaJoueur, BorderLayout.SOUTH);

        // Panneau de l'ennemi
        JPanel ennemiPanel = new JPanel(new BorderLayout(5, 5));
        ennemiPanel.setOpaque(false);

        JLabel ennemiLabel = new JLabel(ennemi.getNom(), JLabel.RIGHT);
        ennemiLabel.setFont(TITLE_FONT);
        ennemiLabel.setForeground(TEXT_COLOR);

        vieEnnemi = new JProgressBar(0, ennemi.getEndurance());
        vieEnnemi.setValue(ennemi.getEndurance());
        vieEnnemi.setStringPainted(true);
        vieEnnemi.setString("vie ennemi");
        vieEnnemi.setForeground(new Color(150, 50, 50));

        ennemiPanel.add(ennemiLabel, BorderLayout.NORTH);
        ennemiPanel.add(vieEnnemi, BorderLayout.CENTER);

        statsPanel.add(joueurPanel);
        statsPanel.add(ennemiPanel);

        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Zone de texte centrale pour les logs de combat
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setFont(TEXT_FONT);
        combatLog.setForeground(TEXT_COLOR);
        combatLog.setBackground(new Color(30, 30, 30));
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        combatLog.setText(
                "Vous affrontez " + ennemi.getNom() + ".\n" + ennemi.getDescription() + "\n\nQue voulez-vous faire ?");

        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau inférieur avec les boutons d'action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0));

        attaquerButton = createButton("Attaquer");
        défendreButton = createButton("Se défendre");
        inventaireButton = createButton("Inventaire");

        buttonPanel.add(attaquerButton);
        buttonPanel.add(défendreButton);
        buttonPanel.add(inventaireButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Crée un bouton stylisé
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);

        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(120, 90, 45));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attaquerButton) {
            attaquer();
        } else if (e.getSource() == défendreButton) {
            défendre();
        } else if (e.getSource() == inventaireButton) {
            ouvrirInventaire();
        }

        // Vérifier si le combat est terminé
        if (ennemi.estVaincu()) {
            combatTerminé = true;
            victoire = true;
            finCombat("Vous avez vaincu " + ennemi.getNom() + " !");
        } else if (joueur.getStatistique("vie") <= 0) {
            combatTerminé = true;
            victoire = false;
            finCombat("Vous avez été vaincu par " + ennemi.getNom() + "...");
        }
    }

    /**
     * Action d'attaque du joueur
     */
    private void attaquer() {
        // Désactiver les boutons pendant l'action
        setButtonsEnabled(false);

        // Calculer la force d'attaque du joueur et de l'ennemi
        int dé1Joueur = (int) (Math.random() * 6) + 1;
        int dé2Joueur = (int) (Math.random() * 6) + 1;
        int forceAttaqueJoueur = joueur.getStatistique("habileté") + dé1Joueur + dé2Joueur;

        int forceAttaqueEnnemi = ennemi.calculerForceAttaque();

        appendToCombatLog("Vous lancez les dés : " + dé1Joueur + " + " + dé2Joueur +
                " + " + joueur.getStatistique("habileté") + " (habileté) = " + forceAttaqueJoueur);

        if (forceAttaqueJoueur > forceAttaqueEnnemi) {
            // Le joueur blesse l'ennemi
            int dégâts = 2; // Dégâts standards
            ennemi.infligerDégâts(dégâts);

            appendToCombatLog("Vous avez touché " + ennemi.getNom() + " et infligé " + dégâts + " points de dégâts !");
            vieEnnemi.setValue(ennemi.getEndurance());
        } else if (forceAttaqueEnnemi > forceAttaqueJoueur) {
            // L'ennemi blesse le joueur
            int dégâts = 2; // Dégâts standards
            joueur.modifierStatistique("vie", -dégâts);

            appendToCombatLog(ennemi.getNom() + " vous a touché et vous a infligé " + dégâts + " points de dégâts !");
            vieJoueur.setValue(joueur.getStatistique("vie"));
        } else {
            // Égalité, les deux esquivent mais il y a une chance que le joueur prenne quand
            // même des dégâts
            int chanceDegatAleatoire = (int) (Math.random() * 100); // 0-99

            if (chanceDegatAleatoire < 30) { // 30% de chance de prendre des dégâts même en esquivant
                int dégâtsMineurs = 1; // Dégâts réduits lors d'une esquive
                joueur.modifierStatistique("vie", -dégâtsMineurs);

                appendToCombatLog("Vous avez esquivé l'attaque principale mais " + ennemi.getNom() +
                        " vous a tout de même effleuré, vous infligeant " + dégâtsMineurs + " point de dégâts !");
                vieJoueur.setValue(joueur.getStatistique("vie"));
            } else {
                appendToCombatLog("Vous avez tous les deux esquivé les attaques !");
            }
        }

        // Réduire la stamina du joueur
        réduireStamina(15);

        // Réactiver les boutons après un court délai
        Timer timer = new Timer(800, evt -> setButtonsEnabled(true));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Action de défense du joueur
     */
    private void défendre() {
        // Désactiver les boutons pendant l'action
        setButtonsEnabled(false);

        appendToCombatLog("Vous adoptez une posture défensive...");

        // L'ennemi attaque mais avec une pénalité
        int forceAttaqueEnnemi = ennemi.calculerForceAttaque() - 2;

        // Seuil de défense du joueur (habileté + bonus de défense)
        int dé1Joueur = (int) (Math.random() * 6) + 1;
        int dé2Joueur = (int) (Math.random() * 6) + 1;
        int forceDefenseJoueur = joueur.getStatistique("habileté") + dé1Joueur + dé2Joueur + 2; // +2 bonus de défense

        if (forceAttaqueEnnemi > forceDefenseJoueur) {
            // L'ennemi blesse le joueur mais moins gravement
            int dégâts = 1; // Dégâts réduits en défense
            joueur.modifierStatistique("vie", -dégâts);

            appendToCombatLog(ennemi.getNom() + " perce votre défense et vous inflige " + dégâts + " point de dégâts.");
            vieJoueur.setValue(joueur.getStatistique("vie"));
        } else {
            // Même en défense parfaite, petite chance de recevoir des dégâts
            int chanceDegatAleatoire = (int) (Math.random() * 100); // 0-99

            if (chanceDegatAleatoire < 15) { // 15% de chance de prendre des dégâts même en défendant bien
                int dégâtsMinimes = 1;
                joueur.modifierStatistique("vie", -dégâtsMinimes);

                appendToCombatLog("Malgré votre défense, " + ennemi.getNom() +
                        " parvient à vous atteindre légèrement, vous infligeant " + dégâtsMinimes
                        + " point de dégâts.");
                vieJoueur.setValue(joueur.getStatistique("vie"));
            } else {
                appendToCombatLog("Vous avez parfaitement paré l'attaque de " + ennemi.getNom() + " !");
            }
        }

        // Réduire moins la stamina car la défense est moins fatigante
        réduireStamina(5);

        // Réactiver les boutons après un court délai
        Timer timer = new Timer(800, evt -> setButtonsEnabled(true));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Ouvre l'inventaire du joueur
     */
    private void ouvrirInventaire() {
        // Créer une fenêtre d'inventaire simplifiée
        JDialog inventaireDialog = new JDialog(this, "Inventaire", true);
        inventaireDialog.setSize(400, 300);
        inventaireDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Votre inventaire", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (joueur.getInventaire().isEmpty()) {
            JLabel emptyLabel = new JLabel("Votre inventaire est vide", JLabel.CENTER);
            emptyLabel.setFont(TEXT_FONT);
            emptyLabel.setForeground(TEXT_COLOR);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(emptyLabel);
        } else {
            for (String item : joueur.getInventaire()) {
                JButton itemButton = new JButton(item);
                itemButton.setFont(TEXT_FONT);
                itemButton.setForeground(TEXT_COLOR);
                itemButton.setBackground(BUTTON_COLOR);
                itemButton.setBorderPainted(false);
                itemButton.setFocusPainted(false);
                itemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                itemButton.setMaximumSize(new Dimension(300, 40));

                // Action pour utiliser l'objet
                itemButton.addActionListener(evt -> {
                    utiliserObjet(item);
                    inventaireDialog.dispose();
                });

                panel.add(itemButton);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        JButton retourButton = new JButton("Retour au combat");
        retourButton.setFont(TEXT_FONT);
        retourButton.setForeground(TEXT_COLOR);
        retourButton.setBackground(BUTTON_COLOR);
        retourButton.setBorderPainted(false);
        retourButton.setFocusPainted(false);
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourButton.addActionListener(evt -> inventaireDialog.dispose());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(retourButton);

        inventaireDialog.setContentPane(panel);
        inventaireDialog.setVisible(true);
    }

    /**
     * Utilise un objet de l'inventaire
     * 
     * @param item Nom de l'objet à utiliser
     */
    private void utiliserObjet(String item) {
        if (item.contains("Potion")) {
            // Utiliser une potion de soin
            int soin = 4; // La potion soigne 4 points de vie
            int vieActuelle = joueur.getStatistique("vie");
            joueur.modifierStatistique("vie", soin);
            joueur.retirerObjet(item);

            appendToCombatLog("Vous utilisez une " + item + " et récupérez " + soin + " points de vie !");
            vieJoueur.setValue(joueur.getStatistique("vie"));
        } else if (item.contains("Rations")) {
            // Utiliser des rations
            int soin = 2; // Les rations soignent 2 points de vie et restaurent de la stamina
            joueur.modifierStatistique("vie", soin);
            joueur.retirerObjet(item);

            appendToCombatLog(
                    "Vous consommez vos " + item + " et récupérez " + soin + " points de vie et de l'énergie !");
            vieJoueur.setValue(joueur.getStatistique("vie"));
            restaurerStamina(40);
        } else {
            // Autre objet
            appendToCombatLog("Vous ne pouvez pas utiliser cet objet pendant le combat.");
        }
    }

    /**
     * Réduit la stamina du joueur
     * 
     * @param quantité Quantité de stamina à réduire
     */
    private void réduireStamina(int quantité) {
        int valeurActuelle = staminaJoueur.getValue();
        staminaJoueur.setValue(Math.max(0, valeurActuelle - quantité));

        if (staminaJoueur.getValue() <= 30) {
            staminaJoueur.setForeground(new Color(200, 100, 50)); // Orange-rouge quand fatigué
        }

        // Si la stamina est trop basse, désactiver certaines actions
        if (staminaJoueur.getValue() < 15) {
            attaquerButton.setEnabled(false);
            appendToCombatLog("Vous êtes trop fatigué pour attaquer !");
        }
    }

    /**
     * Restaure la stamina du joueur
     * 
     * @param quantité Quantité de stamina à restaurer
     */
    private void restaurerStamina(int quantité) {
        int valeurActuelle = staminaJoueur.getValue();
        staminaJoueur.setValue(Math.min(100, valeurActuelle + quantité));

        if (staminaJoueur.getValue() > 30) {
            staminaJoueur.setForeground(new Color(50, 100, 150)); // Bleu normal
            attaquerButton.setEnabled(true);
        }
    }

    /**
     * Active ou désactive les boutons d'action
     */
    private void setButtonsEnabled(boolean enabled) {
        attaquerButton.setEnabled(enabled && staminaJoueur.getValue() >= 15);
        défendreButton.setEnabled(enabled);
        inventaireButton.setEnabled(enabled);
    }

    /**
     * Ajoute du texte au log de combat
     */
    private void appendToCombatLog(String text) {
        combatLog.append("\n" + text);
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
    }

    /**
     * Termine le combat
     */
    private void finCombat(String message) {
        appendToCombatLog("\n" + message);

        Timer timer = new Timer(2000, evt -> {
            latch.countDown();
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Lance le combat et attend la fin
     * 
     * @return true si le joueur est victorieux, false sinon
     */
    public boolean commencerCombat() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return victoire;
    }
}

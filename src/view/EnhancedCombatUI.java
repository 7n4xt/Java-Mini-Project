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
 * Interface graphique améliorée pour les combats
 * Inclut un système de gestion d'endurance et d'aide stratégique
 */
public class EnhancedCombatUI extends JDialog implements ActionListener {
    // Constantes UI
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Color BUTTON_COLOR = new Color(80, 60, 30);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 16);

    // Composants UI
    private Personnage joueur;
    private Enemy ennemi;
    private JTextArea combatLog;
    private JProgressBar vieJoueur;
    private JProgressBar vieEnnemi;
    private JProgressBar staminaJoueur;
    private JButton attaquerButton;
    private JButton défendreButton;
    private JButton inventaireButton;
    private JButton fuirButton;
    private JButton strategyButton;

    // État du combat
    private boolean combatTerminé = false;
    private boolean victoire = false;
    private boolean staminaAlertShown = false;
    private final CountDownLatch latch = new CountDownLatch(1);

    // Coût en endurance des actions
    private static final int COUT_ATTAQUE = 3;
    private static final int COUT_DEFENSE = 1;
    private static final int COUT_FUITE_ECHEC = 10;
    private static final int GAIN_VICTOIRE = 5;

    /**
     * Constructeur de l'interface de combat améliorée
     * 
     * @param parent La fenêtre parente
     * @param joueur Le personnage du joueur
     * @param ennemi L'ennemi à combattre
     */
    public EnhancedCombatUI(JFrame parent, Personnage joueur, Enemy ennemi) {
        super(parent, "Combat - " + ennemi.getNom(), true);
        this.joueur = joueur;
        this.ennemi = ennemi;

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initComponents();
        setupCombatActions();
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
        vieJoueur.setString("Vie: " + joueur.getStatistique("vie") + "/" + joueur.getStatistique("vie"));
        vieJoueur.setForeground(new Color(50, 150, 50));

        // Configure la barre d'endurance avec la nouvelle statistique
        staminaJoueur = new JProgressBar(0, 15); // Valeur max d'endurance = 15
        staminaJoueur.setValue(joueur.getStatistique("endurance"));
        staminaJoueur.setStringPainted(true);
        staminaJoueur.setString("Endurance: " + joueur.getStatistique("endurance") + "/15");
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
        vieEnnemi.setString("Vie: " + ennemi.getEndurance() + "/" + ennemi.getEnduranceMax());
        vieEnnemi.setForeground(new Color(150, 50, 50));

        ennemiPanel.add(ennemiLabel, BorderLayout.NORTH);
        ennemiPanel.add(vieEnnemi, BorderLayout.CENTER);

        statsPanel.add(joueurPanel);
        statsPanel.add(ennemiPanel);

        // Assurons-nous que les barres de statut sont bien visibles
        statsPanel.setPreferredSize(new Dimension(700, 80));
        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Zone de texte centrale pour les logs de combat
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setFont(TEXT_FONT);
        combatLog.setForeground(TEXT_COLOR);
        combatLog.setBackground(new Color(10, 10, 10, 240)); // Plus sombre pour un meilleur contraste
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);
        combatLog.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        combatLog.setMargin(new Insets(15, 15, 15, 15)); // Marge augmentée pour éviter que le texte touche les bords
        combatLog.setOpaque(true);
        combatLog.setText("Vous affrontez " + ennemi.getNom() + ".\n\n" + ennemi.getDescription()
                + "\n\nQue voulez-vous faire ?");

        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau inférieur avec les boutons d'action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 0));

        attaquerButton = createButton("Attaquer");
        défendreButton = createButton("Se défendre");
        inventaireButton = createButton("Inventaire");
        fuirButton = createButton("Fuir");

        buttonPanel.add(attaquerButton);
        buttonPanel.add(défendreButton);
        buttonPanel.add(inventaireButton);
        buttonPanel.add(fuirButton);

        // Ajouter un bouton d'aide stratégique
        strategyButton = createButton("Conseils");
        strategyButton.setBackground(new Color(50, 70, 120)); // Couleur bleue pour le distinguer
        strategyButton
                .setToolTipText("Afficher des conseils sur la gestion de l'endurance et les stratégies de combat");

        JPanel extraButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        extraButtonPanel.setOpaque(false);
        extraButtonPanel.add(strategyButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Appliquer les bonus du chapitre 2 si nécessaire
        initCombatWithChapter2Bonuses();
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
                button.setBackground(button == strategyButton ? new Color(50, 70, 120) : BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Configure les actions spéciales du combat
     */
    private void setupCombatActions() {
        // Si l'ennemi est un ninja, ajouter un message spécial
        if (ennemi.getNom().toLowerCase().contains("ninja")) {
            appendToCombatLog("Cet adversaire est un ninja! Vos attaques feront plus de dégâts.");
        }

        // Ajouter une action spécifique au bouton de stratégie
        strategyButton.removeActionListener(this); // Enlever le listener générique
        strategyButton.addActionListener(e -> showCombatStrategies());
    }

    /**
     * Applique des bonus pour les combats du chapitre 2
     */
    private void initCombatWithChapter2Bonuses() {
        // Vérifier si nous sommes au chapitre 2 en regardant le titre de la fenêtre
        // parente
        boolean isChapter2 = getTitle().contains("Chapitre 2");

        if (isChapter2) {
            // Donner un avantage au joueur pour le chapitre 2
            int bonusEndurance = 5;
            int bonusHabilete = 2;

            joueur.modifierStatistique("vie", bonusEndurance);
            joueur.modifierStatistique("habileté", bonusHabilete);

            // Mettre à jour les barres de vie avec les nouvelles valeurs
            vieJoueur.setMaximum(joueur.getStatistique("vie"));
            vieJoueur.setValue(joueur.getStatistique("vie"));

            // Afficher un message spécial
            appendToCombatLog("Votre expérience du chapitre précédent vous donne un avantage au combat! " +
                    "+" + bonusEndurance + " ENDURANCE, +" + bonusHabilete + " HABILETÉ");
        }
    }

    /**
     * Action d'attaque du joueur
     */
    private void attaquer() {
        // Vérifier si le joueur a assez d'endurance pour attaquer
        if (!joueur.utiliserEndurance(COUT_ATTAQUE)) {
            appendToCombatLog("Vous êtes trop fatigué pour attaquer! Il vous faut récupérer de l'endurance.");

            // Si le joueur n'a plus d'endurance et a déjà essayé de défendre 3 fois sans
            // endurance,
            // l'ennemi l'achève
            int defensesEchouees = (int) combatLog.getText().split("Vous êtes trop fatigué").length - 1;
            if (defensesEchouees >= 3) {
                appendToCombatLog(
                        "Sans endurance pour vous défendre, " + ennemi.getNom() + " vous achève d'un coup fatal!");

                // Infliger des dégâts fatals
                joueur.modifierStatistique("vie", -joueur.getStatistique("vie"));
                updateLifeBars();

                // Défaite
                combatTerminé = true;
                victoire = false;
                finCombat("Vous avez été vaincu par " + ennemi.getNom() + "!");
                return;
            }

            // Mettre à jour la barre d'endurance
            updateStaminaBar();
            setButtonsEnabled(true);
            return;
        }

        // Désactiver les boutons pendant l'action
        setButtonsEnabled(false);

        // Calculer la force d'attaque du joueur et de l'ennemi
        int dé1Joueur = (int) (Math.random() * 6) + 1;
        int dé2Joueur = (int) (Math.random() * 6) + 1;
        int forceAttaqueJoueur = joueur.getStatistique("habileté") + dé1Joueur + dé2Joueur;

        int forceAttaqueEnnemi = ennemi.calculerForceAttaque();

        appendToCombatLog("Vous lancez les dés : " + dé1Joueur + " + " + dé2Joueur +
                " + " + joueur.getStatistique("habileté") + " (habileté) = " + forceAttaqueJoueur);

        // Mettre à jour la barre d'endurance
        updateStaminaBar();

        if (forceAttaqueJoueur > forceAttaqueEnnemi) {
            // Le joueur blesse l'ennemi
            int dégâtsBase = 2; // Dégâts standards
            int dégâtsBonus = 1; // Dégâts bonus supplémentaires
            int dégâtsTotal = dégâtsBase + dégâtsBonus;

            // Infligez les dégâts (ils seront amplifiés dans l'objet Enemy)
            ennemi.infligerDégâts(dégâtsTotal);

            appendToCombatLog("Vous avez touché " + ennemi.getNom() + " avec précision et infligé " + dégâtsTotal +
                    " points de dégâts de base !");
            vieEnnemi.setValue(ennemi.getEndurance());

            // Bonus de dégâts aléatoire (25% de chance)
            if (Math.random() < 0.25) {
                int dégâtsCritiques = 2;
                ennemi.infligerDégâts(dégâtsCritiques);
                appendToCombatLog("COUP CRITIQUE! Vous portez un coup supplémentaire et infligez " +
                        dégâtsCritiques + " points de dégâts supplémentaires!");
                vieEnnemi.setValue(ennemi.getEndurance());
            }
        } else if (forceAttaqueEnnemi > forceAttaqueJoueur) {
            // L'ennemi blesse le joueur
            int dégâts = 2; // Dégâts standards
            joueur.modifierStatistique("vie", -dégâts);

            appendToCombatLog(ennemi.getNom() + " vous a touché et vous a infligé " + dégâts + " points de dégâts !");
            vieJoueur.setValue(joueur.getStatistique("vie"));
        } else {
            // Égalité, les deux esquivent
            int chanceDegatAleatoire = (int) (Math.random() * 100); // 0-99

            if (chanceDegatAleatoire < 30) { // 30% de chance de prendre des dégâts même en esquivant
                int dégâtsMineurs = 1; // Dégâts réduits lors d'une esquive
                joueur.modifierStatistique("vie", -dégâtsMineurs);

                appendToCombatLog("Vous avez esquivé l'attaque principale mais " + ennemi.getNom() +
                        " vous a tout de même effleuré, vous infligeant " + dégâtsMineurs + " point de dégâts !");
                vieJoueur.setValue(joueur.getStatistique("vie"));
            } else {
                appendToCombatLog("Vous avez tous les deux esquivé les attaques !");

                // Quand les deux esquivent, le joueur a une chance de contre-attaquer (40%)
                if (Math.random() < 0.4) {
                    int dégâtsContre = 1;
                    ennemi.infligerDégâts(dégâtsContre);
                    appendToCombatLog("Vous profitez du moment pour effectuer une contre-attaque rapide! " +
                            "Vous infligez " + dégâtsContre + " point de dégâts!");
                    vieEnnemi.setValue(ennemi.getEndurance());
                }
            }
        }

        // Bonus de dégâts contre les ninjas
        if (ennemi.getNom().toLowerCase().contains("ninja")) {
            int bonusDamage = 2;
            ennemi.infligerDégâts(bonusDamage);
            appendToCombatLog("Vous exploitez la faiblesse du ninja! +2 dégâts bonus!");
            vieEnnemi.setValue(ennemi.getEndurance());
        }

        // Réactiver les boutons après un court délai
        Timer timer = new Timer(800, evt -> {
            setButtonsEnabled(true);

            // Vérifier si l'ennemi est vaincu après l'attaque
            if (ennemi.estVaincu()) {
                combatTerminé = true;
                victoire = true;
                finCombat("Vous avez vaincu " + ennemi.getNom() + " !");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Action de défense du joueur
     */
    private void défendre() {
        // Désactiver les boutons pendant l'action
        setButtonsEnabled(false);

        // La défense coûte moins d'endurance que l'attaque
        if (!joueur.utiliserEndurance(COUT_DEFENSE)) {
            appendToCombatLog("Vous êtes trop fatigué même pour vous défendre! Utilisez des objets pour récupérer.");
            setButtonsEnabled(true);
            return;
        }

        updateStaminaBar();
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

                // Riposte quand défense réussie (60% de chance)
                if (Math.random() < 0.6) {
                    int dégâtsRiposte = 2;
                    ennemi.infligerDégâts(dégâtsRiposte);
                    appendToCombatLog("Après avoir paré, vous ripostez immédiatement! Vous infligez " +
                            dégâtsRiposte + " points de dégâts!");
                    vieEnnemi.setValue(ennemi.getEndurance());
                }
            }
        }

        // Réactiver les boutons après un court délai
        Timer timer = new Timer(800, evt -> {
            setButtonsEnabled(true);

            // Vérifier si l'ennemi est vaincu après la défense
            if (ennemi.estVaincu()) {
                combatTerminé = true;
                victoire = true;
                finCombat("Vous avez vaincu " + ennemi.getNom() + " !");
            }
        });
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

                // Ajouter un tooltip avec une description de l'objet
                String tooltip = getItemDescription(item);
                if (tooltip != null) {
                    itemButton.setToolTipText(tooltip);
                }

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
            joueur.modifierStatistique("vie", soin);
            joueur.retirerObjet(item);

            appendToCombatLog("Vous utilisez une " + item + " et récupérez " + soin + " points de vie !");
            updateLifeBars();
        } else if (item.contains("Rations")) {
            // Utiliser des rations - maintenant restaurent plus d'endurance
            int soin = 2; // Les rations soignent 2 points de vie
            int staminaRestore = 6; // Restaurent 6 points d'endurance (augmenté)

            joueur.modifierStatistique("vie", soin);
            joueur.restaurerEndurance(staminaRestore);
            joueur.retirerObjet(item);

            appendToCombatLog("Vous consommez vos " + item + " et récupérez " + soin +
                    " points de vie et " + staminaRestore + " points d'endurance !");

            updateLifeBars();
            updateStaminaBar();
        } else if (item.contains("Katana")) {
            // Utiliser le katana pour une attaque spéciale
            appendToCombatLog("Vous brandissez votre " + item + " et effectuez une attaque spéciale tranchante!");

            // Inflige des dégâts importants (le katana fait de gros dégâts)
            int degatsKatana = 4;
            ennemi.infligerDégâts(degatsKatana, item);

            updateLifeBars();

            // Vérifier si l'ennemi est vaincu après l'attaque
            if (ennemi.estVaincu()) {
                combatTerminé = true;
                victoire = true;
                finCombat("Vous avez vaincu " + ennemi.getNom() + " grâce à votre Katana !");
            }
        } else if (item.contains("Kunai")) {
            // Utiliser le kunai pour une attaque à distance
            appendToCombatLog("Vous lancez un " + item + " avec précision !");

            // Le kunai fait moins de dégâts que le katana mais ne coûte pas d'endurance
            int degatsKunai = 2;
            ennemi.infligerDégâts(degatsKunai);

            appendToCombatLog("Le kunai atteint sa cible et inflige " + degatsKunai + " points de dégâts !");
            updateLifeBars();

            // Consommer l'objet après utilisation
            joueur.retirerObjet(item);
        } else {
            // Autre objet
            appendToCombatLog("Vous ne pouvez pas utiliser cet objet pendant le combat.");
        }
    }

    /**
     * Renvoie une description d'un objet
     */
    private String getItemDescription(String item) {
        if (item.contains("Potion")) {
            return "Restaure 4 points de vie";
        } else if (item.contains("Rations")) {
            return "Restaure 2 points de vie et 6 points d'endurance";
        } else if (item.contains("Katana")) {
            return "Arme puissante: inflige 4× les dégâts normaux";
        } else if (item.contains("Kunai")) {
            return "Arme de jet: inflige 2 points de dégâts, ne coûte pas d'endurance";
        }
        return null;
    }

    /**
     * Action de fuite
     */
    private void fuir() {
        appendToCombatLog("Vous tentez de fuir le combat...");
        int chanceFuite = (int) (Math.random() * 100); // 0-99

        if (chanceFuite < 50) { // 50% de chance de réussir à fuir
            combatTerminé = true;
            victoire = false;
            finCombat("Vous avez réussi à fuir le combat !");
        } else {
            appendToCombatLog("Votre tentative de fuite échoue, l'ennemi vous rattrape !");

            // Coûte de l'endurance en cas d'échec
            joueur.utiliserEndurance(COUT_FUITE_ECHEC);
            updateStaminaBar();
        }
    }

    /**
     * Termine le combat
     */
    private void finCombat(String message) {
        appendToCombatLog("\n" + message);

        // Si le joueur est victorieux et l'ennemi a une récompense
        if (victoire && ennemi.getItemRecompense() != null) {
            // Attendre un peu avant de montrer la récompense
            Timer rewardTimer = new Timer(1000, evt -> {
                // Donner la récompense au joueur
                String recompense = ennemi.getItemRecompense();
                joueur.ajouterInventaire(recompense);

                // Informer le joueur de sa récompense
                String rewardMessage = "Vous avez obtenu: " + recompense + "!";
                appendToCombatLog("\n" + rewardMessage);

                // Afficher une petite boîte de dialogue avec l'icône de l'objet
                String icon = Personnage.getItemIcon(recompense);
                JOptionPane.showMessageDialog(this,
                        "Vous avez vaincu " + ennemi.getNom() + " et obtenu:\n\n" +
                                icon + " " + recompense,
                        "Récompense obtenue",
                        JOptionPane.INFORMATION_MESSAGE);

                // Restaurer de l'endurance après la victoire
                joueur.restaurerEndurance(GAIN_VICTOIRE);

                // Terminer le combat après la récompense
                latch.countDown();
                dispose();
            });
            rewardTimer.setRepeats(false);
            rewardTimer.start();
        } else {
            // Pas de récompense ou défaite, terminer normalement
            Timer timer = new Timer(2000, evt -> {
                latch.countDown();
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Affiche l'écran d'aide stratégique
     */
    private void showCombatStrategies() {
        StaminaTipsWindow tipsWindow = new StaminaTipsWindow(this, joueur);
        tipsWindow.setVisible(true);
    }

    /**
     * Met à jour l'affichage des barres de vie
     */
    private void updateLifeBars() {
        // Utiliser le helper pour mettre à jour les barres de vie avec une meilleure
        // visibilité
        int currentLife = joueur.getStatistique("vie");
        int maxLife = vieJoueur.getMaximum();
        CombatHelper.updateLifeBar(vieJoueur, currentLife, maxLife);

        // Mise à jour de la vie de l'ennemi
        int enemyLife = ennemi.getEndurance();
        int enemyMaxLife = ennemi.getEnduranceMax();
        CombatHelper.updateLifeBar(vieEnnemi, enemyLife, enemyMaxLife);

        // S'assurer que les barres sont visibles
        vieJoueur.setVisible(true);
        vieEnnemi.setVisible(true);

        // Faire clignoter la barre de vie si elle est basse
        if (currentLife <= maxLife * 0.25) {
            CombatHelper.createFlashEffect(vieJoueur, new Color(255, 50, 50), new Color(150, 20, 20), 2);
        }
    }

    /**
     * Met à jour l'affichage de la barre d'endurance
     */
    private void updateStaminaBar() {
        int currentStamina = joueur.getStatistique("endurance");
        int maxStamina = 15; // Valeur max d'endurance

        // Utiliser le helper pour mettre à jour la barre d'endurance
        CombatHelper.updateStaminaBar(staminaJoueur, currentStamina, maxStamina);

        // S'assurer que la barre est visible
        staminaJoueur.setVisible(true);

        // Animation et changement de couleur en fonction du niveau d'endurance
        if (currentStamina <= 3 && currentStamina > 0 && !staminaAlertShown) {
            CombatHelper.createFlashEffect(staminaJoueur, new Color(255, 50, 50), new Color(200, 50, 50), 3);

            // Montrer un message d'avertissement si c'est la première fois qu'on atteint ce
            // niveau
            showStaminaAlert();
        } else if (currentStamina <= 7) {
            staminaJoueur.setForeground(new Color(200, 150, 50)); // Orange si moyen
            staminaAlertShown = false; // Réinitialiser pour afficher à nouveau l'alerte si la stamina baisse encore
        } else {
            staminaJoueur.setForeground(new Color(50, 100, 150)); // Bleu si bon niveau
            staminaAlertShown = false; // Réinitialiser pour afficher à nouveau l'alerte si la stamina baisse encore
        }

        // Mise à jour de l'état des boutons en fonction de l'endurance
        setButtonsEnabled(true);
    }

    /**
     * Affiche une alerte concernant la stamina basse
     */
    private void showStaminaAlert() {
        appendToCombatLog(
                "ATTENTION: Votre endurance est critique! Utilisez des objets ou défendez-vous pour récupérer.");
        staminaAlertShown = true;

        // Afficher un popup d'alerte après un court délai
        Timer alertTimer = new Timer(500, e -> {
            CombatHelper.showQuickTip(this,
                    "Votre endurance est critique!\n\n" +
                            "• La défense coûte moins d'endurance (1 point)\n" +
                            "• Les Rations restaurent 6 points d'endurance\n" +
                            "• 3 défenses impossibles = défaite\n" +
                            "• Cliquez sur le bouton \"Conseils\" pour plus d'informations",
                    JOptionPane.WARNING_MESSAGE);
        });
        alertTimer.setRepeats(false);
        alertTimer.start();
    }

    /**
     * Active ou désactive les boutons d'action
     */
    private void setButtonsEnabled(boolean enabled) {
        attaquerButton.setEnabled(enabled && joueur.getStatistique("endurance") >= COUT_ATTAQUE);
        défendreButton.setEnabled(enabled && joueur.getStatistique("endurance") >= COUT_DEFENSE);
        inventaireButton.setEnabled(enabled);
        fuirButton.setEnabled(enabled);
        strategyButton.setEnabled(true); // Toujours actif
    }

    /**
     * Ajoute du texte au log de combat avec mise en forme améliorée
     */
    private void appendToCombatLog(String text) {
        // Ajouter des espaces pour une meilleure lisibilité
        if (combatLog.getText().trim().length() > 0) {
            combatLog.append("\n\n• " + text);
        } else {
            combatLog.append("• " + text);
        }

        // Limiter la taille du log pour éviter les ralentissements
        String content = combatLog.getText();
        if (content.length() > 2000) {
            content = content.substring(content.length() - 1500);
            int firstNewLine = content.indexOf("\n");
            if (firstNewLine > 0) {
                content = content.substring(firstNewLine + 1);
            }
            combatLog.setText(content);
        }

        // Défiler automatiquement vers le bas
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
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

    /**
     * Implémentation de l'actionPerformed pour gérer les actions des boutons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == attaquerButton) {
            attaquer();
        } else if (source == défendreButton) {
            défendre();
        } else if (source == inventaireButton) {
            ouvrirInventaire();
        } else if (source == fuirButton) {
            fuir();
        }
    }
}

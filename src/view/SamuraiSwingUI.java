package view;

import controller.GameController;
import controller.ScenarioLoader; // Add this import
import model.Chapitre;
import model.Choix;
import model.Enemy;
import model.Personnage;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface graphique améliorée avec un style samouraï inspiré de Ghost of
 * Tsushima.
 */
public class SamuraiSwingUI extends JFrame {
    private GameController gameController;

    // Couleurs et polices pour le style samouraï
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Color BUTTON_COLOR = new Color(80, 60, 30);
    private static final Color BUTTON_HOVER_COLOR = new Color(120, 90, 45);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 28);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 16);
    private static final String BACKGROUND_IMAGE = "/resources/4755308.jpg";

    private JPanel mainPanel;
    private JTextArea texteChapitreArea;
    private JPanel choixPanel;
    private JPanel statsPanel;
    private Image backgroundImage;

    // Progression des chapitres
    private Map<Integer, Integer> chapterProgression;
    private Map<Integer, Boolean> completedChapters;

    /**
     * Constructeur de l'interface graphique samouraï
     * 
     * @param gameController Le contrôleur de jeu
     */
    public SamuraiSwingUI(GameController gameController) {
        super("L'Épée du Samouraï");
        this.gameController = gameController;

        // Initialiser la progression des chapitres
        chapterProgression = new HashMap<>();
        completedChapters = new HashMap<>();

        // Chapitre 1 vers Chapitre 2
        chapterProgression.put(6, 2); // Le chapitre 6 (fin heureuse du chapitre 1) débloque le chapitre 2

        // Configuration de la fenêtre principale
        setTitle("L'Épée du Samouraï - " + gameController.getPersonnage().getNom());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Plein écran
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Ajouter un écouteur pour la touche Échap pour quitter le plein écran
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (JOptionPane.showConfirmDialog(
                            SamuraiSwingUI.this,
                            "Voulez-vous quitter le jeu ?",
                            "Quitter",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Pour que l'écouteur de clavier fonctionne
        setFocusable(true);

        // Essayer de charger l'image de fond
        try {
            // Utiliser l'image définie dans la constante BACKGROUND_IMAGE
            backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();
        } catch (Exception e) {
            System.out.println("Impossible de charger l'image de fond. Utilisation de la couleur par défaut.");
            backgroundImage = null;
        }

        // Création des composants
        initComponents();

        // Affichage du premier chapitre
        afficherChapitre(gameController.getChapitreActuel());
    }

    /**
     * Initialise les composants de l'interface
     */
    private void initComponents() {
        // Panneau principal avec image de fond
        mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180)); // Overlay semi-transparent
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Panel supérieur avec le titre et les informations du joueur
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titreLabel = new JLabel(gameController.getScenario().getTitre());
        titreLabel.setFont(TITLE_FONT);
        titreLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titreLabel, BorderLayout.CENTER);

        // Panel des statistiques du joueur
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ACCENT_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)));
        statsPanel.setOpaque(false);
        updateStatsPanel();
        headerPanel.add(statsPanel, BorderLayout.EAST);

        // Panel pour le titre du chapitre
        JPanel chapterTitlePanel = new JPanel();
        chapterTitlePanel.setOpaque(false);
        chapterTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel chapterLabel = new JLabel("CHAPITRE");
        chapterLabel.setFont(BUTTON_FONT);
        chapterLabel.setForeground(ACCENT_COLOR);
        chapterTitlePanel.add(chapterLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(chapterTitlePanel, BorderLayout.WEST);

        // Zone de texte pour afficher le contenu du chapitre
        texteChapitreArea = new JTextArea();
        texteChapitreArea.setEditable(false);
        texteChapitreArea.setLineWrap(true);
        texteChapitreArea.setWrapStyleWord(true);
        texteChapitreArea.setFont(TEXT_FONT);
        texteChapitreArea.setForeground(TEXT_COLOR);
        texteChapitreArea.setBackground(new Color(0, 0, 0, 100));
        texteChapitreArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        texteChapitreArea.setMargin(new Insets(10, 10, 10, 10));

        // Encapsuler dans un JScrollPane pour gérer le défilement
        JScrollPane scrollPane = new JScrollPane(texteChapitreArea);
        scrollPane.setBorder(new LineBorder(ACCENT_COLOR, 1));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel des choix
        choixPanel = new JPanel();
        choixPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        choixPanel.setLayout(new BoxLayout(choixPanel, BoxLayout.Y_AXIS));
        choixPanel.setOpaque(false);
        mainPanel.add(choixPanel, BorderLayout.SOUTH);

        // Panel pour les boutons de menu
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.EAST);

        // Ajout du panel principal à la fenêtre
        setContentPane(mainPanel);
    }

    /**
     * Crée le panneau de menu avec les boutons
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JButton inventaireButton = createStyledButton("🎒 Inventaire");
        JButton sauvegarderButton = createStyledButton("Sauvegarder");
        JButton chargerButton = createStyledButton("Charger");
        JButton menuButton = createStyledButton("Menu");
        JButton quitterButton = createStyledButton("Quitter");
        JButton helpButton = createStyledButton("❔ Aide");

        inventaireButton.addActionListener(e -> {
            InventoryWindow inventoryWindow = new InventoryWindow(this, gameController.getPersonnage());
            inventoryWindow.setVisible(true);
            updateStatsPanel(); // Mettre à jour l'affichage après modification de l'inventaire
        });

        sauvegarderButton.addActionListener(e -> sauvegarderPartie());
        chargerButton.addActionListener(e -> chargerPartie());
        menuButton.addActionListener(e -> retourMenu());
        quitterButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous vraiment quitter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        helpButton.addActionListener(e -> {
            HelpWindow helpWindow = new HelpWindow(this);
            helpWindow.setVisible(true);
        });

        menuPanel.add(inventaireButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(sauvegarderButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(chargerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(menuButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(quitterButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(helpButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        return menuPanel;
    }

    /**
     * Crée un bouton stylisé
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setPreferredSize(new Dimension(150, 40));

        return button;
    }

    /**
     * Crée un bouton de choix avec le style approprié
     */
    private JButton createChoiceButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(500, 40));
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    /**
     * Met à jour l'affichage du panel des statistiques
     */
    private void updateStatsPanel() {
        statsPanel.removeAll();

        Personnage personnage = gameController.getPersonnage();
        JLabel nomLabel = new JLabel(personnage.getNom());
        nomLabel.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        nomLabel.setForeground(TEXT_COLOR);
        statsPanel.add(nomLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Séparateur
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(ACCENT_COLOR);
        statsPanel.add(separator);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Statistiques
        Map<String, Integer> stats = personnage.getStatistiques();
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            JPanel statPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            statPanel.setOpaque(false);

            JLabel statNameLabel = new JLabel(stat.getKey() + ":");
            statNameLabel.setForeground(ACCENT_COLOR);
            statNameLabel.setFont(new Font("Yu Mincho", Font.PLAIN, 14));

            JLabel statValueLabel = new JLabel(stat.getValue().toString());
            statValueLabel.setForeground(TEXT_COLOR);
            statValueLabel.setFont(new Font("Yu Mincho", Font.BOLD, 14));

            statPanel.add(statNameLabel);
            statPanel.add(statValueLabel);

            statsPanel.add(statPanel);
        }

        // Inventaire
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel inventaireLabel = new JLabel("Inventaire:");
        inventaireLabel.setForeground(ACCENT_COLOR);
        inventaireLabel.setFont(new Font("Yu Mincho", Font.BOLD, 14));
        statsPanel.add(inventaireLabel);

        List<String> inventaire = personnage.getInventaire();
        if (inventaire.isEmpty()) {
            JLabel vide = new JLabel("vide");
            vide.setForeground(TEXT_COLOR);
            vide.setFont(new Font("Yu Mincho", Font.ITALIC, 14));
            statsPanel.add(vide);
        } else {
            for (String item : inventaire) {
                String icon = Personnage.getItemIcon(item);
                JLabel itemLabel = new JLabel(icon + " " + item);
                itemLabel.setForeground(TEXT_COLOR);
                itemLabel.setFont(new Font("Yu Mincho", Font.PLAIN, 14));
                statsPanel.add(itemLabel);
            }
        }

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    /**
     * Affiche un chapitre et ses choix possibles
     * 
     * @param chapitre Le chapitre à afficher
     */
    public void afficherChapitre(Chapitre chapitre) {
        // Mise à jour du texte du chapitre
        texteChapitreArea.setText(chapitre.getTitre() + "\n\n" + chapitre.getTexte());
        texteChapitreArea.setCaretPosition(0);

        // Mise à jour des choix
        choixPanel.removeAll();

        if (chapitre.estFin()) {
            // Si c'est un chapitre de fin, on détermine si c'est une victoire ou une
            // défaite
            boolean isVictory = !chapitre.getTitre().contains("Perdu") &&
                    !chapitre.getTexte().contains("perdu") &&
                    !chapitre.getTitre().toLowerCase().contains("défaite") &&
                    !chapitre.getTitre().toLowerCase().contains("mort") &&
                    !chapitre.getTexte().toLowerCase().contains("mort");

            // Vérifier si ce chapitre permet d'accéder au chapitre suivant
            boolean hasNextChapter = chapitre.getId() == 6; // Si c'est la fin heureuse du chapitre 1
            int nextChapterId = hasNextChapter ? 2 : -1; // Chapitre 2

            SwingUtilities.invokeLater(() -> {
                // Titre du dialogue selon le type de fin
                String title = isVictory ? "Victoire" : "Défaite";

                // Message de fin
                String message = chapitre.getTexte() + "\n\n" +
                        (hasNextChapter && isVictory
                                ? "Voulez-vous continuer au chapitre suivant, recommencer, ou retourner au menu principal ?"
                                : "Voulez-vous recommencer pour explorer d'autres chemins, ou retourner au menu principal ?");

                // Afficher le dialogue de fin et attendre la réponse
                EndChapterDialog endDialog = new EndChapterDialog(this, title, message, isVictory, hasNextChapter,
                        nextChapterId);
                boolean replay = endDialog.showDialogAndWaitForChoice();
                boolean continueNext = endDialog.wantContinueToNextChapter();

                if (replay) {
                    // Si le joueur veut rejouer
                    gameController.demarrerPartie();
                    afficherChapitre(gameController.getChapitreActuel());
                } else if (continueNext && hasNextChapter) {
                    // Si le joueur veut continuer au chapitre suivant
                    Scenario nextChapterScenario = ScenarioLoader.creerScenarioChapitre2();
                    gameController.changerScenario(nextChapterScenario);
                    afficherChapitre(gameController.getChapitreActuel());
                } else {
                    // Si le joueur veut quitter
                    retourMenu();
                }
            });

            // On ajoute quand même un bouton pour recommencer au cas où
            JButton recommencerButton = createChoiceButton("Recommencer l'aventure");
            recommencerButton.addActionListener(e -> {
                gameController.demarrerPartie();
                afficherChapitre(gameController.getChapitreActuel());
            });
            choixPanel.add(recommencerButton);

        } else {
            // Sinon, on affiche les choix disponibles
            List<Choix> choixPossibles = chapitre.getChoixPossibles();
            JLabel choixLabel = new JLabel("Que décidez-vous de faire ?");
            choixLabel.setForeground(ACCENT_COLOR);
            choixLabel.setFont(new Font("Yu Mincho", Font.BOLD, 16));
            choixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            choixPanel.add(choixLabel);
            choixPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            for (int i = 0; i < choixPossibles.size(); i++) {
                final int choixIndex = i;
                Choix choix = choixPossibles.get(i);
                JButton choixButton = createChoiceButton(choix.getTexte());

                choixButton.addActionListener(e -> {
                    // Vérifier si ce choix déclenche un combat
                    if (chapitre.hasEnemy() && choix.declencheCombat()) {
                        // Lancer le combat
                        CombatUI combatUI = new CombatUI(this, gameController.getPersonnage(), chapitre.getEnemy());
                        boolean victoire = combatUI.commencerCombat();

                        if (victoire) {
                            // Si le joueur gagne, on continue au chapitre suivant normal
                            Chapitre chapitreChoisi = gameController.faireChoix(choixIndex);
                            if (chapitreChoisi != null) {
                                afficherChapitre(chapitreChoisi);
                            }
                        } else {
                            // Si le joueur perd, on va au chapitre de défaite
                            Chapitre chapitreDefaite = gameController.getChapitreDefaite(chapitre.getId());
                            if (chapitreDefaite != null) {
                                afficherChapitre(chapitreDefaite);
                            }
                        }
                    } else {
                        // Choix normal sans combat
                        Chapitre chapitreChoisi = gameController.faireChoix(choixIndex);
                        if (chapitreChoisi != null) {
                            afficherChapitre(chapitreChoisi);
                        }
                    }
                });

                choixPanel.add(choixButton);
                choixPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        // Mise à jour des statistiques
        updateStatsPanel();

        choixPanel.revalidate();
        choixPanel.repaint();
    }

    /**
     * Ouvre une boîte de dialogue pour sauvegarder la partie
     */
    private void sauvegarderPartie() {
        JFileChooser fileChooser = new JFileChooser();
        int resultat = fileChooser.showSaveDialog(this);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            String cheminFichier = fileChooser.getSelectedFile().getAbsolutePath();
            boolean succes = gameController.sauvegarderPartie(cheminFichier);

            if (succes) {
                JOptionPane.showMessageDialog(this, "Partie sauvegardée avec succès.",
                        "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde.",
                        "Sauvegarde", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Ouvre une boîte de dialogue pour charger une partie
     */
    private void chargerPartie() {
        JFileChooser fileChooser = new JFileChooser();
        int resultat = fileChooser.showOpenDialog(this);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            String cheminFichier = fileChooser.getSelectedFile().getAbsolutePath();
            boolean succes = gameController.chargerPartie(cheminFichier);

            if (succes) {
                afficherChapitre(gameController.getChapitreActuel());
                JOptionPane.showMessageDialog(this, "Partie chargée avec succès.",
                        "Chargement", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement.",
                        "Chargement", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Retourne au menu principal
     */
    private void retourMenu() {
        if (JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment retourner au menu principal ?\nToute progression non sauvegardée sera perdue.",
                "Retour au menu",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            this.dispose();
            MenuUI menuUI = new MenuUI();
            menuUI.setVisible(true);
        }
    }
    
    /**
     * Ouvre une fenêtre d'inventaire pendant le combat
     * @param enemy L'ennemi actuellement affronté
     * @return true si un objet a été utilisé
     */
    private boolean ouvrirInventaireCombat(Enemy enemy) {
        Personnage personnage = gameController.getPersonnage();
        List<String> inventaire = personnage.getInventaire();
        
        if (inventaire.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Votre inventaire est vide !",
                    "Inventaire", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        // Créer la fenêtre de combat
        JDialog inventaireDialog = new JDialog(this, "Inventaire de Combat", true);
        inventaireDialog.setSize(500, 400);
        inventaireDialog.setLocationRelativeTo(this);
        
        // Créer un panneau avec l'apparence du jeu
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180)); // Overlay semi-transparent
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Titre
        JLabel titleLabel = new JLabel("Utiliser un objet pendant le combat", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Description
        JTextArea descArea = new JTextArea(
                "Sélectionnez un objet à utiliser pour obtenir un avantage au combat.\n" +
                "La nourriture de voyage peut restaurer votre endurance et augmenter temporairement votre force.");
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setOpaque(false);
        descArea.setForeground(TEXT_COLOR);
        descArea.setFont(TEXT_FONT);
        mainPanel.add(descArea, BorderLayout.CENTER);
        
        // Liste des objets avec leurs effets
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setOpaque(false);
        
        // Variable pour capturer si un objet a été utilisé
        final boolean[] itemUsed = {false};
        
        for (String item : inventaire) {
            JButton itemButton = createItemButton(item, enemy);
            itemButton.addActionListener(e -> {
                boolean used = utiliserObjet(item, personnage, enemy);
                if (used) {
                    itemUsed[0] = true;
                    updateStatsPanel(); // Mettre à jour les statistiques
                    inventaireDialog.dispose();
                }
            });
            itemsPanel.add(itemButton);
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        // Panneau pour les objets et les boutons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Bouton de fermeture
        JButton fermerButton = createStyledButton("Retour au combat");
        fermerButton.addActionListener(e -> inventaireDialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(fermerButton);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        inventaireDialog.setContentPane(mainPanel);
        inventaireDialog.setVisible(true);
        
        return itemUsed[0];
    }
    
    /**
     * Crée un bouton d'objet d'inventaire avec description pour le combat
     */
    private JButton createItemButton(String item, Enemy enemy) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        String icon = Personnage.getItemIcon(item);
        String effet = getItemEffectDescription(item);
        
        JLabel iconLabel = new JLabel(icon + " " + item);
        iconLabel.setForeground(TEXT_COLOR);
        iconLabel.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        
        JLabel effectLabel = new JLabel(effet);
        effectLabel.setForeground(ACCENT_COLOR);
        effectLabel.setFont(new Font("Yu Mincho", Font.ITALIC, 14));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(iconLabel);
        panel.add(effectLabel);
        panel.setBackground(new Color(40, 30, 20));
        panel.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        
        button.add(panel, BorderLayout.CENTER);
        button.setPreferredSize(new Dimension(400, 60));
        button.setMaximumSize(new Dimension(400, 60));
        
        return button;
    }
    
    /**
     * Renvoie la description de l'effet d'un objet au combat
     */
    private String getItemEffectDescription(String item) {
        if (item.equalsIgnoreCase("Nourriture de voyage") || item.equalsIgnoreCase("Provisions")) {
            return "Restaure 2 points d'ENDURANCE et donne +1 en HABILETÉ pour ce combat";
        } else if (item.equalsIgnoreCase("Potion de guérison") || item.equalsIgnoreCase("Herbes médicinales")) {
            return "Restaure 4 points d'ENDURANCE";
        } else if (item.equalsIgnoreCase("Saké") || item.equalsIgnoreCase("Élixir")) {
            return "+2 en HABILETÉ pour ce combat";
        } else if (item.contains("Katana") || item.contains("Épée") || item.contains("Sabre")) {
            return "+1 en HABILETÉ pour ce combat";
        } else if (item.contains("Armure")) {
            return "Réduit les dégâts reçus de 1 point";
        } else if (item.contains("Amulette") || item.contains("Talisman")) {
            return "+2 en CHANCE pour ce combat";
        }
        return "Utilisable au combat";
    }
    
    /**
     * Utilise un objet pendant le combat et applique ses effets
     * @return true si l'objet a été utilisé
     */
    private boolean utiliserObjet(String item, Personnage personnage, Enemy enemy) {
        boolean consommable = true;
        String message = "";
        
        if (item.equalsIgnoreCase("Nourriture de voyage") || item.equalsIgnoreCase("Provisions")) {
            // Restaure de l'endurance et donne un bonus d'habileté temporaire
            personnage.modifierStatistique("ENDURANCE", 2);
            personnage.modifierStatistique("HABILETÉ", 1);
            message = "Vous consommez votre nourriture de voyage. Vous regagnez 2 points d'ENDURANCE et " +
                     "obtenez +1 en HABILETÉ pour ce combat!";
        } else if (item.equalsIgnoreCase("Potion de guérison") || item.equalsIgnoreCase("Herbes médicinales")) {
            // Restaure plus d'endurance
            personnage.modifierStatistique("ENDURANCE", 4);
            message = "Vous utilisez votre potion de guérison. Vous regagnez 4 points d'ENDURANCE!";
        } else if (item.equalsIgnoreCase("Saké") || item.equalsIgnoreCase("Élixir")) {
            // Donne un bonus d'habileté temporaire plus important
            personnage.modifierStatistique("HABILETÉ", 2);
            message = "Vous buvez votre " + item + ". Vous obtenez +2 en HABILETÉ pour ce combat!";
        } else if (item.contains("Katana") || item.contains("Épée") || item.contains("Sabre")) {
            // Les armes ne sont pas consommées
            personnage.modifierStatistique("HABILETÉ", 1);
            message = "Vous utilisez votre " + item + " avec une technique spéciale. +1 en HABILETÉ pour ce combat!";
            consommable = false;
        } else if (item.contains("Armure")) {
            // Les armures ne sont pas consommées
            personnage.modifierStatistique("ENDURANCE", 1);
            message = "Vous ajustez votre " + item + " pour une meilleure protection. Réduit les dégâts reçus de 1 point!";
            consommable = false;
        } else if (item.contains("Amulette") || item.contains("Talisman")) {
            // Les amulettes ne sont pas consommées
            personnage.modifierStatistique("CHANCE", 2);
            message = "Vous invoquez le pouvoir de votre " + item + ". +2 en CHANCE pour ce combat!";
            consommable = false;
        } else {
            // Objet inconnu
            JOptionPane.showMessageDialog(this, 
                    "Vous ne pouvez pas utiliser cet objet au combat.",
                    "Objet inutilisable", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        // Afficher le message d'utilisation
        JOptionPane.showMessageDialog(this, message, "Utilisation d'objet", JOptionPane.INFORMATION_MESSAGE);
        
        // Supprimer l'objet s'il est consommable
        if (consommable) {
            personnage.retirerInventaire(item);
        }
        
        return true;
    }
}

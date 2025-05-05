package view;

import controller.GameController;
import model.Chapitre;
import model.Choix;
import model.Personnage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    private JPanel mainPanel;
    private JTextArea texteChapitreArea;
    private JPanel choixPanel;
    private JPanel statsPanel;
    private Image backgroundImage;

    /**
     * Constructeur de l'interface graphique samouraï
     * 
     * @param gameController Le contrôleur de jeu
     */
    public SamuraiSwingUI(GameController gameController) {
        this.gameController = gameController;

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
            // Essayer de charger l'image
            backgroundImage = new ImageIcon(getClass().getResource("/resources/gameplay_background.jpg")).getImage();
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

        JButton sauvegarderButton = createStyledButton("Sauvegarder");
        JButton chargerButton = createStyledButton("Charger");
        JButton menuButton = createStyledButton("Menu");
        JButton quitterButton = createStyledButton("Quitter");

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

        menuPanel.add(sauvegarderButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(chargerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(menuButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(quitterButton);

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
                JLabel itemLabel = new JLabel("- " + item);
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
            // Si c'est un chapitre de fin, on affiche un bouton pour recommencer
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
                JButton choixButton = createChoiceButton(choixPossibles.get(i).getTexte());
                choixButton.addActionListener(e -> {
                    Chapitre chapitreChoisi = gameController.faireChoix(choixIndex);
                    if (chapitreChoisi != null) {
                        afficherChapitre(chapitreChoisi);
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
     * Crée un bouton de choix avec le style approprié
     */
    private JButton createChoiceButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(500, 40));

        return button;
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
}

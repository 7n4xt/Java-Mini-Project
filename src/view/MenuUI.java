package view;

import controller.GameController;
import controller.ChapterProgressManager;
import controller.ScenarioLoader;
import model.Scenario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface graphique de menu du jeu avec une esthétique samouraï.
 */
public class MenuUI extends JFrame {
    private static final String TITLE = "L'Épée du Samouraï";
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color BUTTON_COLOR = new Color(120, 60, 30);
    private static final Color BUTTON_TEXT_COLOR = new Color(255, 241, 224);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 18);
    private static final String BACKGROUND_IMAGE = "/resources/wp6177681-samurai-4k-wallpapers.jpg";

    private JPanel mainMenuPanel;
    private JPanel levelSelectPanel;
    private JPanel nameEntryPanel;
    private JPanel characterSelectPanel; // New panel for character selection

    private Image backgroundImage;
    private int selectedChapter = 1; // Variable pour suivre le chapitre sélectionné
    private CharacterAvatar selectedCharacter; // Store selected character
    private String selectedCharacterType = "Samouraï Shogun"; // Default character
    private ChapterProgressManager progressManager;

    /**
     * Constructeur de l'interface de menu
     */
    public MenuUI() {
        // Configuration de la fenêtre principale
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Mettre en plein écran
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Essayer de charger l'image de fond
        try {
            // Fallback à une image par défaut si nous ne pouvons pas charger l'image
            backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();
        } catch (Exception e) {
            System.out.println("Impossible de charger l'image de fond. Utilisation de la couleur par défaut.");
            backgroundImage = null;
        }

        progressManager = new ChapterProgressManager();

        // Initialisation des panneaux
        initComponents();

        // Afficher le menu principal au démarrage
        showMainMenu();
    }

    /**
     * Initialise les composants de l'interface
     */
    private void initComponents() {
        // Panneau du menu principal
        mainMenuPanel = createBackgroundPanel();
        mainMenuPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JButton playButton = createStyledButton("Jouer");
        JButton importButton = createStyledButton("Importer Sauvegarde");
        JButton helpButton = createStyledButton("Aide");
        JButton quitButton = createStyledButton("Quitter");
        JButton settingsButton = createStyledButton("Paramètres");

        // Actions des boutons
        playButton.addActionListener(e -> showLevelSelect());
        importButton.addActionListener(e -> importSave());
        helpButton.addActionListener(e -> showHelp());
        quitButton.addActionListener(e -> System.exit(0));
        settingsButton.addActionListener(e -> showSettings());

        // Ajout des boutons avec espacement
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        addButtonWithSpacing(buttonPanel, playButton);
        addButtonWithSpacing(buttonPanel, importButton);
        addButtonWithSpacing(buttonPanel, helpButton);
        addButtonWithSpacing(buttonPanel, quitButton);

        // Centrer le panneau de boutons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        // Panneau de paramètres
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsPanel.setOpaque(false);
        settingsPanel.add(settingsButton);

        mainMenuPanel.add(titlePanel, BorderLayout.NORTH);
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
        mainMenuPanel.add(settingsPanel, BorderLayout.SOUTH);

        // Panneau de sélection de niveau
        levelSelectPanel = createBackgroundPanel();
        levelSelectPanel.setLayout(new BorderLayout());

        JPanel levelTitlePanel = new JPanel();
        levelTitlePanel.setOpaque(false);
        JLabel levelTitleLabel = new JLabel("Sélection du Chapitre");
        levelTitleLabel.setFont(TITLE_FONT);
        levelTitleLabel.setForeground(TEXT_COLOR);
        levelTitlePanel.add(levelTitleLabel);

        JPanel levelButtonPanel = new JPanel();
        levelButtonPanel.setLayout(new BoxLayout(levelButtonPanel, BoxLayout.Y_AXIS));
        levelButtonPanel.setOpaque(false);

        JButton level1Button = createStyledButton("Chapitre 1");
        JButton level2Button = createStyledButton("Chapitre 2");
        JButton level3Button = createStyledButton("chapitre 3");
        JButton level4Button = createStyledButton("chapitre 4");
        JButton level5Button = createStyledButton("chapitre 5");
        JButton backFromLevelButton = createStyledButton("Retour");

        level1Button.addActionListener(e -> {
            selectedChapter = 1;
            showNameEntry();
        });

        level2Button.setEnabled(progressManager.isChapterCompleted(1));
        level2Button.addActionListener(e -> {
            if (progressManager.isChapterCompleted(1)) {
                selectedChapter = 2;
                showNameEntry();
                System.out.println("Chapitre 2 sélectionné");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Vous devez compléter le Chapitre 1 avant d'accéder au Chapitre 2.",
                        "Chapitre verrouillé",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        level3Button.setEnabled(false);
        level3Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Le Chapitre 3 est en cours de programmation. Merci de votre patience !",
                    "Chapitre en développement", JOptionPane.INFORMATION_MESSAGE);
        });

        level4Button.setEnabled(false);
        level4Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Le Chapitre 4 est en cours de programmation. Merci de votre patience !",
                    "Chapitre en développement", JOptionPane.INFORMATION_MESSAGE);
        });

        level5Button.setEnabled(false);
        level5Button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Le Chapitre 5 est en cours de programmation. Merci de votre patience !",
                    "Chapitre en développement", JOptionPane.INFORMATION_MESSAGE);
        });

        backFromLevelButton.addActionListener(e -> showMainMenu());

        addButtonWithSpacing(levelButtonPanel, level1Button);
        addButtonWithSpacing(levelButtonPanel, level2Button);
        addButtonWithSpacing(levelButtonPanel, level3Button);
        addButtonWithSpacing(levelButtonPanel, level4Button);
        addButtonWithSpacing(levelButtonPanel, level5Button);
        addButtonWithSpacing(levelButtonPanel, backFromLevelButton);

        // Centrer les boutons
        JPanel centerLevelPanel = new JPanel(new GridBagLayout());
        centerLevelPanel.setOpaque(false);
        centerLevelPanel.add(levelButtonPanel);

        levelSelectPanel.add(levelTitlePanel, BorderLayout.NORTH);
        levelSelectPanel.add(centerLevelPanel, BorderLayout.CENTER);

        // Panneau d'entrée de nom
        nameEntryPanel = createBackgroundPanel();
        nameEntryPanel.setLayout(new BorderLayout());

        JPanel nameTitlePanel = new JPanel();
        nameTitlePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Entrez votre nom");
        nameLabel.setFont(TITLE_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        nameTitlePanel.add(nameLabel);

        JPanel nameInputPanel = new JPanel();
        nameInputPanel.setLayout(new BoxLayout(nameInputPanel, BoxLayout.Y_AXIS));
        nameInputPanel.setOpaque(false);

        JTextField nameField = new JTextField(20);
        nameField.setFont(BUTTON_FONT);
        nameField.setText("Musashi");

        JButton enterButton = createStyledButton("Commencer l'aventure");
        JButton backFromNameButton = createStyledButton("Retour");

        enterButton.addActionListener(e -> showCharacterSelect());
        backFromNameButton.addActionListener(e -> showLevelSelect());

        // Centrer et aligner le champ texte
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textFieldPanel.setOpaque(false);
        textFieldPanel.add(nameField);

        nameInputPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        nameInputPanel.add(textFieldPanel);
        nameInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel nameButtonPanel = new JPanel();
        nameButtonPanel.setOpaque(false);
        nameButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        nameButtonPanel.add(enterButton);
        nameButtonPanel.add(backFromNameButton);

        nameInputPanel.add(nameButtonPanel);

        // Centrer le panneau d'entrée de nom
        JPanel centerNamePanel = new JPanel(new GridBagLayout());
        centerNamePanel.setOpaque(false);

        centerNamePanel.add(nameInputPanel);

        nameEntryPanel.add(nameTitlePanel, BorderLayout.NORTH);
        nameEntryPanel.add(centerNamePanel, BorderLayout.CENTER);

        // Nouveau panneau de sélection de personnage
        initCharacterSelectPanel();
    }

    /**
     * Initialise le panneau de sélection de personnage
     */
    private void initCharacterSelectPanel() {
        characterSelectPanel = createBackgroundPanel();
        characterSelectPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Choisissez Votre Samouraï");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        titlePanel.add(titleLabel);

        // Panneau central avec la grille de personnages
        JPanel charactersGrid = new JPanel(new GridLayout(1, 4, 20, 0));
        charactersGrid.setOpaque(false);

        // Créer les différents types de samouraïs
        List<CharacterAvatar> characters = new ArrayList<>();
        characters.add(new CharacterAvatar("Samouraï Shogun", new Color(120, 0, 0), new Color(200, 150, 0)));
        characters.add(new CharacterAvatar("Samouraï Daimyo", new Color(0, 70, 100), new Color(140, 170, 180)));
        characters.add(new CharacterAvatar("Samouraï Hatamoto", new Color(80, 50, 30), new Color(150, 100, 30)));
        characters.add(new CharacterAvatar("Samouraï Kensai", new Color(50, 0, 80), new Color(100, 40, 130)));

        // Par défaut, sélectionner le premier
        selectedCharacter = characters.get(0);

        // Ajouter chaque personnage au panneau
        for (int i = 0; i < characters.size(); i++) {
            final CharacterAvatar character = characters.get(i);
            final String characterType = character.getSamuraiType();
            final int index = i;

            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
            characterPanel.setOpaque(false);
            characterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Prévisualisation du personnage
            JPanel avatarPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setOpaque(false);

                    // Cadre de sélection
                    if (characterType.equals(selectedCharacterType)) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(new Color(255, 200, 100, 100));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                        g2d.setColor(new Color(201, 121, 66));
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);
                    }

                    // Dessiner l'avatar
                    character.drawFullBody(g, getWidth() / 2, getHeight() / 2);
                }
            };
            avatarPanel.setPreferredSize(new Dimension(150, 250));
            avatarPanel.setMinimumSize(new Dimension(150, 250));
            avatarPanel.setMaximumSize(new Dimension(150, 250));

            // Rendre le panel cliquable
            avatarPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            avatarPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedCharacterType = characterType;
                    selectedCharacter = character;
                    charactersGrid.repaint();
                }
            });

            // Nom du type
            JLabel typeLabel = new JLabel(characterType, JLabel.CENTER);
            typeLabel.setFont(BUTTON_FONT);
            typeLabel.setForeground(TEXT_COLOR);
            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Description
            JTextArea descArea = new JTextArea(getCharacterDescription(characterType));
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setFont(new Font("Yu Mincho", Font.PLAIN, 12));
            descArea.setForeground(TEXT_COLOR);
            descArea.setBackground(new Color(0, 0, 0, 0));
            descArea.setOpaque(false);
            descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            descArea.setPreferredSize(new Dimension(150, 80));
            descArea.setMaximumSize(new Dimension(150, 80));

            characterPanel.add(avatarPanel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            characterPanel.add(typeLabel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            characterPanel.add(descArea);

            charactersGrid.add(characterPanel);
        }

        // Centre panel pour contenir la grille et la centrer
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(charactersGrid);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton startButton = createStyledButton("Commencer l'Aventure");
        JButton backButton = createStyledButton("Retour");

        startButton.addActionListener(e -> {
            String playerName = "Musashi"; // Default name
            startGameWithSelectedCharacter(playerName, selectedCharacterType);
        });

        backButton.addActionListener(e -> showNameEntry());

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);

        characterSelectPanel.add(titlePanel, BorderLayout.NORTH);
        characterSelectPanel.add(centerPanel, BorderLayout.CENTER);
        characterSelectPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Retourne la description correspondant à un type de samouraï
     */
    private String getCharacterDescription(String characterType) {
        switch (characterType) {
            case "Samouraï Shogun":
                return "Général élégant et puissant. Maître stratège portant une armure d'exception avec de grands pouvoirs de commandement.";
            case "Samouraï Daimyo":
                return "Seigneur féodal raffiné. Sa prestance impressionne et son autorité naturelle lui confère des avantages tactiques.";
            case "Samouraï Hatamoto":
                return "Garde d'élite du shogunat. Guerrier agile et dévoué dont la loyauté est aussi affûtée que sa lame.";
            case "Samouraï Kensai":
                return "Maître d'épée légendaire. Son art martial sublime transcende la simple technique pour atteindre la perfection du geste.";
            default:
                return "Noble guerrier en quête d'honneur et de gloire.";
        }
    }

    /**
     * Crée un panneau avec image de fond
     */
    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Dessine l'image en l'étirant à la taille du panneau
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                    // Ajoute un overlay semi-transparent pour rendre le texte plus lisible
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        return panel;
    }

    /**
     * Crée un bouton stylisé pour l'interface
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Dimensions des boutons
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));

        return button;
    }

    /**
     * Ajoute un bouton avec espacement au panneau
     */
    private void addButtonWithSpacing(JPanel panel, JButton button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    /**
     * Affiche le menu principal
     */
    public void showMainMenu() {
        getContentPane().removeAll();
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }

    /**
     * Affiche le panneau de sélection de niveau
     */
    private void showLevelSelect() {
        getContentPane().removeAll();
        getContentPane().add(levelSelectPanel);
        revalidate();
        repaint();
    }

    /**
     * Affiche le panneau d'entrée de nom
     */
    private void showNameEntry() {
        getContentPane().removeAll();
        getContentPane().add(nameEntryPanel);
        revalidate();
        repaint();
    }

    /**
     * Affiche le panneau de sélection de personnage
     */
    private void showCharacterSelect() {
        getContentPane().removeAll();
        getContentPane().add(characterSelectPanel);
        revalidate();
        repaint();
    }

    /**
     * Démarre le jeu avec le nom fourni et le personnage sélectionné
     */
    private void startGameWithSelectedCharacter(String playerName, String characterType) {
        try {
            // Créer le contrôleur de jeu et lancer le jeu
            model.Scenario scenario;

            // Sélectionner le scénario en fonction du chapitre choisi
            if (selectedChapter == 2) {
                scenario = controller.ScenarioLoader.creerScenarioChapitre2();
            } else {
                scenario = controller.ScenarioLoader.creerScenarioDemonstration();
            }

            System.out.println("Démarrage du jeu avec le personnage: " + playerName + " (" + characterType + ")");

            // Fermer ce menu avant de créer la nouvelle fenêtre
            setVisible(false);

            // Créer le contrôleur avec les données du personnage choisi
            GameController gameController = new GameController(scenario, playerName, characterType);

            // Complètement fermer cette fenêtre
            dispose();

            // Lancer le jeu avec l'interface améliorée
            SwingUtilities.invokeLater(() -> {
                SamuraiSwingUI gameUI = new SamuraiSwingUI(gameController);
                gameUI.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Une erreur est survenue lors du démarrage du jeu: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Importe une sauvegarde
     */
    private void importSave() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Créer le contrôleur et charger la sauvegarde
            controller.ScenarioLoader loader = new controller.ScenarioLoader();
            model.Scenario scenario = controller.ScenarioLoader.creerScenarioDemonstration();
            GameController gameController = new GameController(scenario, "Joueur");

            boolean success = gameController.chargerPartie(filePath);

            if (success) {
                // Fermer ce menu
                this.dispose();

                // Lancer le jeu avec la sauvegarde chargée
                SamuraiSwingUI gameUI = new SamuraiSwingUI(gameController);
                gameUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Impossible de charger la sauvegarde.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Affiche l'aide du jeu
     */
    private void showHelp() {
        JOptionPane.showMessageDialog(this,
                "L'Épée du Samouraï\n\n" +
                        "Ce jeu est un livre dont vous êtes le héros. Vous incarnez un samouraï en quête\n" +
                        "de l'épée légendaire de votre ancêtre.\n\n" +
                        "Naviguez à travers l'histoire en faisant des choix qui détermineront votre destin.\n\n" +
                        "Bon courage dans votre quête, honorable samouraï !",
                "Aide", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Affiche les paramètres du jeu
     */
    private void showSettings() {
        JOptionPane.showMessageDialog(this,
                "Les paramètres seront disponibles dans une version future.",
                "Paramètres", JOptionPane.INFORMATION_MESSAGE);
    }
}
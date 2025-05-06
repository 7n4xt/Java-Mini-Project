package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private Image backgroundImage;

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
        JButton level2Button = createStyledButton("Bientôt disponible");
        JButton level3Button = createStyledButton("Bientôt disponible");
        JButton level4Button = createStyledButton("Bientôt disponible");
        JButton backFromLevelButton = createStyledButton("Retour");

        level1Button.addActionListener(e -> showNameEntry());
        level2Button.setEnabled(false);
        level3Button.setEnabled(false);
        level4Button.setEnabled(false);
        backFromLevelButton.addActionListener(e -> showMainMenu());

        addButtonWithSpacing(levelButtonPanel, level1Button);
        addButtonWithSpacing(levelButtonPanel, level2Button);
        addButtonWithSpacing(levelButtonPanel, level3Button);
        addButtonWithSpacing(levelButtonPanel, level4Button);
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

        enterButton.addActionListener(e -> startGame(nameField.getText()));
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
     * Démarre le jeu avec le nom fourni
     */
    private void startGame(String playerName) {
        // Créer le contrôleur de jeu et lancer le jeu
        controller.ScenarioLoader loader = new controller.ScenarioLoader();
        model.Scenario scenario = controller.ScenarioLoader.creerScenarioDemonstration();
        GameController gameController = new GameController(scenario, playerName);

        // Fermer ce menu
        this.dispose();

        // Lancer le jeu avec l'interface améliorée
        SamuraiSwingUI gameUI = new SamuraiSwingUI(gameController);
        gameUI.setVisible(true);
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
        // Pour une version simple, nous affichons juste un message
        JOptionPane.showMessageDialog(this,
                "Les paramètres seront disponibles dans une version future.",
                "Paramètres", JOptionPane.INFORMATION_MESSAGE);
    }
}

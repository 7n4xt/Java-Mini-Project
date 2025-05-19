package view;

import model.Personnage;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre d'aide sur la gestion de la stamina et les stratégies de combat
 */
public class StaminaTipsWindow extends JDialog {

    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 16);

    /**
     * Crée une fenêtre avec des conseils sur la stamina
     * 
     * @param parent     Le composant parent
     * @param personnage Le personnage du joueur
     */
    public StaminaTipsWindow(JDialog parent, Personnage personnage) {
        super(parent, "Conseils de Combat", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        initComponents(personnage);
    }

    /**
     * Initialise les composants de la fenêtre
     */
    private void initComponents(Personnage personnage) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = new JLabel("Guide de gestion de l'endurance", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Contenu principal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Informations sur la stamina actuelle
        int currentStamina = personnage.getStatistique("endurance");
        JLabel staminaLabel = new JLabel("Votre endurance actuelle: " + currentStamina + "/15");
        staminaLabel.setFont(TEXT_FONT);
        staminaLabel.setForeground(TEXT_COLOR);
        staminaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(staminaLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Barre visuelle
        JProgressBar staminaBar = new JProgressBar(0, 15);
        staminaBar.setValue(currentStamina);
        staminaBar.setStringPainted(true);

        // Couleur selon le niveau de stamina
        if (currentStamina <= 3) {
            staminaBar.setForeground(new Color(200, 50, 50)); // Rouge si faible
        } else if (currentStamina <= 7) {
            staminaBar.setForeground(new Color(200, 150, 50)); // Orange si moyen
        } else {
            staminaBar.setForeground(new Color(50, 100, 150)); // Bleu si bon niveau
        }

        staminaBar.setPreferredSize(new Dimension(300, 25));
        staminaBar.setMaximumSize(new Dimension(300, 25));
        staminaBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(staminaBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Conseils selon le niveau de stamina
        JTextArea adviceArea = new JTextArea();
        adviceArea.setEditable(false);
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);
        adviceArea.setFont(TEXT_FONT);
        adviceArea.setForeground(TEXT_COLOR);
        adviceArea.setBackground(new Color(30, 30, 30));
        adviceArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        if (currentStamina <= 3) {
            adviceArea.setText("ATTENTION - ENDURANCE CRITIQUE!\n\n" +
                    "• Défendez-vous au lieu d'attaquer (1 point vs 3 points)\n" +
                    "• Utilisez des objets comme les Rations de voyage pour restaurer de l'endurance\n" +
                    "• Si vous tentez de défendre sans endurance 3 fois de suite, vous serez vaincu!\n" +
                    "• Envisagez de fuir si vous n'avez pas de moyen de récupérer");
        } else if (currentStamina <= 7) {
            adviceArea.setText("ENDURANCE MODÉRÉE - Soyez prudent!\n\n" +
                    "• Alternez entre attaque et défense pour économiser de l'endurance\n" +
                    "• La défense permet parfois de riposter sans coût supplémentaire\n" +
                    "• Gardez des objets de récupération pour les moments critiques\n" +
                    "• Contre les ninjas, vos attaques font plus de dégâts - profitez-en!");
        } else {
            adviceArea.setText("BONNE ENDURANCE - Vous pouvez attaquer!\n\n" +
                    "• Profitez de votre bonne endurance pour attaquer agressivement\n" +
                    "• Les attaques ont 25% de chance de faire des dégâts critiques\n" +
                    "• Le Katana inflige 4x les dégâts normaux - utilisez-le stratégiquement\n" +
                    "• Après une victoire, vous récupérez automatiquement 5 points d'endurance");
        }

        JScrollPane scrollPane = new JScrollPane(adviceArea);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tableau des coûts
        JPanel costPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        costPanel.setBackground(new Color(40, 40, 40));
        costPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                "Coûts en endurance",
                0, 0, TEXT_FONT, TEXT_COLOR));

        addCostRow(costPanel, "Action", "Coût", true);
        addCostRow(costPanel, "Attaquer", "3 points");
        addCostRow(costPanel, "Se défendre", "1 point");
        addCostRow(costPanel, "Fuir (échec)", "10 points");

        costPanel.setMaximumSize(new Dimension(300, 150));
        costPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(costPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Retour au combat");
        closeButton.setFont(TEXT_FONT);
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setBackground(ACCENT_COLOR);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /**
     * Ajoute une ligne au tableau des coûts
     */
    private void addCostRow(JPanel panel, String action, String cost) {
        addCostRow(panel, action, cost, false);
    }

    /**
     * Ajoute une ligne au tableau des coûts avec option titre
     */
    private void addCostRow(JPanel panel, String action, String cost, boolean isHeader) {
        JLabel actionLabel = new JLabel(action, JLabel.CENTER);
        JLabel costLabel = new JLabel(cost, JLabel.CENTER);

        if (isHeader) {
            actionLabel.setFont(new Font(TEXT_FONT.getName(), Font.BOLD, TEXT_FONT.getSize()));
            costLabel.setFont(new Font(TEXT_FONT.getName(), Font.BOLD, TEXT_FONT.getSize()));
        } else {
            actionLabel.setFont(TEXT_FONT);
            costLabel.setFont(TEXT_FONT);
        }

        actionLabel.setForeground(TEXT_COLOR);
        costLabel.setForeground(TEXT_COLOR);

        panel.add(actionLabel);
        panel.add(costLabel);
    }
}

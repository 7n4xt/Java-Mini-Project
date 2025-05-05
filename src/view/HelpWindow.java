package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HelpWindow extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 24);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 16);

    public HelpWindow(JFrame parent) {
        super(parent, "Guide du Samouraï", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = createTitleLabel("Guide du Samouraï");
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sections d'aide
        addSection(mainPanel, "🎮 Comment jouer", 
            "Lisez l'histoire et faites vos choix en cliquant sur les options proposées.\n" +
            "Chaque choix influencera votre aventure et pourra avoir des conséquences sur votre destin.");

        addSection(mainPanel, "📊 Statistiques", 
            "🔸 Honneur : Votre réputation et votre code moral\n" +
            "🔸 Force : Votre puissance physique\n" +
            "🔸 Agilité : Votre rapidité et dextérité\n" +
            "🔸 Sagesse : Votre sagesse et intuition\n" +
            "🔸 Vie : Votre santé actuelle");

        addSection(mainPanel, "🎒 Inventaire", 
            "Gérez votre équipement en cliquant sur le bouton Inventaire.\n" +
            "Vous pouvez ajouter ou retirer des objets qui vous aideront dans votre quête.");

        addSection(mainPanel, "💾 Sauvegarde", 
            "N'oubliez pas de sauvegarder régulièrement votre progression\n" +
            "en utilisant le bouton Sauvegarder dans le menu.");

        // Sections d'aide avec plus de détails
        addSection(mainPanel, "🎮 Commandes de base", 
            "• ESC : Ouvrir le menu pause/quitter\n" +
            "• 🎒 : Ouvrir l'inventaire\n" +
            "• 💾 : Sauvegarder la partie\n" +
            "• 📂 : Charger une partie\n" +
            "• ❔ : Afficher cette aide");

        addSection(mainPanel, "📊 Système de jeu", 
            "• Lisez attentivement l'histoire qui s'affiche\n" +
            "• Examinez vos statistiques dans le panneau de droite\n" +
            "• Réfléchissez bien avant de faire vos choix\n" +
            "• Certains choix peuvent être influencés par vos statistiques\n" +
            "• Votre inventaire peut débloquer des options spéciales");

        addSection(mainPanel, "🎒 Gestion de l'inventaire", 
            "• Cliquez sur un objet disponible pour l'ajouter\n" +
            "• Cliquez sur ❌ pour retirer un objet\n" +
            "• Certains objets sont essentiels pour l'histoire\n" +
            "• Limitez-vous aux objets vraiment utiles\n" +
            "• Les objets peuvent influencer vos statistiques");

        addSection(mainPanel, "💾 Sauvegarde et chargement", 
            "• Sauvegardez régulièrement votre progression\n" +
            "• Créez plusieurs sauvegardes à différents moments\n" +
            "• Donnez des noms explicites à vos sauvegardes\n" +
            "• Vous pouvez charger une partie à tout moment");

        addSection(mainPanel, "⚔️ Statistiques détaillées", 
            "• Honneur (1-10) : Influence les réactions des personnages\n" +
            "• Force (1-10) : Détermine vos capacités au combat\n" +
            "• Agilité (1-10) : Affecte votre capacité d'esquive\n" +
            "• Sagesse (1-10) : Permet de découvrir des choix cachés\n" +
            "• Vie (1-20) : Si elle tombe à 0, c'est la fin de la partie");

        addSection(mainPanel, "💡 Conseils", 
            "• Explorez différents chemins pour découvrir toute l'histoire\n" +
            "• Certains choix peuvent sembler anodins mais sont importants\n" +
            "• Prêtez attention aux descriptions et aux détails\n" +
            "• N'hésitez pas à recommencer avec des choix différents\n" +
            "• Le jeu comporte plusieurs fins possibles");

        // Bouton fermer
        JButton closeButton = new JButton("J'ai compris ✨");
        closeButton.setFont(TEXT_FONT);
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setBackground(new Color(60, 30, 15));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(closeButton);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scrollPane);
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void addSection(JPanel panel, String title, String content) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TEXT_FONT.deriveFont(Font.BOLD));
        titleLabel.setForeground(ACCENT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(TEXT_FONT);
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setBackground(BACKGROUND_COLOR);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(contentArea);
    }
}

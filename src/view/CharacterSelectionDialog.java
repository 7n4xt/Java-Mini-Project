package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Boîte de dialogue pour la sélection du personnage
 */
public class CharacterSelectionDialog extends JDialog {
    private JTextField nameField;
    private int selectedCharacterIndex = 0;
    private String playerName = "Musashi";
    private String characterClass = "Samouraï";
    private boolean selectionDone = false;
    private List<CharacterAvatar> characters = new ArrayList<>();

    /**
     * Crée une boîte de dialogue de sélection de personnage
     */
    public CharacterSelectionDialog(JFrame parent) {
        super(parent, "Choix du Samouraï", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Génère différents types de samouraïs élégants
        characters.add(new CharacterAvatar("Samouraï Shogun", new Color(120, 0, 0), new Color(200, 150, 0)));
        characters.add(new CharacterAvatar("Samouraï Daimyo", new Color(0, 70, 100), new Color(140, 170, 180)));
        characters.add(new CharacterAvatar("Samouraï Hatamoto", new Color(80, 50, 30), new Color(150, 100, 30)));
        characters.add(new CharacterAvatar("Samouraï Kensai", new Color(50, 0, 80), new Color(100, 40, 130)));
        
        initComponents();
    }
    
    /**
     * Initialise les composants de la boîte de dialogue
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessine un fond style papier japonais
                Color bgColor = new Color(240, 230, 210);
                g.setColor(bgColor);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Motif traditionnel japonais
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(200, 190, 170, 50));
                for (int i = 0; i < getWidth(); i += 40) {
                    for (int j = 0; j < getHeight(); j += 40) {
                        g2d.drawOval(i, j, 30, 30);
                        g2d.drawLine(i, j, i+30, j+30);
                    }
                }
            }
        };
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Titre
        JLabel titleLabel = new JLabel("Choisissez Votre Personnage", JLabel.CENTER);
        titleLabel.setFont(new Font("Yu Mincho", Font.BOLD, 28));
        titleLabel.setForeground(new Color(100, 0, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panneau central pour l'affichage des personnages
        JPanel charactersPanel = new JPanel(new GridLayout(1, characters.size(), 10, 0));
        charactersPanel.setOpaque(false);
        
        for (int i = 0; i < characters.size(); i++) {
            final int characterIndex = i;
            CharacterAvatar character = characters.get(i);
            
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
            characterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            characterPanel.setOpaque(false);
            
            // Prévisualisation du personnage
            JPanel avatarPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Fond transparent
                    setOpaque(false);
                    
                    // Dessiner un cadre décoré pour le personnage sélectionné
                    if (characterIndex == selectedCharacterIndex) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Effet de sélection
                        g2d.setColor(new Color(255, 200, 100, 100));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                        
                        g2d.setColor(new Color(150, 0, 0));
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawRoundRect(5, 5, getWidth()-10, getHeight()-10, 15, 15);
                    }
                    
                    // Dessiner le personnage
                    character.drawFullBody(g, getWidth()/2, getHeight()/2);
                }
            };
            avatarPanel.setPreferredSize(new Dimension(150, 250));
            
            // Rendre le panneau du personnage cliquable
            avatarPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedCharacterIndex = characterIndex;
                    characterClass = characters.get(characterIndex).getCharacterClass();
                    charactersPanel.repaint();
                }
            });
            
            // Nom de la classe
            JLabel classLabel = new JLabel(character.getCharacterClass(), JLabel.CENTER);
            classLabel.setFont(new Font("Yu Mincho", Font.BOLD, 18));
            classLabel.setForeground(new Color(80, 40, 0));
            classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Description
            JTextArea descArea = new JTextArea(getCharacterDescription(character.getCharacterClass()));
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setFont(new Font("Yu Mincho", Font.PLAIN, 12));
            descArea.setOpaque(false);
            descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            characterPanel.add(avatarPanel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            characterPanel.add(classLabel);
            characterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            characterPanel.add(descArea);
            
            charactersPanel.add(characterPanel);
        }
        
        mainPanel.add(charactersPanel, BorderLayout.CENTER);
        
        // Panneau inférieur pour le nom et les boutons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        
        // Champ de nom
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Nom de votre personnage:");
        nameLabel.setFont(new Font("Yu Mincho", Font.PLAIN, 16));
        nameField = new JTextField(playerName, 20);
        nameField.setFont(new Font("Yu Mincho", Font.PLAIN, 16));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        JButton confirmButton = new JButton("Commencer l'Aventure");
        confirmButton.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        confirmButton.setBackground(new Color(120, 60, 30));
        confirmButton.setForeground(new Color(255, 241, 224));
        confirmButton.setBorderPainted(false);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText().trim();
                if (playerName.isEmpty()) {
                    playerName = "Musashi";
                }
                selectionDone = true;
                dispose();
            }
        });
        buttonPanel.add(confirmButton);
        
        bottomPanel.add(namePanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(buttonPanel);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    /**
     * Retourne la description correspondant à une classe de personnage
     */
    private String getCharacterDescription(String samuraiType) {
        switch(samuraiType) {
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
     * Affiche la boîte de dialogue et attend la sélection du joueur
     * @return un tableau contenant le nom et la classe du personnage, ou null si annulé
     */
    public String[] showDialogAndWaitForChoice() {
        setVisible(true);
        // Le code s'arrête ici jusqu'à ce que la boîte de dialogue soit fermée
        
        if (selectionDone) {
            return new String[] { playerName, characterClass };
        } else {
            return null;
        }
    }
}

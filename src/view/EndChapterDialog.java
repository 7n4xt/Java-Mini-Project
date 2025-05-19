package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Boîte de dialogue affichée à la fin d'un chapitre
 */
public class EndChapterDialog extends JDialog {
    private boolean replayChoice = false;
    private boolean continueToNextChapter = false;
    
    /**
     * Crée une boîte de dialogue de fin de chapitre
     * 
     * @param parent     La fenêtre parente
     * @param title      Le titre de la boîte de dialogue
     * @param message    Le message à afficher
     * @param isVictory  Si c'est une victoire
     * @param hasNext    Si un chapitre suivant est disponible
     * @param nextId     L'id du chapitre suivant
     */
    public EndChapterDialog(JFrame parent, String title, String message, boolean isVictory, boolean hasNext, int nextId) {
        super(parent, title, true);
        
        // Configuration de la fenêtre
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        // Création d'un panneau avec image de fond
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Fond stylisé pour la boîte de dialogue
                Color backgroundColor = isVictory ? new Color(20, 40, 20) : new Color(50, 20, 20);
                g.setColor(backgroundColor);
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Ajouter des détails visuels
                g.setColor(new Color(200, 200, 200, 50));
                for (int i = 0; i < getWidth(); i += 30) {
                    g.drawLine(i, 0, i, getHeight());
                }
                for (int i = 0; i < getHeight(); i += 30) {
                    g.drawLine(0, i, getWidth(), i);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Titre de la boîte de dialogue
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Yu Mincho", Font.BOLD, 28));
        titleLabel.setForeground(isVictory ? new Color(150, 255, 150) : new Color(255, 100, 100));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Zone de texte pour le message
        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Yu Mincho", Font.PLAIN, 18));
        messageArea.setForeground(Color.WHITE);
        messageArea.setOpaque(false);
        messageArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        // Les boutons pour rejouer ou continuer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        if (hasNext && isVictory) {
            JButton continueButton = createStyledButton("Continuer au chapitre suivant");
            continueButton.addActionListener(e -> {
                System.out.println("DEBUG: Continue button clicked, setting continueToNextChapter = true");
                continueToNextChapter = true;
                replayChoice = false;
                dispose();
            });
            buttonPanel.add(continueButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        }
        
        JButton replayButton = createStyledButton("Recommencer");
        JButton menuButton = createStyledButton("Menu principal");
        
        replayButton.addActionListener(e -> {
            replayChoice = true;
            continueToNextChapter = false;
            dispose();
        });
        
        menuButton.addActionListener(e -> {
            replayChoice = false;
            continueToNextChapter = false;
            dispose();
        });
        
        buttonPanel.add(replayButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(menuButton);
        
        // Ajouter les composants au panneau principal
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Ajouter le panneau principal à la boîte de dialogue
        setContentPane(mainPanel);
    }
    
    /**
     * Constructeur simple pour la compatibilité avec le code existant
     */
    public EndChapterDialog(JFrame parent, String title, String message, boolean isVictory) {
        this(parent, title, message, isVictory, false, -1);
    }
    
    /**
     * Crée un bouton stylisé
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Yu Mincho", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 60, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(120, 90, 45));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 60, 30));
            }
        });
        
        return button;
    }
    
    /**
     * Affiche la boîte de dialogue et attend la décision de l'utilisateur
     * @return true si l'utilisateur a choisi de recommencer, false s'il veut quitter
     */
    public boolean showDialogAndWaitForChoice() {
        setVisible(true);
        return replayChoice;
    }
    
    /**
     * @return true si l'utilisateur veut continuer au chapitre suivant
     */
    public boolean wantContinueToNextChapter() {
        return continueToNextChapter;
    }
}

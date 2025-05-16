package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

/**
 * Dialogue affiché lorsque le joueur termine un chapitre (victoire ou défaite)
 */
public class EndChapterDialog extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Color BUTTON_COLOR = new Color(80, 60, 30);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 28);
    private static final Font TEXT_FONT = new Font("Yu Mincho", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 16);
    private static final String BACKGROUND_IMAGE = "/resources/wp6177681-samurai-4k-wallpapers.jpg";

    private boolean playAgain = false;
    private boolean continueToNextChapter = false;
    private final CountDownLatch latch = new CountDownLatch(1);
    private Image backgroundImage;
    private boolean isVictory;
    private int nextChapterId = -1;

    /**
     * Constructeur du dialogue de fin de chapitre avec option de chapitre suivant
     * 
     * @param parent         La fenêtre parente
     * @param title          Titre du dialogue (varie selon victoire/défaite)
     * @param message        Le message à afficher
     * @param isVictory      Indique si c'est une victoire ou une défaite
     * @param hasNextChapter Indique si un chapitre suivant est disponible
     * @param nextChapterId  L'ID du prochain chapitre (si disponible)
     */
    public EndChapterDialog(JFrame parent, String title, String message, boolean isVictory, boolean hasNextChapter,
            int nextChapterId) {
        super(parent, title, true);
        this.isVictory = isVictory;
        this.nextChapterId = nextChapterId;

        // Charger l'image de fond
        try {
            backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_IMAGE)).getImage();
        } catch (Exception e) {
            System.out.println("Impossible de charger l'image de fond. Utilisation de la couleur par défaut.");
            backgroundImage = null;
        }

        initComponents(message, hasNextChapter);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Si l'utilisateur ferme la fenêtre, c'est comme s'il ne voulait pas rejouer
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                playAgain = false;
                continueToNextChapter = false;
                latch.countDown();
                dispose();
            }
        });
    }

    /**
     * Constructeur simple pour la compatibilité avec le code existant
     */
    public EndChapterDialog(JFrame parent, String title, String message, boolean isVictory) {
        this(parent, title, message, isVictory, false, -1);
    }

    /**
     * Initialise les composants du dialogue
     */
    private void initComponents(String message, boolean hasNextChapter) {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
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
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Titre selon le type de fin
        String titleText = isVictory ? "Victoire!" : "Défaite";
        Color titleColor = isVictory ? ACCENT_COLOR : new Color(180, 70, 70);

        JLabel titleLabel = new JLabel(titleText, JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(titleColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Message de fin
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(TEXT_FONT);
        messageArea.setForeground(TEXT_COLOR);
        messageArea.setBackground(new Color(0, 0, 0, 0));
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panneau de boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, hasNextChapter ? 3 : 2, 20, 0));
        buttonPanel.setOpaque(false);

        // Bouton pour rejouer
        JButton playAgainButton = createStyledButton("Rejouer");
        playAgainButton.addActionListener(e -> {
            playAgain = true;
            continueToNextChapter = false;
            latch.countDown();
            dispose();
        });

        // Bouton pour quitter
        JButton quitButton = createStyledButton("Menu principal");
        quitButton.addActionListener(e -> {
            playAgain = false;
            continueToNextChapter = false;
            latch.countDown();
            dispose();
        });

        buttonPanel.add(playAgainButton);

        // Bouton pour continuer au chapitre suivant (si disponible)
        if (hasNextChapter && isVictory) {
            JButton nextChapterButton = createStyledButton("Chapitre Suivant");
            nextChapterButton.addActionListener(e -> {
                playAgain = false;
                continueToNextChapter = true;
                latch.countDown();
                dispose();
            });
            buttonPanel.add(nextChapterButton);
        }

        buttonPanel.add(quitButton);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setOpaque(false);
        southPanel.add(buttonPanel);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
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
        button.setPreferredSize(new Dimension(150, 40));

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

    /**
     * Affiche le dialogue et attend la réponse de l'utilisateur
     * 
     * @return true si le joueur veut rejouer, false sinon
     */
    public boolean showDialogAndWaitForChoice() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return playAgain;
    }

    /**
     * Vérifie si le joueur veut continuer au chapitre suivant
     * 
     * @return true si le joueur veut continuer au chapitre suivant
     */
    public boolean wantContinueToNextChapter() {
        return continueToNextChapter;
    }

    /**
     * Récupère l'ID du prochain chapitre
     * 
     * @return l'ID du prochain chapitre
     */
    public int getNextChapterId() {
        return nextChapterId;
    }
}

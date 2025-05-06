package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

/**
 * Dialogue de sélection d'interface utilisant le même thème que le jeu
 */
public class InterfaceSelectionDialog extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Color BUTTON_COLOR = new Color(80, 60, 30);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Yu Mincho", Font.BOLD, 16);

    private boolean graphicalUISelected = true;
    private final CountDownLatch latch = new CountDownLatch(1);

    /**
     * Constructeur du dialogue
     */
    public InterfaceSelectionDialog() {
        super((JFrame) null, "L'Épée du Samouraï", true);
        initComponents();
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Si l'utilisateur ferme la fenêtre, on considère qu'il choisit l'interface
        // graphique
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                graphicalUISelected = true;
                latch.countDown();
                dispose();
            }
        });
    }

    /**
     * Initialise les composants du dialogue
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Titre
        JLabel titleLabel = new JLabel("Choisissez votre interface", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panneau de boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 20, 0));
        buttonsPanel.setOpaque(false);

        // Bouton pour l'interface graphique
        JButton graphicalButton = createSelectionButton("Graphical");
        graphicalButton.addActionListener(e -> {
            graphicalUISelected = true;
            latch.countDown();
            dispose();
        });

        // Bouton pour l'interface texte
        JButton textButton = createSelectionButton("Text");
        textButton.addActionListener(e -> {
            graphicalUISelected = false;
            latch.countDown();
            dispose();
        });

        buttonsPanel.add(graphicalButton);
        buttonsPanel.add(textButton);

        // Ajoute un espacement autour des boutons
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        centerPanel.add(buttonsPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    /**
     * Crée un bouton de sélection stylisé
     */
    private JButton createSelectionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
     * Méthode bloquante qui attend que l'utilisateur fasse un choix
     * 
     * @return true si l'interface graphique est sélectionnée, false sinon
     */
    public boolean showDialogAndWaitForChoice() {
        setVisible(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return graphicalUISelected;
    }
}

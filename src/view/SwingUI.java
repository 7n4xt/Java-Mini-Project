package src.view;

import src.controller.GameController;
import src.model.Chapitre;
import src.model.Choix;
import src.model.Personnage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * Interface graphique principale du jeu utilisant Swing.
 */
public class SwingUI extends JFrame {
    private GameController gameController;

    private JPanel mainPanel;
    private JTextArea texteChapitreArea;
    private JPanel choixPanel;
    private JPanel statsPanel;

    /**
     * Constructeur de l'interface graphique
     * 
     * @param gameController Le contrôleur de jeu
     */
    public SwingUI(GameController gameController) {
        this.gameController = gameController;

        // Configuration de la fenêtre principale
        setTitle("L'Épée du Samouraï - Livre dont vous êtes le héros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Création des composants
        initComponents();

        // Affichage du premier chapitre
        afficherChapitre(gameController.getChapitreActuel());
    }

    /**
     * Initialise les composants de l'interface
     */
    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel supérieur avec le titre et les informations du joueur
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titreLabel = new JLabel(gameController.getScenario().getTitre());
        titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerPanel.add(titreLabel, BorderLayout.CENTER);

        // Panel des statistiques du joueur
        statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateStatsPanel();
        headerPanel.add(statsPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Zone de texte pour afficher le contenu du chapitre
        texteChapitreArea = new JTextArea();
        texteChapitreArea.setEditable(false);
        texteChapitreArea.setLineWrap(true);
        texteChapitreArea.setWrapStyleWord(true);
        texteChapitreArea.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(texteChapitreArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel des choix
        choixPanel = new JPanel();
        choixPanel.setLayout(new BoxLayout(choixPanel, BoxLayout.Y_AXIS));
        mainPanel.add(choixPanel, BorderLayout.SOUTH);

        // Panel pour les boutons de menu
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton sauvegarderButton = new JButton("Sauvegarder");
        JButton chargerButton = new JButton("Charger");
        JButton quitterButton = new JButton("Quitter");

        sauvegarderButton.addActionListener(e -> sauvegarderPartie());
        chargerButton.addActionListener(e -> chargerPartie());
        quitterButton.addActionListener(e -> System.exit(0));

        menuPanel.add(sauvegarderButton);
        menuPanel.add(chargerButton);
        menuPanel.add(quitterButton);
        mainPanel.add(menuPanel, BorderLayout.WEST);

        // Ajout du panel principal à la fenêtre
        setContentPane(mainPanel);
    }

    /**
     * Met à jour l'affichage du panel des statistiques
     */
    private void updateStatsPanel() {
        statsPanel.removeAll();

        Personnage personnage = gameController.getPersonnage();
        JLabel nomLabel = new JLabel("Samouraï: " + personnage.getNom());
        nomLabel.setFont(new Font("Serif", Font.BOLD, 14));
        statsPanel.add(nomLabel);

        Map<String, Integer> stats = personnage.getStatistiques();
        for (Map.Entry<String, Integer> stat : stats.entrySet()) {
            JLabel statLabel = new JLabel(stat.getKey() + ": " + stat.getValue());
            statsPanel.add(statLabel);
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
            JButton recommencerButton = new JButton("Recommencer l'aventure");
            recommencerButton.addActionListener(e -> {
                gameController.demarrerPartie();
                afficherChapitre(gameController.getChapitreActuel());
            });
            choixPanel.add(recommencerButton);
        } else {
            // Sinon, on affiche les choix disponibles
            List<Choix> choixPossibles = chapitre.getChoixPossibles();
            for (int i = 0; i < choixPossibles.size(); i++) {
                final int choixIndex = i;
                JButton choixButton = new JButton(choixPossibles.get(i).getTexte());
                choixButton.addActionListener(e -> {
                    Chapitre chapitreChoisi = gameController.faireChoix(choixIndex);
                    if (chapitreChoisi != null) {
                        afficherChapitre(chapitreChoisi);
                    }
                });
                choixPanel.add(choixButton);
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
}
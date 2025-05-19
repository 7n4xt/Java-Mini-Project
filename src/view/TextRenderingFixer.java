package view;

import javax.swing.*;
import java.awt.*;

/**
 * Utilitaire pour résoudre les problèmes de rendu de texte dans l'interface
 */
public class TextRenderingFixer {

    /**
     * Applique des corrections pour améliorer le rendu du texte dans un JTextArea
     * 
     * @param textArea Le JTextArea à optimiser
     */
    public static void fixTextRendering(JTextArea textArea) {
        // Améliorer l'opacité pour éviter les problèmes de transparence
        textArea.setOpaque(true);

        // Améliorer le contraste avec un fond plus sombre
        textArea.setBackground(new Color(10, 10, 10, 240));

        // Augmenter les marges pour éviter que le texte touche les bords
        textArea.setMargin(new Insets(15, 15, 15, 15));

        // Augmenter l'espacement des lignes pour une meilleure lisibilité
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Appliquer des techniques d'amélioration de rendu
        textArea.putClientProperty("JComponent.sizeVariant", "large");
        textArea.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);

        // Activer les optimisations d'antialiasing
        applyAntiAliasing(textArea);
    }

    /**
     * Configure globalement les paramètres de rendu de texte pour toute
     * l'application
     * Appelé une seule fois au démarrage de l'application
     */
    public static void applyGlobalTextSettings() {
        try {
            // Configurer l'antialiasing global pour tous les composants Swing
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

            // Configurer les paramètres de rendu globaux
            UIManager.put("TextArea.margin", new Insets(10, 10, 10, 10));
            UIManager.put("TextField.margin", new Insets(5, 5, 5, 5));
            UIManager.put("TextPane.margin", new Insets(10, 10, 10, 10));
            UIManager.put("EditorPane.margin", new Insets(10, 10, 10, 10));

            // Assurer que le fond soit opaque pour éviter les superpositions de texte
            UIManager.put("TextArea.background", new Color(10, 10, 10));
            UIManager.put("TextPane.background", new Color(10, 10, 10));

            // Configuration des polices pour une meilleure lisibilité
            Font defaultFont = new Font("Yu Mincho", Font.PLAIN, 16);
            UIManager.put("TextArea.font", defaultFont);
            UIManager.put("TextField.font", defaultFont);
            UIManager.put("TextPane.font", defaultFont);
            UIManager.put("EditorPane.font", defaultFont);

        } catch (Exception e) {
            System.err.println(
                    "Erreur lors de l'application des paramètres globaux de rendu de texte: " + e.getMessage());
        }
    }

    /**
     * Applique les optimisations d'antialiasing pour améliorer la lisibilité
     * 
     * @param component Le composant à optimiser
     */
    private static void applyAntiAliasing(JComponent component) {
        // Utiliser les propriétés graphiques pour améliorer l'antialiasing
        component.putClientProperty(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        component.putClientProperty(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        component.putClientProperty(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
    }

    /**
     * Configure une zone de texte pour un dialogue ou une fenêtre d'information
     * 
     * @param textPane        Le JTextPane à configurer
     * @param textColor       La couleur du texte
     * @param backgroundColor La couleur de fond
     */
    public static void configureTextPane(JTextPane textPane, Color textColor, Color backgroundColor) {
        textPane.setEditable(false);
        textPane.setContentType("text/html");

        // Définir le style avec un CSS qui améliore l'espacement et la lisibilité
        String style = "<style>"
                + "body { color: " + colorToHex(textColor) + "; "
                + "background-color: " + colorToHex(backgroundColor) + "; "
                + "font-family: 'Yu Mincho', serif; "
                + "font-size: 16pt; "
                + "line-height: 1.5; "
                + "margin: 15px; }"
                + "h1 { font-size: 24pt; margin-bottom: 15px; }"
                + "p { margin-bottom: 10px; }"
                + "</style>";

        // Appliquer le style au contenu
        String currentText = textPane.getText();
        if (!currentText.contains("<html>")) {
            textPane.setText("<html>" + style + "<body>" + currentText + "</body></html>");
        }
    }

    /**
     * Convertit une couleur Java en code hexadécimal CSS
     * 
     * @param color La couleur à convertir
     * @return La chaîne hexadécimale CSS
     */
    private static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                color.getRed(), color.getGreen(), color.getBlue());
    }
}

package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Classe pour générer des avatars de samouraïs élégants
 */
public class CharacterAvatar {
    private String samuraiType;
    private Color primaryColor;
    private Color secondaryColor;
    private Color accentColor;
    private Random random = new Random();
    
    /**
     * Crée un avatar de samouraï
     * @param samuraiType Le type de samouraï
     * @param primaryColor Couleur principale
     * @param secondaryColor Couleur secondaire
     */
    public CharacterAvatar(String samuraiType, Color primaryColor, Color secondaryColor) {
        this.samuraiType = samuraiType;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.accentColor = new Color(
            Math.min(255, primaryColor.getRed() + 40),
            Math.min(255, primaryColor.getGreen() + 40),
            Math.min(255, primaryColor.getBlue() + 40)
        );
    }
    
    /**
     * @return le type de samouraï
     */
    public String getSamuraiType() {
        return samuraiType;
    }
    
    /**
     * @return le type de samouraï (méthode alias pour compatibilité)
     */
    public String getCharacterClass() {
        return samuraiType;
    }
    
    /**
     * Dessine un avatar en pied
     * @param g Graphics pour le dessin
     * @param centerX Centre X de l'image
     * @param centerY Centre Y de l'image
     */
    public void drawFullBody(Graphics g, int centerX, int centerY) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch (samuraiType) {
            case "Samouraï Shogun":
                drawShogunSamurai(g2d, centerX, centerY);
                break;
            case "Samouraï Daimyo":
                drawDaimyoSamurai(g2d, centerX, centerY);
                break;
            case "Samouraï Hatamoto":
                drawHatamotoSamurai(g2d, centerX, centerY);
                break;
            case "Samouraï Kensai":
                drawKensaiSamurai(g2d, centerX, centerY);
                break;
            default:
                drawShogunSamurai(g2d, centerX, centerY);
        }
    }
    
    /**
     * Dessine un samouraï élégant de type Shogun
     */
    private void drawShogunSamurai(Graphics2D g, int centerX, int centerY) {
        // Base corps
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // Armure de corps ornée
        g.setColor(primaryColor);
        // Torse avec armure lourde
        g.fillRect(centerX - 30, shoulderY, 60, waistY - shoulderY);
        
        // Jambes avec protection
        g.fillRect(centerX - 28, waistY, 24, feetY - waistY);
        g.fillRect(centerX + 4, waistY, 24, feetY - waistY);
        
        // Bras avec armure lourde
        g.fillRect(centerX - 45, shoulderY, 15, 60);
        g.fillRect(centerX + 30, shoulderY, 15, 60);
        
        // Détails d'armure élaborés
        g.setColor(secondaryColor);
        for (int i = 0; i < 4; i++) {
            int y = shoulderY + i * 20;
            g.fillRect(centerX - 30, y, 60, 3);
        }
        
        // Épaulières ornées
        drawOrnateShoulderPlate(g, centerX - 45, shoulderY - 10);
        drawOrnateShoulderPlate(g, centerX + 30, shoulderY - 10);
        
        // Jupe d'armure avec motifs
        g.setColor(secondaryColor);
        g.fillRect(centerX - 35, waistY - 15, 70, 20);
        
        // Motifs sur l'armure
        g.setColor(accentColor);
        for (int i = 0; i < 3; i++) {
            int x = centerX - 25 + i * 20;
            g.fillOval(x, shoulderY + 30, 10, 10);
        }
        
        // Tête et casque impressionnant
        g.setColor(new Color(240, 217, 181)); // Couleur peau
        g.fillOval(centerX - 15, headY, 30, 35);
        
        // Casque kabuto élaboré
        drawShogunHelmet(g, centerX, headY);
        
        // Visage
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 10, headY + 15, 5, 5); // Œil gauche
        g.fillOval(centerX + 5, headY + 15, 5, 5);  // Œil droit
        
        // Deux katanas de qualité
        drawOrnateKatana(g, centerX + 35, shoulderY + 15, true);
    }
    
    /**
     * Dessine un samouraï élégant de type Daimyo
     */
    private void drawDaimyoSamurai(Graphics2D g, int centerX, int centerY) {
        // Base corps
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 80;
        int feetY = waistY + 80;
        
        // Armure de corps
        g.setColor(primaryColor);
        // Torse
        g.fillRect(centerX - 28, shoulderY, 56, waistY - shoulderY);
        
        // Jambes avec protection de style différent
        g.fillRect(centerX - 25, waistY, 20, feetY - waistY);
        g.fillRect(centerX + 5, waistY, 20, feetY - waistY);
        
        // Bras avec design élégant
        g.fillRect(centerX - 42, shoulderY, 14, 65);
        g.fillRect(centerX + 28, shoulderY, 14, 65);
        
        // Détails d'armure luxueux 
        g.setColor(secondaryColor);
        for (int i = 1; i < 4; i++) {
            int y = shoulderY + i * 22;
            g.fillRect(centerX - 28, y, 56, 2);
        }
        
        // Épaulières de style différent
        drawCurvedShoulderPlate(g, centerX - 42, shoulderY - 5);
        drawCurvedShoulderPlate(g, centerX + 28, shoulderY - 5);
        
        // Jupe d'armure ornée
        g.setColor(secondaryColor);
        g.fillRect(centerX - 32, waistY - 12, 64, 16);
        
        // Tête avec un casque distinctif
        g.setColor(new Color(240, 217, 181)); // Couleur peau
        g.fillOval(centerX - 15, headY, 30, 35);
        
        // Casque de style daimyo
        drawDaimyoHelmet(g, centerX, headY);
        
        // Visage avec barbe
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 10, headY + 15, 4, 4); // Œil gauche
        g.fillOval(centerX + 6, headY + 15, 4, 4);  // Œil droit
        
        // Petite barbe
        for (int i = 0; i < 5; i++) {
            int x = centerX - 8 + i*4;
            g.drawLine(x, headY + 28, x, headY + 34);
        }
        
        // Katana de qualité et wakizashi
        drawOrnateKatana(g, centerX + 38, shoulderY + 20, false);
    }
    
    /**
     * Dessine un samouraï élégant de type Hatamoto
     */
    private void drawHatamotoSamurai(Graphics2D g, int centerX, int centerY) {
        // Base corps
        int headY = centerY - 100;
        int shoulderY = headY + 38;
        int waistY = shoulderY + 78;
        int feetY = waistY + 82;
        
        // Armure de corps légère mais élégante
        g.setColor(primaryColor);
        // Torse
        g.fillRect(centerX - 25, shoulderY, 50, waistY - shoulderY);
        
        // Jambes avec armure légère
        g.fillRect(centerX - 22, waistY, 18, feetY - waistY);
        g.fillRect(centerX + 4, waistY, 18, feetY - waistY);
        
        // Bras
        g.fillRect(centerX - 38, shoulderY, 13, 60);
        g.fillRect(centerX + 25, shoulderY, 13, 60);
        
        // Détails d'armure élégants mais plus subtils
        g.setColor(secondaryColor);
        for (int i = 0; i < 3; i++) {
            int y = shoulderY + 18 + i * 25;
            g.fillRect(centerX - 25, y, 50, 2);
        }
        
        // Épaulières plus petites mais finement travaillées
        drawSimpleShoulderPlate(g, centerX - 38, shoulderY);
        drawSimpleShoulderPlate(g, centerX + 25, shoulderY);
        
        // Jupe d'armure plus courte
        g.setColor(secondaryColor);
        g.fillRect(centerX - 27, waistY - 10, 54, 15);
        
        // Tête et casque élégant mais moins imposant
        g.setColor(new Color(240, 217, 181)); // Couleur peau
        g.fillOval(centerX - 15, headY, 30, 35);
        
        // Casque hatamoto
        drawHatamotoHelmet(g, centerX, headY);
        
        // Visage concentré
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 10, headY + 15, 4, 4); // Œil gauche
        g.fillOval(centerX + 6, headY + 15, 4, 4);  // Œil droit
        g.drawLine(centerX - 8, headY + 25, centerX + 8, headY + 25);  // Expression sérieuse
        
        // Katana simple mais de qualité
        drawOrnateKatana(g, centerX + 30, shoulderY + 30, true);
    }
    
    /**
     * Dessine un samouraï élégant de type Kensai (maître d'épée)
     */
    private void drawKensaiSamurai(Graphics2D g, int centerX, int centerY) {
        // Base corps
        int headY = centerY - 100;
        int shoulderY = headY + 40;
        int waistY = shoulderY + 75;
        int feetY = waistY + 80;
        
        // Tenue de maître d'épée (moins d'armure, plus de technique)
        g.setColor(primaryColor);
        // Kimono élégant
        g.fillRect(centerX - 26, shoulderY, 52, waistY - shoulderY);
        
        // Jambes
        g.fillRect(centerX - 20, waistY, 16, feetY - waistY);
        g.fillRect(centerX + 4, waistY, 16, feetY - waistY);
        
        // Manches amples
        drawWideSleeveArm(g, centerX - 45, shoulderY + 5, false);
        drawWideSleeveArm(g, centerX + 45, shoulderY + 5, true);
        
        // Détails du kimono
        g.setColor(secondaryColor);
        g.fillRect(centerX - 15, shoulderY, 30, waistY - shoulderY);
        
        // Ceinture obi élaborée
        g.setColor(accentColor);
        g.fillRect(centerX - 30, waistY - 15, 60, 10);
        
        // Tête et coiffure distinctive
        g.setColor(new Color(240, 217, 181)); // Couleur peau
        g.fillOval(centerX - 15, headY, 30, 35);
        
        // Coiffure de samouraï (chonmage)
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 17, headY - 5, 34, 25);
        g.fillRect(centerX - 2, headY - 15, 4, 15);
        
        // Visage concentré du maître d'épée
        g.fillOval(centerX - 10, headY + 15, 4, 4); // Œil gauche
        g.fillOval(centerX + 6, headY + 15, 4, 4);  // Œil droit
        g.drawLine(centerX - 5, headY + 25, centerX + 5, headY + 25);  // Expression déterminée
        
        // Katana légendaire en position de combat
        drawMasterKatana(g, centerX + 10, shoulderY + 40);
    }
    
    /**
     * Dessine un casque de shogun élaboré
     */
    private void drawShogunHelmet(Graphics2D g, int centerX, int headY) {
        // Base du casque
        g.setColor(primaryColor);
        g.fillArc(centerX - 22, headY - 12, 44, 42, 0, 180);
        
        // Protection faciale
        g.fillRect(centerX - 22, headY + 5, 44, 10);
        
        // Décoration supérieure (kuwagata)
        g.setColor(secondaryColor);
        int[] xPoints = {centerX, centerX - 10, centerX - 15, centerX - 5, centerX};
        int[] yPoints = {headY - 12, headY - 25, headY - 35, headY - 22, headY - 12};
        g.fillPolygon(xPoints, yPoints, 5);
        
        // Miroir le kuwagata pour l'autre côté
        int[] xPointsRight = {centerX, centerX + 10, centerX + 15, centerX + 5, centerX};
        g.fillPolygon(xPointsRight, yPoints, 5);
        
        // Détails dorés
        g.setColor(new Color(218, 165, 32));
        g.fillRect(centerX - 22, headY + 5, 44, 3);
        g.drawRect(centerX - 22, headY - 12, 44, 27);
        
        // Maedate (ornement frontal)
        g.fillOval(centerX - 5, headY - 5, 10, 10);
    }
    
    /**
     * Dessine un casque de daimyo distinctif
     */
    private void drawDaimyoHelmet(Graphics2D g, int centerX, int headY) {
        // Base du casque
        g.setColor(primaryColor);
        g.fillArc(centerX - 20, headY - 10, 40, 40, 0, 180);
        
        // Protection faciale
        g.fillRect(centerX - 20, headY + 5, 40, 10);
        
        // Décoration supérieure (maedate plus haute)
        g.setColor(secondaryColor);
        int[] xPoints = {centerX, centerX - 5, centerX, centerX + 5};
        int[] yPoints = {headY - 10, headY - 25, headY - 40, headY - 25};
        g.fillPolygon(xPoints, yPoints, 4);
        
        // Fukigaeshi (ailettes de protection)
        g.fillOval(centerX - 25, headY, 10, 15);
        g.fillOval(centerX + 15, headY, 10, 15);
        
        // Détails dorés
        g.setColor(new Color(218, 165, 32));
        g.drawArc(centerX - 20, headY - 10, 40, 40, 0, 180);
        g.drawRect(centerX - 20, headY + 5, 40, 10);
    }
    
    /**
     * Dessine un casque de hatamoto plus simple mais élégant
     */
    private void drawHatamotoHelmet(Graphics2D g, int centerX, int headY) {
        // Base du casque
        g.setColor(primaryColor);
        g.fillArc(centerX - 18, headY - 8, 36, 38, 0, 180);
        
        // Protection faciale
        g.fillRect(centerX - 18, headY + 5, 36, 10);
        
        // Shikoro (protège-nuque)
        g.setColor(secondaryColor);
        g.fillRect(centerX - 20, headY + 15, 40, 15);
        
        // Détails sur le casque
        g.setColor(accentColor);
        g.fillRect(centerX - 18, headY, 36, 2);
        
        // Maedate simple
        g.setColor(new Color(218, 165, 32));
        int[] xPoints = {centerX, centerX - 8, centerX, centerX + 8};
        int[] yPoints = {headY - 8, headY - 15, headY - 25, headY - 15};
        g.fillPolygon(xPoints, yPoints, 4);
    }
    
    /**
     * Dessine une épaulière ornée
     */
    private void drawOrnateShoulderPlate(Graphics2D g, int x, int y) {
        g.setColor(secondaryColor);
        g.fillArc(x, y, 20, 30, 0, 180);
        
        // Détails
        g.setColor(accentColor);
        g.drawArc(x + 2, y + 2, 16, 26, 0, 180);
        
        // Rivets
        g.setColor(new Color(218, 165, 32));
        g.fillOval(x + 5, y + 10, 3, 3);
        g.fillOval(x + 12, y + 10, 3, 3);
    }
    
    /**
     * Dessine une épaulière courbée
     */
    private void drawCurvedShoulderPlate(Graphics2D g, int x, int y) {
        g.setColor(secondaryColor);
        
        // Forme courbée
        int[] xPoints = {x, x + 20, x + 18, x - 2};
        int[] yPoints = {y, y, y + 25, y + 25};
        g.fillPolygon(xPoints, yPoints, 4);
        
        // Détails
        g.setColor(accentColor);
        for (int i = 0; i < 3; i++) {
            g.drawLine(x, y + 5 + i*8, x + 20, y + 5 + i*8);
        }
    }
    
    /**
     * Dessine une épaulière simple
     */
    private void drawSimpleShoulderPlate(Graphics2D g, int x, int y) {
        g.setColor(secondaryColor);
        g.fillRect(x - 2, y - 5, 17, 10);
        
        // Bordure
        g.setColor(accentColor);
        g.drawRect(x - 2, y - 5, 17, 10);
    }
    
    /**
     * Dessine un bras avec manche ample
     */
    private void drawWideSleeveArm(Graphics2D g, int x, int y, boolean isRight) {
        g.setColor(primaryColor);
        
        int direction = isRight ? -1 : 1;
        int[] xPoints = {x, x + direction*25, x + direction*30, x + direction*5};
        int[] yPoints = {y, y, y + 60, y + 60};
        g.fillPolygon(xPoints, yPoints, 4);
        
        // Détails de la manche
        g.setColor(secondaryColor);
        g.drawLine(x + direction*5, y + 20, x + direction*25, y + 20);
        g.drawLine(x + direction*8, y + 40, x + direction*28, y + 40);
    }
    
    /**
     * Dessine un katana orné
     */
    private void drawOrnateKatana(Graphics2D g, int x, int y, boolean isLong) {
        // Fourreau
        g.setColor(new Color(100, 0, 0));
        int length = isLong ? 80 : 65;
        g.fillRect(x, y, 5, length);
        
        // Garde tsuba
        g.setColor(new Color(218, 165, 32));
        g.fillOval(x - 5, y - 5, 15, 10);
        
        // Poignée
        g.setColor(new Color(139, 69, 19));
        g.fillRect(x, y - 15, 5, 10);
        
        // Tressage de la poignée
        g.setColor(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            g.drawLine(x, y - 15 + i*3, x + 5, y - 15 + i*3);
        }
        
        // Ornements dorés
        g.setColor(new Color(218, 165, 32));
        g.fillRect(x, y - 17, 5, 2);
    }
    
    /**
     * Dessine le katana légendaire d'un maître d'épée
     */
    private void drawMasterKatana(Graphics2D g, int x, int y) {
        // Lame
        g.setColor(new Color(200, 200, 220));
        g.fillRect(x + 5, y - 70, 3, 90);
        
        // Effet de lumière sur la lame
        g.setColor(Color.WHITE);
        g.drawLine(x + 6, y - 70, x + 6, y + 20);
        
        // Garde tsuba
        g.setColor(new Color(218, 165, 32));
        g.fillOval(x, y + 20, 13, 8);
        
        // Poignée
        g.setColor(new Color(100, 0, 0));
        g.fillRect(x + 3, y + 28, 7, 25);
        
        // Tressage de la poignée
        g.setColor(Color.BLACK);
        for (int i = 0; i < 6; i++) {
            g.drawLine(x + 3, y + 30 + i*4, x + 10, y + 30 + i*4);
        }
        
        // Pommel
        g.setColor(new Color(218, 165, 32));
        g.fillOval(x + 3, y + 53, 7, 5);
    }
}

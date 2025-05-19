package view;

/**
 * Patch pour corriger les problèmes d'affichage de texte
 * Cette classe sera automatiquement chargée lors de l'initialisation de
 * SamuraiSwingUI
 */
public class SamuraiSwingUIFixer {

    /**
     * Applique les corrections aux interfaces de l'application
     * Utilisé par SamuraiSwingUI et CombatUI pour résoudre les problèmes de texte
     * 
     * @param ui L'interface à corriger
     */
    public static void applyFixes(Object ui) {
        if (ui instanceof SamuraiSwingUI) {
            fixSamuraiUI((SamuraiSwingUI) ui);
        } else if (ui instanceof CombatUI) {
            fixCombatUI((CombatUI) ui);
        } else if (ui instanceof InventoryWindow) {
            fixInventoryUI((InventoryWindow) ui);
        } else if (ui instanceof EndChapterDialog) {
            fixEndChapterDialog((EndChapterDialog) ui);
        }
    }

    /**
     * Corrige les problèmes d'affichage dans l'interface principale
     * 
     * @param ui L'interface principale
     */
    private static void fixSamuraiUI(SamuraiSwingUI ui) {
        try {
            // Accéder au champ texteChapitreArea par réflexion
            java.lang.reflect.Field field = SamuraiSwingUI.class.getDeclaredField("texteChapitreArea");
            field.setAccessible(true);
            javax.swing.JTextArea textArea = (javax.swing.JTextArea) field.get(ui);

            // Appliquer les corrections
            if (textArea != null) {
                TextRenderingFixer.fixTextRendering(textArea);
            }

            // Accéder au JScrollPane pour optimiser le viewport
            java.lang.reflect.Field scrollPaneFields[] = SamuraiSwingUI.class.getDeclaredFields();
            for (java.lang.reflect.Field f : scrollPaneFields) {
                if (f.getType() == javax.swing.JScrollPane.class) {
                    f.setAccessible(true);
                    javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane) f.get(ui);
                    if (scrollPane != null) {
                        scrollPane.getViewport().setOpaque(true);
                        scrollPane.getViewport().setBackground(new java.awt.Color(10, 10, 10, 255));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'application des correctifs de texte: " + e.getMessage());
        }
    }

    /**
     * Corrige les problèmes d'affichage dans l'interface de combat
     * 
     * @param ui L'interface de combat
     */
    private static void fixCombatUI(CombatUI ui) {
        try {
            // Accéder au champ combatLog par réflexion
            java.lang.reflect.Field field = CombatUI.class.getDeclaredField("combatLog");
            field.setAccessible(true);
            javax.swing.JTextArea textArea = (javax.swing.JTextArea) field.get(ui);

            // Appliquer les corrections
            if (textArea != null) {
                TextRenderingFixer.fixTextRendering(textArea);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'application des correctifs de texte: " + e.getMessage());
        }
    }

    /**
     * Corrige les problèmes d'affichage dans l'interface d'inventaire
     * 
     * @param ui L'interface d'inventaire
     */
    private static void fixInventoryUI(InventoryWindow ui) {
        // Implémenté si nécessaire
    }

    /**
     * Corrige les problèmes d'affichage dans la boîte de dialogue de fin de
     * chapitre
     * 
     * @param dialog La boîte de dialogue
     */
    private static void fixEndChapterDialog(EndChapterDialog dialog) {
        // Implémenté si nécessaire
    }
}

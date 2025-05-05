package view;

import model.Personnage;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryWindow extends JDialog {
    private static final Color BACKGROUND_COLOR = new Color(24, 24, 24);
    private static final Color TEXT_COLOR = new Color(255, 241, 224);
    private static final Color ACCENT_COLOR = new Color(201, 121, 66);
    private static final Font TITLE_FONT = new Font("Yu Mincho", Font.BOLD, 20);
    private static final Font ITEM_FONT = new Font("Yu Mincho", Font.PLAIN, 16);

    private Personnage personnage;
    private JPanel inventoryPanel;
    private static final Map<String, String> AVAILABLE_ITEMS = new HashMap<>();

    static {
        AVAILABLE_ITEMS.put("Katana de famille", "⚔️");
        AVAILABLE_ITEMS.put("Armure légère", "🛡️");
        AVAILABLE_ITEMS.put("Nécessaire de méditation", "🎐");
        AVAILABLE_ITEMS.put("Rations de voyage", "🍙");
        AVAILABLE_ITEMS.put("Gourde d'eau", "🏺");
        AVAILABLE_ITEMS.put("Shuriken", "✴️");
        AVAILABLE_ITEMS.put("Parchemin magique", "📜");
        AVAILABLE_ITEMS.put("Potion de soin", "🧪");
        AVAILABLE_ITEMS.put("Amulette sacrée", "🔮");
        AVAILABLE_ITEMS.put("Kunai", "🗡️");
    }

    public InventoryWindow(JFrame parent, Personnage personnage) {
        super(parent, "Inventaire du Samouraï", true);
        this.personnage = personnage;
        
        setSize(400, 600);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = new JLabel("Inventaire", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel pour l'inventaire actuel
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setBackground(BACKGROUND_COLOR);
        updateInventoryDisplay();

        // Panel pour les items disponibles
        JPanel availableItemsPanel = new JPanel();
        availableItemsPanel.setLayout(new BoxLayout(availableItemsPanel, BoxLayout.Y_AXIS));
        availableItemsPanel.setBackground(BACKGROUND_COLOR);

        JLabel availableLabel = new JLabel("Items disponibles:");
        availableLabel.setFont(TITLE_FONT);
        availableLabel.setForeground(ACCENT_COLOR);
        availableItemsPanel.add(availableLabel);

        for (Map.Entry<String, String> item : AVAILABLE_ITEMS.entrySet()) {
            JButton itemButton = createItemButton(item.getValue() + " " + item.getKey());
            itemButton.addActionListener(e -> {
                if (!personnage.possèdeObjet(item.getKey())) {
                    personnage.ajouterObjet(item.getKey());
                    updateInventoryDisplay();
                }
            });
            availableItemsPanel.add(itemButton);
        }

        // Scroll panes
        JScrollPane inventoryScroll = new JScrollPane(inventoryPanel);
        JScrollPane availableScroll = new JScrollPane(availableItemsPanel);
        
        // Split pane pour diviser l'écran
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                                            inventoryScroll, availableScroll);
        splitPane.setDividerLocation(300);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void updateInventoryDisplay() {
        inventoryPanel.removeAll();
        
        JLabel currentLabel = new JLabel("Inventaire actuel:");
        currentLabel.setFont(TITLE_FONT);
        currentLabel.setForeground(ACCENT_COLOR);
        inventoryPanel.add(currentLabel);

        for (String item : personnage.getInventaire()) {
            JPanel itemPanel = new JPanel(new BorderLayout(5, 0));
            itemPanel.setBackground(BACKGROUND_COLOR);
            
            String icon = AVAILABLE_ITEMS.getOrDefault(item, "📦");
            JLabel itemLabel = new JLabel(icon + " " + item);
            itemLabel.setFont(ITEM_FONT);
            itemLabel.setForeground(TEXT_COLOR);
            
            JButton removeButton = new JButton("❌");
            removeButton.addActionListener(e -> {
                personnage.retirerObjet(item);
                updateInventoryDisplay();
            });
            
            itemPanel.add(itemLabel, BorderLayout.CENTER);
            itemPanel.add(removeButton, BorderLayout.EAST);
            inventoryPanel.add(itemPanel);
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private JButton createItemButton(String text) {
        JButton button = new JButton(text);
        button.setFont(ITEM_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(new Color(60, 30, 15));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }
}

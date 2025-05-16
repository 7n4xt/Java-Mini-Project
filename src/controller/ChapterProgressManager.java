package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire de progression des chapitres
 */
public class ChapterProgressManager {
    private static final String PROGRESS_FILE = "chapter_progress.dat";
    private Map<Integer, Boolean> completedChapters;

    /**
     * Constructeur du gestionnaire de progression
     */
    public ChapterProgressManager() {
        completedChapters = new HashMap<>();
        loadProgress();
    }

    /**
     * Marque un chapitre comme complété
     * 
     * @param chapterId L'ID du chapitre
     */
    public void markChapterCompleted(int chapterId) {
        completedChapters.put(chapterId, true);
        saveProgress();
    }

    /**
     * Vérifie si un chapitre est complété
     * 
     * @param chapterId L'ID du chapitre
     * @return true si le chapitre a été complété
     */
    public boolean isChapterCompleted(int chapterId) {
        return completedChapters.getOrDefault(chapterId, false);
    }

    /**
     * Sauvegarde la progression dans un fichier
     */
    private void saveProgress() {
        try (FileWriter writer = new FileWriter(PROGRESS_FILE)) {
            for (Map.Entry<Integer, Boolean> entry : completedChapters.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de la progression: " + e.getMessage());
        }
    }

    /**
     * Charge la progression depuis un fichier
     */
    private void loadProgress() {
        try {
            File file = new File(PROGRESS_FILE);
            if (file.exists()) {
                Files.readAllLines(Paths.get(PROGRESS_FILE)).forEach(line -> {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        try {
                            int chapterId = Integer.parseInt(parts[0]);
                            boolean completed = Boolean.parseBoolean(parts[1]);
                            completedChapters.put(chapterId, completed);
                        } catch (NumberFormatException e) {
                            System.err.println("Format invalide dans le fichier de progression");
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la progression: " + e.getMessage());
        }
    }

    /**
     * Réinitialise la progression
     */
    public void resetProgress() {
        completedChapters.clear();
        saveProgress();
    }
}

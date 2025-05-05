package controller;

import model.Chapitre;
import model.Choix;
import model.Scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Charge les scénarios depuis différents formats de fichiers.
 */
public class ScenarioLoader {

        /**
         * Charge un scénario à partir d'un fichier JSON (version simplifiée)
         * 
         * @param cheminFichier Chemin vers le fichier JSON
         * @return Un scénario chargé ou null en cas d'erreur
         */
        public static Scenario chargerDepuisJson(String cheminFichier) {
                try {
                        String contenu = Files.readString(Paths.get(cheminFichier));
                        // Dans une vraie implémentation, utilisez une bibliothèque JSON comme Jackson
                        // ou Gson
                        // Cette version est une simulation simplifiée
                        return parseScenarioJson(contenu);
                } catch (IOException e) {
                        System.err.println("Erreur lors du chargement du scénario : " + e.getMessage());
                        return null;
                }
        }

        /**
         * Crée un scénario de démonstration pour L'Épée du Samouraï
         * 
         * @return Un scénario de test
         */
        public static Scenario creerScenarioDemonstration() {
                // Création du scénario
                Scenario scenario = new Scenario("L'Épée du Samouraï",
                                "Vous êtes un samouraï en quête de l'épée légendaire qui a appartenu à votre ancêtre.");

                // Chapitre 1: Introduction
                Chapitre chapitre1 = new Chapitre(1, "Le début de la quête",
                                "Vous vous trouvez dans le village de votre enfance. Le vieux maître vous a parlé " +
                                                "d'une épée légendaire ayant appartenu à votre ancêtre. Cette épée serait cachée "
                                                +
                                                "dans un temple perdu dans les montagnes. Vous décidez de partir à sa recherche.");

                // Chapitre 2: La forêt
                Chapitre chapitre2 = new Chapitre(2, "La forêt mystérieuse",
                                "Vous entrez dans une forêt dense et mystérieuse. Les arbres semblent vivants " +
                                                "et le brouillard rend la visibilité difficile. Vous entendez des bruits étranges.");

                // Chapitre 3: Les montagnes
                Chapitre chapitre3 = new Chapitre(3, "Le chemin montagneux",
                                "Après avoir traversé la forêt, vous arrivez au pied des montagnes. " +
                                                "Le chemin est escarpé et dangereux. Vous apercevez deux sentiers.");

                // Chapitre 4: Le temple
                Chapitre chapitre4 = new Chapitre(4, "Le temple ancien",
                                "Vous arrivez enfin devant le temple ancien. Son architecture est impressionnante " +
                                                "malgré les siècles qui ont passé. La porte est fermée et comporte des inscriptions.");

                // Chapitre 5: La chambre du trésor
                Chapitre chapitre5 = new Chapitre(5, "La chambre du trésor",
                                "Après avoir résolu l'énigme, vous pénétrez dans la chambre du trésor. " +
                                                "Au centre, sur un autel de pierre, repose l'épée légendaire de votre ancêtre.");

                // Chapitre 6: Fin heureuse
                Chapitre chapitre6 = new Chapitre(6, "Le retour triomphal",
                                "Vous retournez au village avec l'épée légendaire. Vous êtes accueilli en héros. " +
                                                "Le vieux maître vous regarde avec fierté. Votre quête est accomplie.",
                                true);

                // Chapitre 7: Mort dans la forêt
                Chapitre chapitre7 = new Chapitre(7, "Perdu dans les ténèbres",
                                "Vous vous enfoncez profondément dans la forêt et perdez tout sens de l'orientation. " +
                                                "Les bruits étranges se rapprochent. Vous ne retrouverez jamais le chemin du retour...",
                                true);

                // Ajouter les choix aux chapitres
                chapitre1.ajouterChoix(new Choix("Partir immédiatement vers la forêt", 2));
                chapitre1.ajouterChoix(new Choix("Consulter le vieux maître pour plus d'informations", 2));

                chapitre2.ajouterChoix(new Choix("Suivre le sentier principal", 3));
                chapitre2.ajouterChoix(new Choix("Explorer les profondeurs de la forêt", 7));

                chapitre3.ajouterChoix(new Choix("Prendre le sentier de gauche, plus court mais dangereux", 4));
                chapitre3.ajouterChoix(new Choix("Prendre le sentier de droite, plus long mais sûr", 4));

                chapitre4.ajouterChoix(new Choix("Essayer de déchiffrer les inscriptions", 5));
                chapitre4.ajouterChoix(new Choix("Forcer la porte du temple", 5));

                chapitre5.ajouterChoix(new Choix("Prendre l'épée et quitter le temple", 6));

                // Ajouter les chapitres au scénario
                scenario.ajouterChapitre(chapitre1);
                scenario.ajouterChapitre(chapitre2);
                scenario.ajouterChapitre(chapitre3);
                scenario.ajouterChapitre(chapitre4);
                scenario.ajouterChapitre(chapitre5);
                scenario.ajouterChapitre(chapitre6);
                scenario.ajouterChapitre(chapitre7);

                return scenario;
        }

        /**
         * Simule le parsing d'un JSON
         * Dans une vraie implémentation, utilisez une bibliothèque JSON
         */
        private static Scenario parseScenarioJson(String json) {
                // Ce code est une simulation, dans un vrai projet, utilisez Jackson ou Gson
                // Cette méthode serait remplacée par un vrai parser JSON
                return creerScenarioDemonstration();
        }
}
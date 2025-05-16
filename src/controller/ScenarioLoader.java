package controller;

import model.Chapitre;
import model.Choix;
import model.Enemy;
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
                                                "dans un temple perdu dans les montagnes. Vous décidez de partir à sa recherche.\n\n"
                                                +
                                                "En sortant du village, vous apercevez un chemin menant aux montagnes, mais un ninja gardien semble surveiller la route.");

                // Chapitre 2: La forêt
                Chapitre chapitre2 = new Chapitre(2, "La forêt mystérieuse",
                                "Après avoir vaincu le ninja gardien, vous entrez dans une forêt dense et mystérieuse. "
                                                +
                                                "Les arbres semblent vivants et le brouillard rend la visibilité difficile. "
                                                +
                                                "Vous entendez des bruits étranges.");

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

                // Chapitre 8: Défaite contre le ninja gardien
                Chapitre chapitre8 = new Chapitre(8, "Défaite face au gardien",
                                "Le ninja s'est montré trop fort pour vous. Ses techniques de combat vous ont pris " +
                                                "par surprise et vous êtes à terre, incapable de continuer. Votre quête s'achève ici...",
                                true);

                // Ajouter les choix aux chapitres
                chapitre1.ajouterChoix(new Choix("Affronter le ninja gardien", 2, true)); // Combat spécial
                chapitre1.ajouterChoix(new Choix("Essayer de contourner le gardien", 8)); // Conduit à l'échec

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
                scenario.ajouterChapitre(chapitre8);

                // Créer les ennemis
                Enemy ninjaGardien = new Enemy("Ninja Gardien", 7, 8,
                                "Un ninja agile et rapide qui garde le chemin menant aux montagnes. Il porte une armure légère et est armé d'une lame acérée.");

                // Ajouter l'ennemi au premier chapitre
                chapitre1.setEnemy(ninjaGardien);

                return scenario;
        }

        /**
         * Crée un scénario pour le Chapitre 2 de L'Épée du Samouraï
         * 
         * @return Un scénario de combat pour le Chapitre 2
         */
        public static Scenario creerScenarioChapitre2() {
                // Création du scénario
                Scenario scenario = new Scenario("L'Épée du Samouraï - Chapitre 2",
                                "La suite de votre quête pour retrouver l'épée légendaire de votre ancêtre.");

                // Chapitre 1: Le pont d'Hagakure
                Chapitre chapitre1 = new Chapitre(1, "Le pont d'Hagakure",
                                "Vous voici enfin sorti de la forêt des Ombres. Devant vous s'ouvre une route menant à un ancien pont de pierre — " +
                                "le pont d'Hagakure — qui enjambe un large fleuve : le Hiang-Kiang. Au-delà, loin à l'horizon, " + 
                                "se dressent les monts Shios'ii.");

                // Chapitre 2: L'affrontement avec le samouraï spectral
                Chapitre chapitre2 = new Chapitre(2, "Le samouraï spectral",
                                "Alors que vous traversez le pont, le ciel s'obscurcit et l'eau devient rouge comme du sang. " +
                                "Soudain, une apparition hideuse se profile au bout du pont. C'est un guerrier samouraï mort-vivant, " +
                                "dont la tête n'est plus qu'un crâne grimaçant portant un casque rouillé.");

                // Chapitre 3: Le village abandonné
                Chapitre chapitre3 = new Chapitre(3, "Le village abandonné",
                                "Après avoir vaincu le spectre, vous trouvez un village qui semble avoir été attaqué récemment. " +
                                "Les maisons sont partiellement détruites et il n'y a personne en vue.");

                // Chapitre 4: L'embuscade
                Chapitre chapitre4 = new Chapitre(4, "L'embuscade",
                                "En explorant le village, vous êtes soudainement entouré par un groupe de bandits. " +
                                "Leur chef s'avance vers vous, défiant quiconque ose lui résister.");

                // Chapitre 5: La carte secrète
                Chapitre chapitre5 = new Chapitre(5, "La carte secrète",
                                "Après avoir vaincu les bandits, vous trouvez quelques villageois cachés. " +
                                "Le chef du village vous remercie et vous remet une ancienne carte qui indique un passage " +
                                "secret vers les montagnes Shios'ii, vous rapprochant de votre quête.");

                // Chapitre 6: Fin du chapitre 2
                Chapitre chapitre6 = new Chapitre(6, "La route des montagnes",
                                "Avec la carte en main, vous reprenez votre route vers les montagnes. " +
                                "Le chemin sera difficile, mais vous êtes maintenant plus proche de l'épée légendaire de votre ancêtre.",
                                true);

                // Chapitre 7: Mort face au spectre
                Chapitre chapitre7 = new Chapitre(7, "Défaite sur le pont",
                                "Le samouraï spectral était trop puissant. Votre quête s'achève ici, sur ce pont maudit...",
                                true);

                // Ajouter les choix aux chapitres
                chapitre1.ajouterChoix(new Choix("Traverser le pont", 2));
                chapitre1.ajouterChoix(new Choix("Chercher un autre passage", 7));

                chapitre2.ajouterChoix(new Choix("Affronter le spectre", 3, true)); // Combat spécial
                chapitre2.ajouterChoix(new Choix("Tenter de fuir", 7));

                chapitre3.ajouterChoix(new Choix("Explorer le village", 4));
                chapitre3.ajouterChoix(new Choix("Contourner le village", 6));

                chapitre4.ajouterChoix(new Choix("Combattre les bandits", 5, true)); // Combat
                chapitre4.ajouterChoix(new Choix("Tenter de négocier", 5));

                chapitre5.ajouterChoix(new Choix("Continuer vers les montagnes", 6));

                // Créer les ennemis
                Enemy samourai = new Enemy("Samouraï Spectral", 8, 10,
                                "Un guerrier mort-vivant dont la tête n'est plus qu'un crâne grimaçant. Ses mains osseuses sont crispées sur un long sabre.");

                Enemy chefBandit = new Enemy("Chef des Bandits", 9, 8,
                                "Un guerrier robuste portant une armure légère et maniant un katana. Ses yeux reflètent sa cruauté et sa détermination.");

                // Ajouter les ennemis aux chapitres correspondants
                chapitre2.setEnemy(samourai);
                chapitre4.setEnemy(chefBandit);

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
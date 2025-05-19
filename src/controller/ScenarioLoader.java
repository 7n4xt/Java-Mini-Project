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
                chapitre1.ajouterChoix(new Choix("Essayer de contourner le gardien", 8, false)); // Conduit à l'échec

                chapitre2.ajouterChoix(new Choix("Suivre le sentier principal", 3, false));
                chapitre2.ajouterChoix(new Choix("Explorer les profondeurs de la forêt", 7, false));

                chapitre3.ajouterChoix(new Choix("Prendre le sentier de gauche, plus court mais dangereux", 4, false));
                chapitre3.ajouterChoix(new Choix("Prendre le sentier de droite, plus long mais sûr", 4, false));

                chapitre4.ajouterChoix(new Choix("Essayer de déchiffrer les inscriptions", 5, false));
                chapitre4.ajouterChoix(new Choix("Forcer la porte du temple", 5, false));

                chapitre5.ajouterChoix(new Choix("Prendre l'épée et quitter le temple", 6, false));

                // Ajouter les chapitres au scénario
                scenario.ajouterChapitre(chapitre1);
                scenario.ajouterChapitre(chapitre2);
                scenario.ajouterChapitre(chapitre3);
                scenario.ajouterChapitre(chapitre4);
                scenario.ajouterChapitre(chapitre5);
                scenario.ajouterChapitre(chapitre6);
                scenario.ajouterChapitre(chapitre7);
                scenario.ajouterChapitre(chapitre8);

                // Créer les ennemis avec des récompenses
                Enemy ninjaGardien = new Enemy("Ninja Gardien", 7, 8,
                                "Un ninja agile et rapide qui garde le chemin menant aux montagnes. Il porte une armure légère et est armé d'une lame acérée.",
                                "Kunai"); // Récompense: Kunai

                // Ajouter l'ennemi au premier chapitre
                chapitre1.setEnemy(ninjaGardien);

                return scenario;
        }

        /**
         * Crée un scénario pour le chapitre 2
         * 
         * @return Un nouveau scénario contenant le chapitre 2
         */
        public static Scenario creerScenarioChapitre2() {
                try {
                        System.out.println("Création du scénario du chapitre 2...");

                        Scenario scenario = new Scenario("L'Épée du Samouraï - Chapitre 2: Le Temple de la Montagne",
                                        "Après votre victoire contre les bandits du village, une nouvelle aventure commence alors que vous partez à la recherche du temple ancestral dans les montagnes Sakura.");

                        // Chapitre 1 - Début du chapitre 2 (Après la victoire du chapitre 1)
                        Chapitre chapitre1 = new Chapitre(1, "La Route vers la Montagne",
                                        "Après votre victoire contre les bandits du village, vous avez acquis une certaine réputation et "
                                                        +
                                                        "votre expérience de combat s'est améliorée. Vous vous sentez plus fort et plus habile avec votre arme.\n\n"
                                                        +
                                                        "Les anciens du village vous ont parlé d'un temple ancestral niché dans les montagnes Sakura, "
                                                        +
                                                        "où reposerait une arme légendaire capable de vaincre le seigneur de guerre qui terrorise la région.\n\n"
                                                        +
                                                        "Avant de partir, le forgeron du village vous a offert une amélioration pour votre arme, vous donnant "
                                                        +
                                                        "un avantage certain dans les combats à venir.\n\n" +
                                                        "Vous voilà maintenant sur le sentier escarpé qui mène vers les montagnes. Le chemin est périlleux, "
                                                        +
                                                        "mais votre détermination est sans faille. Alors que vous atteignez un passage étroit entre deux falaises, "
                                                        +
                                                        "vous remarquez des traces fraîches sur le sol. Quelqu'un ou quelque chose est passé par là récemment.");

                        // Chapitre 2 - Exploration prudente
                        Chapitre chapitre2 = new Chapitre(2, "Signes de Danger",
                                        "Vous avancez avec prudence, tous vos sens en alerte. Les traces semblent être celles de plusieurs "
                                                        +
                                                        "personnes, peut-être une patrouille. Soudain, vous entendez des voix en avant. Vous vous approchez "
                                                        +
                                                        "silencieusement et apercevez un groupe de soldats aux couleurs du seigneur de guerre Akuma.\n\n"
                                                        +
                                                        "Ils semblent monter la garde devant un petit sanctuaire de pierre qui borde le chemin. L'un d'eux tient "
                                                        +
                                                        "ce qui ressemble à une carte ancienne. Ces soldats paraissent assez inexpérimentés - sans doute de "
                                                        +
                                                        "nouvelles recrues pas encore habituées aux dangers de la montagne.");

                        // Chapitre 3 - Confrontation avec les soldats (ennemis faibles)
                        Chapitre chapitre3 = new Chapitre(3, "Confrontation facile",
                                        "Vous décidez d'affronter directement les soldats. Vous dégainez votre lame et vous avancez "
                                                        +
                                                        "avec détermination. Les gardes vous aperçoivent immédiatement et semblent surpris et effrayés.\n\n"
                                                        +
                                                        "\"Un intrus ! C'est peut-être le guerrier dont on parle dans les villages ! Arrêtez-le !\" crie leur chef avec une voix tremblante.\n\n"
                                                        +
                                                        "Un jeune soldat inexpérimenté se précipite vers vous maladroitement, son arme mal tenue. Il sera facile à vaincre.");

                        // Créer un ennemi encore plus faible pour ce chapitre
                        Enemy soldatInexperimenteEnemy = new Enemy("Soldat Inexpérimenté", 5, 8,
                                        "Un jeune recrue tremblante, tenant maladroitement son arme. Sa posture révèle son manque d'entraînement.");
                        chapitre3.setEnemy(soldatInexperimenteEnemy);

                        // Chapitre 4 - Après la victoire contre les soldats
                        Chapitre chapitre4 = new Chapitre(4, "La Carte Ancienne et l'Équipement",
                                        "Après avoir vaincu facilement les soldats inexpérimentés, vous examinez le corps du chef et récupérez la carte qu'il tenait. "
                                                        +
                                                        "C'est un parchemin ancien qui montre le chemin vers le temple caché, avec des annotations mystérieuses.\n\n"
                                                        +
                                                        "La carte révèle que le temple est protégé par diverses épreuves et pièges. Elle indique également un "
                                                        +
                                                        "passage secret que peu connaissent, différent du chemin principal que prendraient certainement les "
                                                        +
                                                        "hommes du seigneur de guerre.\n\n" +
                                                        "En fouillant l'équipement des soldats, vous trouvez également une potion de force et une pièce d'armure légère. "
                                                        +
                                                        "Ces objets vous seront très utiles pour les combats à venir.");

                        // Chapitre 5 - Choix du chemin
                        Chapitre chapitre5 = new Chapitre(5, "À la Croisée des Chemins",
                                        "En suivant les indications de la carte, vous arrivez à une fourche dans le sentier de montagne. "
                                                        +
                                                        "Le chemin principal continue vers la droite, large mais exposé. L'autre chemin, à peine visible "
                                                        +
                                                        "entre les rochers et la végétation, s'enfonce vers la gauche dans une forêt dense de bambous.\n\n"
                                                        +
                                                        "La carte indique que le passage secret traverse la forêt de bambous, mais il est marqué d'un symbole "
                                                        +
                                                        "d'avertissement que vous ne parvenez pas à déchiffrer.");

                        // Chapitre 6 - Chemin principal
                        Chapitre chapitre6 = new Chapitre(6, "Le Pont Suspendu",
                                        "Vous optez pour le chemin principal. Après une heure de marche, vous arrivez face à un précipice "
                                                        +
                                                        "impressionnant. Un pont suspendu, ancien mais apparemment solide, est le seul moyen de traverser.\n\n"
                                                        +
                                                        "Alors que vous vous engagez sur le pont, vous entendez un craquement sinistre. Au milieu du pont, vous "
                                                        +
                                                        "apercevez une silhouette imposante. Un garde du seigneur Akuma bloque le passage, mais vous remarquez "
                                                        +
                                                        "qu'il semble blessé, s'appuyant sur sa lance pour rester debout.");

                        // Créer un ennemi affaibli pour ce chapitre
                        Enemy gardeBlesseEnemy = new Enemy("Garde Blessé", 6, 10,
                                        "Un garde imposant mais visiblement blessé. Il s'appuie sur sa lance et grimace de douleur.");
                        chapitre6.setEnemy(gardeBlesseEnemy);

                        // Chapitre 7 - Après la victoire sur le pont
                        Chapitre chapitre7 = new Chapitre(7, "Vue sur le Temple",
                                        "Après un combat éprouvant, vous parvenez à vaincre le garde d'élite. Son corps chute dans le précipice "
                                                        +
                                                        "tandis que vous traversez le reste du pont avec précaution.\n\n"
                                                        +
                                                        "De l'autre côté, le sentier grimpe encore pendant quelques heures avant de déboucher sur un plateau. "
                                                        +
                                                        "C'est alors que vous l'apercevez enfin : le temple ancestral de la montagne Sakura. Ses toits rouge et or "
                                                        +
                                                        "brillent sous le soleil couchant, entourés de cerisiers en fleurs.");

                        // Chapitre 8 - Chemin secret à travers la forêt
                        Chapitre chapitre8 = new Chapitre(8, "La Forêt de Bambous",
                                        "Vous choisissez le passage secret à travers la forêt de bambous. Le sentier est à peine visible, "
                                                        +
                                                        "mais vous suivez attentivement les indications de la carte.\n\n"
                                                        +
                                                        "La forêt est d'une beauté envoûtante. Les bambous s'élèvent si haut qu'ils filtrent la lumière en rayons "
                                                        +
                                                        "émeraude. Cependant, plus vous avancez, plus vous remarquez d'étranges symboles gravés dans le bambou. "
                                                        +
                                                        "Un brouillard léger commence à s'élever du sol.");

                        // Chapitre 9 - Épreuve dans la forêt
                        Chapitre chapitre9 = new Chapitre(9, "Le Gardien de la Forêt",
                                        "Le brouillard s'épaissit jusqu'à ce que vous ne puissiez plus voir qu'à quelques mètres devant vous. "
                                                        +
                                                        "Un silence inquiétant tombe sur la forêt. Soudain, une silhouette éthérée se matérialise devant vous – "
                                                        +
                                                        "un esprit guerrier vêtu d'une armure ancienne, son visage caché derrière un masque de démon.\n\n"
                                                        +
                                                        "\"Prouvez votre valeur, voyageur, ou retournez sur vos pas\" déclare l'apparition d'une voix caverneuse. "
                                                        +
                                                        "L'esprit semble plus impressionnant que dangereux - une illusion conçue pour effrayer les intrus plus que pour les blesser.");

                        // Créer un ennemi spectral très faible
                        Enemy espritAffaibliEnemy = new Enemy("Illusion d'Esprit", 6, 8,
                                        "Une apparition spectrale plus impressionnante que réellement dangereuse, créée pour tester votre courage plutôt que votre force.");
                        chapitre9.setEnemy(espritAffaibliEnemy);

                        // Chapitre 10 - Après l'épreuve de l'esprit
                        Chapitre chapitre10 = new Chapitre(10, "Bénédiction Spirituelle",
                                        "Après avoir prouvé votre valeur face à l'esprit gardien, celui-ci s'incline devant vous. Sa forme "
                                                        +
                                                        "devient luminescente et il pose une main spectrale sur votre front.\n\n"
                                                        +
                                                        "\"Vous portez en vous l'essence d'un véritable guerrier. Recevez ma bénédiction.\"\n\n"
                                                        +
                                                        "Une chaleur se répand dans votre corps et vous vous sentez revitalisé. Le brouillard se dissipe, "
                                                        +
                                                        "révélant un chemin clair qui monte vers une clairière de cerisiers en fleurs. Au-delà, vous apercevez "
                                                        +
                                                        "les murs du temple ancestral.");

                        // Chapitre 11 - Arrivée au temple
                        Chapitre chapitre11 = new Chapitre(11, "Le Temple Ancestral",
                                        "Que vous soyez arrivé par le pont ou par la forêt de bambous, vous voici enfin face à l'entrée du temple. "
                                                        +
                                                        "Deux immenses statues de guerriers gardent la porte principale, leurs visages usés par le temps mais leur "
                                                        +
                                                        "posture toujours impressionnante.\n\n" +
                                                        "Les portes du temple sont scellées par un mécanisme ancien. Sur le mur à côté, une inscription en vieux "
                                                        +
                                                        "japonais et trois symboles gravés : un tigre, un dragon et une tortue.");

                        // Chapitre 12 - L'énigme du temple
                        Chapitre chapitre12 = new Chapitre(12, "Les Trois Gardiens",
                                        "L'inscription dit : \"Seuls ceux qui honorent l'ordre céleste peuvent entrer. Placez votre main sur le "
                                                        +
                                                        "gardien qui vient après la sagesse mais avant la férocité.\"\n\n"
                                                        +
                                                        "Vous examinez attentivement les trois symboles gravés. La tortue représente traditionnellement la sagesse, "
                                                        +
                                                        "le dragon le pouvoir céleste, et le tigre la férocité terrestre.");

                        // Chapitre 13 - Mauvais choix (tortue)
                        Chapitre chapitre13 = new Chapitre(13, "Jugement Erroné",
                                        "Vous placez votre main sur le symbole de la tortue. Immédiatement, un mécanisme s'enclenche et une "
                                                        +
                                                        "trappe s'ouvre sous vos pieds. Vous chutez dans une salle inférieure.\n\n"
                                                        +
                                                        "Heureusement, la chute n'est pas mortelle. Vous vous relevez dans une petite chambre avec une porte "
                                                        +
                                                        "qui mène à un escalier. Celui-ci vous ramène à l'entrée du temple. Vous devrez essayer à nouveau.");

                        // Chapitre 14 - Mauvais choix (tigre)
                        Chapitre chapitre14 = new Chapitre(14, "Erreur de Séquence",
                                        "Vous placez votre main sur le symbole du tigre. Un grondement résonne et les yeux de la statue du "
                                                        +
                                                        "guerrier de gauche s'illuminent en rouge. Une série de fléchettes jaillit du mur, dont plusieurs "
                                                        +
                                                        "vous touchent avant que vous ne puissiez réagir.\n\n" +
                                                        "Les fléchettes n'étaient pas empoisonnées, mais elles vous ont blessé. Vous perdez 2 points d'ENDURANCE. "
                                                        +
                                                        "Après avoir récupéré, vous examinez à nouveau l'énigme.");

                        // Chapitre 15 - Bon choix (dragon)
                        Chapitre chapitre15 = new Chapitre(15, "Le Pouvoir Céleste",
                                        "Vous placez votre main sur le symbole du dragon. Un bourdonnement doux résonne et les yeux des deux "
                                                        +
                                                        "statues s'illuminent d'une lumière bleue apaisante. La porte du temple commence à s'ouvrir lentement, "
                                                        +
                                                        "révélant un long corridor illuminé par des lanternes suspendues.\n\n"
                                                        +
                                                        "Vous pénétrez dans le temple avec respect. Le corridor débouche sur une salle centrale circulaire "
                                                        +
                                                        "au plafond ouvert, laissant entrer la lumière du jour. Au centre, sur un piédestal de pierre, "
                                                        +
                                                        "repose un katana dans son fourreau orné.");

                        // Chapitre 16 - La rencontre finale avec équipement bonus
                        Chapitre chapitre16 = new Chapitre(16, "Le Maître du Temple et l'Épreuve",
                                        "Alors que vous vous approchez du katana, une voix sereine résonne dans la salle:\n\n"
                                                        +
                                                        "\"Ainsi, vous avez trouvé votre chemin jusqu'ici, chercheur.\"\n\n"
                                                        +
                                                        "Un vieil homme en tenue de moine émerge de l'ombre. Son visage est marqué par l'âge et la sagesse, "
                                                        +
                                                        "mais son regard est perçant et alerte.\n\n" +
                                                        "\"Je suis le gardien de la Lame du Destin. Avant que vous ne puissiez la réclamer, vous devez prouver "
                                                        +
                                                        "que vous êtes digne de la porter.\"\n\n" +
                                                        "Vous remarquez sur un autel à côté une amulette brillante. 'Prenez l'Amulette du Courage, elle vous aidera "
                                                        +
                                                        "pendant l'épreuve,' vous dit le moine. Vous la mettez autour de votre cou et sentez une vague de force vous envahir.");

                        // Créer un ennemi pour le maître (très faible car le joueur a l'amulette)
                        Enemy maitreTempleAffaibliEnemy = new Enemy("Maître d'Épreuve", 7, 12,
                                        "Le gardien du temple qui teste votre valeur. Il est puissant mais semble retenir ses coups, comme s'il cherchait à vous évaluer plus qu'à vous vaincre.");
                        chapitre16.setEnemy(maitreTempleAffaibliEnemy);

                        // Chapitre 17 - Victoire et fin du chapitre 2
                        Chapitre chapitre17 = new Chapitre(17, "L'Héritage du Samouraï",
                                        "Après un combat éprouvant, vous parvenez à désarmer le Maître du Temple. Loin d'être mécontent, "
                                                        +
                                                        "il sourit avec satisfaction.\n\n" +
                                                        "\"Vous avez prouvé votre valeur, non seulement par votre force mais par votre esprit. La Lame du Destin "
                                                        +
                                                        "vous appartient désormais.\"\n\n" +
                                                        "Le vieil homme vous guide vers le piédestal. Lorsque vous tirez le katana de son fourreau, la lame "
                                                        +
                                                        "brille d'un éclat surnaturel, comme si elle était forgée dans la lumière même.\n\n"
                                                        +
                                                        "\"Cette lame est la seule capable de vaincre le seigneur de guerre Akuma. Mais rappelez-vous : "
                                                        +
                                                        "le véritable pouvoir ne vient pas de l'arme, mais de celui qui la manie.\"\n\n"
                                                        +
                                                        "Vous remerciez le maître pour sa sagesse et quittez le temple, la légendaire Lame du Destin à la ceinture. "
                                                        +
                                                        "Votre quête n'est pas terminée – elle ne fait que commencer véritablement...\n\n"
                                                        +
                                                        "FÉLICITATIONS! Vous avez terminé le Chapitre 2!",
                                        true);

                        // Chapitre 18 - Défaite face au maître
                        Chapitre chapitre18 = new Chapitre(18, "Leçon d'Humilité",
                                        "Malgré tous vos efforts, le Maître du Temple est trop habile. Dans un mouvement fluide, il désarme "
                                                        +
                                                        "votre lame et vous met à genoux.\n\n" +
                                                        "\"Vous avez du potentiel, voyageur, mais vous n'êtes pas encore prêt à porter la Lame du Destin. "
                                                        +
                                                        "Revenez lorsque vous aurez approfondi votre maîtrise et purifié votre esprit.\"\n\n"
                                                        +
                                                        "La défaite est amère, mais instructive. Vous quittez le temple avec la détermination de vous améliorer "
                                                        +
                                                        "et de revenir un jour. Le seigneur de guerre Akuma reste une menace, et d'autres voies devront être "
                                                        +
                                                        "explorées pour l'arrêter...",
                                        true);

                        // Liens entre les chapitres
                        chapitre1.ajouterChoix(new Choix("Avancer en silence pour observer les traces", chapitre2));

                        chapitre2.ajouterChoix(new Choix("Affronter les soldats inexpérimentés", 3, true));
                        chapitre2.ajouterChoix(new Choix("Contourner le groupe et continuer", chapitre5));

                        chapitre3.ajouterChoix(new Choix("Examiner les corps des soldats et prendre leur équipement",
                                        chapitre4));
                        chapitre3.setChapitreDefaite(chapitre18); // En cas de défaite au combat

                        chapitre4.ajouterChoix(new Choix("Suivre la carte vers la croisée des chemins", chapitre5));

                        chapitre5.ajouterChoix(new Choix("Prendre le chemin principal à droite", chapitre6));
                        chapitre5.ajouterChoix(new Choix("Emprunter le passage secret à gauche", chapitre8));

                        // FIX: Ajouter le combat et correct path structure
                        chapitre6.ajouterChoix(new Choix("Affronter le garde blessé sur le pont", 7, true));
                        chapitre6.setChapitreDefaite(chapitre18); // En cas de défaite au combat

                        chapitre7.ajouterChoix(new Choix("S'approcher du temple", chapitre11));

                        chapitre8.ajouterChoix(new Choix("Avancer prudemment dans la forêt de bambous", chapitre9));

                        // FIX: Ajouter le combat et correct path structure
                        chapitre9.ajouterChoix(new Choix("Affronter l'esprit gardien", chapitre10.getId(), true));
                        chapitre9.setChapitreDefaite(chapitre18); // En cas de défaite au combat

                        chapitre10.ajouterChoix(new Choix("Continuer vers le temple", chapitre11));

                        chapitre11.ajouterChoix(new Choix("Examiner l'énigme des symboles", chapitre12));

                        chapitre12.ajouterChoix(new Choix("Toucher le symbole de la tortue", chapitre13));
                        chapitre12.ajouterChoix(new Choix("Toucher le symbole du dragon", chapitre15));
                        chapitre12.ajouterChoix(new Choix("Toucher le symbole du tigre", chapitre14));

                        chapitre13.ajouterChoix(new Choix("Remonter et essayer à nouveau", chapitre12));

                        chapitre14.ajouterChoix(new Choix("Examiner à nouveau l'énigme", chapitre12));

                        chapitre15.ajouterChoix(new Choix("S'approcher du katana", chapitre16));

                        // FIX: Ajouter le combat et correct path structure
                        chapitre16.ajouterChoix(new Choix("Affronter le Maître du Temple", chapitre17.getId(), true));
                        chapitre16.setChapitreDefaite(chapitre18); // En cas de défaite au combat

                        chapitre17.ajouterChoix(new Choix("Fin du chapitre 2", chapitre17));

                        chapitre18.ajouterChoix(new Choix("Recommencer", chapitre1));

                        // Ajout des chapitres au scénario
                        scenario.ajouterChapitre(chapitre1);
                        scenario.ajouterChapitre(chapitre2);
                        scenario.ajouterChapitre(chapitre3);
                        scenario.ajouterChapitre(chapitre4);
                        scenario.ajouterChapitre(chapitre5);
                        scenario.ajouterChapitre(chapitre6);
                        scenario.ajouterChapitre(chapitre7);
                        scenario.ajouterChapitre(chapitre8);
                        scenario.ajouterChapitre(chapitre9);
                        scenario.ajouterChapitre(chapitre10);
                        scenario.ajouterChapitre(chapitre11);
                        scenario.ajouterChapitre(chapitre12);
                        scenario.ajouterChapitre(chapitre13);
                        scenario.ajouterChapitre(chapitre14);
                        scenario.ajouterChapitre(chapitre15);
                        scenario.ajouterChapitre(chapitre16);
                        scenario.ajouterChapitre(chapitre17);
                        scenario.ajouterChapitre(chapitre18);

                        System.out.println("Scénario du chapitre 2 créé avec succès.");
                        return scenario;
                } catch (Exception e) {
                        System.err.println("ERREUR lors de la création du scénario du chapitre 2: " + e.getMessage());
                        e.printStackTrace();
                        // Return a simple fallback scenario in case of error
                        return createFallbackScenario();
                }
        }

        /**
         * Crée un scénario de secours simple en cas d'erreur
         */
        private static Scenario createFallbackScenario() {
                Scenario fallback = new Scenario("L'Épée du Samouraï - Chapitre 2 (Mode secours)",
                                "Une version simplifiée du chapitre 2 chargée en mode de secours.");

                Chapitre introChapter = new Chapitre(1, "Continuation de l'aventure",
                                "Après votre victoire précédente, vous continuez votre quête. " +
                                                "Le chemin vers le temple de la montagne s'annonce périlleux mais votre détermination est sans faille.");

                Chapitre endChapter = new Chapitre(2, "Fin temporaire",
                                "À suivre dans la prochaine mise à jour du jeu...", true);

                introChapter.ajouterChoix(new Choix("Continuer", endChapter));

                fallback.ajouterChapitre(introChapter);
                fallback.ajouterChapitre(endChapter);

                return fallback;
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
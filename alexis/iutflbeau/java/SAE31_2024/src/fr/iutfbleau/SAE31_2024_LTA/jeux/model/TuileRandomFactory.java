package fr.iutfbleau.SAE31_2024_LTA.jeux.model;

import java.util.Random;
/**
 * La classe TuileRandomFactory permet de générer une compositions aléatoires basée sur un index de biome et une graine fournie.
 */
public class TuileRandomFactory {
    private int[] composition = new int[6];
    private int Indexcouleur1;
    private int Indexcouleur2;
    private int soundIndex;
    /**
     * Constructeur de TuileRandomFactory qui initialise la composition de la tuile en
     * fonction de la graine fournie.
     *
     * @param seed La graine pour générer des éléments en pseudo-aléatoires.
     */
    public TuileRandomFactory(int seed) {
        int[] indexBiome = new int[5];
        indexBiome[0] = 0; //Mer
        indexBiome[1] = 1; //Montagne
        indexBiome[2] = 2; //Champ
        indexBiome[3] = 3; //Plaine
        indexBiome[4] = 4; //Foret

        Random random = new Random();
        random.setSeed(seed);

        int indexCouleur1 = indexBiome[random.nextInt(indexBiome.length)];
        int indexCouleur2 = indexBiome[random.nextInt(indexBiome.length)];

        if (indexCouleur1 == indexCouleur2){
            random = new Random();
            random.setSeed(seed+2);
            indexCouleur2 = indexBiome[random.nextInt(indexBiome.length)];
        }

        int territory = random.nextInt(composition.length-1);
        int taille1 = random.nextInt(composition.length+1);
        int decalage = random.nextInt(composition.length);
        int taille2 = 6 - taille1;

        for (int i = 0; i < taille1; i++) {
            composition[decalage] = indexCouleur1;
            decalage = (decalage + 1) % 6;
        }
        for (int i = 0; i < taille2; i++) {
            composition[decalage] = indexCouleur2;
            decalage = (decalage + 1) % 6;
        }
        if (taille1!=6 && taille2!=6){
            this.Indexcouleur1 = indexCouleur1;
            this.Indexcouleur2 = indexCouleur2;
        }
        else {
            if (taille1==6){
                this.Indexcouleur1 = indexCouleur1;
                this.Indexcouleur2 = indexCouleur1;
            }
            else {
                this.Indexcouleur1 = indexCouleur2;
                this.Indexcouleur2 = indexCouleur2;
            }
        }
        if (taille1 > 3)
            soundIndex = indexCouleur1;
        else
            soundIndex = indexCouleur2;
    }

    /**
     * Récupère la composition de la tuile.
     *
     * @return int[]
     */
    public int[] getComposition() {
        return composition;
    }

    /**
     * Récupère l'index de la première couleur.
     */
    public int getIndexcouleur1() {
        return Indexcouleur1;
    }
    /**
     * Récupère l'index de la deuxième couleur.
     */
    public int getIndexcouleur2() {
        return Indexcouleur2;
    }
    /**
     * Récupère l'index du son associé à la composition de la tuile.
     */
    public int getSoundIndex() {
        return soundIndex;
    }
}

package fr.iutfbleau.SAE31_2024_LTA;

import fr.iutfbleau.SAE31_2024_LTA.loading.LoadingFrame;
import fr.iutfbleau.SAE31_2024_LTA.loading.ModelLoader;
import fr.iutfbleau.SAE31_2024_LTA.loading.WindowStateHandler;

/**
 * Classe principale qui gère le lancement de l'application.
 * elle s'occupe de l'initialisation du modèle principal de l'écran de chargement.
 */
public class ControlerMain {

    private static volatile boolean isLoading = true;
    private static ModelPrincipale modelPrincipale;

    public static void main(String[] args) {
        LoadingFrame loadingFrame = new LoadingFrame();
        new Thread(new ModelLoader()).start();

        while (isLoading) {
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        loadingFrame.dispose();
        modelPrincipale.getVuePrincipale().addWindowListener(new WindowStateHandler());
    }

    /**
     * méthode qui définit le modèle principal.
     *
     * @param model l'instance du modèle principal à définir.
     */
    public static synchronized void setModelPrincipale(ModelPrincipale model) {
        modelPrincipale = model;
    }

    /**
     * méthode qui renvoie l'instance du modèle principal.
     *
     * @return l'instance du modèle principal.
     */
    public static synchronized ModelPrincipale getModelPrincipale() {
        return modelPrincipale;
    }


    /**
     * méthode qui définit l'état de chargement.
     *
     * @param loading vrai si le chargement est en cours, faux sinon.
     */
    public static synchronized void setIsLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * méthode qui vérifie si l'application est en cours de chargement.
     *
     * @return vrai si le chargement est en cours, faux sinon.
     */
    public static synchronized boolean isIsLoading() {
        return isLoading;
    }
}

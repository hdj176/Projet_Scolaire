package fr.iutfbleau.SAE31_2024_LTA.loading;

import fr.iutfbleau.SAE31_2024_LTA.ControlerMain;
import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

/**
 * La classe ModelLoader implémente Runnable et est utilisée pour charger le modèle principal.
 * Elle exécute le chargement du modèle dans un thread séparé.
 */
public class ModelLoader implements Runnable {

    /**
     * La méthode run est la méthode d'entrée du thread.
     * Elle crée une nouvelle instance de ModelPrincipale et la définit dans ControlerMain.
     * Elle indique également que le processus de chargement est terminé.
     */
    @Override
    public void run() {
        ControlerMain.setModelPrincipale(new ModelPrincipale());
        ControlerMain.setIsLoading(false);
    }
}

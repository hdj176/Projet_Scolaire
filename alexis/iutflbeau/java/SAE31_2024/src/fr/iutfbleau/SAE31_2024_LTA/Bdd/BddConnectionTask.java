package fr.iutfbleau.SAE31_2024_LTA.Bdd;

import fr.iutfbleau.SAE31_2024_LTA.popup.PopupBd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Représente une tâche de connexion à la base de données pour le modèle {@link ModelBDD}.
 * Cette tâche est exécutée dans un thread séparé pour éviter de bloquer le thread principal de l'application.
 * Elle affiche un popup pendant la tentative de connexion et un autre message en fonction de l'état de connexion.
 *
 * <p>Exemple d'utilisation :
 * <pre>{@code
 * ModelBDD modelBDD = new ModelBDD(vuePrincipale);
 * BddConnectionTask connectionTask = new BddConnectionTask(modelBDD);
 * new Thread(connectionTask).start();
 * }</pre>
 */
public class BddConnectionTask implements Runnable {
    private final ModelBDD modelBDD;

    /**
     * Crée une instance de {@code BddConnectionTask} associée au modèle {@code ModelBDD}.
     *
     * @param modelBDD l'instance du modèle à laquelle la tâche de connexion sera associée
     */
    BddConnectionTask(ModelBDD modelBDD) {
        this.modelBDD = modelBDD;
    }

    /**
     * Méthode exécutée lorsque la tâche est lancée dans un thread séparé.
     * Tente de se connecter à la base de données, met à jour l'état de connexion dans {@code modelBDD},
     * et affiche des popups de statut pour informer l'utilisateur du processus de connexion.
     */
    @Override
    public void run() {
        String message = "Connexion bd...";
        PopupBd popupBd = null;
        Connection db = null;
        try {
            modelBDD.setInConnexion(true);
            popupBd = new PopupBd(message, true);


        } catch (SQLException e) {
            modelBDD.setConnected(false); // Marque la connexion comme échouée
            System.err.println("Erreur de connexion BDD");
        }

        modelBDD.setDb(db);

        // Met à jour l'état de connexion dans le modèle
        if (db != null) {
            modelBDD.setConnected(true);
        }
        modelBDD.removePopup(popupBd);
        modelBDD.setInConnexion(false);
        if (modelBDD.isConnected()) {
            message = "Connexion à la base de donnée réussi";
        } else {
            message = "Connexion à la base de donnée échouer";
        }

        popupBd = new PopupBd(message, modelBDD.isConnected());
        modelBDD.addPopup(popupBd);

        // Pause de 3 secondes pour laisser le popup visible
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        modelBDD.removePopup(popupBd);
    }
}
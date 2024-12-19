package fr.iutfbleau.SAE31_2024_LTA.Bdd;

import fr.iutfbleau.SAE31_2024_LTA.layers.VuePrincipale;
import fr.iutfbleau.SAE31_2024_LTA.popup.PopupBd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle de gestion de la base de données pour l'application.
 * Cette classe gère les interactions avec la base de données,
 * notamment la connexion et la récupération des informations de jeu.
 */
public class ModelBDD {

    private Connection db;
    private List<BddListeTuiles> listeTuiles;
    private List<BddPartieJouer> partieJouees;
    private final VuePrincipale vuePrincipale;
    private boolean connected = false;
    private boolean inConnexion = false;
    private boolean gameSaved = false;

    /**
     * Constructeur qui initialise le modèle de base de données.
     * Il établit également la connexion initiale à la base de données.
     *
     * @param vuePrincipale la vue principale de l'application
     */
    public ModelBDD(VuePrincipale vuePrincipale) {
        this.vuePrincipale = vuePrincipale;
        connexionBdd();
    }

    /**
     * Démarre une tâche en arrière-plan pour établir la connexion avec la base de données.
     */
    public void connexionBdd() {
        new Thread(new BddConnectionTask(this)).start();
    }

    /**
     * Vérifie et met à jour la connexion à la base de données si elle est fermée.
     *
     * @return true si la connexion est établie, sinon false
     */
    public boolean updateBdd(){
        if (!connected) {
            connexionBdd();
        }
        return db != null;
    }

    /**
     * Récupère toutes les entrées de la table "ListeTuiles" dans la base de données.
     *
     * @return une liste d'objets BddListeTuiles contenant les informations de chaque tuile
     */
    public List<BddListeTuiles> getAllListe() {
        if (db!=null) {
            List<BddListeTuiles> seeds = new ArrayList<>();
            String query = "SELECT id, seed, BestScore FROM ListeTuiles";

            try (Statement stmt = db.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int seed = rs.getInt("seed");
                    Integer bestScore = rs.getObject("BestScore", Integer.class);

                    seeds.add(new BddListeTuiles(id, seed, bestScore));
                }

            } catch (SQLException e) {
                System.err.println("Erreur de preparation SQL methode getAllListe() - " + e.getMessage());
            }
            return seeds;
        }
        return null;
    }

    /**
     * Récupère toutes les parties jouées de la table "PartieJouer" dans la base de données.
     *
     * @return une liste d'objets BddPartieJouer contenant les informations de chaque partie
     */
    public List<BddPartieJouer> getAllPartieJouer() {
        if (db!=null) {
            List<BddPartieJouer> parties = new ArrayList<>();
            String query = "SELECT id, PlayerName, Score, ListeTuile FROM PartieJouer";

            try (Statement stmt = db.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String playerName = rs.getString("PlayerName");
                    int score = rs.getInt("Score");
                    int listeTuileId = rs.getInt("ListeTuile");

                    BddListeTuiles listeTuile = getListeTuileById(listeTuileId); //comme sa on refait la FK dans la table

                    parties.add(new BddPartieJouer(id, playerName, score, listeTuile));
                }

            } catch (SQLException e) {
                System.err.println("Erreur de preparation SQL methode getAllPartieJouer() - " + e.getMessage());
            }
            return parties;
        }
        return null;
    }

    /**
     * Récupère un objet BddListeTuiles basé sur son ID.
     *
     * @param id l'identifiant unique de la tuile
     * @return un objet BddListeTuiles correspondant à l'ID donné, ou null si non trouvé
     * @throws SQLException en cas d'erreur d'exécution de la requête
     */
    public BddListeTuiles getListeTuileById(int id) throws SQLException {
        if (db!=null) {
            if (inConnexion){
                while (inConnexion){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            PreparedStatement ps = db.prepareStatement("SELECT * FROM ListeTuiles WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            BddListeTuiles listeTuile = null;
            if (rs.next()) {
                int seed = rs.getInt("seed");
                int bestScore = rs.getInt("BestScore");
                listeTuile = new BddListeTuiles(id, seed, bestScore);
            }

            rs.close();
            ps.close();

            return listeTuile;
        }
        return null;
    }

    /**
     * Récupère le meilleur score associé à un seed donné.
     *
     * @param seed le seed pour lequel le meilleur score est recherché
     * @return le meilleur score pour le seed spécifié, ou null si non trouvé
     */
    public Integer getBestScoreSeed(int seed) {
        if (db == null) {
            return null;
        }
        List<BddListeTuiles> listeTuiles = getAllListe();
        for (BddListeTuiles tuile : listeTuiles) {

            if (tuile.getSeed() == seed) {
                return tuile.getBestScore();
            }
        }
        return null;
    }

    /**
     * Sauvegarde une partie jouée dans la base de données.
     *
     * @param playerName le nom du joueur
     * @param score le score obtenu dans la partie
     * @param listeTuileId l'identifiant de la liste de tuiles utilisée
     * @return true si la partie a été sauvegardée avec succès, sinon false
     * @throws SQLException en cas d'erreur SQL
     */
    public boolean saveGame(String playerName, int score, int listeTuileId) throws SQLException {
        if (gameSaved) {
            return true;
        }
        if (db == null) {
            return false;
        }

        PreparedStatement ps = db.prepareStatement("INSERT INTO tanchou.PartieJouer (PlayerName, Score, ListeTuile) VALUES (?, ?, ?)");
        ps.setString(1, playerName);
        ps.setInt(2, score);
        ps.setInt(3, listeTuileId);
        ps.executeQuery();
        gameSaved = true;
        ps.close();
        db.close();
        db = null;
        connected = false;
        return true;
    }

    /**
     * Vérifie si une connexion est en cours.
     *
     * @return true si une connexion est en cours, sinon false
     */
    public boolean isInConnexion() {
        return inConnexion;
    }

    /**
     * Définit si une connexion est en cours.
     *
     * @param inConnexion un booléen indiquant si une connexion est en cours
     */
    public void setInConnexion(boolean inConnexion) {
        this.inConnexion = inConnexion;
    }

    /**
     * Définit si une partie est sauvegardée.
     *
     * @param b un booléen indiquant si une partie est sauvegardée
     */
    public void setGameSaved(boolean b) {
        gameSaved = b;
    }

    /**
     * Vérifie si la connexion à la base de données est établie.
     *
     * @return true si connecté, sinon false
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Définit l'état de connexion de la base de données.
     *
     * @param connected un booléen indiquant si la base de données est connectée
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Définit la connexion de la base de données.
     *
     * @param db l'objet Connection de la base de données
     */
    public void setDb(Connection db) {
        this.db = db;
    }

    /**
     * Supprime une fenêtre contextuelle de la vue principale.
     *
     * @param popupBd l'objet PopupBd à retirer
     */
    public void removePopup(PopupBd popupBd) {
        vuePrincipale.getPrincipaleLayeredPane().remove(popupBd);
        vuePrincipale.getPrincipaleLayeredPane().repaint();
    }

    /**
     * Ajoute une fenêtre contextuelle à la vue principale.
     *
     * @param popupBd l'objet PopupBd à ajouter
     */
    public void addPopup(PopupBd popupBd) {
        vuePrincipale.getPrincipaleLayeredPane().add(popupBd, Integer.valueOf(4));
    }
}
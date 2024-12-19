package fr.iutfbleau.SAE31_2024_LTA.media;

import fr.iutfbleau.SAE31_2024_LTA.config.ConfigManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Cette classe gère le chargement des fichiers audio nécessaires au jeu.
 * Elle permet de charger des clips audio pour la musique de menu, les sons de clic et les sons associés aux tuiles dans le jeu.
 *
 */
public class ModelMediaLoader {

    private final Clip menuMusicClip;
    private final Clip clicAudioClip;
    private final Clip[] clipsTuiles = new Clip[5];

    private final List<Clip> gameMusicClips;


    /**
     * Constructeur de la classe ModelMediaLoader
     * Charge tous les sons nécessaires pour le jeu, y compris la musique de menu,
     * le son de clic et les sons associés aux tuiles.
     */
    public ModelMediaLoader() {//Charge touts les son du jeu

        menuMusicClip = loadMedia("/Audio/MenuSoundTrack.wav");
        clicAudioClip = loadMedia("/Audio/buttonClic.wav");

        String[] pathTuileSound = new String[5];
        pathTuileSound[0] = "/Audio/TuileSound/eau.wav";
        pathTuileSound[1] = "/Audio/TuileSound/montagne.wav";
        pathTuileSound[2] = "/Audio/TuileSound/champ.wav";
        pathTuileSound[3] = "/Audio/TuileSound/plaine.wav";
        pathTuileSound[4] = "/Audio/TuileSound/foret.wav";

        for (int i = 0; i < pathTuileSound.length; i++) {
            clipsTuiles[i] = loadMedia(pathTuileSound[i]);
        }

        MusiqueTrack musiqueTrack = new MusiqueTrack("/Audio/MusicInGame");
        gameMusicClips = musiqueTrack.getMusicClips();
    }

    /**
     * Charge un fichier audio et retourne un Clip.
     *
     * @param path Le chemin vers le fichier audio.
     * @return Un Clip chargé avec l'audio.
     */
    private Clip loadMedia(String path) {
        Clip clip = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;

        try {
            InputStream audioStream = getClass().getResourceAsStream(path);
            if (audioStream == null) {
                throw new IllegalArgumentException("Le fichier audio " + path + " est introuvable.");
            }

            while ((nRead = audioStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.toByteArray());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de " + path + " : " + e.getMessage());
            System.exit(1);
        }

        return clip;
    }
    /**
     * @return Le clip de musique du menu.
     */
    public Clip getMenuMusicClip() {
        return menuMusicClip;
    }

    /**
     *  @return Le clip de son de clic.
     */
    public Clip getClicAudioClip() {
        return clicAudioClip;
    }
    /**
     * @return Une liste de clips pour les musiques du jeu.
     */
    public List<Clip> getGameMusicClips() {
        return gameMusicClips;
    }
    /**
     * @return Un tableau de clips pour les sons des tuiles.
     */
    public Clip[] getClipsTuiles() {
        return clipsTuiles;
    }
}

package fr.iutfbleau.SAE31_2024_LTA.media;

import fr.iutfbleau.SAE31_2024_LTA.ModelPrincipale;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.util.List;
/**
 * Cette classe gère la lecture des clips audio dans l'application.
 * Elle permet de démarrer, arrêter, et ajuster le volume des clips audio.
 */
public class MediaPlayerManager {
    ModelPrincipale modelPrincipale;
    /**
     * Constructeur de la classe  MediaPlayerManager.
     *
     * @param modelPrincipale Le modèle principal de l'application qui sera utilisé pour accéder aux ressources nécessaires.
     */
    public MediaPlayerManager(ModelPrincipale modelPrincipale) {
        this.modelPrincipale = modelPrincipale;
    }
    /**
     * Démarre la lecture d'un clip audio.
     *
     * @param clip             Le clip à démarrer.
     * @param loopIndefinitely Indique si le clip doit être joué en boucle indéfiniment.
     */
    public void startClip(Clip clip, boolean loopIndefinitely) {
        if (clip != null) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                if (loopIndefinitely) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.start();
                }
            }
        }
    }
    /**
     * Démarre la lecture d'un clip audio à partir d'une liste de clips.
     *
     * @param musicClips      La liste des clips audio.
     * @param currentClipIndex L'index du clip à démarrer.
     */
    public void startClip(List<Clip> musicClips, int currentClipIndex) {//Démmarre une liste de clip
        if (musicClips.isEmpty()) {
            System.out.println("Aucune musique n'a été chargée.");
            return;
        }

        Clip currentClip = musicClips.get(currentClipIndex);

        if (currentClip != null) {
            currentClip.setFramePosition(0);
            currentClip.start();

            currentClip.addLineListener(new ClipStopListener(this, currentClip, currentClipIndex, musicClips));
        }
    }

    /**
     * Arrête la lecture d'un clip audio.
     *
     * @param clip Le clip à arrêter.
     */
    public void stopClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void stopClip(List<Clip> musicClips) {
        if (!musicClips.isEmpty()) {
            for (Clip clip : musicClips) {
                if (clip.isRunning()) {
                    stopClip(clip);
                }
            }
        }
    }

    /**
     * Change le volume d'un clip audio.
     *
     * @param clip  Le clip dont on veut changer le volume.
     * @param level Le niveau de volume entre 0 (muet) et 1 (volume maximum). Attention sa déscend tres vite
     */
    private void setClipVolume(Clip clip, float level) {
        if (clip != null) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float min = volumeControl.getMinimum();
                float max = volumeControl.getMaximum();
                float newVolume = min + (max - min) * level;

                volumeControl.setValue(newVolume);

            }
        }
    }
    /**
     * Ajuste le volume des effets sonores.
     *
     * @param levelP Le niveau de volume en pourcentage (0-100).
     */
    public void setVolumeEffect(int levelP) {
        float level = levelP / 100.0f;
        for (int i = 0; i < modelPrincipale.getModelMediaLoader().getClipsTuiles().length; i++) {
            setClipVolume(modelPrincipale.getModelMediaLoader().getClipsTuiles()[i], level);
        }
        setClipVolume(modelPrincipale.getModelMediaLoader().getClicAudioClip(), level);
        //modelPrincipale.getModelMediaLoader().setVolumeEffect(levelP);
        modelPrincipale.getConfigManager().setVolumeEffet(levelP);
    }
    /**
     * Ajuste le volume de la musique.
     *
     * @param levelP Le niveau de volume en pourcentage (0-100).
     */
    public void setVolumeMusique(int levelP) {
        float level = levelP / 100.0f;
        for (int i = 0; i < modelPrincipale.getModelMediaLoader().getGameMusicClips().size(); i++) {
            setClipVolume(modelPrincipale.getModelMediaLoader().getGameMusicClips().get(i), level);
        }
        setClipVolume(modelPrincipale.getModelMediaLoader().getMenuMusicClip(), level);
        //modelPrincipale.getModelMediaLoader().setVolumeMusic(levelP);
        modelPrincipale.getConfigManager().setVolumeMusique(levelP);
    }
    /**
     * Obtient le volume actuel d'un clip audio.
     *
     * @param clip Le clip dont on veut obtenir le volume.
     * @return Le niveau de volume du clip, ou 0 si le clip n'est pas valide ou n'a pas de contrôle de volume.
     */
    public float getClipVolume(Clip clip) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            return volumeControl.getValue();
        }
        return 0;
    }
}

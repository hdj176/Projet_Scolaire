package fr.iutfbleau.SAE31_2024_LTA.media;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.List;

/**
 * Cette classe implémente un écouteur pour les événements de ligne audio.
 * Elle est utilisée pour gérer le comportement d'un clip audio,
 * lorsqu'ils'arrête. Lorsqu'un clip audio s'arrête,
 * cette classe doit démarrer le clip audio suivant dans la liste des clips.
 */
public class ClipStopListener implements LineListener {

    private final MediaPlayerManager mediaPlayerManager;
    private final Clip currentClip;
    private final int currentClipIndex;
    private final List<Clip> musicClips;
    /**
     * Constructeur de la classe ClipStopListener.
     *
     * @param mediaPlayerManager Le gestionnaire de lecture de médias.
     * @param currentClip Le clip audio actuel à écouter.
     * @param currentClipIndex L'index du clip audio actuel dans la liste.
     * @param musicClips La liste des clips audio de musique à lire.
     */
    public ClipStopListener(MediaPlayerManager mediaPlayerManager, Clip currentClip, int currentClipIndex, List<Clip> musicClips) {
        this.mediaPlayerManager = mediaPlayerManager;
        this.currentClip = currentClip;
        this.currentClipIndex = currentClipIndex;
        this.musicClips = musicClips;
    }
    /**
     * Méthode appelée lorsque l'état d'une ligne audio change.
     *
     * @param event L'événement de ligne audio contenant des informations sur le changement d'état.
     */
    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            currentClip.stop();
            int nextClipIndex = (currentClipIndex + 1) % musicClips.size();
            mediaPlayerManager.startClip(musicClips, nextClipIndex);
        }
    }
}

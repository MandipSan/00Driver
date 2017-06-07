package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import java.io.IOException;
import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 6/6/2017.
 */

public class Music {
    //  PURPOSE:    Holds the music  that are playing
    private MediaPlayer mediaPlayer;
    //  PURPOSE:    Holds the loaded music id
    private int [] musicArr;
    //  PURPOSE:    Holds the Id for the sound/music that is playing
    private int curTrackID;
    //  PURPOSE:    Holds the music volume
    private float volume;
    //  PURPOSE:    Holds the music volume level before mute
    private float muteVolume;

    /** PURPOSE:    Constructor for the music that set the default value for the object
     *  INPUT:      context             - The context for the app
     *  OUTPUT:     NONE
     */
    public Music(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.gamemusic);
        volume = 1f;
        mediaPlayer.setVolume(volume,volume);
        curTrackID = -1;
    }

    /** PURPOSE:    Plays the music whose position is given
     *  INPUT:      track         - The position of the music track to play(Currently not used)
     *  OUTPUT:     NONE
     */
    public void playMusic(int track){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    /** PURPOSE:    Changes the volume for the music
     *  INPUT:      volumeLevel         - The volume level to set between 1 and 0
     *  OUTPUT:     NONE
     */
    public void changeVolume(float volumeLevel){
        if(volumeLevel <= 1 && volumeLevel >= 0){
            volume = volumeLevel;

        }
    }

    /** PURPOSE:    Changes the volume to mute
     *  INPUT:      activateMute        - Holds whether or not to activate mute
     *  OUTPUT:     NONE
     */
    public void mute(boolean activateMute){
        if(activateMute) {
            muteVolume = volume;
            volume = 0f;
        }else{
            volume = muteVolume;
        }
        mediaPlayer.setVolume(volume,volume);
    }

    /** PURPOSE:    Release from memory all the load music
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void releaseMusic(){
        mediaPlayer.release();
    }

    /** PURPOSE:    Stops the music
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void stopMusic(){
        if(mediaPlayer.isPlaying())mediaPlayer.stop();
    }

    /** PURPOSE:    Pauses all active music
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void pauseAllMusic(){
        mediaPlayer.pause();
    }

    /** PURPOSE:    Resume all pause music
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void resumeAllMusic(){
        mediaPlayer.start();
    }

    /** PURPOSE:    Returns the current volume level
     *  INPUT:      NONE
     *  OUTPUT:     Returns a float of the volume level
     */
    public float getVolumeLevel(){
        return volume;
    }
}

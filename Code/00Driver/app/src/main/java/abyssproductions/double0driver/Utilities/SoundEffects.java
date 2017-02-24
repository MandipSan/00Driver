package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import abyssproductions.double0driver.R;

/**
 * Created by Mandip Sangha on 2/22/2017.
 */

public class SoundEffects {
    //  PURPOSE:    Holds the sound effects that are playing
    private SoundPool soundPool;
    //  PURPOSE:    Holds the loaded sound effects id
    private int [] soundEffectsArr;
    //  PURPOSE:    Holds the sound effects volume
    private float volume;

    /** PURPOSE:    Constructor for the GameEngine that set the default value for the object
     *  INPUT:      context             - The context for the app
     *  OUTPUT:     NONE
     */
    public SoundEffects(Context context){
        //AudioAttributes attrs = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
        //        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        //soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attrs).build();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundEffectsArr = new int [8];
        soundEffectsArr[0] = soundPool.load(context, R.raw.test,1);
        volume = 0.5f;
        //TODO:add sound effects to the array
    }

    /** PURPOSE:    Plays the sound effect whose id is given
     *  INPUT:      SoundEffect         - The id of the sound effect to play
     *  OUTPUT:     NONE
     */
    public void  playSoundEffect(int SoundEffect){
        if(SoundEffect>= 0 && SoundEffect < soundEffectsArr.length){
            soundPool.play(soundEffectsArr[SoundEffect],volume,volume,1,0,1);
        }
    }

    /** PURPOSE:    Changes the volume for the sound effects
     *  INPUT:      volumeLevel         - The volume level to set between 1 and 0
     *  OUTPUT:     NONE
     */
    public void changeVolume(float volumeLevel){
        if(volumeLevel <= 1 && volumeLevel >= 0){
            volume = volumeLevel;

        }
    }

    /** PURPOSE:    Release from memory all the load sound effects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void releaseSoundPool(){
        soundPool.release();
        soundPool = null;
    }
}

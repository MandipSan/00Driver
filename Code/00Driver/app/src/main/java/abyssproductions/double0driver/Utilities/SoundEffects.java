package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import abyssproductions.double0driver.GameGlobals;
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
    //  PURPOSE:    Holds the sound effect volume level before mute
    private float muteVolume;

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
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SEExplosionID)] = soundPool.load(context, R.raw.seexplosion,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SEMachineGunID)] = soundPool.load(context, R.raw.semachinegun,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SELaserID)] = soundPool.load(context, R.raw.selaser,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SEFireID)] = soundPool.load(context, R.raw.seflame,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SEMissileID)] = soundPool.load(context, R.raw.semissile,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SEAmbulanceID)] = soundPool.load(context, R.raw.seambulance,1);
        soundEffectsArr[GameGlobals.getInstance().getImageResources().
                getInteger(R.integer.SESpikeStripID)] = soundPool.load(context, R.raw.sespikechains,1);
        volume = 0.5f;
        //TODO:add sound effects to the array
    }

    /** PURPOSE:    Plays the sound effect whose position is given
     *  INPUT:      soundEffect         - The position of the sound effect to play
     *              repeat              - Holds whether to loop sound effect or not
     *                                      (0 for not -1 for)
     *  OUTPUT:     Returns the sound id not sound effect id
     */
    public int playSoundEffect(int soundEffect, int repeat){
        if(soundEffect>= 0 && soundEffect < soundEffectsArr.length){
            return soundPool.play(soundEffectsArr[soundEffect],volume,volume,1,repeat,1);
        }
        return 0;
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
    }

    /** PURPOSE:    Release from memory all the load sound effects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void releaseSoundPool(){
        soundPool.release();
        soundPool = null;
    }

    /** PURPOSE:    Stops the sound effect by sound id
     *  INPUT:      soundId             - The id for the sound effect
     *  OUTPUT:     NONE
     */
    public void stopSoundEffect(int soundId){
        soundPool.stop(soundId);
    }

    /** PURPOSE:    Pauses all active sound effects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void pauseAllSoundEffect(){
        soundPool.autoPause();
    }

    /** PURPOSE:    Resume all pause sound effects
     *  INPUT:      NONE
     *  OUTPUT:     NONE
     */
    public void resumeAllSoundEffect(){
        soundPool.autoResume();
    }

    /** PURPOSE:    Returns the current volume level
     *  INPUT:      NONE
     *  OUTPUT:     Returns a float of the volume level
     */
    public float getVolumeLevel(){
        return volume;
    }
}

package abyssproductions.double0driver.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Mandip Sangha on 2/22/2017.
 */

public class SoundEffects {
    private SoundPool soundPool;
    private int [] soundEffectsArr;
    private float volume;

    public SoundEffects(Context context){
        //AudioAttributes attrs = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
        //        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        //soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attrs).build();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundEffectsArr = new int [8];
        volume = 0.5f;
        //TODO:add sound effects to the array
    }

    public void  playSoundEffect(int SoundEffect){
        if(SoundEffect> 0 && SoundEffect < soundEffectsArr.length){
            soundPool.play(soundEffectsArr[SoundEffect],volume,volume,1,0,1);
        }
    }

    public void changeVolume(float volumeLevel){
        if(volumeLevel < 1 && volumeLevel > 0){
            volume = volumeLevel;
            
        }
    }

    public void releaseSoundPool(){
        soundPool.release();
    }
}

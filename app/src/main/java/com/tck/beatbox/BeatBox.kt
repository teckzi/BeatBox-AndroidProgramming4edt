package com.tck.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException


private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUND = 1

var rate = 1.0f

class BeatBox(private val assets:AssetManager) {

    val sounds:List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUND)
        .build()
    init {
        sounds = loadSounds()
    }

    fun release(){
        soundPool.release()
    }

    private fun loadSounds():List<Sound>{
        val soundNames:Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
            Log.d(TAG,"$soundNames")
        } catch (e:Exception){
            Log.e(TAG, "Could not list assets",e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()

        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try{
                load(sound)
                sounds.add(sound)
            }catch (e:IOException){
                Log.e(TAG, "Couldn't load sound $filename", e)
            }
        }

        return sounds
    }

    private fun load(sound: Sound){
        val afd:AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd,1)
        sound.soundId = soundId
    }

    fun play(sound: Sound){
        sound.soundId?.let {
            /* проигрывание звука (идентификатор звука, громкость слева, громкость справа, приоритет (игнорируется),
                 признак циклического воспроизведения(-1 для циклического воспроизведение) и скорость воспроизведения.) */
            soundPool.play(it,1.0f, 1.0f,1,0,rate)
        }
         //soundPool.play(sound.soundId!!,1.0f,1.0f,1,0, rate)

    }
}
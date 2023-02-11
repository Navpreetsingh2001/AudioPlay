package com.example.clappapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import com.example.clappapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    private  var mediaPlayer :MediaPlayer? =null
    private lateinit var seekBar:SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        seekBar=binding.sbClapping
        handler = Handler(Looper.getMainLooper())

        binding.fabPlay.setOnClickListener {
            if (mediaPlayer == null){
                mediaPlayer =MediaPlayer.create(this,R.raw.clapps)
                initializeSeekbar()

            }
            mediaPlayer?.start()
        }

        binding.fabPause.setOnClickListener {
            mediaPlayer?.pause()

        }
        binding.fabStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer=null
           handler.removeCallbacks(runnable)
          seekBar.progress=0
        }

    }

    private fun initializeSeekbar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //connecting mediaplayer with seekbar
                if (fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        seekBar.max =mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress =mediaPlayer!!.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}
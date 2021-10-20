package com.tck.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tck.beatbox.databinding.ActivityMainBinding
import com.tck.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var beatBoxViewModel : BeatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val factoryModel =  BeatBoxFactoryModel(assets)
        beatBoxViewModel = ViewModelProvider(this,factoryModel).get(BeatBoxViewModel::class.java)

        beatBox = BeatBox(assets)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = progress.toFloat()
                rate = value

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private inner class SoundHolder(private val binding
    :ListItemSoundBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.viewModel = SoundViewModel(beatBox)
        }

        fun bind(sound:Sound){
            binding.apply {
                viewModel?.sound = sound
                //команда быстрого обновления макета
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds:List<Sound>):RecyclerView.Adapter<SoundHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(layoutInflater,R.layout.list_item_sound,parent,false)
            binding.lifecycleOwner = this@MainActivity
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size

    }

}
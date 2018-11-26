package com.rikharthu.itunestopcharts.play

import android.graphics.Bitmap
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rikharthu.itunestopcharts.App
import com.rikharthu.itunestopcharts.R
import com.rikharthu.itunestopcharts.core.executors.uiContext
import com.rikharthu.itunestopcharts.data.source.Resource
import com.rikharthu.itunestopcharts.util.GlideApp
import com.rikharthu.itunestopcharts.util.ImageHelper
import com.rikharthu.itunestopcharts.util.millisToFormattedTime
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import timber.log.Timber
// TODO make it appear as sliding from bottom to top
// TODO dismiss it with swipe down
// TODO fix crash of exiting this activity while player is not ready causing crash
class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRACK_ID = "track_id"
    }

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val trackId: String = intent.extras.getString(EXTRA_TRACK_ID)
                ?: throw IllegalStateException("Track id is null")

//        val repository: TracksRepositoryImpl by App.get(this).kodein.instance()
        mediaPlayer = MediaPlayer()

        val playerControls = listOf<View>(seekBar, currentProgressTv, trackLengthTv,
                rewindBtn, fastForwardBtn, playBtn)

//        GlobalScope.launch(uiContext) {
//            val t1 = System.currentTimeMillis()
//            val trackResource = repository.getTrack(trackId)
//            val t2 = System.currentTimeMillis()
//            Timber.tag("XDEBUG").d("Time taken: ${t2 - t1}")
//            if (trackResource is Resource.Success) {
//                val track = trackResource.data
//
//                trackTitleTv.text = track.name
//                trackAlbumTv.text = track.collection
//                trackArtistTv.text = track.artist
//
//                // TODO refactor this shit
//                loadingProgress.visibility = View.VISIBLE
//                playerControls.forEach {
//                    it.visibility = View.INVISIBLE
//                }
//                launch {
//                    with(mediaPlayer) {
//                        setAudioStreamType(AudioManager.STREAM_MUSIC)
//                        setDataSource(track.previewUrl)
//                        prepare()
//                        GlobalScope.launch(uiContext){
//                            trackLengthTv.text = duration.toLong().millisToFormattedTime()
//                            playBtn.setOnClickListener {
//                                start()
//                            }
//                            loadingProgress.visibility = View.GONE
//                            playerControls.forEach {
//                                it.visibility = View.VISIBLE
//                            }
//                        }
//
//                    }
//                }
//                favoriteBtn.setImageResource(
//                        if (track.isFavorite) R.drawable.ic_favorite
//                        else R.drawable.ic_favorite_border)
//
//                GlideApp.with(albumArt)
//                        .asBitmap()
//                        .load(track.image)
//                        .placeholder(R.drawable.vinyl_placeholder)
//                        .addListener(object : RequestListener<Bitmap> {
//                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
//                                return false
//                            }
//
//                            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                                resource?.let {
//                                    GlobalScope.launch(uiContext) {
//                                        val blurry = ImageHelper.blurImageAsync(this@PlayerActivity, it).await()
//                                        val dimBackground = ImageHelper.dimImageAsync(blurry, 0.7f).await()
//                                        backgroundImage.setImageBitmap(dimBackground)
//                                    }
//                                }
//                                return false
//                            }
//                        })
//                        .into(albumArt)
//            }
//        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }
}

package com.olcertugba.myplaylist.activity

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.mediaPlayer.MediaPlayerState
import com.olcertugba.myplaylist.ui.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class PlayListHomeViewModel(application: Application):BaseViewModel(application) {
    var isGoneMediaPlayer : ObservableBoolean = ObservableBoolean(false)
    var albumData= MutableLiveData<List<AlbumData>>()
    var positionTrack = 0
    val isNetworkError = MutableLiveData(false)

    private val mediaPlayer = SimpleExoPlayer.Builder(application.applicationContext).build()
    private val dataSourceFactory = DefaultDataSourceFactory(application.applicationContext, getUserAgent(application.applicationContext,"DeezerClone"))

    var mediaPlayerState: MutableLiveData<MediaPlayerState> = MutableLiveData()

    init {
        mediaPlayerState.value = MediaPlayerState.BUFFERING
        mediaPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                val state = when {
                    playbackState == ExoPlayer.STATE_READY && mediaPlayer.playWhenReady -> MediaPlayerState.PLAYING
                    playbackState == ExoPlayer.STATE_READY && mediaPlayer.playWhenReady.not() -> MediaPlayerState.PAUSED
                    playbackState == ExoPlayer.STATE_BUFFERING -> MediaPlayerState.BUFFERING
                    else -> MediaPlayerState.ERROR
                }
                mediaPlayerState.value = state
            }
            override fun onPlayerError(error: ExoPlaybackException) {
                mediaPlayerState.value = MediaPlayerState.ERROR
            }
        })
    }
    fun playMusic(){
        launch {
            val musicUri =albumData.value?.get(0)?.preview
            Timber.d("musicUri : $musicUri")
            val extractorMediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .setExtractorsFactory(DefaultExtractorsFactory())
                    .createMediaSource(MediaItem.fromUri(musicUri.toString()))
            mediaPlayer.prepare(extractorMediaSource)
            mediaPlayer.playWhenReady = true
        }
    }
    fun resume() {
        mediaPlayer.playWhenReady = true
    }

    fun stop() {
        mediaPlayer.playWhenReady = false
    }

    fun forwardTrack(){
        Timber.d("forwardTrack : $positionTrack")
        if(!albumData.value.isNullOrEmpty() && albumData.value!!.size-1 > positionTrack)
            ++positionTrack

        Timber.d("forwardTrack : $positionTrack")
        playMusic()
    }

    fun previouslyTrack(){
        Timber.d("previouslyTrack : $positionTrack")
        if(positionTrack>0) positionTrack--
        Timber.d("previouslyTrack : $positionTrack")
        playMusic()
    }

    fun hideMediaPlayer(){
        isGoneMediaPlayer.set(false)
        stop()
    }
}
package com.olcertugba.myplaylist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.model.mediaPlayer.MediaPlayerState
import com.olcertugba.myplaylist.util.Env
import kotlinx.android.synthetic.main.activity_play_list_home.*
import timber.log.Timber

class PlayListHomeActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    lateinit var playListHomeViewModel:PlayListHomeViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list_home)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        
        playListHomeViewModel=ViewModelProviders.of(this).get(PlayListHomeViewModel::class.java)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            if(playListHomeViewModel.isGoneMediaPlayer.get()){
                playListHomeViewModel.hideMediaPlayer()
            }

            var name = getString(R.string.app_name)
            arguments?.let {
                name = it[Env.BUND_NAME].toString()
            }
            Timber.d("name : $name")

            when(destination.id){
                R.id.homeFragment->{ materialToolbar.title = getString(R.string.app_name) }
                R.id.searchFragment->{ materialToolbar.title = getString(R.string.search) }
                R.id.favorites->{ materialToolbar.title = getString(R.string.favorites) }
                R.id.genreListFragment,
                R.id.artistFragment,
                R.id.albumFragment,->{ materialToolbar.title = name }
            }
        }
        //Bottom Navigation
        bottom_navigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.itemMusic->{ navController.navigate(R.id.action_genre) }
                R.id.itemRadio->{ navController.navigate(R.id.action_search) }
                R.id.itemFavorites->{  navController.navigate(R.id.action_favorite) }
            }
        }
        //Media Player
        ibtn_play_player.setOnClickListener {
            when(playListHomeViewModel.mediaPlayerState.value) {
                MediaPlayerState.PLAYING->playListHomeViewModel.playMusic()
                MediaPlayerState.PAUSED->playListHomeViewModel.resume()
                else->playListHomeViewModel.stop()
            }
        }

        ibtn_track_forward.setOnClickListener{
            playListHomeViewModel.forwardTrack()
        }

         ibtn_track_previous.setOnClickListener {
             playListHomeViewModel.previouslyTrack()
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.bottom_navigation_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.itemMusic ->{
//                signOut()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onBackPressed() {
        if(playListHomeViewModel.isGoneMediaPlayer.get()){
            playListHomeViewModel.hideMediaPlayer()
        }
        else{
            super.onBackPressed()
        }
    }

    private fun signOut() {
        auth.signOut()
        val intent=Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}
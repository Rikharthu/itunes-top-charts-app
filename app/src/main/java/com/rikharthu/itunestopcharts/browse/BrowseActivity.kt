package com.rikharthu.itunestopcharts.browse

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.rikharthu.itunestopcharts.R
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.rikharthu.itunestopcharts.App
import com.rikharthu.itunestopcharts.event.TrackCountChangedEvent
import com.rikharthu.itunestopcharts.util.dpToPx
import kotlinx.android.synthetic.main.activity_top_charts.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.greenrobot.eventbus.EventBus
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import timber.log.Timber
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine


class BrowseActivity : AppCompatActivity(), KodeinAware {

    override lateinit var kodein: Kodein

    var count = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_charts)

        kodein = App.get(this).kodein

        setupActionBar()
        setupBackground()
        setupNavigationDrawer()
        settingsBtn.setOnClickListener {
            launch(UI) {
                changeTrackCount()
            }
        }
        favoriteBtn.setOnClickListener {
            supportActionBar!!.title = "Favorite Tracks"
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, FavoriteTracksFragment.newInstance())
                    .commit()
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        hotChartsBtn.setOnClickListener {
            supportActionBar!!.title = "Hot Charts"
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, HotChartsFragment.newInstance())
                    .commit()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentFrame, HotChartsFragment.newInstance())
                    .commit()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Hot Charts"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun setupBackground() {
        (contentFrame.background as AnimationDrawable).apply {
            setEnterFadeDuration(2500)
            setExitFadeDuration(5000)
        }.start()
    }

    private fun setupNavigationDrawer() {
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.drawerElevation = 0f

        val drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            private val scaleFactor = 6f

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                mainContent.apply {
                    translationX = slideX
                    val newScale = 1 - slideOffset / scaleFactor
                    scaleX = newScale
                    scaleY = newScale
                }

                val menuSlideX = drawerContent.width - slideOffset * drawerContent.width
                drawerContent.translationX = menuSlideX

                // Menu label
                chartsLabel.translationX = dpToPx(100f) * (1 - slideOffset)
                chartsLabel.alpha = slideOffset

                // TODO move this below to animations when drawer is fully open (otherwise its almost not seen)
                // TODO animate menu items from bottom+alpha
                // TODO make appear the same as in "Paddock", e.g. first item appears from interval 0.4 to 0.9, second item from 0.5 to 1.0
                // TODO start from bottom Y instead of random value of 400dp
                hotChartsBtn.translationY = dpToPx(400f) * (1 - slideOffset)
                hotChartsBtn.alpha = slideOffset
                favoriteBtn.translationY = dpToPx(400f) * (1 - slideOffset)
                favoriteBtn.alpha = slideOffset
                // TODO line above settings being drawn from right to left
                // TODO animate it from offset 0.75 to 1.0
                val dividerScaleFactorX = if (slideOffset < 0.75) 0f
                else (4 * slideOffset - 3)
                settingsDivider.scaleX = -dividerScaleFactorX

                val settingsBtnTranslationFactor = if (slideOffset >= 0.65) 1f
                else (20f / 13f * slideOffset)
                Timber.tag("XDEBUG").d("settingsFactor=$settingsBtnTranslationFactor")
                val fromY = drawerLayout.height - (settingsBtn.y - settingsBtn.translationY)
                Timber.tag("XDEBUG").d("height=${drawerLayout.height}, btn.y=${settingsBtn.y - settingsBtn.translationY}")
                settingsBtn.translationY = (1 - settingsBtnTranslationFactor) * fromY
                settingsBtn.alpha=settingsBtnTranslationFactor
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                Timber.tag("XDEBUG").d("Drawer open")
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                false
            }
        }
    }

    private fun closeDrawer() = drawerLayout.closeDrawer(GravityCompat.START)

    private suspend fun changeTrackCount() {
        showTrackCountChooserDialog()?.let { newCount ->
            EventBus.getDefault().post(TrackCountChangedEvent(newCount))
            closeDrawer()
        }
    }

    private suspend fun showTrackCountChooserDialog(): Int? {
        lateinit var result: Continuation<Int?>
        TrackCountDialog().apply {
            listener = object : TrackCountDialog.OnTrackCountSelectedListener {
                override fun onTrackCountSelected(count: Int) {
                    result.resume(count)
                }

                override fun onCancelled() {
                    result.resume(null)
                }
            }
        }.show(supportFragmentManager, "track_count_chooser")
        return suspendCoroutine { continuation -> result = continuation }
    }
}

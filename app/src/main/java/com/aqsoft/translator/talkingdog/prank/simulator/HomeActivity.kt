package com.aqsoft.translator.talkingdog.prank.simulator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.aqsoft.translator.talkingdog.prank.simulator.base.BaseActivity
import com.aqsoft.translator.talkingdog.prank.simulator.base.SharePrefUtils
import com.aqsoft.translator.talkingdog.prank.simulator.databinding.ActivityHomeBinding
import com.aqsoft.translator.talkingdog.prank.simulator.intro.RatingDialog
import com.aqsoft.translator.talkingdog.prank.simulator.language.SystemUtil
import com.aqsoft.translator.talkingdog.prank.simulator.sounds.SoundFragment
import com.aqsoft.translator.talkingdog.prank.simulator.training.TrainingFragment
import com.aqsoft.translator.talkingdog.prank.simulator.translator.TranslatorFragment
import com.aqsoft.translator.talkingdog.prank.simulator.whistle.WhistleFragment
import kotlin.system.exitProcess

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private lateinit var soundsFragment: SoundFragment
    private lateinit var translatorFragment: TranslatorFragment
    private lateinit var trainingFragment: TrainingFragment
    private lateinit var whistleFragment: WhistleFragment
    private var currentItem: Int = 0
    var headerNav: View? = null
    var ivBack: ImageView? = null
    var llLanguage: LinearLayout? = null
    var llRate: LinearLayout? = null
    var llShare: LinearLayout? = null
    var llPolicy: LinearLayout? = null
    override fun initView() {
        SystemUtil.setLocale(this)
        disableSwipeDrawerLayout()
        handleDrawer()
        setUpListener()

        soundsFragment = SoundFragment()
        currentItem = R.id.sounds
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutSound, soundsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != currentItem) {
                when (item.itemId) {
                    R.id.sounds -> {
                        soundsFragment = SoundFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frameLayoutSound, soundsFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    }
                    R.id.translator -> {
                        translatorFragment = TranslatorFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frameLayoutSound, translatorFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    }
                }
                currentItem = item.itemId
            }
            true
        }

    }

    private fun setUpListener() {
        headerNav = binding.navView.getHeaderView(0)
        ivBack = headerNav!!.findViewById<ImageView>(R.id.ivBack)
        llShare = headerNav!!.findViewById<LinearLayout>(R.id.llShare)
        llPolicy = headerNav!!.findViewById<LinearLayout>(R.id.llPrivacyPolicy)






        llPolicy!!.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.freeprivacypolicy.com/live/97553f59-47b5-4e45-aeda-29e8d8dafc22")
                )
            )
            binding.drawerLayout.close()
        }

        llShare!!.setOnClickListener {
            try {
                //AppOpenMax.getInstance().disableAppResume()
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                var shareMessage =
                    "${getString(R.string.app_name)} \n ${getString(R.string.let_me_recommend)}"
                shareMessage =
                    "$shareMessage \n https://play.google.com/store/apps/details?id=com.dog.translator.talkingdog.prank.simulator"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                Handler().postDelayed({
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)))
                }, 250)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            binding.drawerLayout.close()
        }
    }

    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

    private fun handleDrawer() {
        binding.mainHeader.ivOpenDrawer.setOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) binding.drawerLayout.openDrawer(
                GravityCompat.START
            ) else binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun disableSwipeDrawerLayout() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close()
        } else {
            finishAffinity()
            exitProcess(1)
            super.onBackPressed()
        }
    }


    private fun showRateDialog() {
        val ratingDialog = RatingDialog(this)
        ratingDialog.setCancelable(false);
        ratingDialog.init(this, object : RatingDialog.OnPress {
            override fun send() {
                Toast.makeText(
                    this@HomeActivity,
                    getString(R.string.rate_thanks),
                    Toast.LENGTH_SHORT
                ).show()
                ratingDialog.dismiss()
                llRate?.visibility = View.GONE

               SharePrefUtils.forceRated(this@HomeActivity)
            }

            override fun rating() {
                val uri: Uri = Uri.parse("market://details?id=$packageName")
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                )
                try {
                    startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }
                llRate?.visibility = View.GONE
                SharePrefUtils.forceRated(this@HomeActivity)
            }

            override fun cancel() {
                ratingDialog.dismiss()
            }

            override fun later() {
                ratingDialog.dismiss()
            }
        })
        try {
            ratingDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }


}
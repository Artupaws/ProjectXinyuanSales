package project.xinyuan.sales.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivitySplashscreenBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.view.dashboard.DashboardActivity
import project.xinyuan.sales.view.login.LoginActivity
import project.xinyuan.sales.view.slider.SliderActivity

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    lateinit var handler: Handler
    private lateinit var sharedPref:SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferencesHelper(this)
        move()
    }

    private fun move(){
        handler = Handler()
        handler.postDelayed({
            when {
                sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN) -> {
                    val intent = Intent(applicationContext, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                sharedPref.getValueBoolien(Constants.FINISH_SLIDER) -> {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    val intent = Intent(applicationContext, SliderActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 2000)
    }
}


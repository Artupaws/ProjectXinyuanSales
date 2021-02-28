package project.xinyuan.sales.view.slider

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import project.xinyuan.sales.R
import project.xinyuan.sales.adapter.AdapterSlideFirstLaunch
import project.xinyuan.sales.databinding.ActivitySliderBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.view.login.LoginActivity

class SliderActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivitySliderBinding
    private lateinit var sharedPref:SharedPreferencesHelper
    private var currentItem:Int = 0
    private lateinit var introAdapter :AdapterSlideFirstLaunch
    private lateinit var  mDots: Array<TextView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        introAdapter = AdapterSlideFirstLaunch(this)
        sharedPref = SharedPreferencesHelper(this)
        setViewpagerAdapter()
        addDotsIndicator(0)
        binding.btnNext.setOnClickListener(this)
        binding.btnSkip.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {
                if (currentItem < introAdapter.count -1){
                    ++currentItem
                    binding.viewPager.currentItem = currentItem
                } else {
                    move()
                }
            }
            R.id.btn_skip -> {
                move()
            }
        }
    }

    private fun setViewpagerAdapter(){
        binding.viewPager.adapter = introAdapter
        binding.viewPager.addOnPageChangeListener(pageChangeListener)
    }

    private fun move(){
        sharedPref.save(Constants.FINISH_SLIDER, true)
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private var pageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            currentItem = position // currentItem used to get current position and then increase position on click on next button
            addDotsIndicator(position)
            if (position == introAdapter.count - 1) {
                binding.btnSkip.visibility = View.INVISIBLE
                binding.btnNext.text = applicationContext.getString(R.string.login)
            } else {
                binding.btnNext.text = applicationContext.getString(R.string.next)
                binding.btnSkip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun addDotsIndicator(position: Int) {
        mDots = arrayOfNulls(introAdapter.count)
        binding.layoutDots.removeAllViews()
        for (i in mDots.indices) {
            mDots[i] = TextView(this)
            mDots[i]?.text = Html.fromHtml("•") // Html code for bullet
            mDots[i]?.textSize = 35f
            mDots[i]?.setTextColor(applicationContext.resources.getColor(R.color.black))
            binding.layoutDots.addView(mDots[i])
        }
        if (mDots.isNotEmpty()) {
            mDots[position]?.setTextColor(resources.getColor(R.color.orange))
        }
    }
}
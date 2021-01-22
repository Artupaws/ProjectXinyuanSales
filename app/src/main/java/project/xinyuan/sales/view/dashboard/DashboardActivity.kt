package project.xinyuan.sales.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityDashboardBinding
import project.xinyuan.sales.view.account.AccountActivity
import project.xinyuan.sales.view.addfragment.AddCustomerFragment
import project.xinyuan.sales.view.history.HistoryFragment
import project.xinyuan.sales.view.home.HomeFragment

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private lateinit var binding:ActivityDashboardBinding
    var doubleTap: Boolean = false
    private val fragmentManager = supportFragmentManager
    private var fragment:Fragment? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(fragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.linearAccount.setOnClickListener(this)
       fragmentManager.beginTransaction().apply {
           replace(R.id.view_botnav, HomeFragment()).commit()
       }

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.view_botnav, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.homeFragment -> fragment = HomeFragment()
            R.id.addFragment -> fragment = AddCustomerFragment()
            R.id.historyFragment -> fragment = HistoryFragment()
        }
        return loadFragment(fragment)
    }

    override fun onBackPressed() {
        if (doubleTap) {
            super.onBackPressed()
        } else {
            Snackbar.make(
                binding.viewBotnav,
                "Please click once again to exit",
                Snackbar.LENGTH_SHORT
            ).show()
            doubleTap = true
            val handler: Handler = Handler()
            handler.postDelayed({ doubleTap = false }, 500)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.linear_account -> {
                val intent = Intent(applicationContext, AccountActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
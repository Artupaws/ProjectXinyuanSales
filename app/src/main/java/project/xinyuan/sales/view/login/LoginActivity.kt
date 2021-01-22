package project.xinyuan.sales.view.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ActivityLoginBinding
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import project.xinyuan.sales.view.dashboard.DashboardActivity
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract {

    private lateinit var binding: ActivityLoginBinding
    private var isEmptyEmail = true
    private var isEmptyPassword = true
    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPref:SharedPreferencesHelper
    private var deviceName:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LoginPresenter(this)
        sharedPref = SharedPreferencesHelper(this)
        getDeviceName()
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> {
                stateButtonLoading()
                checkLogin()
            }
        }
    }

    private fun getDeviceName(){
        deviceName = Build.MANUFACTURER+" "+Build.MODEL
        Log.d("deviceName", deviceName)
    }

    private fun checkLogin(){
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()

        if (email.isEmpty()){
            isEmptyEmail = true
            binding.etEmail.error = "Empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            isEmptyEmail = true
            binding.etEmail.error = "Email not valid"
        } else {
            isEmptyEmail = false
            email = binding.etEmail.text.toString()
        }

        if (password.isEmpty()){
            isEmptyPassword = true
            binding.etPassword.error = "Empty"
        } else {
            isEmptyPassword = false
            password = binding.etPassword.text.toString()
        }

        if (!isEmptyEmail && !isEmptyPassword){
            presenter.loginSales(deviceName, email, password)
        } else{
            stateButtonUnloading()
            Snackbar.make(binding.btnLogin, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun stateButtonLoading(){
        binding.btnLogin.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun stateButtonUnloading(){
        binding.btnLogin.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.INVISIBLE
    }

    private fun move(){
        val intent = Intent(applicationContext, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun messageLogin(msg: String) {
        if (msg.contains("Success")){
            sharedPref.save(Constants.PREF_IS_LOGIN, true)
            move()
            stateButtonUnloading()
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        } else {
            stateButtonUnloading()
            Snackbar.make(binding.btnLogin, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getTokenLogin(token: String) {
        sharedPref.save(Constants.TOKEN_LOGIN, token)
        Log.d("tokenLogin", token)
    }
}
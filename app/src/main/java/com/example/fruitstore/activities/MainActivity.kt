package com.example.fruitstore.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bttCreateAccount.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateAccount::class.java))
        }
        binding.btSignIn.setOnClickListener{
            signInUserFirebaseAuth()
        }
    }

    private fun signInUserFirebaseAuth(){
        val email = binding.etEmail.text.toString().trim(' ')
        val password = binding.etPassword.text.toString().trim(' ')
        if(validateForm(email, password)){
            showProgressDialog(R.string.please_wait.toString())

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    task ->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        Toast.makeText(this, R.string.sign_in_successful, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@MainActivity, ShopMainActivity::class.java))
                    }else{
                        Toast.makeText(this, R.string.sign_in_failed, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    exception ->
                    Log.e("Sign In Error::", exception.toString())
                }

        }else{
            showProgressDialog(R.string.please_enter_email_password_correctly.toString())
        }
    }

}
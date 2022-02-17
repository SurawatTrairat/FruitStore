package com.example.fruitstore.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ActivityCreateAccountBinding
import com.example.fruitstore.firebase.FireStoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CreateAccount : BaseActivity() {
    
    private lateinit var binding: ActivityCreateAccountBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btCreateAccount.setOnClickListener {
            createUserFirebaseAuth()
        }
    }

    private fun createUserFirebaseAuth(){
        val email = binding.etRegistEmail.text.toString().trim(' ')
        val password = binding.etRegistPassword.text.toString().trim(' ')
        if(validateForm(email, password)){
            showProgressDialog((R.string.please_wait).toString())

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    task ->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = com.example.fruitstore.models.User(
                            firebaseUser.uid, firebaseUser.email!!)
                        FireStoreClass().registerUserToFirestore(user)
                        startActivity(Intent(this@CreateAccount, MainActivity::class.java))
                    }else{
                        showErrorSnackBar(R.string.something_went_wrong.toString())
                    }
                }.addOnFailureListener {
                    exception ->
                    Log.e(R.string.something_went_wrong.toString(), exception.toString())
                }

        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarCreateAccountActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp)
        }
        binding.toolbarCreateAccountActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
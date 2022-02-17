package com.example.fruitstore.activities

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fruitstore.R
import com.example.fruitstore.databinding.ProgressDialogBinding
import com.example.fruitstore.network.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

open class BaseActivity: AppCompatActivity() {

    private var currentUser = FirebaseFirestore.getInstance()
    private lateinit var mProgressDialog: Dialog

    private lateinit var binding: ProgressDialogBinding

    fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar(R.string.please_enter_email.toString())
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar(R.string.please_enter_password.toString())
                false
            }
            else -> true
        }
    }

    fun getCurrentUserName(): String{
        var userName = ""
        currentUser.collection(Constants.USER)
            .document(getCurrentUserID())
            .get().addOnSuccessListener { document ->
                userName = document.get(Constants.USER).toString()
            }
        return userName
    }

    fun getCurrentUserID(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            return currentUser.uid
        }
        return ""
    }

    fun showProgressDialog(message: String){
        binding = ProgressDialogBinding.inflate(layoutInflater)
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.progress_dialog)
        binding.tvProgressDialog.text = message
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun showErrorSnackBar(message: String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snack_bar_error_color))
        snackBar.show()
    }

}
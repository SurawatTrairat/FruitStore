package com.example.fruitstore.firebase

import android.util.Log
import com.example.fruitstore.R
import com.example.fruitstore.activities.BaseActivity
import com.example.fruitstore.activities.ShopMainActivity
import com.example.fruitstore.models.FruitItem
import com.example.fruitstore.models.User
import com.example.fruitstore.network.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass: BaseActivity() {

    private lateinit var mFireStore: FirebaseFirestore

    fun registerUserToFirestore(userInfo: User){
        mFireStore = FirebaseFirestore.getInstance()
        mFireStore.collection(Constants.USER)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnFailureListener {
                showErrorSnackBar(R.string.something_went_wrong.toString())
            }
    }

    fun getFruitItemList(activity: ShopMainActivity){
        mFireStore = FirebaseFirestore.getInstance()
        mFireStore
            .collection((Constants.FRUIT))
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val fruitItemList: ArrayList<FruitItem> = ArrayList()
                    for(fruitElement in it.result){
                        val currentFruitElement: FruitItem = fruitElement.toObject(FruitItem::class.java)
                        fruitItemList.add(currentFruitElement)
                    }
                    activity.populateFruitItemIntoRecyclerView(fruitItemList)
                }
            }.addOnFailureListener {exception ->
                Log.e("ERROR::", exception.toString())
            }
    }

}
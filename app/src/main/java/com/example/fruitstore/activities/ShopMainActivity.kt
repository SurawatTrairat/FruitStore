package com.example.fruitstore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fruitstore.adapters.FruitItemAdapter
import com.example.fruitstore.databinding.ActivityShopMainBinding
import com.example.fruitstore.firebase.FireStoreClass
import com.example.fruitstore.models.FruitItem

private lateinit var binding: ActivityShopMainBinding

class ShopMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireStoreClass().getFruitItemList(this@ShopMainActivity)

    }

    fun populateFruitItemIntoRecyclerView(list: ArrayList<FruitItem>){
        binding = ActivityShopMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruitRecyclerView: RecyclerView = binding.recycleViewShopMain

        fruitRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)

        val adapter = FruitItemAdapter(list)
        fruitRecyclerView.adapter = adapter
    }

    private fun createList(): ArrayList<FruitItem>{
        val list: ArrayList<FruitItem> = arrayListOf()
        list.add(FruitItem("apple_product.jpg","Apple", 1.99))
        list.add(FruitItem("avocado_product.jpg","Avocado", 0.99))
        return list
    }

}
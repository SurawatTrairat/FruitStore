package com.example.fruitstore.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitstore.databinding.FruitItemContainerBinding
import com.example.fruitstore.models.FruitItem
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

open class FruitItemAdapter(

    private var list: ArrayList<FruitItem>

): RecyclerView.Adapter<FruitItemAdapter.MyViewHolder>(
){

    class MyViewHolder(val binding: FruitItemContainerBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FruitItemContainerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mStorage = FirebaseStorage.getInstance()
        mStorage.reference.child(list[position].IMAGE).downloadUrl.addOnSuccessListener { url ->
            val imageUrl = url.toString()
            Picasso
                .get()
                .load(imageUrl)
                .into(holder.binding.roundedImageView)
        }.addOnFailureListener {exception ->
            Log.i("ERROR::", exception.toString())
        }

        holder.binding.fruitItemName.text = list[position].NAME
        holder.binding.fruitItemPrice.text = list[position].PRICE.toString()+" $"
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
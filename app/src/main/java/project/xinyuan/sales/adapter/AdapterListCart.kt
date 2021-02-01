package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.transition.Hold
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.roomdatabase.CartItem

class AdapterListCart(val context: Context, val listCart:List<CartItem?>?):RecyclerView.Adapter<AdapterListCart.Holder>() {
    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderBinding.bind(view)
        fun bin(item: CartItem){
            with(binding){
                tvProductName.text = item.type
                tvYourPrice.text = item.price
                Glide.with(context).load(item.photo).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivProduct)
                tvTotalOrder.setText(item.total.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCart.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartOrderBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListCart.Holder, position: Int) {
        val cart : CartItem = listCart?.get(position)!!
        holder.bin(cart)
    }

    override fun getItemCount(): Int {
        return listCart?.size!!
    }
}
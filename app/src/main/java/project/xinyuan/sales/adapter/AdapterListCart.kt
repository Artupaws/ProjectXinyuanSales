package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.roomdatabase.CartItem

class AdapterListCart(val context: Context, private val listCart: List<CartItem?>?):RecyclerView.Adapter<AdapterListCart.Holder>() {
    var totalPayment:Int?=null
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderBinding.bind(view)
        fun bin(item: CartItem){
            with(binding) {
                tvProductName.text = item.type
                tvYourPrice.text = item.price
                tvTotalOrder.text = item.total
                tvSubTotalPrice.text = ((item.total.toInt() * item.price.toInt()).toString())
                totalPayment = ((item.total.toInt() * item.price.toInt()))
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
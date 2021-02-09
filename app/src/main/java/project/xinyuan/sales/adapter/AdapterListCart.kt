package project.xinyuan.sales.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.roomdatabase.CartItem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterListCart(val context: Context, private val listCart: List<CartItem?>?):RecyclerView.Adapter<AdapterListCart.Holder>() {
    private lateinit var helper:Helper
    var totalPayment:String?=null
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderBinding.bind(view)
        fun bin(item: CartItem){
            with(binding) {
                tvProductName.text = item.type
                tvYourPrice.text = helper.convertToFormatMoneyIDR(item.price)
                tvTotalOrder.text = item.total
                tvSubTotalPrice.text = item.subTotal
                totalPayment = helper.changeFormatMoneyToValue((item.total.toInt() * item.price.toInt()).toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCart.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartOrderBinding.inflate(inflater)
        helper = Helper()
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
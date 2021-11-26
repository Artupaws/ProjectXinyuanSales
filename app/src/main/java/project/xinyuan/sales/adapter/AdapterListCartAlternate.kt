package project.xinyuan.sales.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.xinyuan.sales.databinding.ListItemCartOrderAlternateBinding
import project.xinyuan.sales.databinding.ListItemCartOrderBinding
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.roomdatabase.CartItem
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdapterListCartAlternate(val context: Context, private val listCart: List<CartItem?>?):RecyclerView.Adapter<AdapterListCartAlternate.Holder>() {

    private lateinit var helper:Helper
    var totalPayment:String?=null
    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCartOrderAlternateBinding.bind(view)
        fun bin(item: CartItem){
            with(binding) {
                Glide.with(context).load(item.photo).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImage)
                tvProductName.text = item.type
                tvYourPrice.text = helper.convertToFormatMoneyIDR(item.price)
                tvTotalOrder.text = item.total
                tvSubTotalPrice.text = item.subTotal
                totalPayment = helper.changeFormatMoneyToValue((item.total.toInt() * item.price.toInt()).toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListCartAlternate.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartOrderAlternateBinding.inflate(inflater)
        helper = Helper()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListCartAlternate.Holder, position: Int) {
        val cart : CartItem = listCart?.get(position)!!
        holder.bin(cart)
    }

    override fun getItemCount(): Int {
        return listCart?.size!!
    }
}
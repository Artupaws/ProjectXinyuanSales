package project.xinyuan.sales.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import project.xinyuan.sales.R
import project.xinyuan.sales.databinding.ListItemAddStockOrderBinding
import project.xinyuan.sales.model.DataProduct
import project.xinyuan.sales.roomdatabase.CartDao
import project.xinyuan.sales.roomdatabase.CartItem
import project.xinyuan.sales.roomdatabase.CartRoomDatabase
import project.xinyuan.sales.view.dashboard.DashboardActivity

class AdapterListProductAddOrder(val context: Context, private val listProduct: List<DataProduct?>?) : RecyclerView.Adapter<AdapterListProductAddOrder.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null
    private var popupAddStock: Dialog? = null
    var inputPrice: String = ""
    private lateinit var database: CartRoomDatabase
    private lateinit var dao:CartDao

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAddStockOrderBinding.bind(view)
        fun bin(item: DataProduct) {
            with(binding) {
                linearAddStock.isEnabled = false
                tvProductName.text = item.type
                tvProductPrice.text = item.cost.toString()
                Glide.with(context).load(item.photo).skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(ivProduct)
                etInputPrice.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        false
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        false
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        if (p0?.isNotEmpty()!!) {
                            inputPrice = p0.toString()
                            ivActionAdd.isEnabled = true
                        } else {
                            ivActionAdd.isEnabled = false
                        }
                        true
                    }

                })
                ivActionAdd.setOnClickListener {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    popupAddStock = Dialog(context)
                    popupAddStock?.setContentView(R.layout.list_item_product_add_order)
                    popupAddStock?.setCancelable(true)
                    popupAddStock?.window?.setBackgroundDrawable(context.getDrawable(android.R.color.transparent))
                    val window: Window = popupAddStock?.window!!
                    window.setGravity(Gravity.CENTER)
                    val add = popupAddStock?.findViewById<Button>(R.id.iv_action_add)
                    val inputTotal = popupAddStock?.findViewById<EditText>(R.id.tv_total_order)
                    val imageProduct = popupAddStock?.findViewById<ImageView>(R.id.iv_product)
                    val nameProduct = popupAddStock?.findViewById<TextView>(R.id.tv_product_name)
                    val priceSales = popupAddStock?.findViewById<TextView>(R.id.tv_your_price)
                    inputTotal?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            false
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            false
                        }

                        override fun afterTextChanged(p0: Editable?) {
                            if (p0?.isNotEmpty()!!) {
                                add?.visibility = View.VISIBLE
                            } else {
                                add?.visibility = View.GONE
                            }
                            true
                        }

                    })
                    Glide.with(context).load(item.photo).skipMemoryCache(false).diskCacheStrategy(
                            DiskCacheStrategy.NONE).into(imageProduct!!)
                    nameProduct?.text = item.type
                    priceSales?.text = inputPrice
                    add?.setOnClickListener {
                        popupAddStock?.dismiss()
                        linearAddStock.isEnabled = true
                        tvTitleChoose.visibility = View.VISIBLE
                        ivActionAdd.visibility = View.GONE
                        etInputPrice.isFocusable = false
                        val intentTotal = Intent("total")
                                .putExtra("totalOrder", item.id)
                        broadcaster?.sendBroadcast(intentTotal)
                        saveCart(CartItem(id = item.id, type = item.type, photo = item.photo, price = etInputPrice.text.toString(), total = inputTotal?.text.toString()))
                    }
                    popupAddStock?.show()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProductAddOrder.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAddStockOrderBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        database = CartRoomDatabase.getDatabase(context)
        dao = database.getCartDao()
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProductAddOrder.Holder, position: Int) {
        val product: DataProduct = listProduct?.get(position)!!
        holder.bin(product)
    }

    override fun getItemCount(): Int = listProduct?.size!!

    private fun saveCart(cart: CartItem){
        if (dao.getById(cart.id).isEmpty()){
            dao.insert(cart)
        }else{
            dao.update(cart)
        }
    }
}
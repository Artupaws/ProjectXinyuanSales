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
import project.xinyuan.sales.helper.Helper
import project.xinyuan.sales.helper.NumberTextWatcher
import project.xinyuan.sales.model.product.master.DataProduct
import project.xinyuan.sales.roomdatabase.CartDao
import project.xinyuan.sales.roomdatabase.CartItem
import project.xinyuan.sales.roomdatabase.CartRoomDatabase
import java.util.*

class AdapterListProductAddOrder(val context: Context, private val listProduct: List<DataProduct?>?, val callback: Callback) : RecyclerView.Adapter<AdapterListProductAddOrder.Holder>(), Filterable {
    private var broadcaster: LocalBroadcastManager? = null
    private var database: CartRoomDatabase
    private var dao:CartDao
    private var helper:Helper

    var listProductFilter = ArrayList<DataProduct?>()
    init {
        listProductFilter = listProduct as ArrayList<DataProduct?>

        database = CartRoomDatabase.getDatabase(context)
        helper = Helper()
        dao = database.getCartDao()
    }

    interface Callback{
        fun addProduct()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemAddStockOrderBinding.bind(view)
        var inputPrice: String? =null
        var nameAndSize:String?=null
        private var popupAddStock: Dialog? = null
        private var popupAskDelete: Dialog? = null
        fun bin(item: DataProduct) {
            with(binding) {
                val locale = Locale("es", "IDR")
                val numDecs = 2 // Let's use 2 decimals
//                val twSalaryFrom: TextWatcher = NumberTextWatcher(etInputPrice, locale, numDecs)
                linearAddStock.isEnabled = false
                tvProductName.text = item.type
                tvProductSize.text = item.size.toString()
                nameAndSize = "${item.type} ${item.size}"
                Glide.with(context).load(item.photo).skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(ivProduct)

                etInputPrice.setText("")

                if (dao.exists(item.id!!)) {
                    tvTitleChoose.visibility = View.VISIBLE
                    tvRemove.visibility = View.VISIBLE
                    ivActionAdd.visibility = View.GONE
                    tvTitleChoose.text = "Choose ${dao.getById(item.id).total}"
                    etInputPrice.visibility = View.GONE
                }else{
                    tvTitleChoose.visibility = View.GONE
                    tvRemove.visibility = View.GONE
                    linearAddStock.isEnabled = false
                    ivActionAdd.visibility = View.VISIBLE
                    etInputPrice.visibility = View.VISIBLE
                }

                dialogInputTotal(item, binding)
                dialogRemoveItem(item, binding)

//                etInputPrice.addTextChangedListener(twSalaryFrom)
                etInputPrice.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        false
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        false
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        ivActionAdd.isEnabled = p0?.isNotEmpty()!!
                        true
                    }

                })
                ivActionAdd.setOnClickListener {
                    inputPrice = etInputPrice.text.toString()
                    popupAddStock?.show()
                }

                tvRemove.setOnClickListener {
                    popupAskDelete?.show()
                }
            }
        }

        private fun dialogRemoveItem(item: DataProduct, binding: ListItemAddStockOrderBinding){
            @SuppressLint("UseCompatLoadingForDrawables")
            popupAskDelete = Dialog(context)
            popupAskDelete?.setContentView(R.layout.popup_ask)
            popupAskDelete?.setCancelable(true)
            popupAskDelete?.window?.setBackgroundDrawable(context.getDrawable(android.R.color.transparent))
            val window: Window = popupAskDelete?.window!!
            window.setGravity(Gravity.CENTER)
            val btnYes = popupAskDelete?.findViewById<Button>(R.id.btn_yes)
            val btnNo = popupAskDelete?.findViewById<Button>(R.id.btn_no)
            btnYes?.setOnClickListener {
                deleteCart(CartItem(id = item.id!!, type = item.type!!, photo = item.photo!!, binding.etInputPrice.text.toString(), total = binding.tvTitleChoose.text.toString()))
                binding.tvTitleChoose.visibility = View.GONE
                binding.tvRemove.visibility = View.GONE
                binding.linearAddStock.isEnabled = false
                binding.ivActionAdd.visibility = View.VISIBLE
                binding.etInputPrice.text.clear()
                popupAskDelete?.dismiss()
            }
            btnNo?.setOnClickListener { popupAskDelete?.dismiss() }
        }

        private fun dialogInputTotal(item: DataProduct, binding:ListItemAddStockOrderBinding){
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
            nameProduct?.text = nameAndSize
            priceSales?.text = binding.etInputPrice.text.toString()
            add?.setOnClickListener {
                popupAddStock?.dismiss()
                binding.linearAddStock.isEnabled = true
                binding.tvTitleChoose.visibility = View.VISIBLE
                binding.tvRemove.visibility = View.VISIBLE
                binding.ivActionAdd.visibility = View.GONE
                binding.tvTitleChoose.text = "Choose ${inputTotal?.text.toString()}"
                saveCart(CartItem(id = item.id!!, type = nameProduct?.text.toString(), photo = item.photo!!, price = helper.changeFormatMoneyToValue(inputPrice!!), total = inputTotal?.text.toString(),
                    subTotal = (inputTotal?.text.toString().toInt()*helper.changeFormatMoneyToValue(inputPrice!!).toInt()).toString()))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProductAddOrder.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAddStockOrderBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListProductAddOrder.Holder, position: Int) {
        val product: DataProduct = listProductFilter[position]!!
        holder.bin(product)
    }

    override fun getItemCount(): Int = listProductFilter.size

    private fun saveCart(cart: CartItem){
        if (dao.exists(cart.id)){
            dao.update(cart)
        }else{
            dao.insert(cart)
        }
        notifyDataSetChanged()
        callback.addProduct()
    }

    private fun deleteCart(cart: CartItem){
        dao.delete(cart)
        notifyDataSetChanged()
        callback.addProduct()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null || querySearch.isEmpty()) {
                    listProduct
                } else {
                    listProduct?.filter {
                        it?.type?.toLowerCase()!!.contains(querySearch)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listProductFilter = p1?.values as ArrayList<DataProduct?>
                notifyDataSetChanged()
            }
        }
    }
}
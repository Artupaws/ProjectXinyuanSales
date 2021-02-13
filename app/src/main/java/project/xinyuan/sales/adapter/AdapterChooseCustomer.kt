package project.xinyuan.sales.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import project.xinyuan.sales.databinding.ListItemCustomerBinding
import project.xinyuan.sales.databinding.ListItemProductBinding
import project.xinyuan.sales.model.DataCustomer
import project.xinyuan.sales.view.addfragment.addordercustomer.AddOrderCustomerActivity

class AdapterChooseCustomer(val context: Context, private val listCustomer:List<DataCustomer?>?):
        RecyclerView.Adapter<AdapterChooseCustomer.Holder>(), Filterable {

    var listCustomerFilter = ArrayList<DataCustomer?>()
    init {
        listCustomerFilter = listCustomer as ArrayList<DataCustomer?>
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemCustomerBinding.bind(view)
        fun bin(item: DataCustomer){
            with(binding){
                tvCompanyName.text = item.companyName
                tvCompanyAddress.text = item.companyAddress
                tvCompanyPhone.text = item.companyPhone.toString()
                tvCompanyAdmin.text = item.administratorName
                cvChoose.setOnClickListener {
                    val intent = Intent(context, AddOrderCustomerActivity::class.java)
                    intent.putExtra("detailCustomer", item)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChooseCustomer.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCustomerBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterChooseCustomer.Holder, position: Int) {
        val customer: DataCustomer = listCustomerFilter[position]!!
        holder.bin(customer)
    }

    override fun getItemCount():Int = listCustomerFilter.size

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val querySearch = p0?.toString()?.toLowerCase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch==null || querySearch.isEmpty()){
                    listCustomer
                } else {
                    listCustomer?.filter {
                        it?.companyName?.toLowerCase()!!.contains(querySearch)
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listCustomerFilter = p1?.values as ArrayList<DataCustomer?>
                notifyDataSetChanged()
            }

        }
    }
}
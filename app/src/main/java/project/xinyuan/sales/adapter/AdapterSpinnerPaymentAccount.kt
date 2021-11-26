package project.xinyuan.sales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import project.xinyuan.sales.R
import project.xinyuan.sales.model.paymentaccount.master.DataPaymentAccount

class AdapterSpinnerPaymentAccount(val context: Context, private val listPaymentAccount:List<DataPaymentAccount?>?):BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var onItemClick: (Int) -> Unit = {}

    override fun getCount(): Int {
        return listPaymentAccount?.size!!
    }

    override fun getItem(p0: Int): Any {
        return listPaymentAccount?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (p1 == null){
            view = inflater.inflate(R.layout.layout_spinner, p2, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = p1
            vh = view.tag as ItemHolder
        }
        val account = "${listPaymentAccount?.get(p0)?.accountName} - ${listPaymentAccount?.get(p0)?.bank} - ${listPaymentAccount?.get(p0)?.accountNumber} "
        vh.name.text = account
//        vh.name.setOnClickListener {
//            onItemClick(listPaymentAccount?.get(p0)?.id!!)
//        }
        return view
    }

    private class ItemHolder(row: View?){
        val name:TextView = row?.findViewById(R.id.tv_payment_account)!!
    }
}
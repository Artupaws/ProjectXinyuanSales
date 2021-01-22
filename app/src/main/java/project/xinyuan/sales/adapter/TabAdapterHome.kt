package project.xinyuan.sales.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import project.xinyuan.sales.view.home.customer.CustomerFragment
import project.xinyuan.sales.view.home.product.ProductFragment

class TabAdapterHome (fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("Product", "Customer")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ProductFragment()
        1 -> CustomerFragment()
        else -> ProductFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}
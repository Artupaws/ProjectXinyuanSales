package project.xinyuan.sales.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.xinyuan.sales.adapter.TabAdapterHome
import project.xinyuan.sales.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding:FragmentHomeBinding? = null
    private val binding get() =_binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewPager?.adapter = TabAdapterHome(requireActivity().supportFragmentManager, 0)
        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
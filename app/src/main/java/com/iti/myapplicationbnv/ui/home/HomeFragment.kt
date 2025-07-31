package com.iti.myapplicationbnv.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.User
import com.iti.myapplicationbnv.databinding.FragmentHomeBinding
import com.iti.myapplicationbnv.utils.SharedPref

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        textView.setOnClickListener {
            SharedPref.getInstance().saveUser(User("mohamed","developer"))

            val messageToSend = "Hello from HomeFragment!"
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(userDM = User("ahmed","developer"))
            findNavController().navigate(action)
        }
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
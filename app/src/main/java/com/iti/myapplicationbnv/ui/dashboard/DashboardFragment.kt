package com.iti.myapplicationbnv.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.iti.myapplicationbnv.databinding.FragmentDashboardBinding
import com.iti.myapplicationbnv.utils.SharedPref

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: DashboardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard

//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        SharedPref.getInstance().saveData("ahmed",false)
        SharedPref.getInstance().getData("Ahmed",34)
        val user = SharedPref.getInstance().getUser()
        textView.text = buildString {
        append("name : ")
        append(user?.name?:"")
        append(" \t job:")
        append(user?.title?:"")
    }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
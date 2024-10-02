package com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.adapters.MaterialAdapter
import com.mati_tech.masa_english_learning.authenticator.SessionManager
import com.mati_tech.masa_english_learning.viewmodel.MaterialViewModel

class CourseMaterialStudentFragment : Fragment() {
    private lateinit var materialViewModel: MaterialViewModel
    private lateinit var adapter: MaterialAdapter
    private lateinit  var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
        materialViewModel = ViewModelProvider(this)[MaterialViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_course_material_student_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_stu)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        adapter = context?.let { MaterialAdapter(it, materialViewModel, sessionManager) }!!
        recyclerView.adapter = adapter
        materialViewModel.getAllMaterials().observe(viewLifecycleOwner) { materials ->
            adapter.setMaterial(materials)
        }
        return view
    }
}
package com.mati_tech.masa_english_learning.ui.fragments.fragments_drawer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.adapters.MaterialAdapter
import com.mati_tech.masa_english_learning.authenticator.SessionManager
import com.mati_tech.masa_english_learning.viewmodel.MaterialViewModel
import com.mati_tech.masa_english_learning.ui.activities.AddMaterialActivity

class CourseMaterialTeacherFragment : Fragment() {
    private lateinit var addMaterialBtn: Button
    private lateinit var materialViewModel: MaterialViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: MaterialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the SessionManager
        sessionManager = SessionManager(requireContext())
        materialViewModel = ViewModelProvider(requireActivity())[MaterialViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_course_material_teacher_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_teacher)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setHasFixedSize(true)
        adapter = context?.let { MaterialAdapter(it, materialViewModel, sessionManager) }!!
        recyclerView.setAdapter(adapter)
        materialViewModel.getAllMaterials().observe(viewLifecycleOwner) { materials ->
            adapter.materials = (materials.toMutableList())
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMaterialBtn = view.findViewById(R.id.add_material_button)
        addMaterialBtn.setOnClickListener {
            val intent = Intent(
                context,
                AddMaterialActivity::class.java
            )
            startActivity(intent)
        }
    }
}
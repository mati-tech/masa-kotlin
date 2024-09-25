package com.example.masa_english_school.ui.fragments.fragments_dashboards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mati_tech.masa_english_learning.R
import com.example.masa_english_school.authenticator.SessionManager

import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.extraStudy_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.grammer_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.vocabulary_fragment
import com.mati_tech.masa_english_learning.ui.activities.english_test.english_test_levels

class StudentDashboardMainFragment : Fragment() {
    lateinit var sessionManager: SessionManager
    var mListener: OnFragmentInteractionListener? = null
    lateinit var textView: TextView
    lateinit var goGrammer: ImageView
    lateinit var goVocab: ImageView
    lateinit var goMore: ImageView
    lateinit var engTest: ImageView

    interface OnFragmentInteractionListener {
        fun onNavigateToFragment(fragment: Fragment?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_dashboard_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val username = sessionManager.username
        textView = view.findViewById<TextView>(R.id.stud_id_main_frag)
        goGrammer = view.findViewById<ImageView>(R.id.grammer_icon_dashboardstu)
        goVocab = view.findViewById<ImageView>(R.id.vocab_icon_dashboardstu)
        goMore = view.findViewById<ImageView>(R.id.more_icon_dashboardstu)
        engTest = view.findViewById<ImageView>(R.id.exam_icon_stud_dashboard)


        textView.setText("logged as: $username")

        goGrammer.setOnClickListener(View.OnClickListener {
            mListener?.onNavigateToFragment(grammer_fragment())
        })
        goMore.setOnClickListener(View.OnClickListener {
            mListener?.onNavigateToFragment(extraStudy_fragment())
        })

        goVocab.setOnClickListener(View.OnClickListener {
            mListener?.onNavigateToFragment(vocabulary_fragment())
        })
        engTest.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                context,
                english_test_levels::class.java
            )
            startActivity(intent)
        })
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}
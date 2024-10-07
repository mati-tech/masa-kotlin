package com.mati_tech.masa_english_learning.ui.fragments.fragments_dashboard

import android.annotation.SuppressLint
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
import com.mati_tech.masa_english_learning.authenticator.SessionManager

import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.ExtraStudyFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.GrammerFragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.VocabularyFragment
import com.mati_tech.masa_english_learning.ui.activities.english_test.EnglishTestLevels

class StudentDashboardMainFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private  var mListener: OnFragmentInteractionListener? = null
    private lateinit var textView: TextView
    private lateinit var goGrammer: ImageView
    private lateinit var goVocab: ImageView
    private lateinit var goMore: ImageView
    private lateinit var engTest: ImageView

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val username = sessionManager.username
        textView = view.findViewById(R.id.stud_id_main_frag)
        goGrammer = view.findViewById(R.id.grammer_icon_dashboardstu)
        goVocab = view.findViewById(R.id.vocab_icon_dashboardstu)
        goMore = view.findViewById(R.id.more_icon_dashboardstu)
        engTest = view.findViewById(R.id.exam_icon_stud_dashboard)


        textView.text = "logged as: $username"

        goGrammer.setOnClickListener {
            mListener?.onNavigateToFragment(GrammerFragment())
        }
        goMore.setOnClickListener {
            mListener?.onNavigateToFragment(ExtraStudyFragment())
        }

        goVocab.setOnClickListener {
            mListener?.onNavigateToFragment(VocabularyFragment())
        }
        engTest.setOnClickListener {
            val intent = Intent(
                context,
                EnglishTestLevels::class.java
            )
            startActivity(intent)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}
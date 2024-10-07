package com.mati_tech.masa_english_learning.ui.fragments.fragments_dashboard

import android.content.Context
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

class TeacherDashboadMainFragment() : Fragment() {
    private lateinit var sessionManager: SessionManager
    private var mListenerteacher: OnFragmentInteractionListenerteacher? = null
    private lateinit var textView: TextView
    private lateinit var goGrammer: ImageView
    private lateinit var goVocab: ImageView
    private lateinit var goMore: ImageView

    interface OnFragmentInteractionListenerteacher {
        fun onNavigateToFragmentTeacherDash(fragment: Fragment?)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListenerteacher) {
            mListenerteacher = context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListenerteacher"
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
        return inflater.inflate(R.layout.fragment_teacher_dashboad_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val username = sessionManager.username
        textView = view.findViewById(R.id.teacher_id_main_frag)
        goGrammer = view.findViewById(R.id.grammer_icon_dashboardteacher)
        goVocab = view.findViewById(R.id.vocab_icon_dashboardTeacher)
        goMore = view.findViewById(R.id.more_icon_dashboardTeacher)
        textView.text = "logged as: $username"

        goGrammer.setOnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(GrammerFragment())
            }
        }
        goMore.setOnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(ExtraStudyFragment())
            }
        }

        goVocab.setOnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(VocabularyFragment())
            }
        }
    }


    override fun onDetach() {
        super.onDetach()
        mListenerteacher = null
    }
}
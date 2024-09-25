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
import com.example.masa_english_school.authenticator.SessionManager
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.extraStudy_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.grammer_fragment
import com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation.vocabulary_fragment

class TeacherDashboadMainFragment() : Fragment() {
    lateinit var sessionManager: SessionManager
    private var mListenerteacher: OnFragmentInteractionListenerteacher? = null
    lateinit var textView: TextView
    lateinit var goGrammer: ImageView
    lateinit var goVocab: ImageView
    lateinit var goMore: ImageView

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
        textView = view.findViewById<TextView>(R.id.teacher_id_main_frag)
        goGrammer = view.findViewById<ImageView>(R.id.grammer_icon_dashboardteacher)
        goVocab = view.findViewById<ImageView>(R.id.vocab_icon_dashboardTeacher)
        goMore = view.findViewById<ImageView>(R.id.more_icon_dashboardTeacher)
        textView.setText("logged as: $username")

        goGrammer.setOnClickListener(View.OnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(grammer_fragment())
            }
        })
        goMore.setOnClickListener(View.OnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(extraStudy_fragment())
            }
        })

        goVocab.setOnClickListener(View.OnClickListener {
            if (mListenerteacher != null) {
                mListenerteacher!!.onNavigateToFragmentTeacherDash(vocabulary_fragment())
            }
        })
    }


    override fun onDetach() {
        super.onDetach()
        mListenerteacher = null
    }
}
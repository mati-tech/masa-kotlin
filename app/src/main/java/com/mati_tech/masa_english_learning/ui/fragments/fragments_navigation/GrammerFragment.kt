package com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.activities.grammer.grammerDetail



class GrammerFragment : Fragment() {

    private lateinit var grammarCategory1: TextView
    private lateinit var grammarCategory2: TextView
    private lateinit var grammarCategory3: TextView
    private lateinit var grammarCategory4: TextView
    private lateinit var grammarCategory5: TextView
    private lateinit var grammarCategory6: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grammer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize all TextViews for categories
        grammarCategory1 = view.findViewById(R.id.category_1_partofspeech)
        grammarCategory2 = view.findViewById(R.id.category_2_sentencestructure)
        grammarCategory3 = view.findViewById(R.id.category_3_clausesandphrases)
        grammarCategory4 = view.findViewById(R.id.category_4_tenses)
        grammarCategory5 = view.findViewById(R.id.category_5_sentence)
        grammarCategory6 = view.findViewById(R.id.category_6_othergrammerparts)


        // Set OnClickListener for each category TextView
        grammarCategory1.setOnClickListener { navigateToNextActivity("partofspeech") }
        grammarCategory2.setOnClickListener { navigateToNextActivity("sentencestructure") }
        grammarCategory3.setOnClickListener { navigateToNextActivity("clausesandphrases") }
        grammarCategory4.setOnClickListener { navigateToNextActivity("tenses") }
        grammarCategory5.setOnClickListener { navigateToNextActivity("sentencetype") }
        grammarCategory6.setOnClickListener { navigateToNextActivity("othergrammerpart") }
    }
    private fun navigateToNextActivity(categoryName: String) {
        // Create an Intent to navigate to the next activity and pass the category name as data
        val intent = Intent(activity, grammerDetail::class.java)
        intent.putExtra("CATEGORY_NAME", categoryName)
        startActivity(intent)
    }
}
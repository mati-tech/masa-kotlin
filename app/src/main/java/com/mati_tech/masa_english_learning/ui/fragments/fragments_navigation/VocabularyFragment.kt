package com.mati_tech.masa_english_learning.ui.fragments.fragments_navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mati_tech.masa_english_learning.R
import com.mati_tech.masa_english_learning.ui.activities.vocabulary.vocabDetail


class VocabularyFragment : Fragment() {
    lateinit var vocab_category_1: TextView

    lateinit var vocab_category_2: TextView

    lateinit var vocab_category_3: TextView
    lateinit var vocab_category_4: TextView
    lateinit var vocab_category_5: TextView
    lateinit var vocab_category_6: TextView
    lateinit var vocab_category_7: TextView
    lateinit var vocab_category_8: TextView
    lateinit var vocab_category_9: TextView
    lateinit var vocab_category_10: TextView
    lateinit var vocab_category_11: TextView
    lateinit var vocab_category_12: TextView
    lateinit var vocab_category_13: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize all TextViews for categories
        vocab_category_1 = view.findViewById(R.id.category_1_Everyday_act)
        vocab_category_2 = view.findViewById(R.id.category_2_fooddrink)
        vocab_category_3 = view.findViewById(R.id.category_3_traveltrans)
        vocab_category_4 = view.findViewById(R.id.category_4_workbusiness)
        vocab_category_5 = view.findViewById(R.id.category_5_educationlearning)
        vocab_category_6 = view.findViewById(R.id.category_6_healthfitness)
        vocab_category_7 = view.findViewById(R.id.category_7_shoppingfashion)
        vocab_category_8 = view.findViewById(R.id.category_8_natureenvironment)
        vocab_category_9 = view.findViewById(R.id.category_9_entertainmentmedia)
        vocab_category_10 = view.findViewById(R.id.category_10_peoplerelationships)
        vocab_category_11 = view.findViewById(R.id.category_11_placesbuildings)
        vocab_category_12 = view.findViewById(R.id.category_12_sportsgames)
        vocab_category_13 = view.findViewById(R.id.category_13_technologygadgets)

        // Set OnClickListener for each category TextView
        vocab_category_1.setOnClickListener(View.OnClickListener { navigateToNextActivity("Everyday_act") })
        vocab_category_2.setOnClickListener(View.OnClickListener { navigateToNextActivity("fooddrink") })
        vocab_category_3.setOnClickListener(View.OnClickListener { navigateToNextActivity("traveltrans") })
        vocab_category_4.setOnClickListener(View.OnClickListener { navigateToNextActivity("workbusiness") })
        vocab_category_5.setOnClickListener(View.OnClickListener { navigateToNextActivity("educationlearning") })
        vocab_category_6.setOnClickListener(View.OnClickListener { navigateToNextActivity("healthfitness") })
        vocab_category_7.setOnClickListener(View.OnClickListener { navigateToNextActivity("shoppingfashion") })

        vocab_category_8.setOnClickListener(View.OnClickListener { navigateToNextActivity("natureenvironment") })
        vocab_category_9.setOnClickListener(View.OnClickListener { navigateToNextActivity("entertainmentmedia") })
        vocab_category_10.setOnClickListener(View.OnClickListener { navigateToNextActivity("peoplerelationships") })
        vocab_category_11.setOnClickListener(View.OnClickListener { navigateToNextActivity("placesbuildings") })
        vocab_category_12.setOnClickListener(View.OnClickListener { navigateToNextActivity("sportsgames") })
        vocab_category_13.setOnClickListener(View.OnClickListener { navigateToNextActivity("technologygadgets") })
    }

    private fun navigateToNextActivity(categoryName: String) {
        // Create an Intent to navigate to the next activity and pass the category name as data
        val intent = Intent(activity, vocabDetail::class.java)
        intent.putExtra("CATEGORY_NAME", categoryName)
        startActivity(intent)
    }

}
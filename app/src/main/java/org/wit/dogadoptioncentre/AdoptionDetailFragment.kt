package org.wit.dogadoptioncentre

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs

class AdoptionDetailFragment : Fragment() {

    private val args by navArgs<AdoptionDetailFragmentArgs>()


    companion object {
        fun newInstance() = AdoptionDetailFragment()
    }

    private lateinit var viewModel: AdoptionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.adoption_detail_fragment, container, false)

        Toast.makeText(context,"Adoption ID Selected : ${args.adoptionid}",Toast.LENGTH_LONG).show()

        return view



    }


}
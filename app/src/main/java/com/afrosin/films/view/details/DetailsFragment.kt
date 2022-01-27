package com.afrosin.films.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afrosin.films.databinding.FragmentDetailsBinding
import com.afrosin.films.model.Film
import com.afrosin.films.utils.loadImage
import com.afrosin.films.utils.preparePosterUrl

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val FILM_DATA = "film_data"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Film>(FILM_DATA)?.let { film -> setData(film) }
    }

    private fun setData(film: Film) {
        with(film) {
            binding.filmItems.filmName.text = title
            binding.filmItems.filmDescription.text = overview

            loadImage(preparePosterUrl(posterPath), binding.filmItems.filmPoster)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
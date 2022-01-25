package com.afrosin.films.view.popularPerson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afrosin.films.BuildConfig
import com.afrosin.films.R
import com.afrosin.films.databinding.FragmentPopularPersonBinding
import com.afrosin.films.utils.hide
import com.afrosin.films.utils.show
import com.afrosin.films.utils.showSnackBar
import com.afrosin.films.viewmodel.AppState
import com.afrosin.films.viewmodel.PopularPersonViewModel

class PopularPersonFragment : Fragment() {

    private var _binding: FragmentPopularPersonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularPersonViewModel by lazy {
        ViewModelProvider(this).get(PopularPersonViewModel::class.java)
    }

    private val adapter = PopularPersonFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.popularPersonRecyclerView.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        getPopularPeopleDataSet()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessPopularPerson -> {
                val personData = appState.popularPersonData
                binding.includedLoadingLayout.loadingLayout.hide()
                adapter.setData(personData, requireContext())
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.hide()
                binding.popularPersonFragmentRootView.showSnackBar(getString(R.string.error_text),
                    getString(R.string.reload_text), { getPopularPeopleDataSet() })
            }
        }
    }


    private fun getPopularPeopleDataSet(langDataSet: String = "ru-Ru") {
        viewModel.getDataFromFromServer(BuildConfig.FILM_API_KEY, langDataSet)
    }

    companion object {
        fun newInstance() = PopularPersonFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
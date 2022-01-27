package com.afrosin.films.view.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afrosin.films.BuildConfig
import com.afrosin.films.R
import com.afrosin.films.databinding.FragmentMainBinding
import com.afrosin.films.model.Film
import com.afrosin.films.utils.hide
import com.afrosin.films.utils.show
import com.afrosin.films.utils.showSnackBar
import com.afrosin.films.view.details.DetailsFragment
import com.afrosin.films.viewmodel.AppState
import com.afrosin.films.viewmodel.MainViewModel

private const val IS_DATASET_RUS_TAG = "IS_DATASET_RUS"

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var films = mutableListOf<Film>()
    private var page = 0

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.FILM_DATA, film)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            mainFragmentRecyclerView.setHasFixedSize(true)
            val layoutManager = GridLayoutManager(context, 2)
            mainFragmentRecyclerView.layoutManager = layoutManager
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener { changeFilmDataSet() }
            mainFragmentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == films.size - 1) {
                            getIsDataSetParam()
                            getFilmDataSet()
                            isLoading = true
                        }
                    }
                }
            })
            adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    layoutManager.scrollToPositionWithOffset(positionStart, 0)
                }
            })
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

        getIsDataSetParam()
        getFilmDataSet()
    }

    private fun getIsDataSetParam() {
        isDataSetRus =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(IS_DATASET_RUS_TAG, true)
                ?: true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                films.addAll(appState.filmsData)
                binding.includedLoadingLayout.loadingLayout.hide()
                isLoading = false
                adapter.setData(films)
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.hide()
                isLoading = false
                binding.mainFragmentRootView.showSnackBar(getString(R.string.error_text),
                    getString(R.string.reload_text), { getFilmDataSet() })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getFilmDataSet() {
        val lang: String = when (isDataSetRus) {
            true -> "ru-RU"
            else -> "en-EN"
        }
        val includeAdult = true
        page += 1
        viewModel.getDataFromFromServer(BuildConfig.FILM_API_KEY, lang, includeAdult, page)
    }

    private fun changeLang() {
        isDataSetRus = !isDataSetRus
        saveDataSetLang()
    }

    private fun saveDataSetLang() {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_DATASET_RUS_TAG, isDataSetRus)
                apply()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeFilmDataSet() = changeLang().also { getFilmDataSet() }

    companion object {
        fun newInstance() = MainFragment()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }
}
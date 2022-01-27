package com.afrosin.films.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afrosin.films.R
import com.afrosin.films.model.Film
import com.afrosin.films.utils.loadImage
import com.afrosin.films.utils.preparePosterUrl
import com.afrosin.films.utils.toStringFormat


class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var filmData: List<Film> = listOf()

    fun setData(data: List<Film>) {
        filmData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(filmData[position])
    }

    override fun getItemCount(): Int {
        return filmData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(film: Film) {
            itemView.apply {
                findViewById<TextView>(R.id.film_name).text = film.title
                findViewById<TextView>(R.id.release_date).text =
                    film.releaseDate.toStringFormat("dd MMM yyyy")

                loadImage(preparePosterUrl(film.posterPath), findViewById(R.id.film_poster))
                findViewById<ProgressBar>(R.id.progress_bar).progress =
                    (film.voteAverage * 10).toInt()
                findViewById<TextView>(R.id.txt_progress).text = film.voteAverage.toInt().toString()


                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(film)
                }
            }
        }
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

}
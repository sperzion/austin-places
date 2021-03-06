package com.bunchcode.austinplaces.app

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.app.SearchResultsAdapter.ViewHolder
import com.bunchcode.austinplaces.data.Venue
import com.jakewharton.rxbinding2.view.clicks

class SearchResultsAdapter(
        val results: List<Venue>,
        val listener: (Venue) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_venue, parent, false))
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = results[position]
        holder.bindResult(venue)
        holder.itemView.clicks().subscribe { listener.invoke(venue) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)
        val category: TextView = itemView.findViewById(R.id.category)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val isFavorite: ImageView = itemView.findViewById(R.id.isFavorite)
        val icon: ImageView = itemView.findViewById(R.id.icon)

        fun bindResult(venue: Venue) {
            name.text = venue.name
            distance.text = distance.context.getString(R.string.label_miles_from_austin,
                    AppUtils.milesFromAustin(venue.location.latitude, venue.location.longitude))
            isFavorite.setImageResource(
                    if (venue.isFavorite) R.drawable.ic_favorited
                    else R.drawable.ic_unfavorited)

            if (venue.categories.isEmpty()) {
                category.text = ""
                Glide.with(icon)
                        .load(R.mipmap.ic_launcher)
                        .into(icon)
                return
            }

            category.text = venue.categories[0].name
            Glide.with(icon)
                    .load(venue.categories[0].icon.url)
                    .into(icon)
        }
    }
}
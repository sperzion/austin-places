package com.bunchcode.austinplaces.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.viewmodel.VenueDetailsViewModel
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_venue_details.*

class VenueDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: VenueDetailsViewModel

    companion object {

        private const val EXTRA_VENUE: String = "VenueDetailsActivity.extra.venue"

        fun start(venue: Venue, context: Context) {
            context.startActivity(Intent(context, VenueDetailsActivity::class.java)
                    .putExtra(EXTRA_VENUE, venue))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_details)
        setSupportActionBar(toolbar)

        val venue = intent.extras.getParcelable<Venue>(EXTRA_VENUE)
        viewModel = ViewModelProviders.of(this).get(VenueDetailsViewModel::class.java)

        toolbarLayout.title = venue.name

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mapLayout, VenueDetailsMapFragment.newInstance(venue))
                .replace(R.id.contentLayout, VenueDetailsFragment.newInstance(venue))
                .commit()

        viewModel.isFavorited(venue.id, this).observe(this, Observer {
            venue.isFavorite = null != it && it
            favoriteAction.setImageResource(
                    if (venue.isFavorite) R.drawable.ic_favorited
                    else R.drawable.ic_unfavorited)
        })

        favoriteAction.clicks()
                .observeOn(Schedulers.io())
                .doOnNext {
                    venue.isFavorite = !venue.isFavorite
                    viewModel.onFavoriteActionToggled(venue, this)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    favoriteAction.setImageResource(
                            if (venue.isFavorite) R.drawable.ic_favorited
                            else R.drawable.ic_unfavorited)
                }
    }
}

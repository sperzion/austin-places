package com.bunchcode.austinplaces.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.data.Venue
import kotlinx.android.synthetic.main.activity_venue_details.*

class VenueDetailsActivity : AppCompatActivity() {

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

        toolbarLayout.title = venue.name

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mapLayout, VenueDetailsMapFragment.newInstance(venue))
                .replace(R.id.contentLayout, VenueDetailsFragment.newInstance(venue))
                .commit()
    }
}

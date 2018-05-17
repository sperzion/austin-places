package com.bunchcode.austinplaces.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.viewmodel.VenueDetailsViewModel
import kotlinx.android.synthetic.main.content_venue_details.*

class VenueDetailsFragment: Fragment() {

    lateinit var viewModel: VenueDetailsViewModel
    lateinit var venue: Venue

    companion object {

        private const val ARG_VENUE: String = "VenueDetailsFragment.arg.venue"

        fun newInstance(venue: Venue): VenueDetailsFragment {
            val args = Bundle()
            args.putParcelable(ARG_VENUE, venue)

            val instance = VenueDetailsFragment()
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_venue_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        venue = arguments!!.getParcelable(ARG_VENUE)
        updateVenue(venue)

        viewModel = ViewModelProviders.of(activity!!).get(VenueDetailsViewModel::class.java)
        viewModel.venueLiveData.observe(this, Observer { updateVenue(it!!) })
    }

    override fun onStart() {
        super.onStart()
        viewModel.retrieveVenueDetails(venue.id)
    }

    private fun updateVenue(venue: Venue) {

        updateField(venue.formattedPhone, textView = phone, label = phoneLabel)
        updateField(venue.location.formattedAddress?.joinToString("\n"),
                textView = address,
                label = addressLabel
        )
        updateField(venue.url, textView = website, label = websiteLabel)
        updateField(venue.priceTier, textView = price, label = priceLabel)
        updateField(venue.hoursStatus, textView = hours, label = hoursLabel)
        updateField(venue.menuUrl, textView = menu, label = menuLabel)
    }

    private fun updateField(value: String?, textView: TextView, label: View) {

        if (null == value) {
            textView.visibility = GONE
            label.visibility = GONE
        } else {
            textView.visibility = VISIBLE
            textView.text = value
            label.visibility = VISIBLE
        }
    }
}

package com.bunchcode.austinplaces.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_list.*

/**
 * A placeholder fragment containing a simple view.
 */
class SearchListFragment : Fragment() {
    private var searchViewModel: SearchViewModel? = null

    companion object {

        fun newInstance(): SearchListFragment = SearchListFragment()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
        searchViewModel!!.searchResults.observe(this, Observer {
            searchResults.adapter = SearchResultsAdapter(it!!)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchResults.layoutManager = LinearLayoutManager(view.context, VERTICAL, false)
        searchResults.addItemDecoration(DividerItemDecoration(view.context, VERTICAL))
    }
}

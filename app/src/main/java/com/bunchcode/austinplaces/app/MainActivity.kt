package com.bunchcode.austinplaces.app

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.app.AppUtils.Companion.hideKeyboard
import com.bunchcode.austinplaces.viewmodel.SearchViewModel
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel
    var listFragment: SearchListFragment? = null
    var mapFragment: SearchMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        resultsToggle.setOnClickListener {
            val showMap = searchViewModel.resultsAsMap
            showMap.value = !showMap.value!!
        }

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        attachViewListeners()
        attachViewModelObservers()
    }

    @SuppressLint("CheckResult")
    private fun attachViewListeners() {

        searchField.textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    searchViewModel.onQueryChanged(it.toString())
                }

        searchField.setOnEditorActionListener({ _, actionId, _ ->
            var handled = false
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                if (searchViewModel.canSearch.value!!) {
                    searchAction.performClick()
                }
                handled = true
            }
            handled
        })

        searchAction.clicks().subscribe {
            searchViewModel.onSearchSubmitted(this)
            hideKeyboard(searchAction)
            searchField.dismissDropDown()
        }
    }

    private fun attachViewModelObservers() {

        searchViewModel.canSearch.observe(this, Observer { canSearch ->
            searchAction.isEnabled = canSearch!!
        })

        searchViewModel.suggestions.observe(this, Observer { suggestions ->
            searchField.setAdapter(ArrayAdapter(this,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1,
                    suggestions))

            searchField.showDropDown()
        })

        searchViewModel.searchResults.observe(this, Observer {
            searchField.dismissDropDown()
        })

        searchViewModel.resultsAsMap.observe(this, Observer { resultsAsMap ->

            resultsToggle.setImageResource(
                    if (resultsAsMap!!) R.drawable.ic_list
                    else R.drawable.ic_map)

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.resultsFragment,
                            if (resultsAsMap) {
                                mapFragment = mapFragment ?: SearchMapFragment.newInstance()
                                mapFragment
                            }
                            else {
                                listFragment = listFragment ?: SearchListFragment.newInstance()
                                listFragment
                            })
                    .commit()
        })
    }
}

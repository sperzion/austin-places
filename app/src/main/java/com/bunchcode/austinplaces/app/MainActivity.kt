package com.bunchcode.austinplaces.app

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.viewmodel.SearchViewModel
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val searchViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        attachViewListeners()
        attachViewModelObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("CheckResult")
    private fun attachViewListeners() {

        searchField.textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("MainActivity", it.toString())
                    searchViewModel.onQueryChanged(it.toString())
                }

        searchField.setOnEditorActionListener({ field, actionId, keyEvent ->
            var handled = false
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                if (searchViewModel.canSearch.value!!) {
                    searchAction.performClick()
                }
                handled = true
            }
            handled
        })

        searchAction.clicks().subscribe { searchViewModel.onSearchSubmitted() }
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
        })

        searchViewModel.resultsAsMap.observe(this, Observer { resultsAsMap ->
            if (resultsAsMap!!) {
                return@Observer
            }
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.resultsFragment, SearchListFragment.newInstance())
                    .commit()
        })
    }
}

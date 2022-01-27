package com.example.nyarticles.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nyarticles.R
import com.example.nyarticles.adapter.MainAdapter
import com.example.nyarticles.model.ResponseData
import com.example.nyarticles.model.Results
import com.example.nyarticles.network.interfaces.ItemListener
import com.example.nyarticles.ui.fragment.DetailsFragment
import com.example.nyarticles.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class MainActivity : AppCompatActivity(), ItemListener, PopupMenu.OnMenuItemClickListener {

    private val TAG = "MainActivity"

    lateinit var viewModel: MainViewModel
    private var result: ArrayList<Results>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    private fun initListner() {
        img_back.setOnClickListener {
            onBackPressed()
        }

        img_more.setOnClickListener {
            val popup = PopupMenu(this,it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.menu)
            popup.show()
        }
    }

    private val observerName = Observer<ResponseData> { response ->

        progress.visibility=View.GONE

        val adapter = MainAdapter(this, response.results!!, this)

        result = response.results
        recyclerview.adapter = adapter

        adapter.notifyDataSetChanged()
        Log.e(TAG, " Observer data ${response.results[0]}")
    }

    override fun onItemClick(position: Int) {
        Log.i(TAG, "position  $position")

        fragmentLa.visibility = View.VISIBLE
        recyclerview.visibility = View.GONE
        img_more.visibility = View.GONE
        img_back.visibility = View.VISIBLE


        val fragment: Fragment = DetailsFragment()
        val bundle = Bundle()

        //put your ArrayList data in bundle
        bundle.putSerializable("bundle_array", result)
        bundle.putInt("position", position)


        val backStateName = fragment.javaClass.name

        val manger = supportFragmentManager
        val fragmentPopped = manger.popBackStackImmediate(backStateName, 0)

        if (!fragmentPopped) {
            val fragmentTransaction = manger.beginTransaction()
            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.fragmentLa, fragment, backStateName)
            fragmentTransaction.addToBackStack(backStateName)
            fragmentTransaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.articlesList.observe(this, observerName)
        getData(1)
        initListner()
    }

    override fun onBackPressed() {
        if (this.supportFragmentManager.backStackEntryCount > 0) {
            this.supportFragmentManager.popBackStackImmediate()

            fragmentLa.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
            img_more.visibility = View.VISIBLE
            img_back.visibility = View.GONE
        } else {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.per_1 -> getData(1)
            R.id.per_7 -> getData(7)
            R.id.per_30 -> getData(30)
        }
        return true
    }

    fun getData(i: Int) {
        progress.visibility=View.VISIBLE
        viewModel.getArticles(i)
    }
}
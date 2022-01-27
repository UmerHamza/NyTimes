package com.example.nyarticles.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nyarticles.R
import com.example.nyarticles.model.Results


class DetailsFragment : Fragment() {

    private val TAG: String = "DetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)


        val bundle = arguments

        //  bundle.remove("ModelAddress");
        val result = bundle?.getSerializable("bundle_array") as ArrayList<Results>?
        val position = bundle?.getInt("position")
        Log.e(TAG, "data $result  position $position")

        val pos = position!!
        initView(view, result, pos)
        //setData()

        return view
    }


    private fun initView(
        view: View,
        result: ArrayList<Results>?,
        pos: Int
    ) {
        Log.i(TAG, "Caption ${result?.get(pos)?.media?.get(0)?.media_metadata?.get(2)?.url}")


        val tv_url = view.findViewById<TextView>(R.id.tv_url)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_abstratct = view.findViewById<TextView>(R.id.tv_abstratct)
        val imageView = view.findViewById<ImageView>(R.id.imageViewD)

        tv_url.text = "URL : " + result?.get(pos)?.url
        tv_title.text = "Title : " + result?.get(pos)?.title.toString()
        tv_abstratct.text = "Abstract : " + result?.get(pos)?.abstract.toString()

        tv_url.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(result?.get(pos)?.url)
                )
            )
        }
        try {
            Glide.with(requireActivity().applicationContext)
                .load(result?.get(pos)?.media?.get(0)?.media_metadata?.get(2)?.url)
                .placeholder(R.drawable.pla)
                .into(imageView)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            Log.e(TAG, e.cause.toString())
        }
    }
}
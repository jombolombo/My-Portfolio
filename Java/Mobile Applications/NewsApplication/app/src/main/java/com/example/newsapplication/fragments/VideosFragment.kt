package com.example.newsapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adaptors.VideoAdaptor
import com.example.newsapplication.adaptors.YoutubeVideo
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import org.json.JSONObject

/**
 * handles all processes related to the fragment_videos.xml
 */
class VideosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_videos, container, false)
        populate(view)
        return view
    }

    /**
     * method responsible for populating the recyclerView
     */
    private fun populate(view: View) {
        var list = ArrayList<YoutubeVideo>()
        // key is hard coded because url did not work when key was grabbed from string.xml
        Ion.with(context).load(
            "GET", "https://youtube.googleapis.com/youtube/v3/" +
                    "search?maxResults=25&q=news&key=AIzaSyDlC-D3SkAuHhFqSGSta4VeXus3nyiHi-k"
        )
            .asString()
            .setCallback { e, result ->
                list = processVideos(result)
                val layoutManager = LinearLayoutManager(this.context)
                val recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
                recyclerView.layoutManager = layoutManager
                val mAdaptor = VideoAdaptor(list)
                recyclerView.adapter = mAdaptor
            }
    }

    /**
     * converts string data into an arrayList of YoutubeVideo objects
     * @param newsData is data from an Api
     * @return ArrayList with YoutubeVideo objects
     */
    private fun processVideos(newsData: String): ArrayList<YoutubeVideo> {
        val myJson = JSONObject(newsData)
        val jsonArray: JSONArray = myJson.optJSONArray("items")
        val myList = ArrayList<YoutubeVideo>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val videoId: String = jsonObject.optJSONObject("id").optString("videoId")
            val videoUri: String = "<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                    ".youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>"
            val video = YoutubeVideo(videoUri)
            myList.add(video)

        }
        return myList
    }
}
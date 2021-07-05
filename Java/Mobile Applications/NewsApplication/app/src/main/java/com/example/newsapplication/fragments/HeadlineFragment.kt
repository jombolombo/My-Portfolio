package com.example.newsapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adaptors.MyNewsModel
import com.example.newsapplication.adaptors.NewsAdaptor
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import org.json.JSONObject

/**
 * handles all processes related to the fragment_headline.xml
 */
class HeadlineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_headline, container, false)!!
        populateList(view)
        return view
    }

    /**
     * method responsible for populating the recyclerView
     */
    private fun populateList(view: View) {
        var list = ArrayList<MyNewsModel>()
        // key is hard coded because url did not work when key was grabbed from string.xml
        Ion.with(context).load(
            "GET", "https://newsapi.org/v2/top-headlines?country=" +
                    "gb&apiKey=dd5d37db2b29462993d3fb26fb7a785e"
        )
            .setHeader("user-agent", "insomnia/2020.4.1")
            .asString()
            .setCallback { e, result ->
                list = processNews(result)
                val recyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
                val layoutManager = LinearLayoutManager(this.context)
                recyclerView.layoutManager = layoutManager
                val mAdaptor = NewsAdaptor(list)
                recyclerView.adapter = mAdaptor
            }
    }

    /**
     * converts string data into an arrayList of MyNewsModel objects
     * @param newsData is data from an Api
     * @return ArrayList with MyNewsModel objects
     */
    private fun processNews(newsData: String): ArrayList<MyNewsModel> {
        val myJson = JSONObject(newsData)
        val jsonArray: JSONArray = myJson.optJSONArray("articles")
        val myList = ArrayList<MyNewsModel>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val source: String? = jsonObject.optJSONObject("source").optString("name")
            val author: String? = jsonObject.optString("author")
            val title: String? = jsonObject.optString("title")
            val description: String? = jsonObject.optString("description")
            val image: String? = jsonObject.optString("urlToImage")
            val publishedAt: String? = jsonObject.optString("publishedAt")
            val url: String? = jsonObject.optString("url")
            val content: String? = jsonObject.optString("content")
            val newsArticle = MyNewsModel(
                source, author, title, description, image, publishedAt,
                url, content
            )
            myList.add(newsArticle)

        }
        return myList
    }


}
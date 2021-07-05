package com.example.newsapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adaptors.MyNewsModel
import com.example.newsapplication.adaptors.NewsAdaptor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import org.json.JSONObject

/**
 * handles all processes related to the fragment_my_news.xml
 */
class MyNewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_my_news, container, false)
        getCatagories(view)
        return view
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

    /**
     * get data from Api based on the users preferences
     */
    private fun getCatagories(view: View) {
        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser
        var map = HashMap<String, String>()
        var myList = ArrayList<MyNewsModel>()
        val completeList = ArrayList<MyNewsModel>()
        if (user != null) {
            val docRef = db.collection("preferences").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    map = document.data as HashMap<String, String>
                    for ((key, value) in map) {
                        if (value == "subscribed") {
                            myList = getData(key)
                            completeList.addAll(myList)
                        }
                    }

                    Log.d("mainList", completeList.size.toString())
                    val recyclerView =
                        view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
                    val layoutManager = LinearLayoutManager(this.context)
                    recyclerView.layoutManager = layoutManager
                    val mAdaptor = NewsAdaptor(completeList)
                    recyclerView.adapter = mAdaptor
                }
        }
    }

    /**
     * get Data from Api
     * @param key catagory the user is subsribed to
     * @return a MyNewsModel ArrayList
     */
    private fun getData(key: String): ArrayList<MyNewsModel> {
        // key is hard coded because url did not work when key was grabbed from string.xml
        val jsonString = Ion.with(context).load(
            "GET",
            "https://newsapi.org/v2/top-headlines?country=gb&category=$key&pageSize=5" +
                    "&apiKey=dd5d37db2b29462993d3fb26fb7a785e"
        )
            .setHeader("user-agent", "insomnia/2020.4.1")
            .asString()
        return processNews(jsonString.get())
    }
}
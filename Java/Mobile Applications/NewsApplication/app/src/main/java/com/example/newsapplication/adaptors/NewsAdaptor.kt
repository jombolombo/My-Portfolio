package com.example.newsapplication.adaptors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.ReadNewsActivity
import com.koushikdutta.ion.Ion

/**
 * is the adaptor the populates the recyclerView with news articles
 * @param newsModelArrayList is the news data that populates the recyclerView
 */
class NewsAdaptor(private val newsModelArrayList: MutableList<MyNewsModel>) :
    RecyclerView.Adapter<NewsAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.news_card, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = newsModelArrayList[position]
        Ion.with(holder.imgView).load(info.getImage())
        holder.title.text = info.getTitle()
        holder.index = position
    }

    /**
     * @return number of elements in the arrayList populating the recyclerView
     */
    override fun getItemCount(): Int {
        return newsModelArrayList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imgView = itemView.findViewById<View>(R.id.newsImageView) as ImageView
        var title = itemView.findViewById<View>(R.id.title) as TextView
        val EXTRA_MESSAGE = "url"
        var index: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        /**
         * Manages action to be taken when a specific view is clicked
         */
        override fun onClick(v: View) {
            val article = newsModelArrayList[index]
            val url = article.getUrl()
            val intent = Intent(v.context, ReadNewsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, url)
            }
            v.context.startActivity(intent)
        }
    }
}
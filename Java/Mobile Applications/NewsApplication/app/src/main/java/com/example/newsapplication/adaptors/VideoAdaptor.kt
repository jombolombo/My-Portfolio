package com.example.newsapplication.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R


/**
 * is the adaptor the populates the recyclerView with youtube videos
 * @param youtubeVideoList is the youtube data that populates the recyclerView
 */
class VideoAdaptor (private val youtubeVideoList: ArrayList<YoutubeVideo> ):
    RecyclerView.Adapter<VideoAdaptor.VideoViewHolder>(){

    /**
     *@param parent is the viewGroup
     * @param viewType
     * @return VideoViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.video_card, parent, false)
        return VideoViewHolder(v)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.videoWeb.loadData(youtubeVideoList[position].videoUrl!!, "text/html", "utf-8")
    }

    /**
     * returns size of youtube ArrayList
     */
    override fun getItemCount(): Int {
        return youtubeVideoList.size
    }

    /**
     * @param itemView is the view to be repeated in the recyclerView
     */
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoWeb: WebView = itemView.findViewById(R.id.webView)
        init {
            videoWeb.settings.javaScriptEnabled = true
            videoWeb.webChromeClient = object : WebChromeClient() {
            }
        }
    }

}
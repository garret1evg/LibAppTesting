package com.example.libapptesting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class VideoAdapter(
    context: Context,
    videosList: ArrayList<ModelVideo>,
    activity: Activity
) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder?>() {
    var videosList: ArrayList<ModelVideo> = videosList
    var context: Context = context
    var activity: Activity = activity

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var imageView: ImageView = v.findViewById<View>(R.id.imageView) as ImageView
        var relativeLayoutSelect: RelativeLayout = v.findViewById<View>(R.id.relativeLayoutSelect) as RelativeLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        var aa = videosList[position].thumb
        val bitmap = ThumbnailUtils.createVideoThumbnail(aa!!, MediaStore.Images.Thumbnails.MINI_KIND)
        viewHolder.imageView.setImageBitmap(bitmap)





//        Glide.with(context).load("file://" + videosList[position].thumb)
//            .skipMemoryCache(false)
//            .into(viewHolder.imageView)
        viewHolder.relativeLayoutSelect.setBackgroundColor(Color.parseColor("#FFFFFF"))
        viewHolder.relativeLayoutSelect.alpha = 0f
        viewHolder.relativeLayoutSelect.setOnClickListener {
            val videoActivity = Intent(context, VideoViewActivity::class.java)
            videoActivity.putExtra("video", videosList[position].path)
            activity.startActivity(videoActivity)
        }
    }

    override fun getItemCount(): Int {
        return videosList.size
    }


}
package com.example.libapptesting

import android.os.Bundle
import android.view.View
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoViewActivity : AppCompatActivity() {
    var videoPath: String? = null
    var videoView: VideoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        init()
    }

    private fun init() {
        videoView = findViewById<View>(R.id.video_view) as VideoView
        videoPath = intent.getStringExtra("video")
        videoView!!.setVideoPath(videoPath)
        videoView!!.start()
    }
}

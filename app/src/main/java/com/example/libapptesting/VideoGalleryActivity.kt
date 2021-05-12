package com.example.libapptesting

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class VideoGalleryActivity : AppCompatActivity() {

    private lateinit var viewModel: VideoGalleryViewModel
    var adapter: VideoAdapter? = null
    var videoList: ArrayList<ModelVideo> = ArrayList<ModelVideo>()
    var recyclerView: RecyclerView? = null
    var recyclerViewLayoutManager: RecyclerView.LayoutManager? = null
    private val REQUEST_PERMISSIONS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_gallery)
        viewModel = ViewModelProvider(this).get(VideoGalleryViewModel::class.java)
        initRecycler()
    }
    private fun initRecycler() {
        recyclerView = findViewById(R.id.videosRecycler)
        recyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView!!.layoutManager = recyclerViewLayoutManager
        checkPermission()
    }
    private fun checkPermission() {
        /*RUN TIME PERMISSIONS*/
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            Log.e("Else", "Else")
            addVideos()
        }
    }
    private fun addVideos() {
        val cursor: Cursor?
        val columnIndexData: Int
        val columnIndexFolderName: Int
        val columnId: Int
        val thum: Int
        var absolutePathOfImage: String? = null
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Thumbnails.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = applicationContext.contentResolver
            .query(uri, projection, null, null, "$orderBy DESC")
        columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        columnIndexFolderName =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        columnId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData)
            Log.e("Column", absolutePathOfImage)
            Log.e("Folder", cursor.getString(columnIndexFolderName))
            Log.e("column_id", cursor.getString(columnId))
            Log.e("thum", cursor.getString(thum))
            val model = ModelVideo()
            model.isSelected = false
            model.path = absolutePathOfImage
            model.thumb = cursor.getString(thum)
            videoList.add(model)
        }
        adapter = VideoAdapter(applicationContext, videoList, this)
        recyclerView!!.adapter = adapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                var i = 0
                while (i < grantResults.size) {
                    if (grantResults.isNotEmpty() && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        addVideos()
                    } else {
                        Toast.makeText(
                            this,
                            "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    i++
                }
            }
        }
    }
}
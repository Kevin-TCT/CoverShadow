package com.kevin.covershadow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kevin.covershadow.widget.CoverView

/**
 * Create by KevinTu on 2018/10/11
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var coverView = findViewById<CoverView>(R.id.cover_view)
        coverView.refreshView("android.resource://com.kevin.covershadow/mipmap/cover_img_test")
    }
}
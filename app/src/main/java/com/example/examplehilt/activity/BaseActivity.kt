package com.example.examplehilt.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.examplehilt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    fun moveToActivitySlideToRight(activity: Activity, next: Class<*>) {
        val intent = Intent(activity, next)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity)
    }

    fun closeActivitySlideLeft(activity: Activity) {
        activity.finish()
        activity.overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity)
    }
}
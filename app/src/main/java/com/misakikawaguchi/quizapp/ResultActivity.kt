package com.misakikawaguchi.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // ステータスバーを隠す
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // USER_NAMEから名前を取得し変数に代入、テキスtを表示
        val userName = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = userName
    }
}
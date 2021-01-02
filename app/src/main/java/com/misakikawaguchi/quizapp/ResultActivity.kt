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

        // TOTAL_QUESTIONSからトータルの質問数を、CORRECT_ANSWERSから正解数を取得し変数に代入、デフォルトは0
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        // スコアを表示
        tv_score.text = "Your Score is $correctAnswers out of $totalQuestions."
    }
}
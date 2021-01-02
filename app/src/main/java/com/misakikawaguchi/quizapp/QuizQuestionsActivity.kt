package com.misakikawaguchi.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        // 質問のリストを取得して、UIに表示する。ログに出力する
        val questionsList = Constants.getQuestions()
        Log.e("Questions Size", "${questionsList.size}")
        for (i in questionsList) {
            Log.e("Questions", i.question)
        }

        // リストからUIに質問を設定する
        // 今何問目から表示。最初なので1を設定する
        val currentPosition = 1
        // 問題を表示する。0からスタートなので、currentPosition - 1と設定
        val question: Question? = questionsList[currentPosition - 1]

        // progressBarの進捗状況を設定する
        progressBar.progress = currentPosition
        // progressBarのテキストを設定
        tv_progress.text = "$currentPosition" + "/" + progressBar.getMax()

        // 現在の質問とオプションを設定する
        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }
}
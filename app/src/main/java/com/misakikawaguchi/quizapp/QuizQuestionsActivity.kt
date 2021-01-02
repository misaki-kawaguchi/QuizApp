package com.misakikawaguchi.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // 現在何問目かと質問リストのグローバル変数を作成
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null

    // 選択したオプションのグローバル変数
    private var mSelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        // 質問のリストを取得して、UIに表示する。ログに出力する →グローバル変数（mQuestionsList）に変更する
        mQuestionsList = Constants.getQuestions()

        // 質問を表示する
        setQuestion()

        // 選択肢のすべてのクリックイベントを設定
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
    }

    // 以前にonCreateメソッドで実行したUIコンポーネントに質問を設定する関数を作成
    // そして、後で使用する変数のいくつかをグローバルにする
    // 質問をUIコンポーネントに設定するための関数
    private fun setQuestion() {
        // リストからUIに質問を設定する
        // 今何問目から表示。最初なので1を設定する
        // mCurrentPositionに変更
        mCurrentPosition = 1
        // 問題を表示する。0からスタートなので、currentPosition - 1と設定
        val question: Question? = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        // progressBarの進捗状況を設定する
        progressBar.progress = mCurrentPosition
        // progressBarのテキストを設定
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        // 現在の質問とオプションを設定する
        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    // 選択する前（デフォルト）の選択肢の状態を設定する
    private fun defaultOptionsView() {

        // 選択肢をリストに代入
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        // 色・フォントスタイル・ボーダーを設定
        for(option in options) {
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    // 選択肢をクリックした時の処理
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
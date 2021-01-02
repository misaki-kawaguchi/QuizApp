package com.misakikawaguchi.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // 現在何問目かと質問リストのグローバル変数を作成
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null

    // 選択したオプションのグローバル変数
    private var mSelectedOptionPosition: Int = 0

    // 正解を計算するための変数を追加
    private var mCorrectAnswers: Int = 0

    // インテントから名前を取得するための変数を作成
    private var mUserName: String? = null

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
        btn_submit.setOnClickListener(this)
    }

    // 以前にonCreateメソッドで実行したUIコンポーネントに質問を設定する関数を作成
    // そして、後で使用する変数のいくつかをグローバルにする
    // 質問をUIコンポーネントに設定するための関数
    private fun setQuestion() {
        // リストからUIに質問を設定する
        // 今何問目から表示。最初なので1を設定する
        // mCurrentPositionに変更
        // mCurrentPosition = 1
        // 問題を表示する。0からスタートなので、currentPosition - 1と設定
        val question: Question? = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        // 質問の位置が最後かどうかをここで確認してから、ボタンのテキストを変更
        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }

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

        // クリックした選択肢のidが
        when (v?.id) {
            // 選択肢1の場合は
            R.id.tv_option_one -> {
                // 選択肢のテキスト、選択した番号は1
                selectedOptionsView(tv_option_one, 1)
            }

            // 選択肢2の場合は
            R.id.tv_option_two -> {
                // 選択肢のテキスト、選択した番号は2
                selectedOptionsView(tv_option_two, 2)
            }

            // 選択肢3の場合は
            R.id.tv_option_three -> {
                // 選択肢のテキスト、選択した番号は3
                selectedOptionsView(tv_option_three, 3)
            }

            // 選択肢4の場合は
            R.id.tv_option_four -> {
                // 選択肢のテキスト、選択した番号は4
                selectedOptionsView(tv_option_four, 4)
            }

            // SUBMITボタンをクリックイベントを追加
            R.id.btn_submit -> {

                // 何も選択していない場合
                if(mSelectedOptionPosition == 0) {

                    // 次のページに進む
                    mCurrentPosition++

                    when {
                        // 現在のポジションがリストの数以下の場合質問を表示する
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }else -> {
                        // コンプリートのトーストを表示する
                        Toast.makeText(this, "You have successfully completed the quiz.", Toast.LENGTH_SHORT).show()
                        }
                    }

                // 選択した場合
                }else {

                    // 質問のリストをquestionに代入する
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // 答えが正解かどうか確認する
                    // 選択した数字と正解の数字が違う場合は背景が赤になる
                    if(question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        // 正解の場合は正解の数を増やす
                        mCorrectAnswers++
                    }

                    // 正解の場合は背景色を緑にする
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    // 現在の問題が質問リストの数と同じ場合はFINISHと表示
                    // そうでない場合はGO TO NEXT QUESTIONと表示
                    if(mCorrectAnswers == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }

                    // ポジションを0に戻す
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    // 背景色を表示する関数
    private fun answerView(answer: Int, drawableView: Int) {

        when(answer) {

             1 -> {
                 tv_option_one.background = ContextCompat.getDrawable(
                     this, drawableView
                 )
             }

            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    // 選択肢をクリックした時の処理
    private fun selectedOptionsView(tv: TextView, selectedOptionNum: Int) {

        // 最初の状態
        defaultOptionsView()

        //　選択した質問の選択肢
        mSelectedOptionPosition = selectedOptionNum

        // クリックすると紫色のボーダーが表示される・太字になる
        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }
}
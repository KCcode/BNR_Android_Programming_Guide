package com.example.bnr_geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


const val EXTRA_ANSWER_SHOWN = "com.example.bnr_geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.bnr_geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private lateinit var answerTextView : TextView
    private lateinit var showAnswerButton : Button

    companion object{ // making a static function so it is global and can be called without making an instance of the class
        fun newIntent(packageContext : Context, answerIsTrue : Boolean) : Intent{
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false) //Activity.intent always gets the intent that started the activity
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown : Boolean){
        val data = Intent().apply{
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)

    }


}

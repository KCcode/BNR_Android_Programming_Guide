package com.example.bnr_geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private  const val KEY_INDEX = "index"
private  const val KEY_CHEAT = "cheat"
private  const val KEY_TRUE_BTN = "true_btn"
private  const val KEY_FALSE_BTN = "false_btn"
private const val REQUEST_CHEAT_CODE = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : Button
    private lateinit var questionTextView : TextView
    private lateinit var cheatButton : Button

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Got a quizviewmodel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text)
        cheatButton = findViewById(R.id.cheat_button)

        //remember state of buttons upon rotation this is the UI
        trueButton.isEnabled = quizViewModel.getTrueStateBtn()
        falseButton.isEnabled = quizViewModel.getFalseStateBtn()

        trueButton.setOnClickListener{view : View ->
            //val toast = Toast.makeText(this, R.string.corrent_toast, Toast.LENGTH_SHORT)
            //toast.setGravity(Gravity.TOP, 0 , 0)
            //toast.show()
            checkAnswer(true)
            //falseButton.isEnabled = false
            quizViewModel.setFalseStateBtn(false)
            falseButton.isEnabled = quizViewModel.getFalseStateBtn()
        }

        falseButton.setOnClickListener { view : View ->
            //val toast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
            //toast.setGravity(Gravity.TOP, 0 , 0)
            //toast.show()
            checkAnswer(false)
            quizViewModel.setTrueStateBtn(false)
            trueButton.isEnabled = quizViewModel.getTrueStateBtn()
        }

        cheatButton.setOnClickListener { view : View ->
            //start cheat activity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent,  REQUEST_CHEAT_CODE)
        }

        nextButton.setOnClickListener { view : View ->
            quizViewModel.moveToNext()
            updateQuestion()
            quizViewModel.setTrueStateBtn(true)
            quizViewModel.setFalseStateBtn(true)
            trueButton.isEnabled = true
            falseButton.isEnabled = true


            //trueButton.isEnabled = true
            //falseButton.isEnabled = true
        }

        updateQuestion()
        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")

    }


    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer : Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageId = when{
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer-> R.string.corrent_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState?.putInt(KEY_INDEX, quizViewModel.currentIndex)
        outState?.putBoolean(KEY_CHEAT, quizViewModel.isCheater)
        outState?.putBoolean(KEY_TRUE_BTN, quizViewModel.trueBtnState)
        outState?.putBoolean(KEY_FALSE_BTN, quizViewModel.falseBtnState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
            val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
            quizViewModel.currentIndex = currentIndex

            quizViewModel.isCheater = savedInstanceState?.getBoolean(KEY_CHEAT) ?: false
            quizViewModel.trueBtnState = savedInstanceState?.getBoolean(KEY_TRUE_BTN) ?: false
            quizViewModel.falseBtnState = savedInstanceState?.getBoolean(KEY_FALSE_BTN) ?: false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        if(requestCode == REQUEST_CHEAT_CODE){
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}

package com.example.bnr_geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(  Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var currentIndex = 0
    var isCheater = false
    var trueBtnState = true
    var falseBtnState = true

    val currentQuestionAnswer : Boolean
    get() = questionBank[currentIndex].answer

    val currentQuestionText : Int
    get() = questionBank[currentIndex].textResId


    fun moveToNext(){
        currentIndex = (currentIndex+1) % questionBank.size
        isCheater = false
    }

    fun setTrueStateBtn(state : Boolean){
        trueBtnState = state
    }

    fun setFalseStateBtn(state : Boolean){
        falseBtnState = state
    }

    fun getTrueStateBtn() : Boolean{
        return trueBtnState
    }

    fun getFalseStateBtn() : Boolean{
        return falseBtnState
    }

    init{
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared(){
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }


}
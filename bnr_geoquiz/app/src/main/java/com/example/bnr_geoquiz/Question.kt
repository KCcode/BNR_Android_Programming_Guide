package com.example.bnr_geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId : Int, val answer : Boolean)
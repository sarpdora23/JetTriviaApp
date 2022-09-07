package com.sinamekidev.jettrivia.util

import androidx.compose.ui.graphics.Color

fun getColor(isSelected:Boolean,user_answer:String,realAnswer:String):Color{
    if (isSelected){
        if (user_answer.equals(realAnswer)){
            return Color(0xFF1C5820)
        }
        else{
            return Color(0xFF741B1B)
        }
    }
    else{
       return Color(0xFF545464)
    }

}
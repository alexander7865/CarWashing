package com.mod_int.carwash.ext

import android.os.Handler
import android.os.Looper


//람다 연습중
fun lamDa(myNum: Int, comp: (String) -> Unit) {
    Handler(Looper.getMainLooper())
        .postDelayed({
            comp("good $myNum")
        }, 1000L)
}

//fun wmOrderCountPoint(
//    wmCount: Int,
//    wmPoint: (Int) -> String
//) {
//
//}
package com.mod_int.carwash.ext

import android.os.Handler
import android.os.Looper

fun highFun(wmCount: Int, comp: (String) -> Unit) {
    Handler(Looper.getMainLooper())
        .postDelayed({
            comp("현재건수는 $wmCount 건 입니다.")
        }, 1000L)
}

fun wmOrderCount(wmCount: Int, comp: (String) -> Unit) {
    val seCount = wmCount + 10
    comp("현재 누적 건수는 $seCount 건 입니다.")
}
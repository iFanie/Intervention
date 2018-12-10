package com.izikode.izilib.intervention.app

import android.app.Activity
import android.support.annotation.LayoutRes
import android.widget.Toast
import com.izikode.izilib.intervention.Intervene

@Intervene(name = "ContentView", warnAgainst = "setContentView")
fun Activity.layout(@LayoutRes layoutRes: Int) {
    this.setContentView(layoutRes)
}

@Intervene(name = "ToastIntervention", warnAgainst = "Toast.makeText")
fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
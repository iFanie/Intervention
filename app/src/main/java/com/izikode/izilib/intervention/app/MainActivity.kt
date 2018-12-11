package com.izikode.izilib.intervention.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Toast.makeText(this, "Awesome", Toast.LENGTH_SHORT).show()

        toast("Even more Awesome")

        val fragment1 = MainFragment()

        val fragment2 = MainFragment.newInstance()
    }

}

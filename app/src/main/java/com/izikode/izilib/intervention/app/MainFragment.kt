package com.izikode.izilib.intervention.app

import android.support.v4.app.Fragment
import com.izikode.izilib.intervention.Intervene

class MainFragment : Fragment() {

    companion object {

        @Intervene(name = "MainFragmentInitialization", warnAgainst = "MainFragment()", useInstead = "MainFragment.newInstance()")
        fun newInstance() = MainFragment()

    }

}
package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass

class InterventionBuilder(private val interventionName: String) : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.interventions"

    override val simpleName: String get() = "${interventionName}Intervention"

    override val sourceCode: String get() =
"""
class $interventionName {

    companionObject {

        const val NAME = $interventionName

    }

}
"""

}
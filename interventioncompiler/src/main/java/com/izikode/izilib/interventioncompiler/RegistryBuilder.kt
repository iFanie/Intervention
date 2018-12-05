package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass

class RegistryBuilder : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.interventions"

    override val simpleName: String get() = "InterventionRegistry"

    override val sourceCode: String get() =
"""
class $simpleName
"""

}
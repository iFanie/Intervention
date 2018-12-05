package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.BaseKotlinCompiler
import com.izikode.izilib.basekotlincompiler.component.CompilationRound
import com.izikode.izilib.intervention.Intervene
import kotlin.reflect.KClass

class InterventionCompiler : BaseKotlinCompiler() {

    override val processes: Array<KClass<out Any>> = arrayOf( Intervene::class )

    override fun handle(compilationRound: CompilationRound) {

    }

    override fun finally() {

    }

}

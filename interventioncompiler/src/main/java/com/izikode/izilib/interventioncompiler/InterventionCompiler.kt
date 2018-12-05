package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.BaseKotlinCompiler
import com.izikode.izilib.basekotlincompiler.component.CompilationRound
import com.izikode.izilib.intervention.Intervene
import java.io.File
import kotlin.reflect.KClass

class InterventionCompiler : BaseKotlinCompiler() {

    override val processes: Array<KClass<out Any>> = arrayOf( Intervene::class )

    override fun getGeneratedSourceDirectory(options: Map<String, String>) =
        options[PROJECT_ARGUMENT]?.let { File("$it/$GENERATED_DIRECTORY") } ?:
            super.getGeneratedSourceDirectory(options)

    override fun handle(compilationRound: CompilationRound) {

    }

    override fun finally() {

    }

    companion object {

        const val PROJECT_ARGUMENT = "interventionsModuleDir"
        const val GENERATED_DIRECTORY = "build/generated/source/kaptKotlin"

    }

}

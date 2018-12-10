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

    private val interventionBuilders = mutableListOf<InterventionBuilder>()

    override fun handle(compilationRound: CompilationRound) {
        compilationRound.functionsWith(Intervene::class).forEach { interventionFunction ->
            interventionBuilders.add(InterventionBuilder(

                interventionFunction.annotation.name,
                interventionFunction.annotation.warnAgainst,
                interventionFunction.toString(),
                interventionFunction.annotation.priority.value,
                interventionFunction.annotation.type == Intervene.Type.ERROR

            ))
        }
    }

    override fun finally() {
        compilationUtilities.classGenerator.apply {
            interventionBuilders.forEach { interventionBuilder ->
                generate(interventionBuilder)
            }

            generate(RegistryBuilder(Array(interventionBuilders.size) { index ->
                interventionBuilders[index].issue
            }))
        }
    }

    companion object {

        const val PROJECT_ARGUMENT = "interventionsModuleDir"
        const val GENERATED_DIRECTORY = "build/generated/source/kaptKotlin"

    }

}

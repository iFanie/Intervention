package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.BaseKotlinCompiler
import com.izikode.izilib.intervention.Intervene
import java.io.File
import javax.annotation.processing.SupportedOptions
import kotlin.reflect.KClass

@SupportedOptions(InterventionCompiler.MODULE_DIR_ARGUMENT)
class InterventionCompiler : BaseKotlinCompiler() {

    override val processes: Array<KClass<out Any>> = arrayOf( Intervene::class )

    override fun getGeneratedSourceDirectory(options: Map<String, String>) =
        options[MODULE_DIR_ARGUMENT]?.let { File("$it/$GENERATED_DIRECTORY") } ?:
        super.getGeneratedSourceDirectory(options)

    private val interventionBuilders = mutableListOf<InterventionBuilder>()

    override val roundHandler: CompilationRoundHandler.() -> Unit = {
        fetchFunctions(Intervene::class) {
            it.forEach { interventionFunction ->
                interventionBuilders.add(InterventionBuilder(

                    interventionFunction.annotation.name,
                    interventionFunction.annotation.warnAgainst,
                    interventionFunction.toString(),
                    interventionFunction.annotation.priority.value,
                    interventionFunction.annotation.type == Intervene.Type.ERROR

                ))
            }
        }
    }

    override val finallyHandler: FinallyHandler.() -> Unit = {
        generateClasses {
            interventionBuilders.toTypedArray()
        }

        generateClass {
            RegistryBuilder(Array(interventionBuilders.size) { index ->
                interventionBuilders[index].issue
            })
        }
    }

    companion object {

        const val MODULE_DIR_ARGUMENT = "interventionsModuleDir"
        const val GENERATED_DIRECTORY = "build/generated/source/kaptKotlin"

    }

}

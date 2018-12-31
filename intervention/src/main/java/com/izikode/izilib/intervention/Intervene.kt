package com.izikode.izilib.intervention

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Intervene(

    val name: String,
    val warnAgainst: String,
    val useInstead: String = "",
    val priority: Priority = Priority.NORMAL,
    val type: Type = Type.WARNING

) {

    enum class Priority(val value: Int) {

        LOW(1), NORMAL(5), HIGH(10)

    }

    enum class Type {

        WARNING, ERROR

    }

}

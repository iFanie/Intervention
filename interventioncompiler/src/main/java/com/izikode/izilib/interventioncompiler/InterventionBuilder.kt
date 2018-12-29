package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass

class InterventionBuilder(

    private val name: String,
    private val warnAgainst: String,
    private val useInstead: String,
    private val priority: Int,
    private val isError: Boolean

) : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.interventions"

    override val simpleName: String get() = "$name${if (name.toLowerCase().endsWith("intervention")) "" else "Intervention"}"

    val issue get() = "$simpleName.ISSUE"

    private val type: String get() = if (isError) "ERROR" else "WARNING"

    override val sourceCode: String get() =
"""package $packageName

import com.android.tools.lint.detector.api.*
import com.izikode.izilib.intervention.BaseIntervention
import java.util.EnumSet

class $simpleName : BaseIntervention() {

    override val issue: Issue = ISSUE

    override val intervenesOnUsageOf: String = "$warnAgainst"

    override val correctUsage: String = "$useInstead"

    companion object {

        @JvmStatic val ISSUE = Issue.create(
            "$simpleName", "Warns against the usage of \"$warnAgainst\".",
            "$simpleName warns against using \"$warnAgainst\" which has been replaced with \"$useInstead\".",
            Category.CORRECTNESS, $priority, Severity.$type,
            Implementation($simpleName::class.java, EnumSet.of(Scope.JAVA_FILE))
        )

    }

}
"""

}
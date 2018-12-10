package com.izikode.izilib.interventioncompiler

import com.izikode.izilib.basekotlincompiler.component.AbstractKotlinClass
import java.lang.StringBuilder

class RegistryBuilder(private val issues: Array<String>) : AbstractKotlinClass() {

    override val packageName: String = "com.izikode.izilib.interventions"

    override val simpleName: String get() = "InterventionRegistry"

    private val issueList get() = StringBuilder().apply {

        issues.forEachIndexed { index, issue ->
            append(issue)

            if (index < issues.size - 1) {
                append(", ")
            }
        }

    }.toString()

    override val sourceCode: String get() =

        """package $packageName

class $simpleName : com.android.tools.lint.client.api.IssueRegistry() {

    override val api get() = com.android.tools.lint.detector.api.CURRENT_API

    override val issues get() = listOf(

        $issueList

    )

}
"""

}
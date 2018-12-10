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
import org.jetbrains.uast.UClass
import java.util.EnumSet
import java.util.regex.Pattern

class $simpleName : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes() = listOf( UClass::class.java )

    override fun afterCheckFile(context: Context) {
        val source = context.getContents().toString()

        if (!source.contains("@Intervene")) {
            val matcher = PATTERN.matcher(source)

            while (matcher.find()) {
                val location = Location.create(context.file, source, matcher.start(), matcher.end())

                val fix = fix()
                    .name("Replace \"$warnAgainst\"")
                    .replace()
                    .text(matcher.group(0))
                    .with("$useInstead")
                    .autoFix(false, false)
                    .build()

                context.report(ISSUE, location, "\"$warnAgainst\" should not be used directly.", fix)
            }
        }
    }

    companion object {

        private val PATTERN = Pattern.compile(Pattern.quote("$warnAgainst"))

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
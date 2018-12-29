package com.izikode.izilib.intervention

import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Location
import org.jetbrains.uast.UClass
import java.util.regex.Pattern

/**
 * Simple abstraction over the Lint Detector class, to be used for code matching and intervention.
 */
abstract class BaseIntervention : Detector(), Detector.UastScanner {

    /**
     * The Lint issue to be registered and reported.
     */
    protected abstract val issue: Issue

    /**
     * The name of the intervention.
     */
    abstract val interventionName: String

    /**
     * The code that should be monitored and intervened upon.
     */
    abstract val intervenesOnUsageOf: String

    /**
     * The code that should be used instead of the intervened upon code.
     */
    abstract val correctUsage: String

    private val codePattern by lazy { Pattern.compile(Pattern.quote(intervenesOnUsageOf)) }

    override fun getApplicableUastTypes() = listOf( UClass::class.java )

    override fun afterCheckFile(context: Context) {
        val source = context.getContents().toString()

        if (!source.contains("@Intervene")) {
            val matcher = codePattern.matcher(source)

            while (matcher.find()) {
                val location = Location.create(context.file, source, matcher.start(), matcher.end())

                val fix = fix()
                    .name("Replace \"$intervenesOnUsageOf\"")
                    .replace()
                    .text(matcher.group(0))
                    .with(correctUsage)
                    .autoFix(false, false)
                    .build()

                context.report(issue, location, "\"$intervenesOnUsageOf\" should not be used directly.", fix)
            }
        }
    }

}
package org.edtech.curriculum.internal

import org.edtech.curriculum.CourseHtml
import org.edtech.curriculum.GradeStep
import org.edtech.curriculum.RequirementGroup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


/**
 * Parses the course data in the format supplied in syllabus.tgz
 *
 * Extracts the information to be used for processing
 *
 *
 * @param subjectDocument to extract information from
 */
class UpperSecondaryCourseDataExtractor(private val subjectDocument: Document): CourseDataExtractor {

    override fun getCourseData(): List<CourseHtml> {
        return subjectDocument.select("subject > courses")
                .map { getCourse(it) }
                .toList()
    }


    private fun getKnowledgeRequirements(knowledgeRequirementElements: Elements): List<RequirementGroup> {
        return listOf(RequirementGroup(knowledgeRequirementElements.map {
            Pair(
                    GradeStep.valueOf(it.selectFirst("gradeStep").text()),
                    it.selectFirst("text").text()
            )
        }.toMap()))
    }

    private fun getCourse(courseElement: Element): CourseHtml {
        return CourseHtml(
                courseElement.select("name").text(),
                fixDescriptions(courseElement.select("description").text()),
                courseElement.select("code").text(),
                "",
                "",
                courseElement.select("point").text(),
                convertDashListToList(courseElement.select("centralContent, centralContents").text()),
                this.getKnowledgeRequirements(courseElement.select("knowledgeRequirement, knowledgeRequirements"))
        )
    }

}


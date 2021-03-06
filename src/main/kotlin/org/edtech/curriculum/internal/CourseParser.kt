package org.edtech.curriculum.internal

import org.edtech.curriculum.Course
import org.edtech.curriculum.CourseHtml

internal class CourseParser(private val courseData: CourseHtml) {

    /**
     * Return a Course entity from the supplied root element containing the course data
     */
    fun getCourse(): Course {
        return Course(
                courseData.name,
                courseData.description,
                courseData.code,
                CentralContentConverter().getCentralContents(courseData.centralContent),
                KnowledgeRequirementConverter().getKnowledgeRequirements(courseData.knowledgeRequirementGroups),
                courseData.point.toIntOrNull(),
                toYearGroup(courseData.year)
        )
    }
}
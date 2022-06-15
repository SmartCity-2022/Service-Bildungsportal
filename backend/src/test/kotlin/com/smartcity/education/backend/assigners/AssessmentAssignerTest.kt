package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Assessment
import com.smartcity.education.backend.models.AssessmentProperties
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [AssessmentAssigner::class])
class AssessmentAssignerTest {
    @Autowired
    private val sut: AssessmentAssigner? = null

    @Test
    fun testAssign_Empty() {
        val title = "A"
        val obj = Assessment(
            title = title
        )
        val props = AssessmentProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.title, title)
    }

    @Test
    fun testAssign_Populated() {
        val title = "A"
        val obj = Assessment(
            title = ""
        )
        val props = AssessmentProperties(
            title = title
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.title, title)
    }
}
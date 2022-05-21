package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.EducationProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [EducationAssigner::class])
class EducationAssignerTest {
    @Autowired
    private val sut: EducationAssigner? = null

    @Test
    fun testAssign_Empty() {
        val title = "A"
        val description = "B"
        val obj = Education(
            title = title,
            description = description
        )
        val props = EducationProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.title, title)
        Assertions.assertEquals(obj.description, description)
    }

    @Test
    fun testAssign_Populated() {
        val title = "A"
        val description = "B"
        val obj = Education(
            title = "",
            description = ""
        )
        val props = EducationProperties(
            title = title,
            description = description
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.title, title)
        Assertions.assertEquals(obj.description, description)
    }
}
package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Qualification
import com.smartcity.education.backend.models.QualificationProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [QualificationAssigner::class])
class QualificationAssignerTest {
    @Autowired
    private val sut: QualificationAssigner? = null

    @Test
    fun testAssign_Empty() {
        val name = "A"
        val description = "B"
        val obj = Qualification(
            name = name,
            description = description
        )
        val props = QualificationProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
        Assertions.assertEquals(obj.description, description)
    }

    @Test
    fun testAssign_Populated() {
        val name = "A"
        val description = "B"
        val obj = Qualification(
            name = "",
            description = ""
        )
        val props = QualificationProperties(
            name = name,
            description = description
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
        Assertions.assertEquals(obj.description, description)
    }
}
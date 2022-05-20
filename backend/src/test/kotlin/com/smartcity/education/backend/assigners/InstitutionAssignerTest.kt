package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.InstitutionProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [InstitutionAssigner::class])
class InstitutionAssignerTest {
    @Autowired
    private val sut: InstitutionAssigner? = null

    @Test
    fun testAssign_Empty() {
        val name = "Random String"
        val obj = Institution(
            name = name
        )
        val props = InstitutionProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
    }

    @Test
    fun testAssign_Populated() {
        val name = "Random String"
        val obj = Institution(
            name = ""
        )
        val props = InstitutionProperties(
            name = name
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
    }
}
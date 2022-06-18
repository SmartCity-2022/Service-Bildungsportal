package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [GraduationAssigner::class])
class GraduationAssignerTest {
    @Autowired
    private val sut: GraduationAssigner? = null

    @Test
    fun testAssign_Empty() {
        val date = LocalDateTime.now()

        val obj = Graduation(
                date = date
        )
        val props = GraduationProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.date, date)
    }

    @Test
    fun testAssign_Populated() {
        val date = LocalDateTime.now()

        val obj = Graduation(
                date = LocalDateTime.MIN
        )
        val props = GraduationProperties(
                date = date
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.date, date)
    }
}
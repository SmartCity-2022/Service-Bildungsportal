package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [MatriculationAssigner::class])
class MatriculationAssignerTest {
    @Autowired
    private val sut: MatriculationAssigner? = null

    @Test
    fun testAssign_Empty() {
        val date = LocalDateTime.now()

        val obj = Matriculation(
                date = date
        )
        val props = MatriculationProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.date, date)
    }

    @Test
    fun testAssign_Populated() {
        val date = LocalDateTime.now()

        val obj = Matriculation(
                date = LocalDateTime.MIN
        )
        val props = MatriculationProperties(
                date = date
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.date, date)
    }
}
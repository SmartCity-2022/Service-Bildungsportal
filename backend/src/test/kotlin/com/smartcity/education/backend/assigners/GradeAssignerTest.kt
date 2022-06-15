package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Grade
import com.smartcity.education.backend.models.GradeProperties
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [GradeAssigner::class])
class GradeAssignerTest {
    @Autowired
    private val sut: GradeAssigner? = null

    @Test
    fun testAssign_Empty() {
        val grade = 1.0f
        val date = LocalDateTime.now()

        val obj = Grade(
                grade = grade,
                date = date
        )
        val props = GradeProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.grade, grade)
        Assertions.assertEquals(obj.date, date)
    }

    @Test
    fun testAssign_Populated() {
        val grade = 1.0f
        val date = LocalDateTime.now()

        val obj = Grade(
                grade = 0.0f,
                date = LocalDateTime.MIN
        )
        val props = GradeProperties(
                grade = grade,
                date = date
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.grade, grade)
        Assertions.assertEquals(obj.date, date)
    }
}
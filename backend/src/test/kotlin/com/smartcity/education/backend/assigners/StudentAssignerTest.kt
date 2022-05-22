package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [StudentAssigner::class])
class StudentAssignerTest {
    @Autowired
    private val sut: StudentAssigner? = null

    @Test
    fun testAssign_Empty() {
        val name = "A"
        val obj = Student(
            name = name
        )
        val props = StudentProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
    }

    @Test
    fun testAssign_Populated() {
        val name = "A"
        val obj = Student(
            name = ""
        )
        val props = StudentProperties(
            name = name
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.name, name)
    }
}
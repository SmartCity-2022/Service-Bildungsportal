package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.models.LocationProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [LocationAssigner::class])
class LocationAssignerTest {
    @Autowired
    private val sut: LocationAssigner? = null

    @Test
    fun testAssign_Empty() {
        val address = "A"
        val city = "B"
        val zip = "C"
        val obj = Location(
            address = address,
            city = city,
            zip = zip
        )
        val props = LocationProperties()

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.address, address)
        Assertions.assertEquals(obj.city, city)
        Assertions.assertEquals(obj.zip, zip)
    }

    @Test
    fun testAssign_Populated() {
        val address = "A"
        val city = "B"
        val zip = "C"
        val obj = Location(
            address = "",
            city = "",
            zip = ""
        )
        val props = LocationProperties(
            address = address,
            city = city,
            zip = zip
        )

        sut?.assign(props, obj)

        Assertions.assertEquals(obj.address, address)
        Assertions.assertEquals(obj.city, city)
        Assertions.assertEquals(obj.zip, zip)
    }
}
package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshToken (
    @field:JsonProperty("token")
    val token: String
)
package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessToken (
    @field:JsonProperty("accessToken")
    val accessToken: String
)
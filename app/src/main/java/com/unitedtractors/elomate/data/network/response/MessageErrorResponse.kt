package com.unitedtractors.elomate.data.network.response

import com.google.gson.annotations.SerializedName

data class MessageErrorResponse(

    @field:SerializedName("message")
    val message: String
)
package com.shushant.androidmvvmtask.models
import androidx.annotation.Keep
import androidx.room.Entity

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@Keep
@JsonClass(generateAdapter = true)
@Entity(primaryKeys = [("id")])
data class SandboxResponseClassItem(
    @Json(name = "accountId")
    var accountId: String = "", // fdd7eff6-3b33-4b12-81d2-fc9d18b2099e
    @Json(name = "carrier")
    var carrier: String? = "", // LMC Sample User
    @Json(name = "dob")
    var dob: String? = "", // 11/22/2021
    @Json(name = "expirationDate")
    var expirationDate: String? = "", // 11/26/2021
    @Json(name = "firstName")
    var firstName: String? = "", // Sumiti
    @Json(name = "id")
    var id: String = "", // 1c0d6fe3-2744-47d7-b052-837d3f681489
    @Json(name = "lastName")
    var lastName: String? = "", // Tiwari
    @Json(name = "licenseNumber")
    var licenseNumber: String? = "", // DLefvjnerv
    @Json(name = "state")
    var state: String? = "", // Pakistan
    @Json(name = "status")
    var status: String? = "" // ACTIVE
)
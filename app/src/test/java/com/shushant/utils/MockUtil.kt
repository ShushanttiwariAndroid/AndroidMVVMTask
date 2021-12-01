package com.shushant.utils

import com.shushant.androidmvvmtask.models.SandboxResponseClassItem

object MockUtil {

  fun mockSandBoxItems() = SandboxResponseClassItem(

    id = "b81eaf71-a3ac-475f-9ace-04bf307f7240",
    accountId = "fdd7eff6-3b33-4b12-81d2-fc9d18b2099e",
    carrier = "lmc india",
    firstName = "India",
    lastName = "Pune",
    licenseNumber = "India124",
    state = "Maharashtra",
    expirationDate = "11/30/2021",
    status = "ACTIVE",
    dob = "11/01/2021"
  )

  fun mockDummyList() = listOf(mockSandBoxItems())

}
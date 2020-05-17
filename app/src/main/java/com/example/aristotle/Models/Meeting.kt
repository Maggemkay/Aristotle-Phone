package com.example.aristotle.Models

import java.util.*

class Meeting(
    val startTime :Date,
    val endTime :Date,
    val subject :String,
    val location :String,
    val participants :List<User>,
    var notePath :String
)
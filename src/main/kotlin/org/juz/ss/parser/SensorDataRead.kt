package org.juz.ss.api

import java.time.ZonedDateTime

data class SensorDataRead(var eventTime: ZonedDateTime = ZonedDateTime.now(),
						  var ip: String = "",
						  var name: String = "",
						  var data: List<NameValue> = ArrayList()
)
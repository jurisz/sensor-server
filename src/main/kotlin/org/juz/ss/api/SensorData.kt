package org.juz.ss.api

import java.time.LocalDateTime

data class SensorData(val eventTime: LocalDateTime,
					  val ip: String,
					  val name: String,
					  val data: List<NameValue>
)



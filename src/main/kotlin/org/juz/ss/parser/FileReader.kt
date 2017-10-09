package org.juz.ss.parser

import org.apache.commons.io.Charsets
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.io.IOUtils.toBufferedReader
import org.juz.ss.ApplicationConfiguration
import org.juz.ss.api.SensorDataRead
import org.juz.ss.util.JsonUtil
import java.io.File
import java.io.InputStreamReader
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun main(args: Array<String>) {

	val input = FileUtils.openInputStream(File(sensorFilePath()))
	val reader = toBufferedReader(InputStreamReader(input, Charsets.toCharset(StandardCharsets.UTF_8)))

	var line: String? = reader.readLine()

	while (line != null) {

		val sensorData = JsonUtil.fromString(
				line,
				SensorDataRead::class.java
		)

		val time = convertAndApproximateTime(sensorData.eventTime)
		val temp = findData(sensorData, "temp1")
		val vv = findData(sensorData, "vv")
		println("$time; $temp; $vv")

		line = reader.readLine()
	}

	IOUtils.closeQuietly(reader)
}

fun findData(data: SensorDataRead, name: String): BigDecimal? {
	return data.data.find { it.name == name }?.value
}

fun convertAndApproximateTime(eventTime: ZonedDateTime): String {
	val time = eventTime.toInstant().atZone(ZoneId.of("Europe/Riga"))

	val remain = time.minute % 15
	val quarters = (time.minute / 15) * 15
	var mins = if (remain < 8) {
		quarters
	} else {
		quarters + 15
	}

	var hours = time.hour
	if (mins > 45) {
		hours++
		mins = 0
	}
	return LocalDateTime.of(time.year, time.month, time.dayOfMonth,
			hours, mins, 0, 0).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun sensorFilePath(): String {
	return ApplicationConfiguration().sensorDataFilePath()
}
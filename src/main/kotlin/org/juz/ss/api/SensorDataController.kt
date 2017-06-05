package org.juz.ss.api

import org.juz.ss.ApplicationConfiguration
import org.juz.ss.util.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@RestController
class SensorDataController(val applicationConfiguration: ApplicationConfiguration) {

	private val log = LoggerFactory.getLogger(SensorDataController::class.java)

	@PostMapping("/save/{name}")
	fun save(@PathVariable name: String, @RequestBody sensorData: List<NameValue>, httpRequest: HttpServletRequest) {
		val ip = httpRequest.remoteAddr
		val time = LocalDateTime.now()
		val fullSensorJson = SensorData(time, ip, name, sensorData)
		val json = JsonUtil.toJsonString(fullSensorJson)

		openDataFile().appendText(json + "\n")
	}

	private fun openDataFile(): File {
		val file = File(applicationConfiguration.sensorDataFilePath())
		if (!file.exists()) {
//			log.info("File: {}", file.absolutePath)
			file.createNewFile()
		}
		return file
	}


}




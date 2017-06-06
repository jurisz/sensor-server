package org.juz.ss.api

import org.apache.commons.io.FileUtils
import org.juz.ss.ApplicationConfiguration
import org.juz.ss.util.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.io.File
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


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

	@GetMapping("download")
	fun download(response: HttpServletResponse) {
		val file = openDataFile()
		response.reset()
		response.contentType = "text/html"
		response.setDateHeader("Expires", 0)
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")
		response.setHeader("Pragma", "no-cache")
		response.addHeader("Content-Length", java.lang.Long.toString(file.length()))
		FileUtils.copyFile(file, response.outputStream)
	}

	@PostMapping("/clean-file")
	fun cleanFile() {
		log.warn("Deleting sensor data file")
		openDataFile().delete()
	}
	
	private fun openDataFile(): File {
		val file = File(applicationConfiguration.sensorDataFilePath())
		if (!file.exists()) {
			file.createNewFile()
		}
		return file
	}


}




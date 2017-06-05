package org.juz.ss

import org.springframework.context.annotation.Configuration

@Configuration
open class ApplicationConfiguration {

	private val DATA_FILE_PATH = "/sensor-data/data.txt"

	fun sensorDataFilePath(): String {
		return System.getProperty("user.home") + DATA_FILE_PATH
	}
}
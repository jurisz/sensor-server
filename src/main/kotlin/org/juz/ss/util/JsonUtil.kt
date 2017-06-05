package org.juz.ss.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

class JsonUtil {
	companion object Factory {
		private val jsonMapper: ObjectMapper = createMapper()

		private fun createMapper(): ObjectMapper {
			return ObjectMapper()
					.setSerializationInclusion(JsonInclude.Include.NON_NULL)
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule(JavaTimeModule())
					.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		}

		fun toJsonString(data: Any): String = jsonMapper.writeValueAsString(data)

		fun <T> fromString(content: String, clazz: Class<T>): T = jsonMapper.readValue(content, clazz)
	}
}


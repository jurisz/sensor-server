package org.juz.ss.parser

import java.math.BigDecimal

data class NameValueRead(var name: String = "",
						 var value: BigDecimal = BigDecimal.ZERO)
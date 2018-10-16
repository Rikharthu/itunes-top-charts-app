package com.rikharthu.itunestopcharts.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject

infix fun JsonObject.getString(field: String): String {
    return this.get(field).asString
}

infix fun JsonObject.getInt(field: String): Int {
    return this.get(field).asInt
}

infix fun JsonObject.getObject(field: String): JsonObject {
    return this.get(field).asJsonObject
}

infix fun JsonObject.getArray(field: String): JsonArray {
    return this.get(field).asJsonArray
}
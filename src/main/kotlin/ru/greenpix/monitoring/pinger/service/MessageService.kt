package ru.greenpix.monitoring.pinger.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.util.concurrent.ConcurrentHashMap

@Service
class MessageService(
    private val objectMapper: ObjectMapper
) {

    val keys: MutableMap<String, String> = ConcurrentHashMap<String, String>()

    init {
        javaClass.getResourceAsStream("/lang.json")?.let { it1 ->
            InputStreamReader(it1).use { reader ->
                val el = objectMapper.readTree(reader)
                if (el.isObject) {
                    el.fields().forEach {
                        keys[it.key] = it.value.asText()
                    }
                }
            }
        }
    }

    fun translate(key: String): String {
        return keys.getOrDefault(key, key)
    }

    fun parse(json: JsonNode): String {
        val builder = StringBuilder()
        if (json.isObject) {
            if (json.has("text")) {
                builder.append(parse(json["text"]))
            }
            if (json.has("translate")) {
                builder.append(translate(parse(json["translate"])))
                if (json.has("with")) {
                    val withEl = json["with"]
                    if (withEl.isArray) {
                        val with = mutableListOf<String>()
                        for (withElement in withEl) {
                            with.add(parse(withElement))
                        }
                        val text = String.format(builder.toString(), *with.toTypedArray<Any?>())
                        builder.clear()
                        builder.append(text)
                    }
                }
            }
            if (json.has("extra")) {
                builder.append(parse(json["extra"]))
            }
        }
        else if (json.isArray) {
            json.forEach { builder.append(parse(it)) }
        }
        else {
            builder.append(json.asText())
        }
        return builder.toString()
    }
}
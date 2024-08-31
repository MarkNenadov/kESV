package com.pythonbyte.kesv

import org.pythonbyte.krux.json.JsonObject

class SearchResult(val reference: String, val content: String) {
    companion object {
        fun create(jsonObject: JsonObject): SearchResult {
            val (reference, content) = jsonObject.getStrings("reference", "content")
            return SearchResult(
                reference,
                content,
            )
        }
    }
}

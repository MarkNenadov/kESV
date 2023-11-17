package com.pythonbyte.kesv

class LookupCache {
    private val textLookupCache = mutableMapOf<String, List<String>>()
    private val htmlLookupCache = mutableMapOf<String, List<String>>()

    fun hasTextValue(lookupValue: String) = lookupValue in textLookupCache

    fun hasHtmlValue(lookupValue: String) = lookupValue in htmlLookupCache

    fun getTextValue(lookupValue: String) = textLookupCache[lookupValue]

    fun getHtmlValue(lookupValue: String) = htmlLookupCache[lookupValue]

    fun addTextValue(key: String, value: List<String>) {
        textLookupCache.put(key, value)
    }

    fun addHtmlValue(key: String, value: List<String>) {
        htmlLookupCache.put(key, value)
    }
}

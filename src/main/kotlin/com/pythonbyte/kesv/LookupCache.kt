package com.pythonbyte.kesv

const val MAXIMUM_LOOKUP_CACHE_SIZE = 25

class LookupCache {
    private val textLookupCache = LinkedHashMap<String, List<String>>()
    private val htmlLookupCache = LinkedHashMap<String, List<String>>()

    fun hasTextValue(lookupValue: String) = lookupValue in textLookupCache

    fun hasHtmlValue(lookupValue: String) = lookupValue in htmlLookupCache

    fun getTextValue(lookupValue: String) = textLookupCache[lookupValue]

    fun getHtmlValue(lookupValue: String) = htmlLookupCache[lookupValue]

    fun addTextValue(key: String, value: List<String>) {
        if (textLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = textLookupCache.keys.first()
            textLookupCache.remove(firstKey)
        }
        textLookupCache[key] = value
    }

    fun addHtmlValue(key: String, value: List<String>) {
        if (htmlLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = htmlLookupCache.keys.first()
            htmlLookupCache.remove(firstKey)
        }
        htmlLookupCache[key] = value
    }
}

package com.pythonbyte.kesv

import org.pythonbyte.krux.types.ByteArrayCache
import org.pythonbyte.krux.types.StringArrayCache

const val MAXIMUM_LOOKUP_CACHE_SIZE = 25

class LookupCache(private val allowCaching: Boolean = true) {
    private val textLookupCache = StringArrayCache()
    private val htmlLookupCache = StringArrayCache()
    private val searchLookupCache = LinkedHashMap<String, List<SearchResult>>()
    private val mp3BytesLookupCache = ByteArrayCache()

    fun hasTextValue(lookupValue: String): Boolean {
        requireAllowCaching()

        return lookupValue in textLookupCache
    }

    fun hasHtmlValue(lookupValue: String): Boolean {
        requireAllowCaching()

        return lookupValue in htmlLookupCache
    }

    fun hasMp3BytesValue(lookupValue: String): Boolean {
        requireAllowCaching()

        return lookupValue in mp3BytesLookupCache
    }

    fun getTextValue(lookupValue: String): List<String>? {
        requireAllowCaching()

        return textLookupCache[lookupValue]
    }

    fun getHtmlValue(lookupValue: String): List<String>? {
        requireAllowCaching()

        return htmlLookupCache[lookupValue]
    }

    fun getMp3BytesValue(lookupValue: String): ByteArray? {
        requireAllowCaching()

        return mp3BytesLookupCache[lookupValue]
    }

    fun addTextValue(
        key: String,
        value: List<String>,
    ) {
        requireAllowCaching()

        if (textLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = textLookupCache.keys.first()
            textLookupCache.remove(firstKey)
        }
        textLookupCache[key] = value
    }

    fun addHtmlValue(
        key: String,
        value: List<String>,
    ) {
        requireAllowCaching()

        if (htmlLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = htmlLookupCache.keys.first()
            htmlLookupCache.remove(firstKey)
        }
        htmlLookupCache[key] = value
    }

    fun addMp3BytesValue(
        key: String,
        value: ByteArray,
    ) {
        requireAllowCaching()

        if (mp3BytesLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = mp3BytesLookupCache.keys.first()
            mp3BytesLookupCache.remove(firstKey)
        }
        mp3BytesLookupCache[key] = value
    }

    fun hasSearchValue(searchText: String): Boolean {
        requireAllowCaching()

        return searchText in searchLookupCache
    }

    fun getSearchValue(searchText: String): List<SearchResult>? {
        requireAllowCaching()

        return searchLookupCache[searchText]
    }

    fun addSearchValue(
        key: String,
        value: List<SearchResult>,
    ) {
        requireAllowCaching()

        if (searchLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = searchLookupCache.keys.first()
            searchLookupCache.remove(firstKey)
        }
        searchLookupCache[key] = value
    }

    private fun requireAllowCaching() {
        require(allowCaching) { "Cache lookups not allowed" }
    }
}

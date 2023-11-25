package com.pythonbyte.kesv

const val MAXIMUM_LOOKUP_CACHE_SIZE = 25

class LookupCache(private val allowCaching: Boolean = true) {
    private val textLookupCache = LinkedHashMap<String, List<String>>()
    private val htmlLookupCache = LinkedHashMap<String, List<String>>()
    private val searchLookupCache = LinkedHashMap<String, List<SearchResult>>()
    private val mp3BytesLookupCache = LinkedHashMap<String, ByteArray>()

    fun hasTextValue(lookupValue: String): Boolean {
        require(allowCaching) { "Cache lookups not allowed" }

        return lookupValue in textLookupCache
    }

    fun hasHtmlValue(lookupValue: String): Boolean {
        require(allowCaching) { "Cache lookups not allowed" }

        return lookupValue in htmlLookupCache
    }

    fun hasMp3BytesValue(lookupValue: String): Boolean {
        require(allowCaching) { "Cache lookups not allowed" }

        return lookupValue in mp3BytesLookupCache
    }

    fun getTextValue(lookupValue: String): List<String>? {
        require(allowCaching) { "Cache lookups not allowed" }

        return textLookupCache[lookupValue]
    }

    fun getHtmlValue(lookupValue: String): List<String>? {
        require(allowCaching) { "Cache lookups not allowed" }

        return htmlLookupCache[lookupValue]
    }

    fun getMp3BytesValue(lookupValue: String): ByteArray? {
        require(allowCaching) { "Cache lookups not allowed" }

        return mp3BytesLookupCache[lookupValue]
    }

    fun addTextValue(
        key: String,
        value: List<String>,
    ) {
        require(allowCaching) { "Cache lookups not allowed" }

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
        require(allowCaching) { "Cache lookups not allowed" }

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
        require(allowCaching) { "Cache lookups not allowed" }

        if (mp3BytesLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = mp3BytesLookupCache.keys.first()
            mp3BytesLookupCache.remove(firstKey)
        }
        mp3BytesLookupCache[key] = value
    }

    fun hasSearchValue(searchText: String): Boolean {
        require(allowCaching) { "Cache lookups not allowed" }

        return searchText in searchLookupCache
    }

    fun getSearchValue(searchText: String): List<SearchResult>? {
        require(allowCaching) { "Cache lookups not allowed" }

        return searchLookupCache[searchText]
    }

    fun addSearchValue(
        key: String,
        value: List<SearchResult>,
    ) {
        require(allowCaching) { "Cache lookups not allowed" }

        if (searchLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = searchLookupCache.keys.first()
            searchLookupCache.remove(firstKey)
        }
        searchLookupCache[key] = value
    }
}

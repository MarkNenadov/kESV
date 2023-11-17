package com.pythonbyte.kesv

const val MAXIMUM_LOOKUP_CACHE_SIZE = 25

class LookupCache {
    private val textLookupCache = LinkedHashMap<String, List<String>>()
    private val htmlLookupCache = LinkedHashMap<String, List<String>>()
    private val searchLookupCache = LinkedHashMap<String, List<SearchResult>>()

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

    fun hasSearchValue(searchText: String) = searchText in searchLookupCache

    fun getSearchValue(searchText: String) = searchLookupCache[searchText]
    fun addSearchValue(key: String, value: List<SearchResult>) {
        if (searchLookupCache.size > MAXIMUM_LOOKUP_CACHE_SIZE) {
            val firstKey = searchLookupCache.keys.first()
            searchLookupCache.remove(firstKey)
        }
        searchLookupCache[key] = value
    }
}

package com.pythonbyte.kesv

interface BibleLookupService {
    fun mp3Bytes(
        lookupValue: String,
        useCache: Boolean = false,
    ): ByteArray

    fun text(
        lookupValue: String,
        useCache: Boolean = false,
    ): List<String>

    fun html(
        lookupValue: String,
        useCache: Boolean = false,
    ): List<String>

    fun searchText(
        searchText: String,
        useCache: Boolean = false,
    ): List<SearchResult>

    fun randomVerse(): String

    fun randomVerses(resultCount: Int): List<String>
}

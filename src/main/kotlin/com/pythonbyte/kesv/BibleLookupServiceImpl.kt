package com.pythonbyte.kesv

import com.squareup.okhttp.Headers
import org.pythonbyte.krux.http.HttpResponse
import org.pythonbyte.krux.http.buildGetRequest
import org.pythonbyte.krux.http.sendRequest
import org.pythonbyte.krux.url.utf8UrlValue

const val ESV_API_URL_PREFIX = "https://api.esv.org/"
const val PASSAGE_AUDIO_ENDPOINT = "v3/passage/audio/"
const val SEARCH_TEXT_ENDPOINT = "v3/passage/search/"
const val PASSAGE_TEXT_ENDPOINT = "v3/passage/text/"
const val PASSAGE_HTML_ENDPOINT = "v3/passage/html/"

const val MAX_RANDOM_VERSES_PER_REQUEST = 10

class BibleLookupServiceImpl(
    private val privateKey: String,
    private val allowCaching: Boolean = true,
) : BibleLookupService {
    private val lookupCache = LookupCache(allowCaching)

    private fun generateHeaders(
        privateKey: String,
        contentType: String? = "text/plain",
    ): Headers {
        return Headers.Builder().apply {
            add("Authorization", "Token $privateKey")
            add("Content-Type", contentType)
        }.build()
    }

    private inline fun <T> makeRequest(
        endpoint: String,
        lookupValue: String,
        contentType: String? = "text/plain",
        responseHandler: (HttpResponse) -> T,
    ): T {
        val passage = lookupValue.utf8UrlValue()
        val url = "$ESV_API_URL_PREFIX$endpoint?q=$passage"

        val request =
            buildGetRequest(
                url,
                generateHeaders(privateKey, contentType),
            )
        val response = sendRequest(request)

        return if (response.isOk()) {
            responseHandler(response)
        } else {
            throw BibleLookupException("$endpoint lookup failed")
        }
    }

    override fun mp3Bytes(
        lookupValue: String,
        useCache: Boolean,
    ): ByteArray {
        require(lookupValue != "") { "Request requires a lookupValue"  }

        if (useCache && lookupCache.hasMp3BytesValue(lookupValue)) {
            return lookupCache.getMp3BytesValue(lookupValue)!!
        }

        val result =
            makeRequest(PASSAGE_AUDIO_ENDPOINT, lookupValue, "audio/mp3") {
                it.getBytes()
            }

        if (useCache) {
            lookupCache.addMp3BytesValue(lookupValue, result)
        }

        return result
    }

    override fun text(
        lookupValue: String,
        useCache: Boolean,
    ): List<String> {
        require(lookupValue != "") { "Request requires a lookupValue"  }

        if (useCache && lookupCache.hasTextValue(lookupValue)) {
            return lookupCache.getTextValue(lookupValue)!!
        }

        val result =
            makeRequest(PASSAGE_TEXT_ENDPOINT, lookupValue) {
                it.getJsonObject().getStringArray("passages")
            }

        if (useCache) {
            lookupCache.addTextValue(lookupValue, result)
        }

        return result
    }

    override fun html(
        lookupValue: String,
        useCache: Boolean,
    ): List<String> {
        require(lookupValue != "") { "Request requires a lookupValue"  }

        if (useCache && lookupCache.hasHtmlValue(lookupValue)) {
            return lookupCache.getHtmlValue(lookupValue)!!
        }

        val result =
            makeRequest(PASSAGE_HTML_ENDPOINT, lookupValue, "text/html") {
                it.getJsonObject().getStringArray("passages")
            }

        if (useCache) {
            lookupCache.addHtmlValue(lookupValue, result)
        }

        return result
    }

    override fun searchText(
        searchText: String,
        useCache: Boolean,
    ): List<SearchResult> {
        if (useCache && lookupCache.hasSearchValue(searchText)) {
            return lookupCache.getSearchValue(searchText)!!
        }

        val result =
            makeRequest(SEARCH_TEXT_ENDPOINT, searchText) { response ->
                response.getJsonObject().getArray("results").map {
                    SearchResult.create(it)
                }
            }

        if (useCache) {
            lookupCache.addSearchValue(searchText, result)
        }

        return result
    }

    override fun randomVerse(): String {
        return try {
            val bibleData = getBibleDataJsonObject()
            val randomBibleBookName = getRandomBibleBookName(bibleData)
            val randomBook = bibleData.getObject(randomBibleBookName)
            val randomChapterNumber = getRandomChapterNumber(randomBook)
            val randomVerseNumber = getRandomVerseNumber(randomBook, randomChapterNumber)

            text("$randomBibleBookName $randomChapterNumber:$randomVerseNumber")[0]
        } catch (e: Exception) {
            throw BibleLookupException("An error occurred: ${e.message}")
        }
    }

    override fun randomVerses(resultCount: Int): List<String> {
        require(resultCount <= MAX_RANDOM_VERSES_PER_REQUEST) {
            "for randomVerses(), resultCount must be 10 or less"
        }

        return List(resultCount) { randomVerse() }
    }
}

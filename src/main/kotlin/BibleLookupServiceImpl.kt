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

class BibleLookupServiceImpl(private val privateKey: String) : BibleLookupService {
    private fun generateHeaders(privateKey: String, contentType: String): Headers {
        return Headers.Builder().apply {
            add("Authorization", "Token $privateKey")
            add("Content-Type", contentType)
        }.build()
    }

    private inline fun <T> makeRequest(
        endpoint: String,
        lookupValue: String,
        contentType: String,
        responseHandler: (HttpResponse) -> T,
    ): T {
        val passage = utf8UrlValue(lookupValue)
        val url = "$ESV_API_URL_PREFIX$endpoint?q=$passage"

        val request = buildGetRequest(url, generateHeaders(privateKey, contentType))
        val response = sendRequest(request)

        return if (response.isOk()) {
            responseHandler(response)
        } else {
            throw BibleLookupException("$endpoint lookup failed")
        }
    }

    override fun getMp3Bytes(lookupValue: String): ByteArray {
        return makeRequest(PASSAGE_AUDIO_ENDPOINT, lookupValue, "audio/mp3") {
            it.getBytes()
        }
    }

    override fun getText(lookupValue: String): List<String> {
        return makeRequest(PASSAGE_TEXT_ENDPOINT, lookupValue, "text/plain") {
            it.getJsonObject().getStringArray("passages")
        }
    }

    override fun getHtml(lookupValue: String): List<String> {
        return makeRequest(PASSAGE_HTML_ENDPOINT, lookupValue, "text/html") {
            it.getJsonObject().getStringArray("passages")
        }
    }

    override fun searchText(searchText: String): List<SearchResult> {
        return makeRequest(SEARCH_TEXT_ENDPOINT, searchText, "text/plain") {
            it.getJsonObject().getArray("results").map { SearchResult.create(it) }
        }
    }

    override fun getRandomVerse(): String {
        return try {
            val bibleData = getBibleDataJsonObject()
            val randomBibleBookName = getRandomBibleBookName(bibleData)
            val randomBook = bibleData.getObject(randomBibleBookName)
            val randomChapterNumber = getRandomChapterNumber(randomBook)
            val randomVerseNumber = getRandomVerseNumber(randomBook, randomChapterNumber)

            getText("$randomBibleBookName $randomChapterNumber:$randomVerseNumber")[0]
        } catch (e: Exception) {
            throw BibleLookupException("An error occurred: ${e.message}")
        }
    }
}

import com.squareup.okhttp.Headers

import org.pythonbyte.krux.http.buildGetRequest
import org.pythonbyte.krux.http.sendRequest
import org.pythonbyte.krux.url.utf8UrlValue

const val ESV_API_URL_PREFIX = "https://api.esv.org/"
const val PASSAGE_AUDIO_ENDPOINT = "v3/passage/audio/"
const val SEARCH_TEXT_ENDPOINT = "v3/passage/search/"
const val PASSAGE_TEXT_ENDPOINT = "v3/passage/text/"

class BibleLookupServiceImpl(private val privateKey: String): BibleLookupService {
    private  fun generateHeaders( privateKey: String ): Headers {
        return Headers.Builder().add( "Authorization", "Token $privateKey" ).add( "Content-Type", "audio/mp3" ).build()
    }

    override fun getMp3Bytes(lookupValue: String): ByteArray {
        val passage = utf8UrlValue( lookupValue)
        val url = "$ESV_API_URL_PREFIX$PASSAGE_AUDIO_ENDPOINT?q=$passage"

        val request = buildGetRequest(url, generateHeaders( privateKey ) )

        val response = sendRequest( request )

        if ( response.isOk() ) {
            return response.getBytes()
        } else {
            throw Exception( "getMp3Bytes lookup failed" )
        }
    }

    override fun getText(lookupValue: String): List<String> {
        val passage = utf8UrlValue( lookupValue)
        val url = "$ESV_API_URL_PREFIX$PASSAGE_TEXT_ENDPOINT?q=$passage"

        val request = buildGetRequest(url, generateHeaders( privateKey ) )

        val response = sendRequest( request )

        if ( response.isOk() ) {
            return response.getJsonObject().getStringArray( "passages")
        } else {
            throw Exception( "getText lookup failed" )
        }
    }

    override fun searchText(searchText: String): List<SearchResult> {
        val searchTextValue = utf8UrlValue( searchText)
        val url = "$ESV_API_URL_PREFIX$SEARCH_TEXT_ENDPOINT?q=$searchTextValue"

        val request = buildGetRequest(url, generateHeaders( privateKey ) )

        val response = sendRequest( request )

        if ( response.isOk() ) {
            return response.getJsonObject().getArray( "results").map{ SearchResult.create( it ) }
        } else {
            throw Exception( "searchText lookup failed" )
        }
    }

    override fun getRandomVerse(): String {
        try {
            val bibleData = getBibleDataJsonObject()
            val randomBibleBookName = getRandomBibleBookName( bibleData )
            val randomBook = bibleData.getObject( randomBibleBookName )
            val randomChapterNumber = getRandomChapterNumber( randomBook )
            val randomVerseNumber = getRandomVerseNumber( randomBook, randomChapterNumber )

            val verses = getText("$randomBibleBookName $randomChapterNumber:$randomVerseNumber")

            return verses[0]
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        }

        return ""
    }

}
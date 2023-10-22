import com.squareup.okhttp.Headers

class BibleLookupService {
    private  fun generateHeaders( privateKey: String ): Headers {
        return Headers.Builder().add( "Authorization", "Token $privateKey" ).add( "Content-Type", "audio/mp3" ).build()
    }

}
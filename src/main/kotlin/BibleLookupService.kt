interface BibleLookupService {
    fun mp3Bytes(lookupValue: String): ByteArray

    fun text(lookupValue: String): List<String>

    fun html(lookupValue: String): List<String>

    fun searchText(searchText: String): List<SearchResult>

    fun randomVerse(): String
}

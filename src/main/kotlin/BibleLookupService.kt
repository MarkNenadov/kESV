interface BibleLookupService {
    fun getMp3Bytes(lookupValue: String): ByteArray

    fun getText(lookupValue: String): List<String>


    fun getHtml(lookupValue: String): List<String>

    fun searchText(searchText: String): List<SearchResult>

    fun getRandomVerse(): String
}
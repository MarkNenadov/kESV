import org.pythonbyte.krux.json.JsonObject

class SearchResult(val reference: String, val content: String ) {
    companion object {
        fun create(jsonObject: JsonObject): SearchResult {
            return SearchResult( jsonObject.getString("reference"), jsonObject.getString("content") )
        }
    }
}
package com.pythonbyte.kesv

import org.pythonbyte.krux.json.JsonObject
import kotlin.random.Random

fun getBibleDataJsonObject(): JsonObject {
    val bibleData = BibleLookupServiceImpl::class.java.getResource("bibleData.json")?.readText() ?: ""

    return JsonObject(bibleData)
}

fun getRandomBibleBookName(bibleData: JsonObject): String {
    val bibleBookNames = bibleData.getKeys()

    return bibleBookNames[Random.nextInt(bibleBookNames.size)]
}

fun getRandomChapterNumber(book: JsonObject): Int {
    val chapterList = book.getKeys()
    return chapterList[Random.nextInt(chapterList.size)].toInt()
}

fun getVersesInChapter(book: JsonObject, chapterNumber: Int): Int {
    return book.getString(chapterNumber.toString()).toInt()
}

fun getRandomVerseNumber(book: JsonObject, chapterNumber: Int): Int {
    return Random.nextInt(getVersesInChapter(book, chapterNumber) + 1)
}

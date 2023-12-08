package com.pythonbyte.kesv

import org.pythonbyte.krux.files.getAsString
import org.pythonbyte.krux.files.resourceToInputStream
import org.pythonbyte.krux.json.JsonObject
import kotlin.random.Random

fun getBibleDataJsonObject(): JsonObject {
    val bibleDataStream = resourceToInputStream("bibleData.json")

    return JsonObject(bibleDataStream.getAsString())
}

fun getRandomBibleBookName(bibleData: JsonObject): String = bibleData.getKeys().random()

fun getRandomChapterNumber(book: JsonObject): Int = book.getKeys().random().toInt()

fun getVersesInChapter(
    book: JsonObject,
    chapterNumber: Int,
): Int {
    return book.getString(chapterNumber.toString()).toInt()
}

fun getRandomVerseNumber(
    book: JsonObject,
    chapterNumber: Int,
): Int {
    return Random.nextInt(getVersesInChapter(book, chapterNumber) + 1)
}

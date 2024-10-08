
# kESV2

A wrapper library for the Crossway ESV Bible API

To use this library you will have to apply for an ESV API key with Crossway (https://api.esv.org/).

All 4 endpoints are covered (Passage Text, Passage Html, Passage Audio, and Passage Search).

## Recent Releases

* v2.0 (June 29, 2024) - https://github.com/MarkNenadov/kESV/releases/download/v2.0/kESV-2.0.jar
* v2.1 (August 31, 2024) - https://github.com/MarkNenadov/kESV/releases/download/v2.0/kESV-2.1.jar

## Usage

Note: The following examples are showing explicit typing for the purpose of demonstration.

```
val service = BibleLookupServiceImp("YOUR API KEY"")

// passage audio (note: useCache is optiona and defaults to false)
val mp3Bytes: ByteArray = service.mp3Bytes("John 3:16",useCache=true)

// passage text (note: useCache is optiona and defaults to false)
val text: List<String> = service.text("John 3:16-17",useCache=true)

// passage html (note: useCache is optiona and defaults to false)
val html: List<String> = service.html("John 3:16-17",useCache=true)

// passage search (note: useCache is optiona and defaults to false)
val searchResult = bibleLookupService.searchText("merciful",useCache=true)
searchResult.forEach { println("${it.reference} -> ${it.content}") }

// random verse
val randomVerse: Stringh: String = service.randomVerse()

// 4 random verses (param must be 10 or less)
val randomVerses: Stringh: String = service.randomVerses(4)

```

## TODO

* way to limit random verse to NT or OT

## Tech

Kotlin, IntelliJ, Maven

<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=kotlin,idea,maven" />
  </a>
</p>

## Thanks

* Thanks to Ben Flannery (https://github.com/oflannabhra) for bible.json (https://github.com/oflannabhra/bible.json), which was used to provide the data necessary for the random Bible verse functionality
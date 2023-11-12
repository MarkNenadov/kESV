
# kESV2

A wrapper library for the Crossway ESV Bible API

To use this library you will have to apply for an ESV API key with Crossway (https://api.esv.org/).

All 4 endpoints are covered (Passage Text, Passage Html, Passage Audio, and Passage Search).

## Releases

v1.0 (October 26, 2023) - https://github.com/MarkNenadov/kESV/releases/download/v1.0/kESV-1.0.jar

## Usage

Note: The following examples are showing explicit typing for the purpose of demonstration.

```
val service = BibleLookupServiceImp("YOUR API KEY"")

// passage audio
val mp3Bytes: ByteArray = service.getMp3Bytes("John 3:16")

// passage text
val text: List<String> = service.getText("John 3:16-17")

// passage html
val html: List<String> = service.getHtml("John 3:16-17")

// passage search
val searchResult = bibleLookupService.searchText("merciful")
searchResult.forEach { println("${it.reference} -> ${it.content}") }

// random verse
val randomVerse: Stringh: String = service.getRandomVerse()
```

## Thanks

* Thanks to Ben Flannery (https://github.com/oflannabhra) for bible.json (https://github.com/oflannabhra/bible.json), which was used to provide the data necessary for the random Bible verse functionality

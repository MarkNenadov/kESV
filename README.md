== kESV2 ==

A wrapper library for the Crossway ESV Bible API

To use this library you will have to apply for an ESV API key with Crossway (https://api.esv.org/).

Usage
-----

val service = BibleLookupServiceImp("YOUR API KEY"")

val mp3Bytes = service.getMp3Bytes("John 3:16")
val text = service.getText("John 3:16")

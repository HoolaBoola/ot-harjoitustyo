Musiikkisoitin
===========

Sovelluksen avulla käyttäjien on mahdollista lisätä äänitiedostoja musiikkikirjastoon, joista niitä voi lisätä soittolistoihin ja soittaa niitä.

Dokumentaatio
------------
[Käyttöohje](documentation/user_guide.md) 

[Vaatimusmäärittely](documentation/requirement_analysis.md)

[Arkkitehtuurikuvaus](documentation/architecture.md)

[Testausdokumentti](documentation/testing.md)

[Työaikakirjanpito](documentation/hours.md)

Releaset
----------

[Viikko 7](https://github.com/HoolaBoola/ot-harjoitustyo/releases/tag/v1.0)

[Viikko 6](https://github.com/HoolaBoola/ot-harjoitustyo/releases/tag/viikko6)


Komentorivikomennot
----------

# Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

GitHubin Actioneissa tapahtuva testaus ei tällä hetkellä mene läpi, sillä SongPlayerTest vaatii musiikin soittoon sellaisia resursseja, joita ilmeisesti palvelimella ei ole.

# JavaDoc-tiedostojen generointi

JavaDoc-tiedot voi generoida komennolla

`mvn javadoc:javadoc`

# Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston MusicPlayer-1.0-SNAPSHOT.jar


# Ohjelman käynnistäminen

Ohjelman voi käynnistää joko syöttämällä projektin lähdekansion juuressa komennon

`mvn compile exec:java`

tai halutessaan voi käynnistää generoidun JAR-tiedoston komennolla

`java -jar polku/tiedostoon/MusicPlayer-1.0-SNAPSHOT.jar`

Ensimmäisellä käynnistyskerralla ohjelma alustaa kansioon `player.db`-tiedoston

Musiikkisoitin
===========

Sovelluksen avulla käyttäjien on mahdollista lisätä äänitiedostoja musiikkikirjastoon, joista niitä voi lisätä soittolistoihin ja soittaa niitä.

Dokumentaatio
------------
Käyttöohje (ei valmis)

[Vaatimusmäärittely](documentation/requirement_analysis.md)

Arkkitehtuurikuvaus (ei valmis)

Testausdokumentti (ei valmis)

[Työaikakirjanpito](documentation/hours.md)

Releaset
----------

Ei vielä releaseja

Komentorivikomennot
----------

# Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

# Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston MusicPlayer.jar


Javan ja Mavenin asennusohjeita Macille Homebrew'n kautta
--------

Homebrew on Linuxin pakettimanagereita vastaava pakettimanageri MacOS-käyttöjärjestelmälle. Nämä ohjeet toimivat ainakin MacOS:n versiolle 10.15. Asennusohjeet Homebrew'lle.

# Javan asennus

Homebrew'n asennuksen jälkeen Javan saa asennettua Macille yksinkertaisesti esimerkiksi komennolla

`brew install adoptopenjdk`

# Mavenin asennus ja paluu Javan versioon 11

Mavenin saa asennettua komennolla

`brew install maven`

Tällöin Mavenin oletuksena käyttämä Java-versio on Java 15. Java-versioon 11 päästään asentamalla Java 11 komennolla

`brew install java11`

Lisäksi täytyy osoittaa Mavenille Javan versio 11. Mavenin versiolla 3.6.3_1 tämä tapahtuu muokkaamalla tiedostoa: /usr/local/Cellar/maven/3.6.3_1/bin/mvn esim. nanolla komennolla

`sudo nano /usr/local/Cellar/maven/3.6.3_1/bin/mvn`

HUOM Muista tarkistaa mikä versio Mavenista asentui ja muokkaa tiedostopolkuun oikea versio version 3.6.3_1 tilalle

Muokkaa rivi

`JAVA_HOME="${JAVA_HOME:-/usr/local/opt/openjdk/libexec/openjdk.jdk/Contents/Home}" exec "/usr/local/Cellar/maven/`

Muotoon

`JAVA_HOME="${JAVA_HOME:-/usr/local/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home}" exec "/usr/local/Cellar/maven/`

Eli muokkaa polkuun openjdk `openjdk@11` ja tallenna tiedosto. Nyt voit tarkistaa komennolla `mvn --version`, että Maven käyttää Javan versiota 11.

Käyttöjärjestelmän Java version vaihtaminen onnistuu esimerkiksi lisäämällä tiedoston: `~/.zshrc` (vanhemmilla MacOS-käyttöjärjestelmillä `~/.bashrc`) loppuun rivi

`export JAVA_HOME=/usr/local/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home/`

Muista käynnistää lisäyksen jälkeen terminaali uudestaan, jolloin komento `java --version` näyttää versioksi 11.

Käyttöohje
=====

Lataa uusin versio ohjelmasta [releaseista](https://github.com/HoolaBoola/ot-harjoitustyo/releases)

----

Ohjelman käynnistäminen
----

Ohjelman voi käynnistää komennolla

 `java -jar [tiedoston nimi]`

Ohjelma alustaa ensimmäisellä käynnistyskerralla tietokannan automaattisesti.

Käynnistyttyään ohjelma näyttää tältä:

![Tilanne alussa](guide_pics/alku.png)


----
Kappaleen lisääminen sovellukseen
-----

Sovellukseen voi lisätä kappaleita painamalla Songs-välilehdessä ylhäällä keskellä olevaa "+"-nappia. Silloin aukeaa kappaleen luonti -näkymä:

![Uusi kappale](guide_pics/new_song.png)

Uusi kappale vaatii mp3-muotoisen tiedoston, jota soittaa. Tämän saa haettua tiedostojärjestelmästä klikkaamalla "..."-painiketta. Napin painaminen avaa tiedostovalikon:

![Valitse tiedosto](guide_pics/choose_file.png)

Syötettyäsi kappaleen nimen, artistin ja valittuasi tiedoston voit painaa "OK"-nappia ikkunan oikeassa alanurkassa, jolloin sovellus luo kappaleen ja tallettaa tietokantaan.

----
Kappaleen muokkaaminen
-----

Kappaleen muokkaaminen tapahtuu klikkaamalla Songs-välilehdessä kappalelistassa olevan kappaleen oikeassa reunassa olevaa "Actions"-nappia. Tällöin aukeaa lisävalintoja, joista yksi on "Edit":

![Muokkausnappi](guide_pics/edit_song_button.png)

Napin painaminen avaa kappaleenmuokkausnäkymän, joka toimii täsmälleen samalla tavalla kuin aiemmin esitelty [kappaleenluontinäkymä](#kappaleen-lisääminen-sovellukseen).

----
Kappaleen poistaminen
-----

Kappaleen voi poistaa klikkaamalla Songs-välilehdessä jonkin kappaleen "Actions"-valikosta löytyvästä "Delete"-painikkeesta:

![Poistonappi](guide_pics/delete_song_button.png)

Sovellus kysyy tämän jälkeen varmistusta poistamiselle. Jos painat "OK", sovellus poistaa kappaleen lopullisesti.

----
Kappaleen soittaminen
-----

Sovelluksessa voi soittaa kappaleita painamalla kappalelistauksessa "Play"-nappia jonkin kappaleen kohdalla. Tällöin sovellus alkaa automaattisesti soittaa kyseistä kappaletta. Soiton voi pysäyttää alareunassa sijaitsevalla "Pause"-napilla, joka muuttuu sen jälkeen "Play"-napiksi.

![Pause-nappi](guide_pics/play_pause_button.png)

----
Soittolistan luominen
----

Soittolistojen luomista varten täytyy ensin siirtyä yläreunassa löytyvästä "Playlists"-napista toiseen välilehteen. Nyt auenneessa näkymässä on yläreunassa "+"-nappi, jota klikkaamalla pääsee soittolistan luomisnäkymään. 

![Lisää soittolista](guide_pics/new_playlist_button.png)

Luomisnäkymä toimii samalla tavalla kuin kappaleiden luomiseen tarkoitettu [näkymä](#kappaleen-lisääminen-sovellukseen), mutta soittolistaa varten tarvitsee syöttää vain listalle nimi.

-----
Soittolistan muokkaaminen
----

Luodut soittolistat näkyvät "Playlists"-välilehdessä haitarilistana. Jos klikkaat jotain listaa, se aukeaa ja näyttää kolme nappia, joiden alla on näytetään listalle lisätyt kappaleet:

![Soittolistan vaihtoehdot](guide_pics/playlist_options.png)

Soittolistaa voi muokata painamalla kuvassa näkyvää "Edit"-nappia. Tällöin aukeaa erillinen soittolistan muokkausnäkymä, josta voi vaihtaa listan nimeä.

-----
Soittolistan poistaminen
----

Soittolistan voi poistaa edellisessä kuvassa näkyvästä "Delete"-napista painamalla. Tällöin aukeaa varmistusikkuna, jossa painamalla "OK"-painiketta sovellus poistaa soittolistan tietokannasta.

-----
Kappaleen lisääminen soittolistalle
----

Kappaleen voi lisätä soittolistalle "Songs"-välilehdessä. Avaa haluamasi kappaleen "Actions"-valikko. Auenneessa valikossa ylimpänä vaihtoehtona on "Add to playlist"-jonka oikeasta reunasta painaminen avaa listan soittolistoista (huom! JavaFX:n hassujen toiminnallisuuksien vuoksi pitää nimenomaan painaa oikeaa reunaa, muuten lista saattaa kadota heti).

![Kappaleen lisääminen soittolistalle](guide_pics/song_to_playlist.png)

Klikkaa sitä soittolistaa, jolle haluat kappaleen lisätä.

-----
Kappaleen poistaminen soittolistalta
----

Kappaleen voi poistaa soittolistalta "Playlist"-välilehdestä. Avaa soittolista painamalla siitä, jolloin aukeaa lista kappaleista, jotka on lisätty listalle.

![Listan kappaleet](guide_pics/playlist_options.png)

Klikkaamalla haluamasi kappaleen "Delete"-nappia kappale poistuu soittolistalta.

-----
Soittolistan kappaleiden soittaminen
----

Yksittäisten kappaleiden soittamisen lisäksi sovelluksessa voi toistaa kokonaisia soittolistoja. Avaa haluamasi soittolista "Playlist"-välilehdessä painamalla siitä. Auenneessa valikossa painamalla "Play"-nappia voit soittaa koko listan. Kappaleet soivat satunnaisessa järjestyksessä.

![Soita listan kappaleet](guide_pics/playlist_options.png)


-----
Palaaminen edelliseen kappaleeseen
----

Soittolistaa soittaessa sovellus muistaa jo soitetut kappaleet ja tulevat kappaleet. Painamalla alareunan "Previous"-nappia sovellus palaa edelliseen soitettuun kappaleeseen (jos sellainen on). Painamalla "Next"-nappia voit ohittaa soivan kappaleen ja siirtyä seuraavaan.

![Eteen ja taakse](guide_pics/control_buttons.png)


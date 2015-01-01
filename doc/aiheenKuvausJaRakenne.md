# Aiheen kuvaus ja rakenne

## Aihe

Toteutetaan ohjelmointikieli tulkki joka mahdollistaa Ernu-kielen lähdekoodin
ajamisen. Ernu-kielen tulee olla helppolukuista ja sen ominaisuuksien sopivan
rajattuja. Kielen ulkoasun tulee muistuttaa Lua-kieltä, mutta lisäyksiä muista
kielistä ja muutenvaan kivalta vaikuttavia ideoita. Koska performanssilla ei
ole väliä Javalabrassa, tehdään kieli aika läskisti.

Sovelluksen tulee pystyä toimimaan muutamassa eri tilassa. Päätilana REPL eli
Read Evaluate Print Loop. Pitäisi myös pystyä ajamaan Ernu-tiedosto suoraa ja
pystyä myös kertomaan erillaisia tärkeitä tai vähemmän tärkeitä tietoja
lähdekoodista jota se käsittelee.

## Käyttäjät

* Ohjelmoija

### Ohjelmoijan toiminnot

* Ajaa tiedostossa oleva ohjelma
* Käynnistää komentorivi kielen interaktiiviseen käyttämiseen
* Näyttää debug tietoja ohjelmasta

## Rakenne

Ohjelman suoritus alkaa komentoriviargumenttien tulkitsemisesta. Käytännössä
kuitenkin `REPL` on kaikkein kiinnostavin lähtököhta ohjelmaan. `REPL` tietää
`Tokenizer`, `ErnuParser` ja jonkun `Environment`. `REPL` saa syötteeksi
käyttäjältä lähdekoodia, joka sitten menee tokenizerille tokenisoitavaksi.
`Token`it saavat tietoonsa mitä tyyppiä ovat `TokenType`ltä. Jokainen tokeni
voi olla vain yhtä tyyppiä kerralla. Seuraavaksi `REPL` sanoo `ErnuParser`ille
että haluaisi saada `Node`n. `ErnuParser` on `Parser`in alaluokka, ja lisää
siihen kaikki kielispesifiset ominaisuudet. `Parser` tietää kasan aliparsereita
jotka tekevät `Node`ja `Token`sta. `Parser` katsoo että mikä parseri vastaa
mitäkin `Token`ia. On kahdenlaisia aliparsereita, `PrefixParser`ta ja
`InfixParser`ta. Näitä sitten kutsutaan saatujen `Token`en mukaan, ja loppujen
lopuksi käteen jää yksi tai useampi `Node`. Nämä annetaan `REPL`lle, ja sitten
`REPL` kutsuu näiden `Node`en `getValue` metodia joka selvittää rekursiivisesti
jonkun arvon, `Node` tyyppiä sekin. Tämä selivitys tapahtuu `Environment`n
avulla. `Environment`t luovat linkitetyn listan, jossa aina symbolin selvitys
tapahtuu rekursiivisesti ylöspäin niin, että aikaisemmissa `Environment`ssa
määritellyt symbolit näkyvät myös alemmissa, tosin immutableina.

Loppujenlopuksi meillä on kädessä jokin `Node` ja sitten se printataan
käyttäjälle.
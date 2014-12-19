# Aiheen kuvaus ja rakenne

## Aihe

Toteutetaan ohjelmointikieli tulkki joka mahdollistaa Ernu-kielen lähdekoodin
ajamisen. Ernu-kielen tulee olla helppolukuista ja sen ominaisuuksien sopivan
rajattuja. Kielen ulkoasun tulee muistuttaa Lua-kieltä, mutta lisäyksiä muista
kielistä ja muutenvaan kivalta vaikuttavia ideoita. Koska performanssilla ei
ole väliä Javalabrassa, tehdään kieli aika läskisti.

Sovelluksen tulee pystyä toimimaan muutamassa eri tilassa. Päätilana REPL eli
Read Evaluate Print Loop. Mutta pitäisi myös tekemää jonkintapaista projekti-
hallintaa ja pystyä myös kertomaan erillaisia tärkeitä tai vähemmän tärkeitä
tietoja lähdekoodista jota se käsittelee.

## Käyttäjät

* Ohjelmoija

### Ohjelmoijan toiminnot

* Ajaa tiedostossa oleva ohjelma
* Käynnistää komentorivi kielen interaktiiviseen käyttämiseen
* Paketoida tiedostot kätevään arkistoon
* Näyttää debug tietoja ohjelmasta
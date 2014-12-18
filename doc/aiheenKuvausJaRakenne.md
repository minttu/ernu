Toteutetaan ohjelmointikieli joka mahdollistaa toimivien ohjelmien
kirjoittamisen. Kompiloidaan JVM bytecodeksi. Kielen tulee näyttää jokseenkin
Lualta mutta vaikutteita muista kielistä joita olen kirjoitellut. Toisin kuin
nimi antaa käsityksen ei ole tarkoitus olla erillainen kieli. Jahka perusjutut
toimivat olisi kiva lisätä joitain funktionaalisen ohjelmoinnin vaikutteita.

Toteutettavan ohjelman tulisi pystyä ajamaan lähdetiedosto ja antamaan
hyödyllistä tietoa lähdetiedostosta.

Lähdetiedoston ajaminen tapahtuu niin, että ensin tekstistä poimitaan kaikki
tokenit. Sitten tokeneista parsetaan puu kuvaamaan ohjelman rakennetta. Sen
jälkeen suoritetaan mahdolliset optimisaatiot puuhun ja sitten generoidaan
JVM bytecode puun perusteella. JVM bytecoden generoinnin valmistuttua
suoritetaan generoitu bytecode.
# Käyttöohje

## Käynnistäminen

Interaktiivisen terminaali komentorivin käynnistäminen

    java -jar ernu-1.0-SNAPSHOT.jar repl

Interaktiivisen graaffisen komentorivin käynnistäminen

    java -jar ernu-1.0-SNAPSHOT.jar grepl

Tiedoston ajaminen

    java -jar ernu-1.0-SNAPSHOT.jar run TIEDOSTO

## Interaktiivinen terminaali komentorivi

Kirjoittamalla näppäimmistölläsi saat merkkejä ilmestymään ruudulle. Kun painat
rivinvaihtoa kirjoittamasi syöte ajetaan. Tästä komentorivistä pääset pois
painamalla `Ctrl-C`. Komentorivi saattaa näyttää virheitä kun kirjoitat jotain
epävalidia tai muutenvaan asiat menee kurjasti.

Huomaa että interaktiivisessa komentorivissä `math` paketti tuodaan ympäristöön
automaattisesti toisin kuin tiedostoja ajettaessa.

Komentorivi alkaa `(^_^)` jos edellinen komento suoritettiin onnistuneesti,
ja `(>_<)` jos edellinen komento ei ollut onnistunut.

## Interaktiivinen graaffinen komentorivi

Koostuu kahdesta tekstilaatikosta, alempaan kirjoitetaan koodia ja tulokset
tulevat ylempää. Voit liikkua komento historiassa `Ctrl-Up` ja `Ctrl-Down`,
lisätä uuden rivin ilman että komento syötetään `Alt-Enter` ja pakottaa
syötteen lähettämisen `Ctrl-Enter`. Alempi tekstilaatikko on hieman punainen
jos edellistä komentoa ei suoritettu onnistuneesti.

## Ernu-kieli

Ernu-kieli muistuttaa syntaksiltaan hieman Lua-kieltä. Tässä on muutama ote
Ernu-kielestä.

### Primitiivit

| Nimi       | Eli                                        | Esim                            |
| ---------- | ------------------------------------------ | ------------------------------- |
| Numero     | Rajattoman suuri rationaalinumero.         | `3.14` `52353580239.4583059345` |
| Merkkijono | Merkkijono joka on ympäröity `"` merkillä. | `"asd"` `"a"` `"asd asd asd"`   |
| Tunniste   | Merkkijono ilman `"` merkkejä.             | `i` `cool?` `asd_ASD_321`       |

### Funktiot

    factorial = function (n)
      if n == 0 do
        1
      else
        n * factorial(n - 1)
      end
    end

Tämä laskee luvun kertoman. Huomaathan että funktio on anonyymi, eli se ei
tiedä omaa nimeään. Sen lisäksi funktiot ovat käypiä arvoja, eli voidaan
esimerkiksi käyttää funktiota sellaisenaan taulukon arvona.

### Taulukot

    a = [0, 1, 2, 3]
    a[1:3] # [1, 2]
    a += 4
    a[::-1] # [4, 3, 2, 1, 0]

Taulukot voivat koostua mistätahansa elementistä ja kaikkien arvojen ei
tarvitse olla samaa tyyppiä. Arvoja voi hakea Python-kielestä tutulla array
slice operaatioilla.

### Konditionaalit ja loopit

    for i in range(1, 101) do
      match
        case i % 15 == 0 do
          print("FizzBuzz")
        case i % 3 == 0 do
          print("Fizz")
        case i % 5 == 0 do
          print("Buzz")
        case true do
          print(i)
      end
    end

Tässä on hieman eriskummallinen konditionaalimuoto, käytännössä se on kuitenkin
vain if/elseif. Käytössä on myös normaali if.

    c = 3
    if c == 3 do
      print("3!")
    else
      print("!3")
    end

Looppien puolesta on saatavilla while ja foreach.

    i = 0
    while i <= 10 do
      print(1/i)
      i += 1
    end


    for i in range(0, 11) do
      print(1/i)
    end

### Luokat

    Cat = class
      init = function (this, name, age)
        this.name = name
        this.age = age
      end
      grow = function (this, val)
        this.age += val
      end
      toString = function (this)
        return "Name: " + this.name + " Age: " + this.age
      end
    end

Tässä luodaan Cat niminen luokka, jolla on luontifunktio `init` ja kaksi
metodia `grow` ja `toString`. Huomaathan että kuten funktiot, myös luokat ovat
oikeastaan anonyymejä Ernu-kielessä.

### Import

    from math import sqrt
    sqrt(2) # 1.414213562373095048801689623503
    import functional
    functional.map([0, 1, 2], sqrt)
    # [5.8207660913467407227E-11, 1, 1.414213562373095048801689623503]

Tässä tuodaan `math` paketista `sqrt` eli neliöjuuri funktio. Voidaan myös
tuoda koko paketti kerralla, kuten tehdään `functional` paketin kanssa, mutta
silloin pitää käyttää `.` objekti notaatiota.
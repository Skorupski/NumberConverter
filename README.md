# NumberConverter

Projekt polega na konwersji wartości w systemie dziesiętnym na system szesnastkowy lub liczbę rzymską.
Wszystkie pliki znajdują się w repozytorium [GitHub](https://github.com/Skorupski/NumberConverter).

## Serwer

Do projektu wykorzystany został serwer Apache TomEE [7.0.5](https://www.apache.org/dyn/closer.cgi/tomee/tomee-7.0.5/apache-tomee-7.0.5-plume.zip). Aby go uruchomić do katalogu 'apache-tomee-plume-7.0.5/webapps' należy przenieść plik [NumberConverter-1.0.war](https://github.com/Skorupski/NumberConverter/blob/master/build/libs/NumberConverter-1.0.war) i wykonać skrypt 'apache-tomee-plume-7.0.5/bin/startup.bat'.

## Klient

Plik wykonywalny znajduje się pod ścieżką [NumberConverter-1.0.jar](https://github.com/Skorupski/NumberConverter/blob/master/build/libs/NumberConverter-1.0.jar). Jeżeli zostanie uruchomiony bez argumentów wyświetli się okno GUI. Po dodaniu '-h' lub '-help' omówione zostaną możliwe komendy. Istnieją trzy możliwości: '-address' powoduje zmianę serwera (domyślnie: http://localhost:8080/NumberConverter-1.0/conversion), '-method' konfiguruję metodę konwersji (domyślnie: roman), '-value' jest wartością dziesiętną (domyślnie: 123). Do uruchomienia programu w konsoli wystarczy zmienić jeden, dowolny argument.

## Dokumentacja

Opis wszystkich klas i metod znajduje się pod adresem [JavaDoc](http://htmlpreview.github.io/?https://github.com/Skorupski/NumberConverter/blob/master/build/docs/javadoc/index.html).

## Szybki start

Do konfiguracji i uruchomienia programu wystarczy plik [EXECUTE.jar](https://github.com/Skorupski/NumberConverter/blob/master/EXECUTE.jar). Niestety, z powodu braku czasu był on tylko testowany pod Windowsem.

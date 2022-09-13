# ITCenterTest
Weather app

W ramach zadania proszę o przygotowanie prostej aplikacji do sprawdzania pogody w języku Kotlin. 

 

Ogólny schemat aplikacji to 2 ekrany – jeden pozwalający wpisać nazwę miasta, ulicy, kodu pocztowego itp., a drugi wyświetlający wyniki wyszukania, np. opis pogody, temperatura w C itd. dodatkowo wyszukiwarka powinna tworzyć historię ostatnich wyszukiwań i podpowiadać je przy kolejnym razie.

Dodać mapę na której będzie zaznaczone wyszukane miejsce.

Wyniki wyszukiwania powinny zostać zapisane do lokalnej bazy, a następnie przy ponownej próbie wyszukania poprzednio już wyszukiwanego hasła pobrane z bazy zamiast z internetu, szczególnie w przypadku braku połączenia z internetem.

 

Rzeczy, na które szczególnie będziemy zwracać uwagę to:

    Architektura (MVVM, RxJava/Coroutines)
    Nawigacja (Navigation component)
    Zapytania do API (Retrofit)
    Dependency injection (Dagger)
    Obsługa cachowania danych w bazie (Room)
    Czytelność kodu
    Walidacja wprowadzanych danych
    Obsługa błędów

 

Aplikacja może być w języku angielskim lub polskim, kod tylko w języku angielskim. Layout aplikacji nie będzie oceniany.

Poniżej lista sugerowanych API z których można skorzystać:

    https://openweathermap.org/api
    https://weatherstack.com/
    https://www.weatherapi.com/

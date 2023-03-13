# RickAndMorty Sample App

**Rick and Morty Sample App**

Sample app that consumes a Rick and Morty API to display a character list, character details, and character lookup. It has been built with principles of clean architecture, repository pattern and MVVM pattern, and applying SOLID principles.

The MVP displays a list of characters, managed by Remote Mediator with Pager3 and local caching. All the user interface was done with Compose, and Koin has been used as a dependency injector. Some unit tests have also been done.

As possible improvements for the future, some tests could be finished to be able to cover all the functionalities, and for the architecture part, could be extract both the database package and the network package in independent modules.

**Some screenshots**

<img src="https://user-images.githubusercontent.com/1913709/224829671-4f2d1603-b56c-436d-8112-8db4dadb594d.png" height="300"/>    <img src="https://user-images.githubusercontent.com/1913709/224829666-f0315f20-8508-4518-8e5d-2343d2a66110.png" height="300"/> 


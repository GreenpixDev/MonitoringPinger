# MonitoringPinger (pet project)

## Описание
Сервис для проверки состояний игровых серверов [minecraft](https://www.minecraft.net/ru-ru) (далее просто - игровой сервер) с помощью пакетов, описанных в [протоколе minecraft java edition](https://wiki.vg/Protocol). Для отправки пакетов использовалась библиотека reactor netty.

## Архитектура
Полное приложение имеет микросервисную архитектуру и состоит из 2 сервисов:
* Backend - сервис для хранения списка серверов, регистрации пользователей (https://github.com/GreenpixDev/MonitoringBackend)
* Pinger - сервис для проверки статусов серверов (текущий репозиторий)

![Dependency Visualizer-Страница 5 drawio (1)](https://user-images.githubusercontent.com/58062046/227698060-295aa234-1ede-459f-bfea-478a72627a7c.png)

## Стек и технологии
При разработке использовались данные технологии:
* [Java](https://www.java.com/)
* [Kotlin](https://kotlinlang.org/)
* [Gradle (Kotlim DSL)](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
* [Reactor Netty](https://projectreactor.io/docs/netty/release/reference/index.html)
* [Spring boot 3](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Release-Notes)
* [Spring webflux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
* [SpringDoc OpenAPI](https://springdoc.org/)

## Используемые пакеты
* [Handshake](https://wiki.vg/Protocol#Handshake)
* [Status Request](https://wiki.vg/Protocol#Status_Request)
* [Ping Request](https://wiki.vg/Protocol#Ping_Request)
* [Status Response](https://wiki.vg/Protocol#Status_Response)
* [Ping Response](https://wiki.vg/Protocol#Ping_Response)

## Ссылки
* Ссылка на демонстрацию: https://new-servers.ru
* Ссылка на репозиторий "Основного сервиса": https://github.com/GreenpixDev/MonitoringBackend
* Документация протокола minecraft java edition: https://wiki.vg/Protocol

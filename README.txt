# Учёт потребления коммунальных ресурсов

Приложение на Spring Boot для управления помещениями, счётчиками, показаниями и начислениями.

## Основные сущности
- **User** — владелец помещения
- **Premises** — помещение (квартира/дом)
- **Meter** — счётчик ресурсов (вода, газ, свет)
- **MeterReading** — показания счётчика
- **UtilityCharge** — начисление за ресурсы

## Технологии
- Java 21, Spring Boot 3.5.5
- Spring Data JPA, PostgreSQL
- Docker (для запуска БД)

## Запуск
1. Поднять БД: `docker-compose up -d`
2. Запустить приложение: `mvn spring-boot:run`

## Бизнес-операции
1. `POST /api/business/meters/{id}/readings` — подать показание счётчика
2. `POST /api/business/readings/{id}/approve` — подтвердить показание и начислить плату
3. `POST /api/business/charges/{id}/pay` — оплатить начисление
4. `POST /api/business/premises/{id}/transfer/{newOwnerId}` — передать помещение новому владельцу
5. `GET /api/business/reports/{period}` — отчёт по потреблению за период

## Коллекция Postman
Файл `postman_collection.json` в корне проекта.
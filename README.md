![GitHub tag (latest SemVer)](https://shields.herrvergesslich.de/github/v/tag/smartcity-2022/service-bildungsportal?label=Version)
# Service-Bildungsportal
Microservice Bildungsportal

# Umgebungsvariablen

## Backend

| Variable           | Beschreibung                         |
|:-------------------|:-------------------------------------|
| DATABASE\_HOST     | Hostname oder IP des MySQL Servers   |
| DATABASE\_PORT     | Port des MySQL Servers               |
| DATABASE           | Name der Datenbank-Instanz           |
| DATABASE\_USER     | Benutzername                         |
| DATABASE\_PASSWORD | Passwort des Benutzers               |
| RABBITMQ\_HOST     | Hostname des RabbitMQ Servers        |
| RABBITMQ\_PORT     | Port des RabbitMQ Servers            |
| RABBITMQ\_USER     | Benutzername auf dem RabbitMQ Server |
| RABBITMQ\_PASSWORD | Passwort des Benutzers               |
| RABBITMQ\_EXCHANGE | Name des Topic Exchanges             |
| MAINHUB            | Base-URL der MainHub-API             |


## Frontend

| Variable            | Beschreibung             |
|:--------------------|:-------------------------|
| REACT\_APP\_BACKEND | Base-URL des Backends    |

## Test

### Cypress

| Variable             | Beschreibung           |
|:---------------------|:-----------------------|
| CYPRESS\_baseUrl     | Base-URL des Frontends |
| CYPRESS\_accessToken | AccessToken            |

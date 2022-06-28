![GitHub tag (latest SemVer)](https://shields.herrvergesslich.de/github/v/tag/smartcity-2022/service-bildungsportal?label=Version)

# Service-Bildungsportal
Microservice Bildungsportal

# Backend

## Abhängigkeiten

Es muss ein JDK 18 installiert sein (z.B. OpenJDK-18.0.1.1) und die Umgebungsvariable `JAVA_HOME` muss auf das Verzeichnis des JDKs gesetzt werden.

Zur Laufzeit wird eine MySQL-Datenbank und ein RabbitMQ-Server benötigt.

## Startanweisungen

Zunächst müssen die Umgebungsvariablen (siehe unten) gesetzt werden. 
Anschließend kann im Ordner `backend` das Backend folgendermaßen gebuildet, Unittests durchlaufen und gestartet werden:
```
./gradlew build
./gradlew bootRun
```

## Umgebungsvariablen

| Variable           | Beschreibung                                                  |
|:-------------------|:--------------------------------------------------------------|
| DATABASE\_HOST     | Hostname oder IP des MySQL Servers                            |
| DATABASE\_PORT     | Port des MySQL Servers                                        |
| DATABASE           | Name der Datenbank-Instanz                                    |
| DATABASE\_USER     | Benutzername                                                  |
| DATABASE\_PASSWORD | Passwort des Benutzers                                        |
| RABBITMQ\_HOST     | Hostname des RabbitMQ Servers                                 |
| RABBITMQ\_PORT     | Port des RabbitMQ Servers                                     |
| RABBITMQ\_USER     | Benutzername auf dem RabbitMQ Server                          |
| RABBITMQ\_PASSWORD | Passwort des Benutzers                                        |
| RABBITMQ\_EXCHANGE | Name des Topic Exchanges                                      |
| MAINHUB            | Base-URL der MainHub-API                                      |
| JWT_SECRET         | Secret des JWT-Tokens (nur benötigt in den Integrationstests) |

# Frontend

## Abhängigkeiten

Es muss npm (Version 8.5.0) installiert sein.

Zur Laufzeit wird das Backend benötigt.

## Starten

Zunächst müssen die Umgebungsvariablen (siehe unten) gesetzt werden. 
Anschließend kann im Ordner `frontend` das Frontend folgendermaßen gestartet werden:
```
npm run start
```

## Umgebungsvariablen

| Variable            | Beschreibung             |
|:--------------------|:-------------------------|
| REACT\_APP\_BACKEND | Base-URL des Backends    |

# Test (Cypress)

## Abhängigkeiten

Es muss npm (Version 8.5.0) installiert sein.

Zur Laufzeit wird das Frontend und das Backend benötigt.

## Starten

Zunächst müssen die Umgebungsvariablen (siehe unten) gesetzt werden. 
Anschließend kann im Ordner `test/cypress` die Integrationstests folgendermaßen gestartet werden:
```
npm run cypress:run
```

## Umgebungsvariablen

| Variable             | Beschreibung           |
|:---------------------|:-----------------------|
| CYPRESS\_baseUrl     | Base-URL des Frontends |
| CYPRESS\_accessToken | AccessToken            |

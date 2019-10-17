# Migrator
> Java application for generating database migration files

[![Build Status](https://travis-ci.com/pipan/migrator.svg?branch=master)](https://travis-ci.com/pipan/migrator)

Application that generate migration files for file based database migrations like Phinx or Flyway. This application provides GUI and handles creating migration files for different languages.

Migrations are used to change database structure when application evolve and you need to propagate this change to multiple servers and dvelopers.

![preview](https://media.giphy.com/media/cmBgK26y8jbIEM2Q6J/giphy.gif)

## Installation

Download the latest [release](https://github.com/pipan/migrator/releases/download/v0.1.1/migrator.jar)

```sh
java -jar migrator.jar
```

## Usage example

// TODO

## Development setup

// TODO

## Release History

* 0.1.1
    * Bugfixes
        * fixed loading column types
        * setting signed / unsigned value from database
        * setting presicion for decimal from db
* 0.1.0
    * First Woking version
        * support for phinx migrations
        * support for mysql database



## Meta

Peter Gasparik – gasparik.pe@gmail.com

Distributed under the MIT license. See ``LICENSE`` for more information.

## Contributing

1. Fork it (<https://github.com/pipan/migrator/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

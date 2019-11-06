# Migrator
> Java application for generating database migration files

[![Build Status](https://travis-ci.com/pipan/migrator.svg?branch=master)](https://travis-ci.com/pipan/migrator)

![migrator icon](src/main/resources/images/splash_small.png)

Application that generate migration files for file based database migrations like Phinx or Flyway. This application provides GUI and handles creating migration files for different languages.

Migrations are used to change database structure when application evolves and you need to share this change with multiple servers or dvelopers.

![preview](https://media.giphy.com/media/cmBgK26y8jbIEM2Q6J/giphy.gif)

## Installation

Download the latest [release](https://github.com/pipan/migrator/releases/download/v0.2.2/migrator.jar)

```sh
java -jar migrator.jar
```

## Usage example

One project has one database connection. Project also has migration output type. Currently, only supported output type is phinx, but phinx is used in variaty of PHP frameworks (cakephp, laravel).

### Creating project

To create a project click on the `+` button and fill the form on the left. Some fields are prefilled with default values.

![create project](https://media.giphy.com/media/S5yhNdLDYc4QpMZsji/giphy.gif)

> If your application has multiple databases you have to create multiple plojects

### Creating table

Select project by clicking on `open` button of a project card. If your database contains tables, they will be shown in this list.

1. To create a new table click on `+` button in the upper right corner. 
2. Select a name for your new table
3. add columns by clicking on `+` button in columns list
4. add indexes by clicking on `+` button in indexes list

![create table](https://media.giphy.com/media/L0w7hjWOCrsNVeS8Pf/giphy.gif)

> index can have only 3 columns, it's a reasonable amount for most cases.

### Generating migration

Select project by clicking on `open` button of a project card.

1. Click on `:heavy_check_mark:` button in the upper right corner
2. Name this migration. This name will be used to generate migration file name.
3. Click on the commit button

![craete migration file](https://media.giphy.com/media/dY0y5gabkE5wKijvyr/giphy.gif)

## Release History

* 0.3.0
  * Features
    * added support for MariaDB
    * added support for PostgreSQL
    * new table contains default columns (id, created_at, modified_at) and default index (primary)
    * rounded corners for cards, buttons and form elements
    * added auto increment column attribute
  * Bugfixes
    * deselecting indexes table row after selecting column
    * deselecting columns table row after selecting index
    * added tooltips for create project, create table, create column and create index buttons

* 0.2.2
  * deploy .jar for all platforms

* 0.2.1
  * Bugfixes
    * fix generating migration files. Changes were not selectet properly, we were still selecting changes by project name, but we changed it to selecting changes by project id.

* 0.2.0
  * Features
    * you can search in tables by presing CTRL+F
    * added support for hotkeys

* 0.1.2
  * Features
    * added icon


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

Have you found a bug?

Do you wan to share your ideas how to improve this application?

Are you looking for some little thing to code?

Look at the [contribution manual](CONTRIBUTING.md)

### Deploy

release: https://github.com/aktau/github-release

## TODO

* add analytics
* allow changing column order
* add tests
* add support for frameworks:
  * flyway (JAVA)
  * fluid (.NET)
  * db-migrate (nodejs)
* allow data seeding
* set database connection info from project (phinx config, ...)

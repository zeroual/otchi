[![codecov](https://codecov.io/gh/zeroual/otchi/branch/master/graph/badge.svg?token=27o9BTuSbA)](https://codecov.io/gh/zeroual/otchi)
[![codecov](https://circleci.com/gh/zeroual/otchi.svg?style=shield&circle-token=0d87372f166fdaf1080c8c2982f046d2bffb984e)](https://circleci.com/gh/zeroual/otchi)

##Otchi App 

Otchi is a social network for food.

You can use othci to:

- Look up what to cook.
- See what people eat.
- Find a good place to eat.
- ...

TODO soon we will add an impact mapping schema

## Submit ideas

You've been on **[Otchi](http://otchi.herokuapp.com)** application and you have any suggestions?
Maybe the application is not completely ergonomic and does not match your needs, or that some important features are missing?

### Means ?

To keep Otchi  growing and improving we need all help we can get, feel free to make your comments.
 Two possibilities:
- Issues tracker available on github [here](https://github.com/zeroual/otchi/issues)
- Email available: zeroual.abde@gmail.com

**NOTE: If you’re stuck, can’t get something working or need some help, please head on over and join our [Slack community](https://otchi.slack.com) rather than opening an issue.**

To know more about our flow work please read this [Contributing guide](docs/how-to-contribute.md)

## Required

### source code

    fork this project on github  https://github.com/zeroual/otchi.git

**Maven**

    sudo apt-get install maven

**NodeJs**

    sudo apt-get install nodejs npm

**Java**

    sudo apt-get install java

**Git**

    sudo apt-get install git

**Front Build Tools**

Front builds are dependendant of bower and gulp.

    npm install -g bower gulp

## Dependencies

With npm, install plugins. Npm install dependencies to ./node_modules/.
To the project root :

    npm install

Install packages with bower. Bower installs packages to ./app/bower_components/.
To the project root :

    bower install

## Run all tests

    mvn clean install

## Run the Project (for developers)

Run the commands

    mvn spring-boot:run

  Or run the `OtchiApplicationStarter` class with your IDE

## Otchi Structure Conventions

- how the application is layered ? [check here](docs/how-application-is-layered.md)
- how the back end is structured ? [check here](docs/back-end-structure.md)
- how front end is structured ? [check here](docs/front-end-structure.md)
- how automated tests are structured ? //TODO

## Copyright and license

This source code is copyrighted by [zeroual](https://twitter.com/AbdeZeros)

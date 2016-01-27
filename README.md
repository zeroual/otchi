##Otchi App

Otchi is a social network for food.

You can use othci to:

- Look up what to cook.
- See what people eat.
- Find a good place to eat.
- ...

## Submit ideas

You've been on **[Otchi](http://otchi.com)** application and you have any suggestions?
Maybe the application is not completely ergonomic and does not match your needs, or that some important features are missing?

### Means ?

Feel free to make your comments. Two possibilities:
- Issues tracker available on github [here](https://github.com/zeroual/otchi/issues)
- Email available: zeroual.abde@gmail.com

##Required

### source code

    git clone https://github.com/zeroual/otchi.git

**Maven**

    sudo apt-get install maven

**NodeJs**

    sudo apt-get install nodejs npm

**Java**

    sudo apt-get install java

**Git**

    sudo apt-get install git

**Elasticsearch**

    wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-0.90.9.deb
    dpkg -i elasticsearch-0.90.9.deb
    rm elasticsearch-0.90.9.deb


**Front Build Tools**

Front builds are dependendant of bower and gulp.

    npm install -g bower gulp

##Dependencies

With npm, install plugins. Npm install dependencies to ./node_modules/.
To the project root (front or front-admin only):

    npm install

Install packages with bower. Bower installs packages to ./app/bower_components/.
To the project root (front or front-admin only):

    bower install

## Run all tests

    mvn clean install


## Run the Project (for developers)

Run the commands

    mvn clean tomcat7:run (for the back)
    npm start (equivalent to gulp serve for front and front-admin)

## Access to the front

Front is available on : http://localhost:9000/
Front-Admin is available on : http://localhost:9001/

## Production

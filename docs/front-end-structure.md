# Front end structure

The frontend should be built around a view-specific model (which is not the domain model), 
and should only handle presentation logic, but no business logic. These are the three layers of the frontend.

## The View Layer
The view layer is composed of Html templates, CSS, and any Angular directives representing the different UI components

## The Controller Layer
The controller layer is made of Angular controllers that glue the data retrieved from the backend and the view together.
The controller initializes the view model and defines how the view should react to model changes and vice-versa.

One of the main responsibilities of the controller is to perform frontend validations. 
Any validations done on the frontend are for user convenience only - for example they are 
useful to immediately inform the user that a field is required.

Any frontend validations need to be repeated in the backend layer level due to security reasons,
as the frontend validations can be easily bypassed.

## The Frontend Services Layer
A set of Angular services that allow to interact with the backend and that can be injected into Angular controllers

# Guideline style
This guide includes best practices for one-way dataflow, event delegation,component architecture and component routing. 

The goal of this style guide is to present a set of best practices and style guidelines for one AngularJS application.


## Directory structure

Since this is a large AngularJS application and has many components it's best to structure it in the following directory hierarchy.

* high-level divisions by functionality and lower-level divisions by component types.

Here is the layout:

```
.
├── app
│   ├── app.js
│   ├── app.config.js
│   ├── app.run.js
│   ├── common
│   │   ├── controllers
│   │   ├── directives
│   │   ├── filters
│   │   └── services
│   ├── home
│   │   ├── controllers
│   │   │   ├── FirstCtrl.js
│   │   │   └── SecondCtrl.js
│   │   ├── directives
│   │   │   └── directive1.js
│   │   │   └── directive1.template.html
│   │   ├── filters
│   │   │   ├── filter1.js
│   │   │   └── filter2.js
│   │   └── services
│   │       ├── service1.js
│   │       └── service2.js
│   └── about
│       ├── controllers
│       │   └── ThirdCtrl.js
│       ├── directives
│       │   ├── directive2.js
│       │   └── directive3.js
│       ├── filters
│       │   └── filter3.js
│       └── services
│           └── service3.js
├── assets
├── bower_components
└── index.html
```

* The `app.js` file  contain route definitions, configuration and/or manual bootstrap.

Conventions about component naming can be found in each component section.

## Naming conventions
The following table is shown the naming conventions for every element:

Element | Naming style | Example | usage
----|------|----|--------
Modules | lowerCamelCase  | angularApp |
Controllers | Functionality + 'Controller'  | AdminController |
Directives | lowerCamelCase  | userInfo |
Filters | lowerCamelCase | userFilter |
Services | UpperCamelCase | User | constructor
Factories | lowerCamelCase | dataFactory | others

## Others

* Use:
    * `$timeout` instead of `setTimeout`
    * `$interval` instead of `setInterval`
    * `$window` instead of `window`
    * `$document` instead of `document`
    * `$http` instead of `$.ajax`
    * `$location` instead of `window.location` or `$window.location`
    * `$cookies` instead of `document.cookie`

This will make your testing easier and in some cases prevent unexpected behaviour (for example, if you missed `$scope.$apply` in `setTimeout`).

* Use promises (`$q`) instead of callbacks. It will make your code look more elegant and clean, and save you from callback hell.
* Use `$resource` instead of `$http` when possible. The higher level of abstraction will save you from redundancy.
* Don't use globals. Resolve all dependencies using Dependency Injection, this will prevent bugs and monkey patching when testing.

* Do not pollute your `$scope`. Only add functions and variables that are being used in the templates.
* Prefer the usage of [controllers instead of `ngInit`](https://github.com/angular/angular.js/commit/010d9b6853a9d2718b095e4c017c9bd5f135e0b0). There are only a few appropriate uses of ngInit, such as for aliasing special properties of ngRepeat, and for injecting data via server side scripting. Besides these few cases, you should use controllers rather than ngInit to initialize values on a scope. The expression passed to `ngInit` should go through lexing, parsing and evaluation by the Angular interpreter implemented inside the `$parse` service. This leads to:
    - Performance impact, because the interpreter is implemented in JavaScript
    - The caching of the parsed expressions inside the `$parse` service doesn't make a lot of sense in most cases, since `ngInit` expressions are often evaluated only once
    - Is error-prone, since you're writing strings inside your templates, there's no syntax highlighting and further support by your editor
    - No run-time errors are thrown
* Do not use `$` prefix for the names of variables, properties and methods. This prefix is reserved for AngularJS usage.
* Do not use `JQUERY` inside your app, If you must, use `JQLite` instead with `angular.element`.
* When resolving dependencies through the DI mechanism of AngularJS, sort the dependencies by their type - the built-in AngularJS dependencies should be first, followed by your custom ones:

```javascript
module.factory('Service', function ($rootScope, $timeout, MyCustomDependency1, MyCustomDependency2) {
  return {
    //Something
  };
});
```

# Modules

* Modules should be named with lowerCamelCase. For indicating that module `b` is submodule of module `a` you can nest them by using namespacing like: `a.b`.

	There are two common ways for structuring the modules:

	0. By functionality
	0. By component type

	Currently there's not a big difference, but the first way looks cleaner. Also, if lazy-loading modules is implemented (currently not in the AngularJS roadmap), it will improve the app's performance.

# Controllers

* Do not manipulate DOM in your controllers, this will make your controllers harder for testing and will violate the [Separation of Concerns principle](https://en.wikipedia.org/wiki/Separation_of_concerns). Use directives instead.
* The naming of the controller is done using the controller's functionality (for example shopping cart, homepage, admin panel) and the substring `Controller` in the end.
* Controllers are plain javascript [constructors](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/constructor), so they will be named UpperCamelCase (`HomePageController`, `ShoppingCartController`, `AdminPanelController`, etc.).
* The controllers should not be defined as globals (even though AngularJS allows this, it is a bad practice to pollute the global namespace).

* Avoid use of `$scope` service to define functions and properties as part of controllers. Use `$scope` only if It's really needed:
    0. For publish and subscribe to events: `$scope.$emit`, `$scope.$broadcast`, and `$scope.$on`.
    0. For _watch_ values or collections: `$scope.$watch`, `$scope.$watchCollection`

* Prefer using `controller as` syntax and capture `this` using a variable:

  ```html
  <div ng-controller="MainCtrl as main">
     {{ main.things }}
  </div>
  ```

  ```JavaScript
    app.controller('MainCtrl', function MainCtrl($http) {
                var vm = this;
                //a clearer visual connection on how is defined on the view
                vm.title = 'Some title';
                vm.description = 'Some description';

                $http.get('/api/main/things').then(function (response) {
                    vm.things = response.data.things; // Adding 'things' as a property of the controller
                });
            }
    );
  ```

   Avoid using `this` keyword repeatedly inside a controller:

  ```JavaScript
        app.controller('MainCtrl', function MainCtrl($http) {
            // Avoid
            this.title = 'Some title';
            this.description = 'Some description';

            $http.get('/api/main/things').then(function (response) {
                // Warning! 'this' is in a different context here.
                // The property will not be added as part of the controller context
                this.things = response.data.things;
            });
        });
    ```

   Using a consistent and short variable name is preferred, for example `vm`.

   The main benefits of using this syntax:
   * Creates an 'isolated' component - binded properties are not part of `$scope` prototype chain. This is good practice since `$scope` prototype inheritance has some major drawbacks (this is probably the reason it was removed on Angular 2):
      * It is hard to track where data is coming from.
      * Scope's value changes can affect places you did not intend to affect.
      * Harder to refactor.
      * The '[dot rule](http://jimhoskins.com/2012/12/14/nested-scopes-in-angularjs.html)'.
   * Removes the use of `$scope` when no need for special operations (as mentioned above). This is a good preparation for AngularJS V2.
   * Syntax is closer to that of a 'vanilla' JavaScript constructor

   Digging more into `controller as`: [digging-into-angulars-controller-as-syntax](http://toddmotto.com/digging-into-angulars-controller-as-syntax/)

* Make the controllers as lean as possible. Abstract commonly used functions into a service.

  Why business logic / app state inside controllers is bad?
  * Controllers instantiated for each view and dies when the view unloads
  * Controllers are not reusable - they are coupled with the view
  * Controllers are not meant to be injected


* Communicate within different controllers using method invocation (possible when a child wants to communicate with its parent) or `$emit`, `$broadcast` and `$on` methods. The emitted and broadcasted messages should be kept to a minimum.
* Make a list of all messages which are passed using `$emit`, `$broadcast` and manage it carefully because of name collisions and possible bugs.

   Example:

   ```JavaScript
   // app.js
   /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
   Custom events:
     - 'authorization-message' - description of the message
       - { user, role, action } - data format
         - user - a string, which contains the username
         - role - an ID of the role the user has
         - action - specific action the user tries to perform
   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
   ```

* When you need to format data encapsulate the formatting logic into a [filter](#filters) and declare it as dependency:

   ```JavaScript
        module.filter('myFormat', function myFormat() {
            return function () {
                // ...
            };
        });

        module.controller('MyCtrl', function MyCtrl($scope, myFormatFilter) {
            // ...
        });
   ```
* In case of nested controllers use "nested scoping" (the `controllerAs` syntax):

   **app.js**
   ```javascript
   module.config(function ($routeProvider) {
     $routeProvider
       .when('/route', {
         templateUrl: 'partials/template.html',
         controller: 'HomeCtrl',
         controllerAs: 'home'
       });
   });
   ```
   **HomeCtrl**
   ```javascript
   function HomeCtrl() {
     var vm = this;

     vm.bindingValue = 42;
   }
   ```
   **template.html**
   ```html
   <div ng-bind="home.bindingValue"></div>
   ```

# Directives

* Name your directives with lowerCamelCase.
* Use `scope` instead of `$scope` in your link function. In the compile, post/pre link functions you have already defined arguments which will be passed when the function is invoked, you won't be able to change them using DI. This style is also used in AngularJS's source code.
* Use custom prefixes for your directives to prevent name collisions with third-party libraries.
* Do not use `ng` or `ui` prefixes since they are reserved for AngularJS and AngularJS UI usage.
* DOM manipulations must be done only through directives.
* Create an isolated scope when you develop reusable components.
* Use directives as attributes or elements instead of comments or classes, this will make your code more readable.
* Use `scope.$on('$destroy', fn)` for cleaning up. This is especially useful when you're wrapping third-party plugins as directives.
* Do not forget to use `$sce` when you should deal with untrusted content.

# Filters

* Name your filters with lowerCamelCase.
* Make your filters as light as possible. They are called often during the `$digest` loop so creating a slow filter will slow down your app.
* Do a single thing in your filters, keep them coherent. More complex manipulations can be achieved by piping existing filters.

# Services

This section includes information about the service component in AngularJS. It is not dependent of the way of definition (i.e. as provider, `.factory`, `.service`), except if explicitly mentioned.

* Use camelCase to name your services.
  * UpperCamelCase (PascalCase) for naming your services, used as constructor functions i.e.:

    ```JavaScript
        module.controller('MainCtrl', function MainCtrl(User) {
                    var vm = this;
                    vm.user = new User('foo', 42);
                }
        );

        module.factory('User', function User(name, age) {
            this.name = name;
            this.age = age;
        });
    ```

  * lowerCamelCase for all other services.

* Encapsulate all the business logic in services.

  See 'Avoid writing business logic inside controllers' for an example of a controller consuming this service.
* Services representing the domain preferably a `service` instead of a `factory`. In this way we can take advantage of the "klassical" inheritance easier:

* For session-level cache you can use `$cacheFactory`. This should be used to cache results from requests or heavy computations.
* If given service requires configuration define the service as provider and configure it in the `config` callback like:

	```JavaScript
	angular.module('demo', [])
	.config(function ($provide) {
	  $provide.provider('sample', function () {
	    var foo = 42;
	    return {
	      setFoo: function (f) {
	        foo = f;
	      },
	      $get: function () {
	        return {
	          foo: foo
	        };
	      }
	    };
	  });
	});

	var demo = angular.module('demo');

	demo.config(function (sampleProvider) {
	  sampleProvider.setFoo(41);
	});
	```

# Templates

* Avoid writing complex expressions in the templates.
* When you need to set the `src` of an image dynamically use `ng-src` instead of `src` with `{{ }}` template.
* When you need to set the `href` of an anchor tag dynamically use `ng-href` instead of `href` with `{{ }}` template.

# Routing

* Use `resolve` to resolve dependencies before the view is shown.
* Do not place explicit RESTful calls inside the `resolve` callback. Isolate all the requests inside appropriate services. This way you can enable caching and follow the separation of concerns principle.

# i18n

* For newer versions of the framework (>=1.4.0) use the built-in i18n tools, when using older versions (<1.4.0) use [`angular-translate`](https://github.com/angular-translate/angular-translate).

# Performance

* Optimize the digest cycle

	* Watch only the most vital variables. When required to invoke the `$digest` loop explicitly (it should happen only in exceptional cases), invoke it only when required (for example: when using real-time communication, don't cause a `$digest` loop in each received message).
	* For content that is initialized only once and then never changed, use single-time watchers like [`bindonce`](https://github.com/Pasvaz/bindonce) for older versions of AngularJS or one-time bindings in AngularJS >=1.3.0.
		```html
		<div>
		  {{ ::main.things }}
		</div>
		```
		or
		```html
		  <div ng-bind="::main.things"></div>
		```
		After that, **no** watchers will be created for `main.things` and any changes of `main.things` will not update the view.
	* Make the computations in `$watch` as simple as possible. Making heavy and slow computations in a single `$watch` will slow down the whole application (the `$digest` loop is done in a single thread because of the single-threaded nature of JavaScript).
	* When watching collections, do not watch them deeply when not strongly required. Better use `$watchCollection`, which performs a shallow check for equality of the result of the watched expression and the previous value of the expression's evaluation.
	* Set third parameter in `$timeout` function to false to skip the `$digest` loop when no watched variables are impacted by the invocation of the `$timeout` callback function.
	* When dealing with big collections, which change rarely, [use immutable data structures](http://blog.mgechev.com/2015/03/02/immutability-in-angularjs-immutablejs).


* Consider decreasing number of network requests by bundling/caching html template files into your main javascript file, using [grunt-html2js](https://github.com/karlgoldstein/grunt-html2js) / [gulp-html2js](https://github.com/fraserxu/gulp-html2js). See [here](http://ng-learn.org/2014/08/Populating_template_cache_with_html2js/) and [here](http://slides.com/yanivefraim-1/real-world-angularjs#/34) for details. This is particularly useful when the project has a lot of small html templates that can be a part of the main (minified and gzipped) javascript file.

## Angular docs
For anything else, including API reference, check the [Angular documentation](//docs.angularjs.org/api).
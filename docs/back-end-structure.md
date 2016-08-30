# Back end structure

```
 java
    ├── api
    ├── application
    ├── domain
    ├── infrastructure
```

## Rest API
**REST resources and representations are located under this package.** REST is an architectural style which promotes
state transfer between a client (like a browser) and a stateless server through an uniform interface (HTTP in our case).

Rules:

* Domain objects are never exposed without being transformed into DTOs, which are called representations in REST.
* Representations should be as flat as possible.
* Representations can be mapped from and to domain aggregates by assemblers and their DSL facade `FluentAssembler`. They
can also be directly constructed from **read-only** data finders which can bypass the application and domain layers to
efficiently query persistence.
* Using hypermedia should be preferred over hard-coded URLs.

## Application
**The application logic is located under this package.** It is responsible for driving the workflow of the application,
executing the use cases of the system.

Rules:

* These operations are independent of the interfaces by which they are exposed (REST, CLI, ...).
* This layer is well suited for spanning transactions, high-level logging and security.
* The application layer is thin in terms of domain logic, it merely coordinates the domain layer objects to perform the
actual work through application services.

## Domain

**The domain and its sub-domains are located under this package.** The Domain Layer is where the business is expressed.

Important:

It is often necessary to share the domain (or a subset of it) between multiple modules. In this case you can use the
"domain" project template to create **a reusable domain JAR** that you will use instead of, or in addition to this package.
For instance, you can choose to create one or more domain JAR that you will add as dependencies of your batch, web and
cli modules.

Rules:

* The domain is independent of the use cases of the system, but is used to achieve their realization,
* It is a very behaviour-rich and expressive model of the domain, based on entities, values objects and aggregates.
* It contains additional patterns, such as domain services, repositories, factories, policies, etc...

## Infrastructure
**The infrastructure is located under this package.** The infrastructure layer contains the technology-specific
implementations of interfaces defined in other layers.

Rules:

* It supports all of the three other layers in different ways, facilitating communication between the layers.
* It consists of everything that would still exist without the application: external libraries, database engine, application
server, messaging backend and so on.
* This layer can be completely replaced by another one with other technological choices without altering the system behavior.

We often declare Java interfaces in interface, application and domain layers and implement them in the infrastructure layer.
A good example is the repository: interfaces are located in the domain layer but the implementation is in the infrastructure
under a sub-package named after its technology .

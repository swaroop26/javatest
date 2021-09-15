# AltX Java Technical Test

Hello! This is a small exercise to judge your knowledge of the Java skills
required by AltX for a backend developer. This is only intended
to take you **about one or two hours**. Please feel free to ask any questions or
ask for clarification if you have difficulties or are unsure of what to do.

You are allowed to use Google, Stack Overflow, or any other reference you like
in completing this task, just as you would in day-to-day work. If you
want to include additional libraries or tools, again, please do so.

The solution you submit will be used as a starting place for a further conversation
during your technical interview. Please have it handy.

## The Task

### Setup 
You will require a computer with the following development environment:
* Java 8
* Gradle (I use Gradle 7.1.1)
* Git

First clone the repository from: https://github.com/mwyatt-altx/javatest

(Chances are you already have if you're reading this)

The project contains a small RESTful web service to store and retrieve a
catalogue in an in-memory H2 database. It should compile and run out of the box, and some test
cases have already been provided. **Before continuing check that you can
build and run the project and that both tests pass.**

i.e. the output should look similar to this:
```
% ./gradlew build

Welcome to Gradle 7.1.1!

Here are the highlights of this release:
 - Faster incremental Java compilation
 - Easier source set configuration in the Kotlin DSL

For more details see https://docs.gradle.org/7.1.1/release-notes.html

Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details

> Task :test
2021-09-15 21:02:07.145  INFO 28533 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2021-09-15 21:02:07.147  INFO 28533 --- [ionShutdownHook] .SchemaDropperImpl$DelayedDropActionImpl : HHH000477: Starting delayed evictData of schema as part of SessionFactory shut-down'
2021-09-15 21:02:07.173  INFO 28533 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2021-09-15 21:02:07.190  INFO 28533 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.

BUILD SUCCESSFUL in 27s
9 actionable tasks: 4 executed, 5 up-to-date
% 
```

### API Description

The API stores the catalogue of Movies for a new streaming service.

There are HTTP methods to **POST** a new movie, **DELETE** a movie,
**GET** a list of all movies, and **GET** a specific movie by its `id`.

The current structure of a *Movie* is a simple JSON object:

```
{
    "id":1,
    "title":"Top Gun",
    "runningTimeMins":110,
    "stars":"Tom Cruise, Kelly McGillis, Val Kilmer"
}
```

(You don't have to use Top Gun, I just watched it again recently. Feel free
to pick another movie to use as test data.)

### The Required Change

The `stars` property is currently a plain text `String`. This isn't going
to meet the future needs of the service. Instead a new API is required to
store `Actor`s as a separate data structure:

e.g.:
```
{
    "id":1,
    "firstName":"Val"
    "lastName":"Kilmer"
    "dateOfBirth":"1959-12-31"
}
```

`Actor`s should be able to be added and queried by a new set of REST methods. At the
minimum a **POST**, **DELETE**, **GET** all values and **GET** a single value by `id`
is required.

The `Movie` type should be modified so that instead of a single `String` there is
a One to Many mapping to `Actor`s, e.g:

to **POST** a new movie, first post one or more stars:
```
{ "firstName":"Will", "lastName":"Ferrell", "dateOfBirth":"1967-07-16" }
```
The return value should include the auto-generated `id`, e.g. `5`

Then **POST** the new movie including the IDs of the stars
```
{ "title":"Blades of Glory","runningTimeMins":93, "stars":[5, 6] }
```

Running a **GET** of the movie should provide details of the actors:

```
{
    "id":2,
    "title":"Blades of Glory",
    "runningTimeMins":93,
    "stars":[
        {
            id: 5,
            "firstName":"Will",
            "lastName":"Ferrell",
            "dateOfBirth":"1967-07-16"
        },
        {
            id: 6,
            "firstName":"Jon",
            "lastName":"Heder",
            "dateOfBirth":"1977-10-26"
        }
    ]
}
```

The automated tests in `JavatestApplicationTests.java` should be updated and extended
to reflect the new functionality.

### Submission

Once you are happy with your solution, please commit it back to the GitHub repository.
If that doesn't work, please Zip up and email your solution back. 
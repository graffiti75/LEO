# LEO

## Implementation Steps

I followed some basic steps to implement this repo.

1. Create repo on Github.
2. Include basic dependencies in Gradle, like: `Dagger2`, `Timber`, `Retrofit` and `RecyclerView`.
3. Customize file `gradle.properties`.
4. Add MVP pattern.
5. Apply Dependency Injection with `Dagger2`.
6. Import classes to perform basic `Retrofit` call.
7. Add `Retrofit` Interceptors: Cache, NetworkInterceptor and OfflineInterceptor.
8. Add `Timber`.
9. Test basic `Retrofit` call using the `Github API`: `https://api.github.com`.
10. Apply `MockWebServer` on project to perform tests.
11. Create Instrumented Tests to test the app.
12. Implement `RxJava` on `Retrofit` calls.
13. Insert `Retrofit` result in the `RecyclerView`.
14. Add Loading view before data retrieval.
15. Update method to check Internet connection.
16. Add `Pagination`.
17. Write README.

## Implementation Details

The Requirements of this project are:

1. The user can type in a GitHub username.
2. Present data about the given GitHub user.
3. Write a readme with instructions on how to run the application and explain any relevant design considerations.
4. [Optional] Cover relevant functionality with Unit Tests.

As requested in the project spec:

- The user can type in a GitHub username.
- This action is done through a SearchView in the Toolbar of the app.
- The app validates this search and informs the user.
  - In case of lack of connection or invalid data (like empty data from the API), a message is showed for the User.
  - Otherwise, a RecyclerView is showed with the valid data.
- Clicking on each repo, a Toast message is showed.
- Internet connection check was implemented.
- The test implemented on this project was a Network test using `IdlingResource`.
  - Since this test had the limit of 4 hours of implementation, this was the only test implemented.

Also, this app is using:

- Kotlin
- SOLID principles
- Retrofit
- Dagger 2
- Espresso Tests
- RxJava 2
- Timber
- RecyclerView
- CardView

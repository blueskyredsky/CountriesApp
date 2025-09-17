CountriesApp App
==================
CountriesApp is a mobile application built with Android and Jetpack Compose that allows users to
explore a list of countries and their details, organised by continent.

# Features ✨ 
**Browse Continents:** View a list of continents with a clean, intuitive UI.

**Explore Countries:** Select a continent to see all the countries within it.

**Detailed Information:** Get key details for each country, including:
🌍 Flag
💵 Currency
🏙️ Capital
📞 Phone Code

**Search Functionality:** Quickly find a country by its name using the search bar.

# Features 🏗️ 
This app is built using a modular architecture following the principles of Clean Architecture to
ensure a separation of concerns, scalability, and testability. The project is organized into distinct modules:

**app:** The main application module.

**benchmark:** Contains performance benchmarks.

**build-logic:** Centralised build configuration logic.

**core:** Reusable core components.

**common:** Shared utilities.

**designsystem:** UI components and design tokens.

**logging:** Logging implementation.

**networking:** API and networking logic using Apollo GraphQL.

**testing:** Test utilities and shared test code.

**threading:** Threading and coroutine management.

**feature:** Contains feature-specific modules.

**continents:** Displays the list of continents.

**countries:** Displays the countries for a selected continent.

# Technology Stack 🛠️
[Kotlin](https://kotlinlang.org/): The primary programming language.

[Jetpack Compose](https://developer.android.com/compose): The modern UI toolkit for building native Android UIs.

[Apollo GraphQL](https://www.apollographql.com/): For fetching data from the GraphQL API.

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android): A dependency injection framework for Android.

[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines): For asynchronous and concurrent programming.

[Kotlin Flows](https://kotlinlang.org/docs/flow.html): For handling streams of data.

[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): To manage UI-related data in a lifecycle-conscious way.

[Compose Navigation](https://developer.android.com/develop/ui/compose/navigation): For navigating between screens.

[JUnit](https://github.com/junit-team/junit4) & [Mockito](https://github.com/mockito/mockito): For unit and integration testing.

[Turbine](https://github.com/cashapp/turbine): A testing library for Kotlin Flows.

and other libs
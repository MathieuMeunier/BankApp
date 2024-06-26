# BankApp

A simple Two screens application

## Architecture

The application is composed of three layers

- A UI Layer (ViewModel + Screens)
- A Repository Layer : responsible for accessing data and presenting it to the UI layer
- An API Layer that calls the web services and giving it's result to the Repository Layer

Each layer has it's own data model

## UI

The UI is done with jetpack Compose and use a Navigation Host Controller to route the user in the screens.

Navigation is minimalist:
 - A main screen presenting the data ordered by Type and by Name
 - A detail screen accessed by the main screen

Each screen has it's own ViewModel to access Data

## Repository

The repository handles the call from the ViewModels and present it to the UI Layer

For now it only calls an API but the data could be retrieve from internal storage (cache or database)

## API

This layer manages the calls to the webservice.

For now it's only one client and one API but the project can handle more if needed.

### Warning
We use a special client that accept any certificate. The endpoint certificate was not valid at the time of testing.
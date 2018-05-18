# Austin Places
Simple places search powered by Foursquare.

## Development environment
This application was developed using:

    Android Studio 3.1.2
    Build #AI-173.4720617, built on April 13, 2018
    JRE: 1.8.0_152-release-1024-b01 x86_64
    JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
    Mac OS X 10.13.4

#### Google Maps
Google Maps requires an API key which is tied to the certificate used to sign the Android application.  For debug builds, the certificate is the default debug certificate that can typically be found in the user's home directory.

In order to run the application and see the maps functionality working, you will need to [obtain your own API key](https://developers.google.com/maps/documentation/android-sdk/signup) tied to your own debug certificate (and update the value in `AndroidManifest.xml` under the meta-data name `com.google.android.geo.API_KEY`) or replace your debug certificate with the one included in this project (`debug.keystore`).

To replace your debug certificate, copy the `debug.keystore` file in this project and paste it into your `$HOME/.android/` directory.

#### Other issues
If gradle sync fails with the message:

    3rd-party Gradle plug-ins may be the cause
    
Follow the [work around on Stack Overflow](https://stackoverflow.com/a/49529270) to remove the `Instant App Provision` from the run configurations screen.

## App structure
This app is written in Kotlin and utilizes the MVVM (Model-View-ViewModel) design pattern by incorporating [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

[Retrofit](http://square.github.io/retrofit/) is used for implementing the interface to the [Foursquare REST API](https://developer.foursquare.com/docs).

[RxJava](https://github.com/ReactiveX/RxJava) / [RxAndroid](https://github.com/ReactiveX/RxAndroid) are used to simplify data stream manipulation in a more functional manner.  Similarly, [RxBinding](https://github.com/JakeWharton/RxBinding) is used to simplify UI input event streams, and [RxLifecycle](https://github.com/trello/RxLifecycle) to make sure the Rx chains are lifecycle-aware.

[Glide](https://github.com/bumptech/glide) is used for loading images from the Foursquare API.

[Room](https://developer.android.com/topic/libraries/architecture/room) is used for local persistence, specifically for storing whether the user has marked a place as a favorite.

## Future versions
Future versions of the app could include
* Progress and error states
* More information on the venue details pages
* Offline functionality
* Test automation
* An app icon
* Animations and other UI polish

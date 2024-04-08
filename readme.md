# Movie Listings

## Structure

1. Language - Kotlin
2. Architecture - MVVM Clean
3. Networking - Retrofit
4. Thread - LiveData, Coroutines
5. Test - JUnit, Hilt Testing
6. Design - Navigation Component
7. API Support - Min 24 > 34
8. DI - Hilt
9. UI Component - Jetpack Component
10. Supported Display - ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi 

## Usages

- End point stored securely under `Build.gradle` BuildConfig properties
- Pagination used to paginate a data, `ListingItemsDataSource` fetches a json file from assets
- Used Binding adapter to load image with use of glide `BindingLayoutUtils.loadAsset`
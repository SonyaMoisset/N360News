# N360News

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6dc7ce8322894dd989fb4525f78e2210)](https://www.codacy.com/app/sonya.moisset/MovieApp_PhaseTwo_ADVANCED_ANDROID_NANODEGREE?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SonyaMoisset/MovieApp_PhaseTwo_ADVANCED_ANDROID_NANODEGREE&amp;utm_campaign=Badge_Grade) [![Known Vulnerabilities](https://snyk.io/test/github/sonyamoisset/movieapp_phasetwo_advanced_android_nanodegree/badge.svg?targetFile=app%2Fbuild.gradle)](https://snyk.io/test/github/sonyamoisset/movieapp_phasetwo_advanced_android_nanodegree?targetFile=app%2Fbuild.gradle)   
An app that allows users to discover news.

## Features
- On MainActivity, users can search for news, query by keywords or look at their favorites

<img width="350" src="https://github.com/SonyaMoisset/N360News/blob/master/1.png">

- On SourceActivity, users can filter between 6 categories
- On SourceActivity, users can see different news sources

<img width="350" src="https://github.com/SonyaMoisset/N360News/blob/master/2.png">

- On ArticleActivity, users can see the news from the selected news source

<img width="350" src="https://github.com/SonyaMoisset/N360News/blob/master/3.png">

- Users can also check their favorites articles

<img width="350" src="https://github.com/SonyaMoisset/N360News/blob/master/4.png">


This app is built following the MVVM design pattern and using Android JetPack components
### Architecture Components
  - LiveData
  - Room
  - ViewModel

### Foundation Components
  - AppCompat
  
### UI Components
  - Activities
  - Fragments
  - Constraint Layouts
  - CardView
  - Widget
  
### Behavior Components
  - Permissions
  
### Other libraries
  - Retrofit
  - Picasso
  - AdMob
  - Analytics

## You will need to obtain your API Key on the newsapi.org website and replace it in the gradle.properties file (NewsToken="YOUR_API_KEY")

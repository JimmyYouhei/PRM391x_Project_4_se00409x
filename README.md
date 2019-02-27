# YoutubeApp
This is a Youtube App with simple Sign In and Sign Up Screen for my learning purpose

## Version
-	The project was made by Android Studio 3.2
-	Target SDK was API 27: Android 8.1 Oreo . By the time the app was being made , API 28 was not stable
-	min SDK: 17

## How to install
- If you have an Android OS phone, please go to [google app store](https://play.google.com/store/apps/details?id=vn.org.quantestyoutube2.prm391x_project_4_se00409x) to install. Due to the store forbid me to use Youtube in my title, my title and description may be vague 
- If you want to work with the source code then please open your Android  Studio and use _new_ -> _Project From Version Control_ -> _Git_ and paste _https://github.com/JimmyYouhei/YoutubeApp.git_

## How to use 

-	First You will go to the “Sign In” screen . Please click  “Sign Up” to go to “Sign Up” Screen
-	Then on the “Sign Up” screen please input any username and password that you like. Also, keep in mind that my app does check for errors like no username or no password or confirm password does not match. Then please press on “Sign Up” to go back the “Sign In” screen
-	After that please input your username and password. The next time you open the app, your user name and password are already stored to SQLite so no need to Sign Up again
-	After Signing In you will be redirected to the Video screen where by default it will show videos of Funix (the Unversity that I was studying by the time the app was being made)
    - You can search for any video by click on the 3 dot icon and click on “Search Video”. Please input text into the search box for your video and if the right text is inputted , the video will be shown
    - You can “Log Out” to go back to Sign In screen
    - You can also so see History OF YOUR USERNAME. The different usernames will have different histories as the app record differently for each username
- To play a video  simply click on the video and then it will be played just like normal on Youtube
- Please note that there is a limit of 10 videos to be display


## Features

-	For the SQLite, I use Room Persistence Library for video history database and also normal SQLite API for the account user.  Reason: first the Android documentation recommend using Room instead of SQLite API. Also, I want to try both to evaluate. And Room is superior in term of speed and ease of use. However the Room mechanical is encapsulated so it is not easy to understand like the direct API. As a result, if there is a bug in Room = trouble
-	for the video history database,  I also included the username as a part of the database name to make it unique to each account
-	I use AsyncTask for YoutubeConnector . The AsyncTask is very easy to use compare to code Thread/Looper/Handler 
-	I make a “Command” abstract class to manage methods to be easily reusable, maintainable , readable
-	The Room library is now updated to work in the background Thread
-	Search Video activity was very quick to be made due to the reusable code above


## Possible issues
Sometimes when pressing the hard back button during “VideoPlayer” activity I notice that Android Studio complain that there was Intent receiver leak. I cannot find the source so it may come from the Youtube API. However, I did not see any real error that it caused during using the app

## Known issues
-	If the instant run features of Android Studio is active the app will fail to build So please disable the instant run
-	No landscape layout is designed , It will be less nice when using landscape compare to portrait layout. However, I did practice making landscape layout on [my calculator project](https://github.com/JimmyYouhei/Calculator) so I don’t think it is needed anymore  


## Special note
-	This project is the first project that I used a 3rd party API ([the Youtube API](https://developers.google.com/youtube/android/player/) ). Complex as it is but much quicker than coding everything from scratch and with many features
-	In this project, I coded both SQLite normally and use Room Persistence Library for comparison and I can easily understand why Google recommend Room by the time this app was being made. It is quick , convenience , less code and less class. But I ran into trouble at first that I did not make any primary key for “VideoEntity” class. But it can be solved very quickly
-	My “SignUp” activity do check for common errors when making a new user. So please try ahead
-	This project also uses transition. although minor but just for learning
-	I did reference code  [from others](https://github.com/abhi5658/search-youtube) for Youtube Connector part. I had to replicate all his code again line by line first into a new blank project to understand his code and how to build the connector. His code is fine but it has serious flaws in the SHA1 and Package name and X-Android that make me unable to connect to my API key. I resolved that and build a lot more of my code into my own class to make it run as my intention
-	I also use 2 types of background thread for this project: the Asynctask and Executor with [EventBus](http://greenrobot.org/eventbus/) 
-	The project also uses [Picasso library](https://square.github.io/picasso/) to load the image of Youtube Videos. It makes life easier to load just an image compared to many step coding from scratch.
-	Also, it is strange that by the time the project was being made , nothing is wrong. But when I revisited and move Room library into the background thread . It was also the time when the API 28 was finally released. It gave a strange warning that I was mixing both version 27.1.1 and 27.1.0 for some implementation. As a result, I had to include all those implementations and specifies the version 27.1.1 to resolve the error
-	I also make a special class: Command to store methods that I can reuse in other classes. I wish to make it an Interface but the minimum API 17 did not allow me to do so as it is unsupported. As a result, I made it an abstract class and all method static in order to avoid the class being Initialize by mistake


## License
Free Software that follows the MIT License. More detail can be seen [here](https://github.com/JimmyYouhei/YoutubeApp/blob/master/LICENSE)
  

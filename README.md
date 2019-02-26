# YoutubeApp
This is an Youtube App with simple Sign In and Sign Up Screen for my learning purpose 

## Version
- The project was made by Android Studio 3.2 
- Target SDK was API 27: Android 8.1 Oreo . By the time the app was being made , API 28 was not stable 
- min SDK: 17

## How to install
- If you have an Android OS phone please go to [google app store]() to install  
- If you want to work with the source code then please open your Android Studio and use _new_ -> _Project From Version Control_ -> _Git_ and paste _https://github.com/JimmyYouhei/YoutubeApp.git_

## How to use 

- First You will go to the Sign In Screen . Please click to Sign Up to go to Sign Up Screen

- Then On the Sign Up screen please input username and password that you like. Also keep in mind that my app do check for error like no username or password or confirm password do not match. Then please press on SignUp to go back the Sign In screen

- After that please input your username and password. The next time you open the app, your user name and password is already stored to SQLite so no need to Sign Up again

- After Signing In you will be redirect to the Video screen where by default will show video of Funix (which is the Unversity that I was studying)
    - You can search for Video by click on the 3 dot icon to see other Video. Please input text to search for your video and if right text is inputed , the video will be shown
    - You can Log Out to go back to Sign In screen
    - You can also so see History OF YOUR USERNAME. The different usernames will have different histories as the app record differently for each username

- To play a video just simply push on the video and then it will be play just like normal on Youtube

Please note that there is a limit of 10 videos to be display

## Features

- for the SQLite I use Room Persistence Library for video history database but normal SQLite API for the account user 
Reason: first the Andorid documentation recommend to use Room instead of SQLite API. Also I want to try both to evaluate and Room is quite superior in term of speed and ease of use 
However the Room mechanical is encapsulated so it is not quite easy to understand as the direct API. So if there is a bug in Room = quite troublesome

- for the name of video history I also included the username as a part of the database name to make it unique to each account 

- New Thread and Handler is quite troublesome to make it to be reusable so Instead I use AsyncTask for reusable

- Command abstract class to manage methods to be easily reusable , maintainable , readable 

- The Room library is now updated to work in the background Thread

- Search Video activity was very quick to be made due to reusable code above 

## Possible issues
Sometime when press the hard back button during VideoPlayer activity I notice that Android Studio complain the there was Intent receiver leak. 
I cannot find the source so it may come from the Youtube API . However I did not see any real error that it caused 

## Known issues
if the instant run features of Android Studio is active the app will faild to build 
So please disable the instant run

## Special note
-	This project is the first project that I used 3rd party API ([the Youtube API](https://developers.google.com/youtube/android/player/) ). Complex as it is but much quicker to code everything from scratch and with many features 
-	In this project It is funny but I did code both SQLite normally and use room persistence library for comparison and I can easily understand why google recommend Room by the time that app was being made. It is quick , convenience , less code and less class . But I ran into a trouble at first that I did not make any primary key for VideoEntity class. But it can be solved very quickly 
-	My SignUp activity do check for common error when making new user. So please try ahead
-	This project also use transition . although minor but just for learning 
-	I did reference code [from others](https://github.com/abhi5658/search-youtube) for Youtube Connector part. I had to replicate all of his code again line by line first into a new blank project to understand his code and how to build connector. His code is fine but it has serious flaws in the YoutubeConnector class that make me unable to connect to my API key which is the part SHA1 and Package name and X-Android . I resolved that and build a lot more of my code into my own class to make it run as I intent
-	The I also use 2 type of background thread for this project: the Asynctask and Executor with [EventBus](http://greenrobot.org/eventbus/) 
-	The project also use [Picasso library](https://square.github.io/picasso/) to load the image of Youtube Video. It makes life easier to load just an image compare to many step coding from scratch.
-	Also it is strange that by the time the project was being made ,  nothing is wrong. But when I revisited and move Room library to the background Thread , also the time when the API 28 was finally released . It gave strange warning that I was mixing both version 27.1.1 and 27.1.0 for some implementation . As a result I had to included all those implementation and specifies the version 27.1.1 to resolve the error
-	I also make a special class: Command to store methods that I can reuse in other class. I wish to make it an Interface but the minimum API 17 did not allow me to do so as it is unsupported . As a result I made it an abstract class and all method static in order to avoid the class being Initialize by mistake  

## License
Free Software that follow the MIT License . More detail can be see [here](https://github.com/JimmyYouhei/YoutubeApp/blob/master/LICENSE)
  

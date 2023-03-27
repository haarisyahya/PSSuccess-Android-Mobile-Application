# PSSuccess-Android-Mobile-Application

When the app is launched, note the ‘Splash Screen’ displaying the app logo + theme.
![image](https://user-images.githubusercontent.com/80646420/228089948-a3239019-7b28-4a0b-8de3-cad99476f2d0.png)
*The associated code is under ‘MainActivity.java’ and ‘activity_main.xml’
After the five seconds, the user is redirected to the app’s home screen (‘HomeActivity.java’/’activity_home.xml’), where they are prompted to enter a location:
![image](https://user-images.githubusercontent.com/80646420/228089970-ad0041c0-addd-4acf-8dc6-84a90a0fa6fe.png)
*Note that with the google maps/geocode api, the input can be in the form of any standard location, such as a street address.
After entering an address and clicking ‘search’, the app then displays information about the nearest university:
![image](https://user-images.githubusercontent.com/80646420/228089986-98f38d54-4f30-47e9-bf3e-a8bd0bf25702.png)
From here, the user can click ‘next’ to view information about the next-closest university to their location, or click ‘view on map’ to be redirected to a maps-view of the displayed university:
![image](https://user-images.githubusercontent.com/80646420/228089999-cc98697e-5250-4d64-9f8c-eec54eb27f10.png)
From here, users can interact with the map by inspecting the nearby area, and can additionally press the back arrow at the bottom left of the screen to return to the home page, where they can once again enter a new address.
*The associated code for the map declarations can be found under ‘MapsActivity.java’ and ‘activity_maps.xml’
Additionally, also note the custom app icon and name on the phone home screen: ![image](https://user-images.githubusercontent.com/80646420/228090031-a30a8151-336a-4ee9-8bf0-c405d924f87b.png)

Purpose:
The actual intended purpose of our app is for future university students (such as those still in high school) to have a resource to see their options. By being able to enter their address and view the university on a google map, they are also able to see surrounding locations of interest via moving the map around, thus giving them some perception of the area. Additionally, by being able to view the next-closes location, they can systematically see which universities are closest to them and periodically view the associated maps as well.

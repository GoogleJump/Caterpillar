TravelLog by team Caterpillar gjtravellog.appspot.com 
===========
gjtravellog.appspot.com 

About
--------------
TravelLog is an app that allows the user to save and track travel logs, either online or on their mobile device 

To start your adventure: Sign up to create a new account. Begin adding trips and entries to your log. View your entries and trips as a map so you can see how much of the world you've explored!

TravelLog is a Web App and Android App with a Google App Engine backend
*Web:*
- Javascript, HTML, CSS for web app frontend
- Java for backend servlets
*Android:*
- Java for backend and frontend in Android 

Unimplemented, but planned on features:
	- better sorting and searching through content
	- uploading videos
	- make it a social app where friends can comment and like your trip!
	- restaurant/hotels/places reviews
	
We feel about 80% complete with this project.


**AndroidWorkspace** - the android project and all files related to the android app.

**GAEBackend** - the files for the Google App Engine Backend (Java).

Web App
--------------
To run locally:
Download and install Apache Maven http://maven.apache.org/download.cgi
``` cd Caterpillar/GAEBackend/travellog
mvn clean install
cd travellog-ear
mvn appengine:devserver```
The app should be running at ```localhost:8000```

Android App
--------------


Data storage structure (this is not up to date)
--------------

Entities in the datastore will be divided into 3 kinds:

1) User kind

Each entity will have the following properties (formatted as follows: property name - value type):
a) first name - String
b) last name - String
c) email - String
d) username - String
e) password - String (will be hashed by SHA1 or another encryption algorithm)g) date created - Date
f) dateCreated - Date
g) key - Key

2) Post kind

Each entity will either be a parent entity or a child entity. There will be one parent entity for every trip, and all children entities will be posts in a trip.

Each Trip entity will have the following properties:
a) owner - Key of user that created this trip
b) posters - int collection of user keys of users who can post to the trip (for now just the owner)
c) viewers - int collection of user keys of users who can views the posts of the trip (for now just the owner)
d) title - String
e) description - String
f) location - String for city and country (or can be 2 int properties for long and lat coordinates)
g) dateCreated - Date that trip was made
h) departDate - Date
i) returnDate - Date
j) hashtags - String collection

Each Entry entity will have the following properties:
a) title - String
b) description - String
c) hashtags - String collection
d) location - String for city and country (or can be 2 properties for long and lat coordinates - easier to pin on a map with this info)
e) poster - Key (user key) //do we need this??
f) image key - blob key collection (of images on blob store) (??? Should there be descriptions associated with images and videos?)
g) video key - blob key collection (of videos on blob store)
h) key - Key
i) dateCreated - Date
j) tripPoster - Key of thrip it belongs to

3) BlobInfo kind

Entities of this kind will automatically be added to the datastore when we upload a blob to the blobstore. 

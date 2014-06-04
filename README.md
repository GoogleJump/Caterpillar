Caterpillar
===========

AndroidWorkspace - the android project and all files related to the android app.

GAEBackend - the files for the Google App Engine Backend (Java).


Data storage structure
======================

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
a) owner - String
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
e) poster - int (user key)
f) image key - blob key collection (of images on blob store) (??? Should there be descriptions associated with images and videos?)
g) video key - blob key collection (of videos on blob store)
h) key - Key
i) dateCreated - Date

3) BlobInfo kind

Entities of this kind will automatically be added to the datastore when we upload a blob to the blobstore. 

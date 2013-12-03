Kinect Drawing App

Installation

Application should be installed in the directory...  If the tracks specified exist outside of the main application package (ex. My music, Documents, etc) make sure to include the path in the configuration file.

Configuration

All configuration is done via an XML file located in the bin/data folder of the main application package. This file contains absolute paths for assets, including images and fonts, as well as specifying tracks and input region.

Logging

There exists both an error and logging text file in the logs folder of the main application package. These should be checked in the application fails and may provide insight into a broken path or input error

Input Regions

The application was designed to use multiple frameworks including GestTracker 3D Hand tracker, and The Simple Open NI framework for Processing. The active framework should be defined in the configuration file for 'input'.

GestTracker 3D Hand Tracker Region

This region uses values sent by hand tracker using the gestOSC Sender application. Both the tracker and the sender must be installed and running for this region to work. Currently the region covers up to 3 hands but further testing is required. If the Simple OpenNI framework was previously installed you may find the tracker won't be able to find the data stream of the Kinect camera, uninstalling the Kinect API drivers usually fixes this.

GestTracker Restrictions

The hand data being sent does not identify users, and there is no way to explicitly set which or how many users get registered. Because of this it's sometimes difficult to map the hand to the user and can create a confusing experience. Also, the tracker tends to loose registration more when the hand passes in front on the user's body.


Simple OpenNI

This framework requires the Microsoft Kinect API and drivers be installed. Link to drivers....

Simple OpenNI Restrictions

To register a hand the framework requires a gesture from the user. Simply waving one's hand should do the trick.


Mouse

This is the default region for the application, and provides no multiuser, only the use of a mouse to explore the application.

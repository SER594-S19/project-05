# Project-04
Simulating Sensing Devices

Team Members: Aditya Vikram, Amit Pandey, Aprajita Thakur, Shreyas Gaiki, Srinivas Kartik Angadi and Sanket Wadyalkar

Three different servers (Eye tracker, BCI and Face simulator ) will push their generated data to a client when 
connected via proper port number. Server GUI and Client GUI code are written in JAVA and prediction algorithm
is written in Python

# Dependencies

Software tool: Eclipse
Necessary Python modules: pandas, matplotlib, mpl_toolkits, numpy, keras, Pillow
Command to install modules: pip install <module name>


# How to run

1) Browse to src > EyeTrackerV1 package and run Gui.java.
2) Browse to src > Face > Core package and run Gui.java.
3) Browse to src > BCI > Core package and run Gui.java.
4) Browse to src > clientEyeTrackerV1 package and run ClientDemo.java.
5) Make adjustments as desired on all the GUIs using sliders and hit "run" or "start".
6) Go to client GUI, enter port number in corresponding text fields and hit "connect".
7) First connect Face simulator then BCI and then Eye tracker in that order. 
8) Port numbers are available on server GUI.
9) Repeat step 6 to connect to all three servers.
10) Wait for as long as you desire depending upon the amount of data you want to work with and hit "Stop servers".
11) You can switch tabs on client GUI to check the raw data.
12) Click on Predict and wait till the Prediction tab opens up automatically
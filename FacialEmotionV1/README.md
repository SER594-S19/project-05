# project-05
Added functionality for integrating and cleaning up data from multiple servers and generating 3D graph plots as images using Matplotlib.

Steps to RUN:
1) Run mulitple servers by opening different Eclipse projects and running the GUI.java in the Core directory.
2) For example : My sample code run includes data from Heart Rate Monitor and Facial Detection simulators.
3) Data can be collected into .csv files by configuring the port number and running the client.
4) .csv files are generated based on the server port number. for example: 1594.csv, 1593.csv etc.
5) After data collection is completed , run the command --> python3 readyToGoData.py inside Client/Scripts/ directory. This  will join the data with other servers data and will also do a cleanup resulting in a merged.csv file.
6) Next run --> python3 graphGenerator.py,  this generates a 3D graph plot in the form of a png image by normalizing values in the merged.csv file to P,A,D format.
7) The training data can be found inside Client/TrainingImages directory.

Team members: Sumanth Paranjape | Shashidhar Reddy Vanteru | Srivan Reddy Gutha | Surya Cherukuri | Narendra Mohan | Vaibhav 

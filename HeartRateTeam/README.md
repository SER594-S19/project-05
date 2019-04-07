# project-05
# Team members
    Adhiraj Tikku
    Harshita Kajal
    Manish Tandon
    Nikita Bahl
    

# To start the two servers:
    - Run Gui.java file present at src/main/java/HeartRateServer2/Gui.java file
    - This will run at port 1700 for HeartRate

     - Run Gui.java file present at src/main/java/facialgesturesServer1/Gui.java file
     - This will run at port 1598 for Facial Gestures
    
# To run the client and connect to servers for this application:
    - do mvn clean install
    - run the ClientUI.java file present at src/main/java/MultiModalClient/ClientUI.java
    - Change ports to 1598 and 1700, to connect to Facial Gestures and HeartRate server respectively.
    - Change server values exmaple HeartRate server inputs to view changes in client UI emoticons.



# For the live prediction of data :
    - We have used two servers namely heart rate data and facial data.
    - We have given more weightage to the heart rate values.
    - We have trained our model using the data.txt as the train data and the test data is live from the server.
    - If you vary the heartRate and facialGesture values, the Pleasure and Arousal values get plotted in the client.
    - We have taken the Pleasure and Arousal values to be positive if > 0.4 and negative otherwise.
    - We predict Happy if P>0.6 and A>0.6, Sad if P<0.4, A<0.4 and Normal otherwise.
    - The neural network gives us an integer output for these three classes : Happy(0), Normal(1), Sad(2).
    - These values are depicted on the client as the smiley faces.
    - If the heart rate values are at 'Resting' state, the predicted label is closer to 2(Sad).
    - If the heart rate values are at 'Moderate' state, the predicted label is closer to 1(Normal).
    - If the heart rate values are at 'Vigorous' state, the predicted label is closer to 0(Happy).


# project-05
# To run the client for this application:
    - do mvn clean install
    - run the ClientUI.java file present in src/main/java/ClientHeartRateTeam/ClientUI

# To connect the two servers:
    - Run Gui.java file present in src/main/java/HeartRate/Gui.java file
    - This will run at port 1700 for HeartRate

     - Run Gui.java file present in src/main/java/facialGestures/Gui.java file
     - This will run at port 1598 for HeartRate

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


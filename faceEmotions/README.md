--------------------------------------------------------------------------------
STEPS TO RUN:
1. Clone Entire Project
2. Please download CNN.zip file from this link:  https://drive.google.com/open?id=16ZEhKPQ4KGGmxY_iwQLoIo5U3f_2-tcz
It contains all dependencies for python and the python interpreter. 
3. When you download the zip, you will get a folder named CNN. Kindly copy paste all the items in that folder to the CNN folder in the project (which is left empty). We did this so that you don't have to deal with Python imports. This is however a crucial part. So please let us know if any problems occur. 
4. in terminal type "mvn clean install"
5. Now run GUI in /Core/GUI, click RUN. For good images, increase values of thinking, concentration and agree( above 75%) and
 let values of disagree, unsure and frustate be below 10%.
6. Now run ClientDemo in /Client/ClientDemo, in the face tab select port number which server is runnng on. Click Connect.
7. After few minutes, stop RUN ON SERVER FIRST and then click Disconnect on Client, your png images should be created.
8. Now click on Predict, it will predict the images created when you ran the Server.


_____________________________________________________________________________________________________

Project-04: POINTS TO CONSIDER
1. IMPROVED SERVER SIDE UI: /res/serverUIFinal.png
2. DATA GENERATED FROM ONE SERVER AND PLOTTED
3. DATA FROM OTHER TEAMS WAS GENERATED AND MERGED IN CSV, HOWEVER DID NOT INCLUDE IT IN ANALYSIS for Neural Nets. YOU CAN FIND THE CODE WE USED IN THE directory "stateMachineCode", we however did not incorporate it in our analysis.
3. THE X-AXIS WAS TAKEN AS PLEASURE AND Y-AXIS AS AROUSAL, WE TRANSFORMED THE PIXEL COORDINATES TO GRAPH COORDINATES
USING A FORMULA. THE LINES YOU SEE ARE PLOTTED POINTS ON A GRAPH, WE REMOVED THE X-AXIS AND Y-AXIS SO THAT OUR NEURAL NET COULD TRAIN BETTER.
4. IMAGE GENEREATED IS OF PIXEL 50 * 50
5. WE GENERATED GOOD IMAGES : HIGH AGREE, HIGH CONCENTRATE, HIGH THINKING
6. WE GENERATED BAD IMAGES : HIGH DISAGREE, HIGH FRUSTATE, HIGH UNSURE
7. WE GENERATED OKAY IMAGES : ALL MIDDLE VALUES
8. TOTAL TRAINING IMAGES GENERATED = 3200, TOTAL TEST IMAGES GENERATED = 1500
10. We Ran Our Neural Net, TEST SET ACCURACY WAS BETWEEN 0.87 AND 0.9.


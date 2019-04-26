from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
from numpy import array
import csv
import glob
import predictEmotion
import os

'''graphGenerator.py: Normalizes the merged data into P,A,D values and plots it on a 3D graph
    Author: Sumanth Paranjape '''

#Map of column names to Pleasure,Arousal,Dominance
attributeToPAD = {
    "agreement": "+,+,-",
    "concentrating": "+,-,-",
    "disagreement": "-,-,+",
    "interested": "+,+,-",
    "thinking": "-,+,-",
    "unsure": "-,+,-",
    "frustration": "-,-,+",
    "engagement": "+,+,-",
    "short_term_engagement": "-,+,+",
    "long_term_engagement": "+,+,-",
    "heartrate": "+,+,+",
    "meditation": "+,-,-",
    "frustration": "-,-,+"
}

#Initializing variables
columnsNames = list()
P = 0
A = 0
D = 0

#P,A,D arrays
pleasure = list()
arousal = list()
dominance = list()

pngFiles = list()
pngFiles = glob.glob('Client/TrainingImages/*.png')
pngFiles.sort()

startT = ""
endT = ""

#Converts the merged CSV to Pleasure,Arousal,Dominance
def convertToPADValues(startTime,endTime):
    global startT
    global endT
    startT = startTime
    endT = endTime
    print("Attributes To PAD:",attributeToPAD)
    with open('Client/CSVDumps/merged.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=',')
        global columnsNames
        if(columnsNames == []):
            columnsNames = next(readCSV)
            print("Column Names:",columnsNames)
        for row in readCSV:
            numCols = len(row)
            setPADArrays(numCols)
            for i in range(0,numCols):
                if(i > 0):
                    addPADValues(row[i],columnsNames[i])
    generate3DImage()

#Does summation of P,A,D values of each row
def addPADValues(value,columnName):
    attribute=''
    if('_x' in columnName):
        attribute = columnName.replace('_x','')
    else:
        attribute = columnName.replace('_y','')
    attribute = attribute.strip()
    attribute = attribute.replace(' ','_')
        #elif("_y" in columnName):
        #else:
        #attribute = columnName
    global P
    global A
    global D
    padSigns = attributeToPAD[attribute.lower()].split(',')
    P += float(value) if(padSigns[0] == "+") else (-1) * float(value)
    A += float(value) if(padSigns[1] == "+") else (-1) * float(value)
    D += float(value) if(padSigns[2] == "+") else (-1) * float(value)

#Set P,A,D values into respective arrays
def setPADArrays(numCols):
	global P
	global A
	global D

	total = numCols-1
	if(P != 0 and A != 0 and D != 0):
		pleasure.append(P/total)
		arousal.append(A/total)
		dominance.append(D/total)

	P = 0
	A = 0
	D = 0

#Plot the P,A,D arrays in a 3D graph and generate image
def generate3DImage():
    global pleasure
    global arousal
    global dominance
    global start
    global end
    
    remove = 'rm Client/ImagesToPredict/predict/*'
    os.system(remove)

    start = 0;
    end = 10;
    length = len(pleasure)
    
    while end <= length:
        print("Start=",start)
        print("End=",end)
        fig = plt.figure()
        ax = fig.add_subplot(111, projection='3d')
        ax.scatter(pleasure[start:end], arousal[start:end], dominance[start:end])

        ax.set_xlabel('Pleasure')
        ax.set_ylabel('Arousal')
        ax.set_zlabel('Dominance')
        #plt.show()
        plt.savefig('Client/ImagesToPredict/predict/PAD_'+str(start)+'.png')
        start += 50
        end += 50

    predictEmotion.makePrediction(startT,endT)

if __name__== "__main__":
  convertToPADValues(startTime,endTime)

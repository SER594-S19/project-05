from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import csv

'''graphGenerator.py: Normalizes the merged data into P,A,D values and plots it on a 3D graph
    Author: Sumanth Paranjape '''

#Map of column names to P,A,D
attributeToPAD = {
    "agreement": "+,+,-",
    "concentrating": "+,-,-",
    "disagreement": "-,-,+",
    "interested": "+,+,-",
    "thinking": "+,-,-",
    "unsure": "-,+,-",
    "frustration": "-,-,+",
    "engagement": "+,+,-",
    "short_term_engagement": "-,+,+",
    "long_term_engagement": "+,+,-",
    "heartrate": "+,+,+"
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

#Converts the merged CSV to Pleasure,Arousal,Dominance
def convertToPADValues():
    print("Attributes To PAD:",attributeToPAD)
    with open('../CSVDumps/merged.csv') as csvfile:
        readCSV = csv.reader(csvfile, delimiter=',')
        global columnsNames
        if(columnsNames == []):
            columnsNames = next(readCSV)
            print("Column Names:",columnsNames)
        for row in readCSV:
            numCols = len(row)
            setPADArrays()
            for i in range(0,numCols):
                if(i > 0):
                    addPADValues(row[i],columnsNames[i])

#Does summation of P,A,D values of each row
def addPADValues(value,columnName):
    attribute = ""
    if("_x" in columnName):
        attribute = columnName.replace('_x','')
    elif("_y" in columnName):
        attribute = columnName.replace('_y','')
    else:
        attribute = columnName
    global P
    global A
    global D
    padSigns = attributeToPAD[attribute.strip()].split(',')
    P += float(value) if(padSigns[0] == "+") else (-1) * float(value)
    A += float(value) if(padSigns[1] == "+") else (-1) * float(value)
    D += float(value) if(padSigns[2] == "+") else (-1) * float(value)

#Set P,A,D values into respective arrays
def setPADArrays():
    global P
    global A
    global D
    if(P != 0 and A != 0 and D != 0):
        pleasure.append(P)
        arousal.append(A)
        dominance.append(D)
    P = 0
    A = 0
    D = 0

#Plot the P,A,D arrays in a 3D graph and generate image
def generate3DImage():
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    global pleasure
    global arousal
    global dominance
    print("P=",pleasure)
    print("A=",arousal)
    print("D=",dominance)
    ax.scatter(pleasure, arousal, dominance, c='r', marker='o')

    ax.set_xlabel('Pleasure')
    ax.set_ylabel('Arousal')
    ax.set_zlabel('Dominance')

    #plt.show()
    plt.savefig('../TrainingImages/sample2.png')

if __name__== "__main__":
  convertToPADValues()
  generate3DImage()


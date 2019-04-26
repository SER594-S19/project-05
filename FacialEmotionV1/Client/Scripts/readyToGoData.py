import pandas as pd
import numpy as np
import glob
import graphGenerator
import os

# Data Integration/Cleaning Script | Author: Sumanth Paranjape

#Outer Join Data from multiple servers. CSV file names are based on the server port number
def mergeDataFromSources():
    csvFiles = glob.glob('Client/CSVDumps/*.csv')
    mergedDF = pd.DataFrame()
    for i in range(len(csvFiles)):
        if(mergedDF.empty):
            DFL = pd.read_csv(csvFiles[i])
            mergedDF = DFL
            if(i+1<len(csvFiles)):
                DFR = pd.read_csv(csvFiles[i+1])
                mergedDF = pd.merge(left=DFL, right=DFR, on="timestamp", how="outer")
        else:
            DFR = pd.read_csv(csvFiles[i])
            mergedDF = pd.merge(left=mergedDF, right=DFR, on="timestamp", how="outer")
    print("Merged Successfully!")
    removeOutliersAndNANs(mergedDF.columns.tolist(),mergedDF)

#Remove Outliers: Data must be in the range of 0 and 1 | Fill NaN with mean value.
def removeOutliersAndNANs(mergedColumnsNames,mergedData):
    startTime = 0
    endTime = 0
    rowCount = len(mergedData.index)
    for name in mergedColumnsNames:
        if(name != "timestamp"):
            try:
                colVal = np.array(mergedData[name].values.tolist())
                mergedData[name] = np.where(colVal > 1.0, float('nan'), colVal).tolist() or np.where(colVal < 0.0, float('nan'), colVal).tolist()
                meanValue=mergedData[name].mean()
                mergedData[name]=mergedData[name].fillna(meanValue)
            except:
                print(colVal)
        else:
            startTime = mergedData['timestamp'].iloc[0]
            endTime = mergedData['timestamp'].iloc[rowCount-1]

    mergedData.to_csv("Client/CSVDumps/merged.csv",index=False)
    deleteCSVs()
    print("Start time:",startTime)
    print("End time:",endTime)
    graphGenerator.convertToPADValues(startTime,endTime)

#Deletes all csv files except merged file
def deleteCSVs():
    csvFiles = glob.glob('Client/CSVDumps/*.csv')
    for path in csvFiles:
        if("merged" in path):
            continue
        else:
            os.system("rm "+path)

if __name__== "__main__":
  mergeDataFromSources()

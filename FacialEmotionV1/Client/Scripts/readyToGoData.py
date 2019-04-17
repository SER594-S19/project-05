import pandas as pd
import numpy as np
import glob

# Data Integration/Cleaning Script | Author: Sumanth Paranjape

#Outer Join Data from multiple servers. CSV file names are based on the server port number
def mergeDataFromSources():
    csvFiles = glob.glob('../CSVDumps/*.csv')
    mergedDF = pd.DataFrame()
    for path in csvFiles:
        if(mergedDF.empty):
            DFL = pd.read_csv(path)
            DFR = pd.read_csv(path)
            mergedDF = pd.merge(left=DFL, right=DFR, on="timestamp", how="outer")
        else:
            DFR = pd.read_csv(path)
            mergedDF = pd.merge(left=mergedDF, right=DFR, on="timestamp", how="outer")
    print("Merged Successfully!")
    removeOutliersAndNANs(mergedDF.columns.tolist(),mergedDF)

#Remove Outliers: Data must be in the range of 0 and 1 | Fill NaN with mean value.
def removeOutliersAndNANs(mergedColumnsNames,mergedData):
    for name in mergedColumnsNames:
        if(name != "timestamp"):
            try:
                colVal = np.array(mergedData[name].values.tolist())
                mergedData[name] = np.where(colVal > 1.0, float('nan'), colVal).tolist() or np.where(colVal < 0.0, float('nan'), colVal).tolist()
                meanValue=mergedData[name].mean()
                mergedData[name]=mergedData[name].fillna(meanValue)
            except:
                print(colVal)

    mergedData.to_csv("../CSVDumps/merged.csv",index=False)

if __name__== "__main__":
  mergeDataFromSources()

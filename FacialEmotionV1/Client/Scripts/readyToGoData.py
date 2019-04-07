import pandas as pd
import numpy as np

# Data Integration/Cleaning Script | Author: Sumanth Paranjape

#Outer Join Data from multiple servers. CSV file names are based on the server port number
def mergeDataFromSources():
	brain_computer_interface = pd.read_csv("../CSVDumps/1594.csv")
	skin_conductance = pd.read_csv("../CSVDumps/1593.csv")

	mergedData = pd.merge(left=brain_computer_interface, right=skin_conductance, on="time", how="outer")
	#print("Merged Successfully!")
	#print("Column names:",mergedData.columns.tolist())
	removeOutliersAndNANs(mergedData.columns.tolist(),mergedData)

#Remove Outliers: Data must be in the range of 0 and 1.
def removeOutliersAndNANs(mergedColumnsNames,mergedData):
	print("Merged Data:",mergedData)
	for name in mergedColumnsNames:
		if(name != "time"):
			colVal = np.array(mergedData[name].values.tolist())
			mergedData[name] = np.where(colVal > 1, float('nan'), colVal).tolist() or np.where(colVal < 0, float('nan'), colVal).tolist() 
		meanValue=mergedData[name].mean()
		mergedData[name]=mergedData[name].fillna(meanValue)
	#print("Merged Data After removing outliers:",mergedData)
	#print("Merged data has been cleaned Successfully")
	mergedData.to_csv("../CSVDumps/merged.csv",index=False)

#def removeOutliers(mergedData):
  
if __name__== "__main__":
  mergeDataFromSources()

import numpy as np
import pandas as pd
import csv 

def cleanData(colNames,finalData):
	for cName in colNames:
		if(cName != "time"):
			colVal = np.array(finalData[cName].values.tolist())
			finalData[cName] = np.where(colVal > 1, float('nan'), colVal).tolist() or np.where(colVal < 0, float('nan'), colVal).tolist() 
		meanValue=finalData[cName].mean()
		finalData[cName]=finalData[cName].fillna(meanValue)
	finalData.to_csv("finalData.csv",index=False)
  
def mergedData():
	sc = pd.read_csv("1594.csv")
	bci = pd.read_csv("1595.csv")
	finalData = pd.merge(left=sc, right=bci, on="time", how="outer")
	cleanData(finalData.columns.tolist(),finalData)

if __name__== "__main__":
  mergedData()
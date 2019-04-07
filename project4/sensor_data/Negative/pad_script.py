from pandas import DataFrame, read_csv
from matplotlib.pyplot import *
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
#import csv
import re 
import random as rd

##################### NEEDED: WRITE A LOGIC TO SAMPLE DATA ONLY FOR 2-3 S AND EXPORT IT TO final.csv ###########
##################### DONE: SAMPLE ENTIRE CSV AND EXPORT IT TO final.csv ################################## 

file = 'face.csv'
df = pd.read_csv(file)
df = df.replace(0,np.nan)
df = df.fillna(method = 'bfill') 
df['P1'] = df['Agreement']+df['Concentrating']+(-1)*df['Disagreement']+df['Thinking']+(-1)*df['Unsure']+df['Interested']
df['A1'] = (-1)*df['Agreement']+(-1)*df['Concentrating']+df['Disagreement']+(-1)*df['Thinking']+df['Unsure']+df['Interested']
df['D1'] = (-1)*df['Agreement']+df['Concentrating']+df['Disagreement']+df['Thinking']+(-1)*df['Unsure']+(-1)*df['Interested']
header = ['timestamp','P1','A1','D1']
df.to_csv('face_output.csv', columns = header, index = False )
print('level 1')
 
file2 = 'brain.csv'
df2 = pd.read_csv(file2) 
#df2 = df2.drop_duplicates()
df2 = df2.replace(0,np.nan)
df2 = df2.fillna(method = 'bfill')
df2['P2'] = (-1)*df2['Frustration']+df2['Engagement']+df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
df2['A2'] = df2['Frustration']+df2['Engagement']+(-1)*df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
df2['D2'] = df2['Frustration']+df2['Engagement']+df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
header = ['timestamp','P2','A2','D2']
df2.to_csv('bci_output.csv', columns = header, index=False)
print('level 2')

file3 = 'face_output.csv'
file4 = 'bci_output.csv'
df3 = pd.read_csv(file3)
df4 = pd.read_csv(file4)


df5 = pd.merge(df4,df3,how="outer",on="timestamp")
df5 = df5.fillna(method = 'bfill')

df5['P'] = df5['P1'] + df5['P2']
df5['A'] = df5['A1'] + df5['A2']
df5['D'] = df5['D1'] + df5['D2']

df5['t_sec'] = df5['timestamp'].str[14:19]
df5['t_sec'] = df5['t_sec'].str.replace(':','')
#print(df5.t_sec.head(100))
header = ['timestamp','t_sec','P','A','D']
df5.to_csv('final.csv', columns = header, index=False)
Shape = df5.shape
i = 0
j = i+1
x1 = []
y1 = []
z1 = []
print(Shape[0])

while i <= 56046:
	if df5.at[i,'t_sec'] == df5.at[j,'t_sec']:
		
		x1.append(df5.at[i,'P'])
		y1.append(df5.at[i,'A'])
		z1.append(df5.at[i,'D'])

	elif df5.at[i,'t_sec'] != df5.at[j,'t_sec'] or i == 56046:
		x1.append(df5.at[i,'P'])
		y1.append(df5.at[i,'A'])
		z1.append(df5.at[i,'D'])
		#print(str(len(x1))+ "," + str(df6.at[i,'time in secs']))
		fig = plt.figure()
		ax = Axes3D(fig)
		ax.set_xlabel('P')
		ax.set_ylabel('A')
		ax.set_zlabel('D')
		ax.set_yticklabels([])
		ax.set_xticklabels([])
		ax.set_zticklabels([])	
		ax.scatter(x1,y1,z1)
		plt.savefig('PAD-'+str(df5.at[i,'t_sec']))
		plt.close(fig)
		x1 = []
		y1 = []
		z1 = []
	j = j+1
	i = i+1
	
print('level 5')

from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import os
import predict

##################### NEEDED: WRITE A LOGIC TO SAMPLE DATA ONLY FOR 2-3 S AND EXPORT IT TO final.csv ###########
##################### DONE: SAMPLE ENTIRE CSV AND EXPORT IT TO final.csv ##################################

mydir = './test_parent/test_plots'
filelist = [ f for f in os.listdir(mydir) if f.endswith(".png") ]
for f in filelist:
    os.remove(os.path.join(mydir, f))

#print("Exisiting files deleted")

file = './sensor_data/Positive/face.csv'
df = pd.read_csv(file)
df = df.dropna()
df = df.replace(0,np.nan)
df = df.fillna(method = 'bfill')
df = df.dropna()
df['P1'] = df['Agreement']+df['Concentrating']+(-1)*df['Disagreement']+df['Thinking']+(-1)*df['Unsure']+df['Interested']
df['A1'] = (-1)*df['Agreement']+(-1)*df['Concentrating']+df['Disagreement']+(-1)*df['Thinking']+df['Unsure']+df['Interested']
df['D1'] = (-1)*df['Agreement']+df['Concentrating']+df['Disagreement']+df['Thinking']+(-1)*df['Unsure']+(-1)*df['Interested']
header = ['timestamp','P1','A1','D1']
df.to_csv('./intermediate_csv/face_output.csv', columns = header, index = False )
#print('level 1')

file2 = './sensor_data/Positive/brain.csv'
df2 = pd.read_csv(file2)
df2 = df2.dropna()
#df2 = df2.drop_duplicates()
df2 = df2.replace(0,np.nan)
df2 = df2.fillna(method = 'bfill')
df2 = df2.dropna()
df2['P2'] = (-1)*df2['Frustration']+df2['Engagement']+df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
df2['A2'] = df2['Frustration']+df2['Engagement']+(-1)*df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
df2['D2'] = df2['Frustration']+df2['Engagement']+df2['Meditation']+df2['Short_term_engagement']+df2['Long_term_engagement']
header = ['timestamp','P2','A2','D2']
df2.to_csv('./intermediate_csv/bci_output.csv', columns = header, index=False)
#print('level 2')

file3 = './intermediate_csv/face_output.csv'
file4 = './intermediate_csv/bci_output.csv'
df3 = pd.read_csv(file3)
df4 = pd.read_csv(file4)


df5 = pd.merge(df4,df3,how="outer",on="timestamp")
df5 = df5.fillna(method = 'bfill')
df5 = df5.fillna(method = 'ffill')
df5 = df5.dropna()
df5['P'] = df5['P1'] + df5['P2']
df5['A'] = df5['A1'] + df5['A2']
df5['D'] = df5['D1'] + df5['D2']

df5['t_sec'] = df5['timestamp'].str[14:19]
df5['t_sec'] = df5['t_sec'].str.replace(':','')
#print(df5.t_sec.head(100))
header = ['timestamp','t_sec','P','A','D']
df5.to_csv('./intermediate_csv/final.csv', columns = header, index=False)
Shape = df5.shape
i = 0
j = i+1
x1 = []
y1 = []
z1 = []
#print(Shape[0])
print("Plotting PAD and exporting images")
while i < (Shape[0])-2:
#while i<1000:
    if df5.at[i,'t_sec'] == df5.at[j,'t_sec']:
        x1.append(df5.at[i,'P'])
        y1.append(df5.at[i,'A'])
        z1.append(df5.at[i,'D'])
        #print(i,j,"if")


    elif df5.at[i,'t_sec'] != df5.at[j,'t_sec'] or i == (Shape[0]) :

        x1.append(df5.at[i,'P'])
        y1.append(df5.at[i,'A'])
        z1.append(df5.at[i,'D'])
        fig = plt.figure()
        ax = Axes3D(fig)
        ax.set_xlabel('P')
        ax.set_ylabel('A')
        ax.set_zlabel('D')
        ax.set_yticklabels([])
        ax.set_xticklabels([])
        ax.set_zticklabels([])
        ax.scatter(x1,y1,z1)
        plt.savefig('./test_parent/test_plots/PAD-'+str(df5.at[i,'t_sec']))
        plt.close(fig)
        x1 = []
        y1 = []
        z1 = []
        #print(i,j,"else")
    j = j+1
    i = i+1


#print('level 5')

#####Calling predict.py#########
print("Starting prediction")
predict.make_predictions()

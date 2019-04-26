# -*- coding: utf-8 -*-
from keras.layers import Conv2D
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import MaxPooling2D
from keras.preprocessing.image import ImageDataGenerator
import numpy as np
from keras.layers.normalization import BatchNormalization
from keras.models import Sequential
import os
import time


def makePrediction(startTime,endTime):
    predictDir = 'Client/ImagesToPredict'
    finalDir = 'Client/ImagesToPredict/predict'
    imageClassifier = Sequential()
    imageClassifier = Sequential()
    # First Convolution Layer and Pooling Layer
    imageClassifier.add(Conv2D(32,(3,3), input_shape = (64,64,3), activation = 'relu'))
    imageClassifier.add(MaxPooling2D(pool_size = (2,2)))
    imageClassifier.add(BatchNormalization())
    
    # Second Convolution Layer and Pooling Layer
    imageClassifier.add(Conv2D(64,(3,3), activation = 'relu'))
    imageClassifier.add(MaxPooling2D(pool_size = (2,2)))
    imageClassifier.add(BatchNormalization())
    
    imageClassifier.add(Conv2D(64,(3,3), activation = 'relu'))
    imageClassifier.add(MaxPooling2D(pool_size = (2,2)))
    imageClassifier.add(BatchNormalization())
    
    imageClassifier.add(Conv2D(96,(3,3), activation = 'relu'))
    imageClassifier.add(MaxPooling2D(pool_size = (2,2)))
    imageClassifier.add(BatchNormalization())
    
    # Flattening Layer
    imageClassifier.add(Flatten())
    
    imageClassifier.add(Dense(units = 128, activation = 'relu'))
    imageClassifier.add(Dense(units = 3, activation = 'softmax'))
    
    # Setting loss and optimizer and compile time
    imageClassifier.compile(optimizer = 'adam', loss = 'binary_crossentropy', metrics = ['accuracy'])
    
    imageClassifier.load_weights("Client/Model/emotion-predict-model.h5")
    
    test_data_scale = ImageDataGenerator(rescale = 1./255)
    
    test_generator = test_data_scale.flow_from_directory(
        directory=predictDir,
        target_size=(64, 64),
        color_mode="rgb",
        batch_size=32,
        class_mode=None,
        shuffle=False
    ) 
    
    a = [f for f in os.listdir(finalDir) if os.path.isfile(os.path.join(finalDir, f))]
    total = len(a)-1

    test_generator.reset()
    
    pred=imageClassifier.predict_generator(test_generator,verbose=1,steps=total/32)
    print(pred)
    
    predictedClasses=np.argmax(pred,axis = 1)
    
    train_data_scale = ImageDataGenerator(rescale = 1./255, shear_range = 0.2, zoom_range = 0.2, horizontal_flip = True)
    
    train_set = train_data_scale.flow_from_directory('Client/TrainingImages',target_size = (64,64), batch_size = 32, class_mode = 'categorical')
    
    labels = (train_set.class_indices)
    print("Labels:",labels)
    labels = dict((v,k) for k,v in labels.items())
    predictions = [labels[k] for k in predictedClasses]
    
    j=0
    try:
        os.remove("results.txt")
        os.remove("Client/CSVDumps/merged.csv")
    except:
        print("Results file doesnt exist")

    file = open('results.txt','w')
    file.write(findDuration(startTime,endTime)+'\n')

    for i in predictions:
        file.write(str(i+"\n"))
        print("Prediction for "+str(j)+" is: "+str(i))
        j+=1

    file.close()

def findDuration(startTime,endTime):
    time_tuple = time.strptime(str(startTime), '%d-%m-%Y %H:%M:%S:%f')
    startEpoch = time.mktime(time_tuple)

    time_tuple = time.strptime(str(endTime), '%d-%m-%Y %H:%M:%S:%f')
    endEpoch = time.mktime(time_tuple)

    duration = abs(endEpoch-startEpoch)/60
    return str(duration)


if __name__ == "__main__":
    makePrediction(startTime,endTime)

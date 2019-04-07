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


def make_predictions():
    #print("Hello World")
    pred_dir = 'test_parent'
    final_dir = './test_parent/test_plots'
    classifier = Sequential()
    classifier = Sequential()
    # First Convolution Layer and Pooling Layer
    classifier.add(Conv2D(32,(3,3), input_shape = (64,64,3), activation = 'relu'))
    classifier.add(MaxPooling2D(pool_size = (2,2)))
    classifier.add(BatchNormalization())
    
    # Second Convolution Layer and Pooling Layer
    classifier.add(Conv2D(64,(3,3), activation = 'relu'))
    classifier.add(MaxPooling2D(pool_size = (2,2)))
    classifier.add(BatchNormalization())
    
    classifier.add(Conv2D(64,(3,3), activation = 'relu'))
    classifier.add(MaxPooling2D(pool_size = (2,2)))
    classifier.add(BatchNormalization())
    
    classifier.add(Conv2D(96,(3,3), activation = 'relu'))
    classifier.add(MaxPooling2D(pool_size = (2,2)))
    classifier.add(BatchNormalization())
    
    # Flattening Layer
    classifier.add(Flatten())
    
    classifier.add(Dense(units = 128, activation = 'relu'))
    classifier.add(Dense(units = 3, activation = 'softmax'))
    
    # Time to compile the network
    classifier.compile(optimizer = 'adam', loss = 'binary_crossentropy', metrics = ['accuracy'])
    
    classifier.load_weights("./model/weights-Test-CNN.h5")
    
    test_data_scale = ImageDataGenerator(rescale = 1./255)
    
    test_generator = test_data_scale.flow_from_directory(
        directory=pred_dir,
        target_size=(64, 64),
        color_mode="rgb",
        batch_size=32,
        class_mode=None,
        shuffle=False
    ) 
    
    a = [f for f in os.listdir(final_dir) if os.path.isfile(os.path.join(final_dir, f))]
    #print(len(a))
    total_images = len(a)-1

    test_generator.reset()
    
    pred=classifier.predict_generator(test_generator,verbose=1,steps=total_images/32)
    #print(pred)
    
    predicted_class_indices=np.argmax(pred,axis = 1)
    #print(predicted_class_indices)
    
    train_data_scale = ImageDataGenerator(rescale = 1./255, shear_range = 0.2, zoom_range = 0.2, horizontal_flip = True)
    
    train_set = train_data_scale.flow_from_directory('./hci_plots/train_set',target_size = (64,64), batch_size = 32, class_mode = 'categorical')
    
    labels = (train_set.class_indices)
    labels = dict((v,k) for k,v in labels.items())
    predictions = [labels[k] for k in predicted_class_indices]
    
    j=0
    for i in predictions:
        print("Prediction for image number "+str(j)+" is: "+str(i))
        j+=1

if __name__ == "__main__":
    make_predictions()
    
 

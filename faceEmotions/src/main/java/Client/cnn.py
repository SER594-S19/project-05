import org.python.core ;
import org.python.util.PythonInterpreter;

from keras.models import Sequential
from keras.layers import Conv2D
from keras.layers import Dense
from keras.layers import Flatten
from keras.layers import MaxPooling2D
from keras.preprocessing.image import ImageDataGenerator
from keras.layers.normalization import BatchNormalization
import tensorflow as tf
from PIL import *
import numpy as np

def NN():
    classifier = Sequential()
    # First Convolution Layer and Pooling Layer
    classifier.add(Conv2D(32,(3,3), input_shape = (50,50,3), activation = 'relu'))
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

    train_data_scale = ImageDataGenerator(rescale = 1./255, shear_range = 0.2, zoom_range = 0.2, horizontal_flip = True)
    test_data_scale = ImageDataGenerator(rescale = 1./255)

    train_set = train_data_scale.flow_from_directory('/Users/sanaydevi/PycharmProjects/CNN/training-images',target_size = (50,50), batch_size = 32, class_mode = 'categorical')
    test_set = test_data_scale.flow_from_directory('/Users/sanaydevi/PycharmProjects/CNN/test-images',target_size = (50,50), batch_size = 32, class_mode = 'categorical')

    classifier.fit_generator(train_set, steps_per_epoch = 300, epochs = 10, validation_data = test_set, validation_steps = 200)
    classifier.save("/Users/sanaydevi/PycharmProjects/CNN/model/weights-Test-CNN.h5")


    (classifier.summary())

    classifier.test_function()



if __name__ == '__main__':
    NN()



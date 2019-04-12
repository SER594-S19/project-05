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

classifier = Sequential()

classifier.add(Conv2D(32, (3, 3), input_shape=(50, 50, 3), activation='relu'))
classifier.add(MaxPooling2D(pool_size=(2, 2)))
classifier.add(BatchNormalization())


classifier.add(Conv2D(64, (3, 3), activation='relu'))
classifier.add(MaxPooling2D(pool_size=(2, 2)))
classifier.add(BatchNormalization())

classifier.add(Conv2D(64, (3, 3), activation='relu'))
classifier.add(MaxPooling2D(pool_size=(2, 2)))
classifier.add(BatchNormalization())

classifier.add(Conv2D(96, (3, 3), activation='relu'))
classifier.add(MaxPooling2D(pool_size=(2, 2)))
classifier.add(BatchNormalization())

# Flattening Layer
classifier.add(Flatten())

classifier.add(Dense(units=128, activation='relu'))
classifier.add(Dense(units=3, activation='softmax'))


classifier.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

classifier.load_weights("/Users/sanaydevi/Desktop/final2/FinalAIHCI/project-05/faceEmotions/src/main/resources/weights-Test-CNN.h5")

test_data_scale = ImageDataGenerator(rescale=1. / 255)

test_generator = test_data_scale.flow_from_directory(
    directory="/Users/sanaydevi/Desktop/final2/FinalAIHCI/project-05/faceEmotions/res",
    target_size=(50, 50),
    color_mode="rgb",
    batch_size=32,
    class_mode=None,
    shuffle=False
)


pred = classifier.predict_generator(test_generator, verbose=1, steps=1)
# print(pred)

predicted_class_indices = np.argmax(pred, axis=1)
print(predicted_class_indices)


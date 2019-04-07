#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 15:40:31 2019

@author: adityavikram
"""
from tkinter import *

window = Tk()
window.geometry('350x200')
 
window.title("Welcome to Prediction app")
 
predictions=['something','one thing','another']
for i in range(len(predictions)):
    exec('Label%d=Label(window,text="%s")\nLabel%d.pack()' % (i,predictions[i],i))
window.mainloop()
#!/bin/bash

files=`ls -1 *.png`
i=1200
pleasure='PAD-'
png='.png'

for x in $files
do
    ((i++))
    filename=$pleasure$i$png
    echo $filename
    echo $x
    mv $x $filename
done


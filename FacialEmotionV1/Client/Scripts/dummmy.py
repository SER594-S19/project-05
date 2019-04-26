import sys
print(sys.path)

attribute=''
columnName="frustration_y"
if('_x' in 'frustration_y'):
	attribute = columnName.replace('_x','')
else:
	attribute = columnName.replace('_y','')
    	
attribute = attribute.strip()
attribute = attribute.replace(' ','_')
print(attribute)	

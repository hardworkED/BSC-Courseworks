import cv2
from matplotlib import pyplot as pt
import numpy as np

def detect_text_and_table(img):
    doc = cv2.imread(img,1)
    gray = cv2.cvtColor(doc, cv2.COLOR_BGR2GRAY)
    thresh = cv2.adaptiveThreshold(gray,255,cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY,3,-1)
 
    horizontal = np.copy(thresh)
    
    cols = horizontal.shape[1]
    horizontal_size = cols // 3
    
    horizontalStructure = cv2.getStructuringElement(cv2.MORPH_RECT, (horizontal_size, 1))
    horizontal = cv2.morphologyEx(horizontal,cv2.MORPH_OPEN,horizontalStructure)
    
    contoursTable, hierarchy = cv2.findContours(horizontal, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    if len(contoursTable) >= 4:
        idx = 0
        yaxis = []
        for c in contoursTable:
            x, y, w, h = cv2.boundingRect(c)
            yaxis.append(y)
            idx += 1
        
        idx = 0
        for c in contoursTable:
            x, y, w, h = cv2.boundingRect(c)
            if (idx == 0):
                cv2.rectangle(doc, (x, y), (x + w, y - (y - yaxis[idx+4]) ), (255,0,0), 3)
                cv2.rectangle(thresh, (x - 20, y + 10), (x + 20 + w , y - (y - yaxis[idx+4])), (0,0,0), -1)
                
            if (idx % 7 == 0):
                cv2.rectangle(doc, (x, y), (x + w, y - (y - yaxis[idx+4]) ), (255,0,0), 3)
                cv2.rectangle(thresh, (x - 20, y + 10), (x + 20 + w, y - (y - yaxis[idx+4])  ), (0,0,0), -1)
            idx += 1 
            
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5,5))
    text = cv2.dilate(thresh, kernel, iterations = 3)
    
    contoursText, hierarchy= cv2.findContours(text, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    for c in contoursText:
        x,y,w,h = cv2.boundingRect(c)
        if (h > 800):
            continue
        cv2.rectangle(doc, (x, y), (x + w, y + h), (36,255,12), 2)

    return doc
   
testImages = ['allText1.png','allText2.png','withTable1.png','withTable2.png','withTwoTables1.png','withTwoTables2.png']

for img in range (len(testImages)):
    pt.figure()
    pt.title("Image" + str(img+1))
    pt.imshow(cv2.cvtColor(detect_text_and_table(testImages[img]),cv2.COLOR_BGR2RGB))





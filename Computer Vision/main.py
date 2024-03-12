import numpy as np
import cv2 as cv
from matplotlib import pyplot as pt
import os

#to ensure that the image has odd number of size
def oddSize(img):
    [row,col] = img.shape
    if row % 2 == 0:
        img = cv.copyMakeBorder(img, 0, 1, 0, 0, cv.BORDER_REPLICATE)
    if col % 2 == 0:
        img = cv.copyMakeBorder(img, 0, 0, 0, 1, cv.BORDER_REPLICATE)
    return img 

#to change binary image 0/1 to 0/255
def changeBin(img):
    img[img > 0] = 255
    return img



#prompt user input for location of the image
print("\nPlease enter full path of the image that needed to be processed: \nExample: C:\\folder\\nestedFolder\\image.jpg")
img = input("Full path (or enter '-1' to exit): ")


if os.path.isfile(img.replace('\\', '/')):
    print("\nImage processing... This can take awhile...")
    img = cv.imread(img, 1)
    imgGray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    
    #resize image for shorter processing time
    [row,col] = imgGray.shape
    resizedVal = max(row // 1000, col // 1000, 1)
    imgResized = cv.resize(imgGray, (int(col / resizedVal), int(row / resizedVal)))
    imgResized = oddSize(imgResized)
    
    #remove noise on image
    [row,col] = imgResized.shape
    imgGrayBlur = cv.GaussianBlur(imgResized, (row,col), 1, borderType=cv.BORDER_DEFAULT)
    
    #detect edges
    imgGrayMedian = np.median(imgGrayBlur)
    minVal = 0.5 * imgGrayMedian
    maxVal = 1.5 * imgGrayMedian
    edges = cv.Canny(imgGrayBlur, minVal, maxVal)
    
    #find contours
    contours,_ = cv.findContours(edges, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_SIMPLE)
        
    #combine all coordinates of contours to find corners of the document
    contoursCctn = contours[0].copy()
    for i in range(1,len(contours)):
        contoursCctn = np.concatenate((contoursCctn,contours[i]))
    
    SUM = np.sum(contoursCctn, axis=2)
    topLeft = contoursCctn[np.argmin(SUM)][0] * resizedVal
    bottomRight = contoursCctn[np.argmax(SUM)][0] * resizedVal
    DIFF = np.diff(contoursCctn, axis=2)
    topRight = contoursCctn[np.argmin(DIFF)][0] * resizedVal
    bottomLeft = contoursCctn[np.argmax(DIFF)][0] * resizedVal
    
    #calculate width and height of the document
    width = int(max(np.sqrt((topLeft[0] - topRight[0]) ** 2 + (topLeft[1] - topRight[1]) ** 2),
                np.sqrt((bottomLeft[0] - bottomRight[0]) ** 2 + (bottomLeft[1] - bottomRight[1]) ** 2))) - 1
    height = int(max(np.sqrt((topLeft[0] - bottomLeft[0]) ** 2 + (topLeft[1] - bottomLeft[1]) ** 2),
                np.sqrt((topRight[0] - bottomRight[0]) ** 2 + (topRight[1] - bottomRight[1]) ** 2))) - 1
    
    #perform perspective transform
    imgCorners = np.float32([topLeft, bottomLeft, topRight, bottomRight])
    imgCornersTrans = np.float32([[0, 0], [0, height], [width, 0], [width, height]])
    transformationMatrix = cv.getPerspectiveTransform(imgCorners, imgCornersTrans)
    transformedImg = cv.warpPerspective(img, transformationMatrix, (width + 1, height + 1))
    
    transformedImgGray = cv.cvtColor(transformedImg, cv.COLOR_BGR2GRAY)    
    transformedImgGray = oddSize(transformedImgGray)
    [nrow,ncol] = transformedImgGray.shape
    
    #perform histogram equalization to increase contrast of document
    transformedImgEq = cv.equalizeHist(transformedImgGray) 
    
    #remove noise on the document    
    imgGrayBlur = cv.GaussianBlur(transformedImgGray, (row,col), 3, borderType=cv.BORDER_DEFAULT)
    #perform adaptive thresholding and turn image into binary image
    transformedImgBin = cv.adaptiveThreshold(imgGrayBlur, 1, cv.ADAPTIVE_THRESH_MEAN_C, cv.THRESH_BINARY, 9, 2)

    transformedImgRGB = cv.cvtColor(transformedImg, cv.COLOR_BGR2RGB)
    pt.figure()
    pt.title("Coloured Image - 1")
    pt.imshow(transformedImgRGB)
    pt.figure()
    pt.title("Binary Image - 2")
    pt.imshow(transformedImgBin, cmap='gray')
    pt.figure()
    pt.title("Grayscale Image - 3")
    pt.imshow(transformedImgEq, cmap='gray')        
    pt.show()
    
    pt.pause(5)
    imgChosen = {'1': transformedImg, '2': changeBin(transformedImgBin), '3': transformedImgEq}
    userImg = input("\nPlease enter number for desired image to save (1, 2, 3): ")
    while userImg not in imgChosen:
        print("\nInvalid option")
        userImg = input("Please enter number for desired image to save (1, 2, 3): ")
    pt.close('all')
    
    print("\nPlease enter full path of the folder to save the image: \nExample: C:\\folder\\nestedFolder")
    userLoc = input("Full path: ")
    while not os.path.isdir(userLoc):
        print("\nFolder does not exist")
        print("Please enter full path of the folder to save the image: \nExample: C:\\folder\\nestedFolder")
        userLoc = input("Full path: ")
    
    userName = "\\" + input("Please enter name of the image: ") + ".png"
    loc = userLoc + userName
    
    cv.imwrite(loc, imgChosen[userImg])
    
    print("Image chosen is saved!")
else:
    print("\nFile does not exist")


    



# This is a sample Python script.

# Press ⌃R to execute it or replace it with your code.
# Press Double ⇧ to search everywhere for classes, files, tool windows, actions, and settings.

import flask
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

import requests
from flask import request

app = flask.Flask(__name__)

@app.route('/', methods=['GET'])
def handleCall():
    return "Successfully Connected"

@app.route('/getAQI', methods=['GET'])
def getAQI():
    latitude = request.args.get('latitude')
    longitude = request.args.get('longitude')
    print("given lat is..." + str(latitude))
    result = (str(findAQIAt(latitude, longitude)))
    return ("AQI is...." + result)

def findLat(myLat):

    tempLat = 0

    # check to see if the previous latitude is greater than the next one. If so, then we have found the desired index
    count = 0
    for index, row in lats.iterrows():
        tempLat = row['Latitude']
        if (myLat < tempLat):
            # print("myLat is smaller than..." + str(tempLat))
            count += 1
        else:
            # print("We've passed over the threshold at" + str(tempLat) + " at index..." + str(count))
            return count
        # print(row['Latitude'])

def findLon(myLon):
    #[0] = -112
    #[1250] = -111
    tempLon = -113
    count = 0
    for index, row in lons.iterrows():
        tempLon = row['Longitude']
        if (tempLon < myLon):
            count += 1
            # print("myLon is greater than" + str(tempLon))

        else:
            # print("We've passed over the threshold at" + str(tempLon) + " at index..." + str(count))
            return count

def findAQIAt(lat, lon):

    ### o3 is shaped as [lat][lon]
    #########[2020][1150]#########
    latIndex = findLat(float(lat))
    print("latIndex is..." + str(latIndex))
    lonIndex = findLon(float(lon))
    print("lonIndex is..." + str(lonIndex))

    print(o3.iloc[latIndex][lonIndex])
    return (o3.iloc[latIndex][lonIndex])

# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    #reading in latitude csv
    lats = pd.read_csv("lats.csv")
    lats.columns.values[0] = "Latitude"
    # print(lats.shape)

    #reading in longitude csv
    lons = pd.read_csv("lons.csv")
    lons.columns.values[0] = "Longitude"
    # print(lons)

    #reshaping missing data for lons
    lons.loc[len(lons.index)] = -1.116999913043478320e+02
    print(lons.shape)

    #reading in atmosphere data
    o3 = pd.read_csv("o3.csv")

    findAQIAt("40.95000000", "-112.15000000000")

    #displaying data to see what it looks like at all
    # x_1, y_1 = np.meshgrid(lons, lats)
    # plt.contourf(y_1, x_1, o3, cmap='jet')





    app.run(host="0.0.0.0", port=8080, debug=True)
# See PyCharm help at https://www.jetbrains.com/help/pycharm/

#!/usr/bin/env python
# coding: utf-8

# In[28]:


import pandas as pd
import pandas_datareader.data as web
from datetime import datetime, timedelta
def getPrice(tag):
    ticker = str(tag)
    endDate = datetime.date(datetime.now())
    weekday = endDate.weekday()
    startDate = dayOWeek(weekday)
    frame = web.DataReader(ticker, 'yahoo', startDate, endDate)
    dayClose = round(frame["Close"][1], 2)
    return dayClose

def getChange(tag):
    ticker = str(tag)
    endDate = datetime.date(datetime.now())
    weekday = endDate.weekday()
    startDate = dayOWeek(weekday)
    frame = web.DataReader(ticker, 'yahoo', startDate, endDate)
    dayClose = round(frame["Close"][1], 2)
    ydayClose = round(frame["Close"][0], 2)
    change = round(((dayClose - ydayClose)* 100 / ydayClose), 2)
    if change > 0:
        returnVal = "+" + str(change)
    else:
        returnVal = str(change)
    return returnVal

def dayOWeek(weekday):
    if (weekday == 5):
        startDate = datetime.date(datetime.now() - timedelta(2))
    elif (weekday == 6):
        startDate = datetime.date(datetime.now() - timedelta(3))
    else:
        startDate = datetime.date(datetime.now() - timedelta(1))
    return startDate


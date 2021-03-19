#!/usr/bin/env python
# coding: utf-8

# In[21]:


import pandas as pd
import pandas_datareader.data as web
from datetime import datetime, timedelta
def getPrice(tag):
    ticker = str(tag)
    endDate = datetime.date(datetime.now())
    startDate = datetime.date(datetime.now() - timedelta(1))
    frame = web.DataReader(ticker, 'yahoo', startDate, endDate)
    dayClose = round(frame["Close"][1], 2)
    return dayClose

def getChange(tag):
    ticker = str(tag)
    endDate = datetime.date(datetime.now())
    startDate = datetime.date(datetime.now() - timedelta(1))
    frame = web.DataReader(ticker, 'yahoo', startDate, endDate)
    dayClose = round(frame["Close"][1], 2)
    ydayClose = round(frame["Close"][0], 2)
    change = round(((dayClose - ydayClose)* 100 / ydayClose), 2)
    if change > 0:
        returnVal = "+" + str(change)
    else:
        returnVal = str(change)
    return returnVal


# In[23]:


getChange("^DJI")


# In[ ]:





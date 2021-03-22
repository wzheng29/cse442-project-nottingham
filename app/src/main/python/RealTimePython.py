import pandas_datareader as pdr
import pandas as pd
import io
import matplotlib.pyplot as plt
import numpy as np
from datetime import date, datetime, timedelta

def getCSV(tag, start):
    startDate = start
    endDate = date.today()
    dateData = pdr.DataReader(tag,'yahoo', startDate, endDate)
    datacsv = dateData.to_csv(tag + '.csv')
    return datacsv

def getPrice(tag):
    ticker = str(tag)
    endDate = datetime.date(datetime.now())
    weekday = endDate.weekday()
    startDate = dayOWeek(weekday)
    frame = pdr.DataReader(ticker, 'yahoo', startDate, endDate)
    dayClose = round(frame["Close"][0], 2)
    return dayClose

def dayOWeek(weekday):
    if (weekday == 5):
        startDate = datetime.date(datetime.now() - timedelta(2))
    elif (weekday == 6):
        startDate = datetime.date(datetime.now() - timedelta(3))
    elif (weekday == 0):
        startDate = datetime.date(datetime.now() - timedelta(3))
    else:
        startDate = datetime.date(datetime.now() - timedelta(1))
    return startDate


def Plotter ( tag, start):

    startDate=start
    endDate = date.today()
    spy = pdr.DataReader(tag,'yahoo', startDate, endDate)
    close = spy['Adj Close']
    msft = close.loc[:]
    plt.rcParams.update({'font.size': 22})
    short_rolling_msft = msft.rolling(window=20).mean()
    long_rolling_msft = msft.rolling(window=100).mean()
    xa = np.linspace(0, 5, 20)

    ya = xa**2
    # Plot everything by leveraging the very powerful matplotlib package
    fig, ax = plt.subplots(figsize=(40, 15))

    ax.plot( msft.index, msft, label='Closing Price',  linewidth=10,color='#4b0082',marker='h', markerfacecolor='lightgreen', markeredgewidth=9,
            markersize=20, markevery=1)

    font = {'family' : 'normal',
        'weight' : 'bold',
        'size'   : 60}
    plt.rc('font', **font)
    ax.set_xlabel('Date')
    ax.set_ylabel('Adjusted closing price ($)')
    ax.spines['bottom'].set_color('white')
    ax.spines['top'].set_color('white')
    ax.spines['left'].set_color('white')
    ax.spines['right'].set_color('white')
    ax.xaxis.label.set_color('white')
    ax.yaxis.label.set_color('white')
    ax.tick_params(axis='x', colors='white')
    ax.tick_params(axis='y', colors='white')
    ax.legend()
    bio = io.BytesIO()
    plt.savefig(bio, format= "png", transparent=True)
    b = bio.getvalue()
    return b
    
  # fig = spy['Close'].plot(figsize=(80, 8), fontsize=36, linewidth=8).get_figure()
 #  bio = io.BytesIO()
    
   # fig.savefig(bio, format= "png", transparent=True)
  #  b = bio.getvalue()
   # return b
    
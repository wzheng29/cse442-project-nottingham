#Importing libraries


import matplotlib.pyplot as plt
import pandas_datareader as pdr
import numpy as np
import pandas as pd
import sklearn
import io
from io import StringIO
from matplotlib import pyplot as plt
from sklearn.metrics import mean_absolute_error
from statsmodels.tsa.stattools import adfuller
from statsmodels.tsa.seasonal import seasonal_decompose
from statsmodels.tsa.arima_model import ARIMA
from pandas.plotting import register_matplotlib_converters
from datetime import date, datetime, timedelta
register_matplotlib_converters()



def stationarityTest(timeseries):
    
    #Determining the rolling statistics, using a window of 20 days
    rolling_mean = timeseries.rolling(window=20).mean()
    rolling_std = timeseries.rolling(window=20).std()
    
    result = adfuller(timeseries)
    

def numberOfStocks(currentMoney, stockPrice):
    return currentMoney//stockPrice


def predict(tag, start, years):
    stream = io.StringIO()
    dateData = pdr.DataReader(tag,'yahoo', start, date.today())
    dateData.to_csv(stream)
    stream.seek(0)
    sp = pd.read_csv(stream)
    
    sp = sp.fillna(method='ffill')
    sp=sp[['Date','Adj Close']]
    sp.Date = pd.to_datetime(sp.Date)
    sp.columns=['ds','y']
    train, dev, test = sp[:1800], sp[1800:2250], sp[2250:]
    type(train.ds[0])

    sp_log = np.log(sp['y'])
    log_df = pd.concat([sp['ds'],sp_log],axis=1)
    log_df = log_df.set_index('ds')
    log_df.head()

    #Computing the differenced log-price process
    log_diff = sp_log - sp_log.shift(1)
    log_diff.dropna(inplace=True)

    #Testing for stationarity
    stationarityTest(log_diff)
    model = ARIMA(np.asarray(log_df), order=(5,1,1))
    results = model.fit(disp=-1) 


    predictions_ARIMA_diff = pd.Series(results.fittedvalues, copy=True)
    predictions_ARIMA_diff_cumsum = np.array(predictions_ARIMA_diff.cumsum())
    #print(predictions_ARIMA_diff_cumsum)
    predictions_ARIMA_diff_cumsum = pd.Series(np.insert(predictions_ARIMA_diff_cumsum,0,0))
    predictions_ARIMA = pd.Series(log_df['y'].iloc[0],index=sp.index)
    predictions_ARIMA = predictions_ARIMA.add(predictions_ARIMA_diff_cumsum)
    predictions_ARIMA = np.exp(predictions_ARIMA)

    initialMoney = sp['y'][len(log_df.index)-1]
    currentMoney = sp['y'][len(log_df.index)-1]
    firstBuy = True 
    shares = 0
    sharesInc = 5
    wantToBuy = True
    dayInc = 7
    daysBuy = 1 #number o f days that 1 buy cover


    for i in range(dayInc,len(predictions_ARIMA)):
        if(i%daysBuy==0):
            wantToBuy = True
        todayPredictedClose = predictions_ARIMA[i-dayInc]
        tomorrowPredictedClose = predictions_ARIMA[i]
        if tomorrowPredictedClose > todayPredictedClose and wantToBuy:
            stocks = numberOfStocks(currentMoney,sp['y'][i])
            shares += stocks
            currentMoney -= stocks*sp['y'][i]
            wantToBuy = False
        else:
            moneyBack = shares * sp['y'][i]
            currentMoney += moneyBack
            shares = 0

    return round(currentMoney + shares*sp['y'][years*251],2)

def futurePlot(tag, start, years):
    stream = io.StringIO()
    dateData = pdr.DataReader(tag,'yahoo', start, date.today())
    dateData.to_csv(stream)
    stream.seek(0)
    sp = pd.read_csv(stream)

    sp = sp.fillna(method='ffill')
    sp=sp[['Date','Adj Close']]
    sp.Date = pd.to_datetime(sp.Date)
    sp.columns=['ds','y']
    train, dev, test = sp[:1800], sp[1800:2250], sp[2250:]
    type(train.ds[0])

    sp_log = np.log(sp['y'])
    log_df = pd.concat([sp['ds'],sp_log],axis=1)
    log_df = log_df.set_index('ds')
    log_df.head()

    #Computing the differenced log-price process
    log_diff = sp_log - sp_log.shift(1)
    log_diff.dropna(inplace=True)

    #Testing for stationarity
    stationarityTest(log_diff)
    model = ARIMA(np.asarray(log_df), order=(5,1,1))
    results = model.fit(disp=-1)


    predictions_ARIMA_diff = pd.Series(results.fittedvalues, copy=True)
    predictions_ARIMA_diff_cumsum = np.array(predictions_ARIMA_diff.cumsum())
    #print(predictions_ARIMA_diff_cumsum)
    predictions_ARIMA_diff_cumsum = pd.Series(np.insert(predictions_ARIMA_diff_cumsum,0,0))
    predictions_ARIMA = pd.Series(log_df['y'].iloc[0],index=sp.index)
    predictions_ARIMA = predictions_ARIMA.add(predictions_ARIMA_diff_cumsum)
    predictions_ARIMA = np.exp(predictions_ARIMA)
    initialMoney = sp['y'][len(log_df.index)-1]

    currentMoney = sp['y'][len(log_df.index)-1]
    firstBuy = True
    shares = 0
    sharesInc = 5
    wantToBuy = True
    dayInc = 7
    daysBuy = 1 #number o f days that 1 buy cover
    a = []
    b= []

    for i in range(dayInc,len(predictions_ARIMA)):
        if(i%daysBuy==0):
            wantToBuy = True
        todayPredictedClose = predictions_ARIMA[i-dayInc]
        tomorrowPredictedClose = predictions_ARIMA[i]
        if tomorrowPredictedClose > todayPredictedClose and wantToBuy:
            stocks = numberOfStocks(currentMoney,sp['y'][i])
            shares += stocks
            currentMoney -= stocks*sp['y'][i]
            wantToBuy = False
        else:
            moneyBack = shares * sp['y'][i]
            currentMoney += moneyBack
            shares = 0
    todays_date = date.today()
    for j in range(0,years+1):
        a.append(currentMoney + shares*sp['y'][j*251])
        b.append(todays_date.year+j)

    plt.close()
    plt.rcParams.update({'font.size': 22})
    xa = np.linspace(0, 5, 20)

    ya = xa**2
    # Plot everything by leveraging the very powerful matplotlib package
    fig, ax = plt.subplots(figsize=(40, 15))
    ax.plot( b, a, label='Closing Price',  linewidth=10,color='#4b0082',marker='h', markerfacecolor='lightgreen', markeredgewidth=9,
                markersize=20, markevery=1)
    font = {'family' : 'normal',
            'weight' : 'bold',
            'size'   : 60}
    plt.rc('font', **font)
    ax.set_xlabel('Year')
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

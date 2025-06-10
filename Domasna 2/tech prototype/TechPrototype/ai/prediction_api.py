from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
import pandas as pd
from statsmodels.tsa.arima.model import ARIMA
from datetime import datetime

app = FastAPI()


# Define a model for historical data items
class HistoricalDataItem(BaseModel):
    date: str  # Expect date as 'YYYY-MM-DD'
    average_price: float


# Define a model for the list of historical data
class HistoricalData(BaseModel):
    data: List[HistoricalDataItem]


# Prediction function using ARIMA
def predict_next_month_price(historical_data: pd.DataFrame) -> float:
    # Set the date as the index
    historical_data['date'] = pd.to_datetime(historical_data['date'])
    historical_data.set_index('date', inplace=True)

    # Apply ARIMA model
    model = ARIMA(historical_data['average_price'], order=(5, 1, 0))  # Adjust order if needed
    model_fit = model.fit()

    # Forecast for 30 days (next month)
    forecast = model_fit.forecast(steps=30)

    # Return the mean forecast price for the next month
    return forecast.mean()


# Define an endpoint for predicting the stock price
@app.post("/predict-next-month-price/")
async def predict_next_month_price_endpoint(historical_data: HistoricalData):
    # Convert the list of data to a DataFrame
    try:
        data = pd.DataFrame([item.dict() for item in historical_data.data])
        predicted_price = predict_next_month_price(data)
        return {"predicted_next_month_price": predicted_price}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Run the app with Uvicorn (Python ASGI server)
# Command to run: uvicorn prediction_api:app --reload

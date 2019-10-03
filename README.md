# Geo-Weather Sample REST API Controller

`http://localhost:8080/api/geolocation`

 - **Sample POST Request with JSON:**
  { 
		"zipCode": "11111"
  }
  
 - **Response:**
  {
    "timeZone": "GMT +02:00"
  }

`http://localhost:8080/api/weather`

 - **Sample POST Request with JSON:**
  { 
		"cityCode": "1"
  }
  
 - **Response:**
  {
  "temperature": 5.5,
  "humidity": 80.5,
  "pressure": 801.3,
  "wind": {
    "direction": "East",
    "strength": "2-3"
  }
}

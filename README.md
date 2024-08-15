# MyntParcelApplication

This application calculates the cost of delivery for a parcel based on its weight and volume, with additional features like applying discounts through an external Voucher API.


- **Example Request Body:**
### POST: http://localhost:8080/api/parcels/calculate-cost
```json
{
    "weight": 5.0,
    "height": 10.0,
    "width": 15.0,
    "length": 20.0,
    "voucherCode": "MYNT"
}
```
- **Example Response:**
```json
{
    "cost": 150.0
}
```
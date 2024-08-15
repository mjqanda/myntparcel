# MyntParcelApplication

This application calculates the cost of delivery for a parcel based on its weight and volume, with additional features like applying discounts through an external Voucher API.


- **Example Request:**
### POST: http://localhost:8080/api/parcels/calculate-cost
{
    "weight": 5.0,
    "height": 10.0,
    "width": 15.0,
    "length": 20.0,
    "voucherCode": "GFI"
}
```

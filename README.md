# MyntParcelApplication

This application calculates the cost of delivery for a parcel based on its weight and volume, with additional features like applying discounts through an external Voucher API.


- **Example Request:**
bash
curl -X POST \
  http://localhost:8080/calculate-cost \
  -H 'Content-Type: application/json' \
  -d '{
    "weight": 5.0,
    "height": 10.0,
    "width": 15.0,
    "length": 20.0,
    "voucherCode": "GFI"
}'
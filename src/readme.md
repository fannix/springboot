###Test Transfer

```bash
curl -X POST -i 'http://localhost:8080/transfer' --data 'from=12345678&to=8888888a&amount=1'
```

### Test Get Balance

```bash
curl -X GET -i http://localhost:8080/balance/88888888
```

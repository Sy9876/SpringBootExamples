# SPEL

```
curl -i "http://localhost:8080/hello?expr=2-1"
expr: 2-1, result: 1

curl -i "http://localhost:8080/hello?expr=T(java.lang.Math).abs(-1)"
expr: T(java.lang.Math).abs(-1), result: 1

```


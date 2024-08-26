# SFTJ
Secure File Transfer in Java

## MySQL setup
```MySQL
CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
CREATE DATABASE sftj;
GRANT ALL PRIVILEGES ON sftj.* TO 'dev'@'localhost' WITH GRANT OPTION;
```

## SSL setup
```console
keytool -genkeypair -alias springboot -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore springboot.p12 -validity 3650 -storepass password
```

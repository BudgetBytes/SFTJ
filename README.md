# SFTJ
Secure File Transfer in Java

## MySQL setup
```MySQL
CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
CREATE DATABASE sftj;
GRANT ALL PRIVILEGES ON sftj.* TO 'dev'@'localhost' WITH GRANT OPTION;
```

## TLS setup - Change passwords
```console
keytool -genkeypair -alias sftj-tls -keyalg RSA -keysize 4096 -validity 3650 -dname "CN=localhost" -keypass sftj-tls -keystore sftj-tls.p12 -storeType PKCS12 -storepass sftj-tls
mkdir sslcerts && mv sftj-tls.p12 sslcerts
```

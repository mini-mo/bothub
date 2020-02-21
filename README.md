# Bothub

# PRE
- maven 3.3+
- jdk 1.8+

# Build
```bash
mvn clean package
```

# DEBUG MESSAGE
```bash
java -jar target
curl -X POST -H "Content-Type: application/json" -d '{"title": "不要意思 打扰几位了?", "lines":[], "handler": "telegram", "chatId": "-377286098"}' http://127.0.0.1:8080/messages/
```


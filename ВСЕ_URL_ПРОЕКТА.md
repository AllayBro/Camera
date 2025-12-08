# Все URL проекта Camera12

**⚠️ ВАЖНО:** Приложение работает на порту **8090** с контекстным путем **/camera2**

**Базовый URL:** `http://localhost:8090/camera2`

**НЕ работает:** `http://localhost:8090` (404 ошибка)  
**Работает:** `http://localhost:8090/camera2/...` (всегда с `/camera2`)

---

## 🟢 SOAP веб-сервис (JAX-WS) - Лабораторная работа №6

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/DroneService` | SOAP endpoint - основной адрес веб-сервиса |
| `http://localhost:8090/camera2/DroneService?wsdl` | WSDL описание - динамический WSDL от сервиса |
| `http://localhost:8090/camera2/ws/droneService.wsdl` | Статический WSDL (конкретная привязка) |
| `http://localhost:8090/camera2/ws/droneServiceAbstract.wsdl` | Статический абстрактный WSDL |

**Операции SOAP сервиса:**
- `GetDroneInfo` - получение информации о дроне
- `RegisterFine` - регистрация штрафа
- `GetFines` - получение списка штрафов

---

## 🔵 REST API (JAX-RS)

| URL | Метод | Описание |
|-----|-------|----------|
| `http://localhost:8090/camera2/webresources/drone` | GET | Каталог дронов (JSON) |
| `http://localhost:8090/camera2/webresources/analytics` | GET | Аналитика по фотографиям (JSON) |
| `http://localhost:8090/camera2/webresources/fines` | GET | Список всех штрафов (JSON) |
| `http://localhost:8090/camera2/webresources/fines` | POST | Добавление штрафа (JSON в теле запроса) |

---

## 📄 Статические WSDL файлы (для BPEL и других компонентов)

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/services/DroneFineProcess?wsdl` | WSDL описание BPEL процесса |
| `http://localhost:8090/camera2/services/DroneFineProcess` | Без параметра ?wsdl тоже работает |

---

## 📚 Swagger UI (Документация REST API)

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/swagger-ui/index.html` | Swagger UI - интерактивная документация |
| `http://localhost:8090/camera2/swagger-ui.html` | Альтернативный URL (редирект) |
| `http://localhost:8090/camera2/v3/api-docs` | OpenAPI JSON спецификация |

---

## 📋 Краткая сводка по портам

- **8090** - Spring Boot приложение (основной порт)
  - REST API
  - SOAP сервис
  - Swagger UI
  - Статические WSDL

- **8080** - (не используется в Spring Boot, только если разворачивать в OpenESB/GlassFish)

---

## 🧪 Тестирование

### SOAP сервис:
1. WSDL: `http://localhost:8090/camera2/DroneService?wsdl`
2. Клиент: Запустить `DroneServiceClient.main()`
3. SoapUI/Postman: Импортировать WSDL по URL выше

### REST API:
1. Swagger: `http://localhost:8090/camera2/swagger-ui/index.html`
2. Bruno/Postman: Использовать URL из таблицы выше
3. curl/браузер: GET запросы можно открыть прямо в браузере

---

## 🔗 Примеры полных URL для копирования

```
# SOAP
http://localhost:8090/camera2/DroneService?wsdl
http://localhost:8090/camera2/ws/droneService.wsdl

# REST
http://localhost:8090/camera2/webresources/drone
http://localhost:8090/camera2/webresources/analytics
http://localhost:8090/camera2/webresources/fines

# Документация
http://localhost:8090/camera2/swagger-ui/index.html

# BPEL WSDL
http://localhost:8090/camera2/services/DroneFineProcess?wsdl
```

---

**Примечание:** Все URL работают только при запущенном сервере (`Application.main()` или `mvn spring-boot:run`)


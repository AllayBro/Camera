# Все URL проекта Camera12

**⚠️ ВАЖНО:** Приложение работает на порту **8090** с контекстным путем **/camera2**

**Базовый URL:** `http://localhost:8090/camera2`

**НЕ работает:** `http://localhost:8090` (404 ошибка)  
**Работает:** `http://localhost:8090/camera2/...` (всегда с `/camera2`)

---

## 🟢 SOAP веб-сервисы (JAX-WS) - Лабораторная работа №6

### DroneService

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/DroneService` | SOAP endpoint - основной адрес веб-сервиса |
| `http://localhost:8090/camera2/DroneService?wsdl` | WSDL описание - динамический WSDL от сервиса |
| `http://localhost:8090/camera2/ws/droneService.wsdl` | Статический WSDL (конкретная привязка) |
| `http://localhost:8090/camera2/ws/droneServiceAbstract.wsdl` | Статический абстрактный WSDL |

**Операции DroneService:**
- `GetDroneInfo` - получение информации о дроне
- `RegisterFine` - регистрация штрафа
- `GetFines` - получение списка штрафов

### FinesService

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/FinesService` | SOAP endpoint - сервис для работы со штрафами |
| `http://localhost:8090/camera2/FinesService?wsdl` | WSDL описание - динамический WSDL от сервиса |

**Операции FinesService:**
- `RegisterFine` - регистрация штрафа
- `GetFines` - получение списка штрафов для дрона

### JournalService

| URL | Описание |
|-----|----------|
| `http://localhost:8090/camera2/JournalService` | SOAP endpoint - сервис журнала дронов |
| `http://localhost:8090/camera2/JournalService?wsdl` | WSDL описание - динамический WSDL от сервиса |

**Операции JournalService:**
- `GetDroneJournal` - получение журнала записей для дрона

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

### SOAP сервисы:
1. **DroneService:**
   - WSDL: `http://localhost:8090/camera2/DroneService?wsdl`
   - Статический WSDL: `http://localhost:8090/camera2/ws/droneService.wsdl`
   - Клиент: Запустить `DroneServiceClient.main()`
2. **FinesService:**
   - WSDL: `http://localhost:8090/camera2/FinesService?wsdl`
3. **JournalService:**
   - WSDL: `http://localhost:8090/camera2/JournalService?wsdl`
4. **Тестирование:**
   - SoapUI/Postman/Bruno: Импортировать WSDL по URL выше
   - Все SOAP endpoints поддерживают `?wsdl` для получения WSDL описания

### REST API:
1. Swagger: `http://localhost:8090/camera2/swagger-ui/index.html`
2. Bruno/Postman: Использовать URL из таблицы выше
3. curl/браузер: GET запросы можно открыть прямо в браузере

---

## 🔗 Примеры полных URL для копирования

``` 
# SOAP - DroneService
http://localhost:8090/camera2/DroneService?wsdl
http://localhost:8090/camera2/ws/droneService.wsdl
http://localhost:8090/camera2/ws/droneServiceAbstract.wsdl

# SOAP - FinesService
http://localhost:8090/camera2/FinesService?wsdl

# SOAP - JournalService
http://localhost:8090/camera2/JournalService?wsdl

# REST
http://localhost:8090/camera2/webresources/drone
http://localhost:8090/camera2/webresources/analytics
http://localhost:8090/camera2/webresources/fines

# Документация
http://localhost:8090/camera2/swagger-ui/index.html
http://localhost:8090/camera2/v3/api-docs

# BPEL WSDL
http://localhost:8090/camera2/services/DroneFineProcess?wsdl
http://localhost:8090/camera2/services/DroneFineProcess
```

---

**Примечание:** Все URL работают только при запущенном сервере (`Application.main()` или `mvn spring-boot:run`)


# Camera12 - Составное приложение OpenESB

## Описание проекта

Составное приложение (Composite Application) для системы управления дронами и обработки штрафов, разработанное для OpenESB. Включает 6 сервисных модулей: Java EE, BPEL, XSLT, SQL, SOAP BC и File BC.

## URL проекта

### REST API Endpoints (JAX-RS)

#### Базовый URL: `http://localhost:8090/camera2`

| URL | Метод | Описание |
|-----|-------|----------|
| `/webresources/drone` | GET | Каталог дронов - возвращает список всех дронов в формате JSON |
| `/webresources/analytics` | GET | Аналитика - возвращает статистику по фотографиям и среднюю высоту в формате JSON |
| `/webresources/fines` | GET | Список штрафов - возвращает все зарегистрированные штрафы в формате JSON |
| `/webresources/fines` | POST | Добавление штрафа - принимает JSON с данными о штрафе и добавляет его в систему |

### SOAP Web Services (JAX-WS)

#### Базовый URL: `http://localhost:8080/camera2`

| URL | Описание |
|-----|----------|
| `/DroneService` | Java EE веб-сервис (Stateless Session Bean) - предоставляет операции GetDroneInfo, RegisterFine, GetFines |
| `/DroneService?wsdl` | WSDL описание для DroneService - используется для генерации SOAP клиентов |

### WSDL Endpoints (Статические файлы)

#### Базовый URL: `http://localhost:8090/camera2`

| URL | Описание |
|-----|----------|
| `/services/DroneFineProcess?wsdl` | WSDL описание BPEL процесса DroneFineProcess |
| `/ws/droneService.wsdl` | Статический WSDL файл для DroneService (конкретная привязка) |
| `/ws/droneServiceAbstract.wsdl` | Абстрактный WSDL файл для DroneService (без привязки) |

### Swagger UI

#### Базовый URL: `http://localhost:8090/camera2`

| URL | Описание |
|-----|----------|
| `/swagger-ui/index.html` | Swagger UI - интерактивная документация REST API (OpenAPI 3.0) |
| `/swagger-ui.html` | Альтернативный URL для Swagger UI (редирект) |

**Примечание:** Swagger отображает только REST API endpoints. SOAP сервисы не отображаются в Swagger, так как используют WSDL для описания.

### OpenESB Composite Application Endpoints

#### После развертывания в OpenESB (порт 8080)

| URL | Описание |
|-----|----------|
| `http://localhost:8080/soap/DroneFineProcess` | BPEL процесс через SOAP Binding Component - основной способ вызова BPEL процесса по SOAP |
| `http://localhost:8080/ode/processes/DroneFineProcess` | BPEL процесс через Apache ODE - альтернативный способ вызова BPEL процесса |
| `http://localhost:8080/xslt/PhotoTransformService` | XSLT Service Engine - сервис трансформации фотографий дронов |
| `http://localhost:8080/file/FileProcessService` | File Binding Component (SOAP порт) - альтернативный SOAP порт для File BC |

### File Binding Component

#### Работа с файлами (File BC)

| Путь/Папка | Описание |
|------------|----------|
| `file_bc_data/input_*.xml` | Входные файлы для File BC - File BC читает XML файлы из этой папки (шаблон имени: `input_0.xml`, `input_1.xml`, ...) |
| `file_bc_data/output_*.xml` | Выходные файлы от File BC - File BC записывает результаты обработки в файлы с временной меткой (шаблон: `output_20240101-12-30-45-123.xml`) |

**Настройки File BC:**
- Директория: `file_bc_data` (относительно текущей рабочей директории)
- Интервал опроса: 1000 мс (1 секунда)
- Входные файлы: `input_%d.xml` (последовательный номер)
- Выходные файлы: `output_%t.xml` (дата и время)

### Apache ODE (BPEL Engine)

| URL | Описание |
|-----|----------|
| `http://localhost:8080/ode/` | Веб-интерфейс Apache ODE для управления BPEL процессами |
| `http://localhost:8080/ode/processes/DroneFineProcess?wsdl` | **НЕ РАБОТАЕТ** - WSDL через ODE доступен только после развертывания BPEL процесса в ODE. Используйте статический WSDL вместо этого |

**Важно:** URL `http://localhost:8080/ode/processes/DroneFineProcess?wsdl` возвращает 404, потому что:
- BPEL процесс должен быть развернут в Apache ODE через OpenESB Composite Application
- Для получения WSDL через ODE процесс должен быть активен и задеплоен
- **Решение:** Используйте статический WSDL: `http://localhost:8090/camera2/services/DroneFineProcess?wsdl` (работает всегда)

## Структура модулей

### 1. Java EE Module (DroneServiceSU)
- **Тип:** Stateless Session Bean с веб-сервисом
- **Компонент:** http-binding
- **Интерфейс:** `http://www.example.com/drone/service#wsdl.interface(DroneServicePortType)`
- **Операции:** GetDroneInfo, RegisterFine, GetFines

### 2. BPEL Module (DroneFineProcessSU)
- **Тип:** BPEL бизнес-процесс
- **Компонент:** bpelserviceengine
- **Интерфейс:** `http://www.example.com/bpel#wsdl.interface(DroneFinePT)`
- **Операция:** processDroneData

### 3. XSLT Service Engine (PhotoTransformSU)
- **Тип:** XSLT трансформации
- **Компонент:** xslt-service-engine
- **Интерфейс:** `http://www.example.com/xslt#wsdl.interface(PhotoTransformPT)`
- **Операция:** transformPhotos

### 4. SQL Service Engine (DroneDataSQLSU)
- **Тип:** SQL запросы к БД
- **Компонент:** sql-service-engine
- **Интерфейс:** `http://www.example.com/sql#wsdl.interface(DroneDataPT)`
- **Операции:** GetAllDrones, GetDroneInfo, InsertFine

### 5. SOAP Binding Component (SOAPBCSU)
- **Тип:** SOAP привязка
- **Компонент:** soap-binding
- **Интерфейс:** `http://www.example.com/bpel#wsdl.interface(DroneFinePT)`
- **Назначение:** Подключение к BPEL процессу по протоколу SOAP

### 6. File Binding Component (FileBCSU)
- **Тип:** Файловая привязка
- **Компонент:** file-binding
- **Интерфейс:** `http://www.example.com/bpel#wsdl.interface(DroneFinePT)`
- **Назначение:** Чтение/запись XML файлов для вызова BPEL процесса

## Связи между модулями

1. **BPEL → Java EE:** BPEL процесс вызывает Java EE сервис DroneService
2. **SOAP BC → BPEL:** SOAP Binding Component вызывает BPEL процесс
3. **File BC → BPEL:** File Binding Component вызывает BPEL процесс
4. **BPEL → XSLT:** BPEL процесс может использовать XSLT для трансформации данных
5. **BPEL → SQL:** BPEL процесс может использовать SQL для работы с базой данных

## Тестирование

### REST API
- Используйте Swagger UI: `http://localhost:8090/camera2/swagger-ui/index.html`
- Или используйте HTTP клиент (Postman, Bruno, curl)

### SOAP Services
- Используйте SOAP клиент (SoapUI, Postman, NetBeans Test Web Service)
- WSDL доступен по адресу: `http://localhost:8080/camera2/DroneService?wsdl`

### File BC
- Поместите XML файл в папку `file_bc_data` с именем `input_0.xml`
- File BC автоматически обработает файл и создаст `output_*.xml` с результатом

## Порты по умолчанию

- **8090** - Spring Boot приложение (REST API, Swagger, статические WSDL)
- **8080** - OpenESB/GlassFish (SOAP сервисы, BPEL, компоненты JBI)

## Технологии

- **Java EE:** Jakarta EE (Stateless Session Bean, JAX-WS, JAX-RS)
- **BPEL:** Apache ODE
- **XSLT:** XSLT Service Engine
- **SQL:** SQL Service Engine (H2 Database)
- **SOAP:** SOAP Binding Component
- **File:** File Binding Component
- **REST:** Spring Boot, Jersey (JAX-RS)
- **Documentation:** Swagger/OpenAPI 3.0


# Mutual Fund API

This project is a Spring Boot application that provides APIs for mutual fund information and future value calculations. It integrates with external APIs to fetch market data and calculate financial metrics.

---

## Features
- Retrieve a list of mutual funds.
- Calculate the future value of an investment based on beta, market returns, and the risk-free rate.
- Fetch beta values for stocks from an external API.
- Compute market return rates using data from an external API.

---

## Endpoints

### 1. Get All Mutual Funds
- **Endpoint:** `GET /api/mutualfunds`
- **Description:** Retrieves a list of available mutual funds.
- **Response:**
  ```json
  [
    {
      "ticker": "DYNF",
      "name": "iShares US Equity Factor Rotation Active ETF"
    },
    {
      "ticker": "SPHB",
      "name": "Invesco S&P 500®"
    }
  ]
  ```

### 2. Calculate Future Value
- **Endpoint:** `POST /api/mutualfunds/future-value`
- **Description:** Calculates the future value of an investment based on input parameters.
- **Request Body:**
  ```json
  {
    "ticker": "SPY",
    "initialInvestment": 10000,
    "timeInYears": 5
  }
  ```
- **Response:**
  ```json
  {
    "initialInvestment": 10000.0,
    "timeHorizon": 5,
    "returnRate": 7.5,
    "riskFreeRate": 4.61,
    "mutualFundBeta": 1.2,
    "earnings": 4385.5,
    "totalBalance": 14385.5
  }
  ```

---

## Technologies Used
- **Java**: Programming language
- **Spring Boot**: Framework for creating the API
- **RestTemplate**: To consume external APIs
- **Jackson ObjectMapper**: For JSON parsing
- **Maven**: Dependency management
- **External APIs**:
  - Beta API: Fetch beta values for stocks.
  - Market Return API: Retrieve historical market return data.

---

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- An IDE like IntelliJ IDEA or Eclipse
- External API keys for:
  - **Beta API**: Ensure you have a valid endpoint for beta data.
  - **Market Return API**: Requires an API key (e.g., from St. Louis Fed's FRED).

### Steps to Run the Application
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/mutual-fund-api.git
   cd mutual-fund-api
   ```

2. Update API configurations:
   - Replace placeholders in the `CalculationService` class with your actual API endpoints and keys.

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the API at `http://localhost:8080/api/mutualfunds`.

---

## Testing the APIs
You can test the APIs using:
- **Postman**
- **cURL**: Example:
  ```bash
  curl -X GET http://localhost:8080/api/mutualfunds
  ```

---

## Folder Structure
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/        # API Controllers
│   │   ├── model/             # Data Models
│   │   ├── service/           # Service Layer
│   ├── resources/
│       ├── application.properties  # Configuration file
```

---

## Future Enhancements
- Implement user authentication for API access.
- Support additional investment metrics (e.g., CAGR, IRR).
- Add a front-end interface for user interaction.
- Integrate with a database to store historical calculations.


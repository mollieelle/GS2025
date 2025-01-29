# Mutual Fund Calculator

The **Mutual Fund Calculator** is a web-based application that helps users estimate the future value of investments in various mutual funds based on initial investment, time horizon, and market dynamics. It includes both a backend and a frontend for seamless interaction.

---

## Features
- Fetch mutual fund data.
- Calculate the future value of investments.
- Display detailed results, including return rates, risk-free rates, and earnings.
- User-friendly UI.

---

## Project Structure
This project has a **frontend** built with Angular and a **backend** developed in Java (Spring Boot).

### Backend
The backend provides APIs to:
- Fetch available mutual fund data.
- Perform future value calculations.

### Frontend
The frontend is an Angular application that:
- Displays a list of mutual funds.
- Accepts user inputs for calculations.
- Shows interactive and detailed results.

---

## Installation and Setup

### Backend

#### Technologies Used
- **Java**: Programming language
- **Spring Boot**: Framework for creating the API
- **RestTemplate**: For consuming external APIs
- **Jackson ObjectMapper**: For JSON parsing
- **Maven**: Dependency management

#### External APIs
- **Beta API**: Fetch beta values for stocks.
- **Market Return API**: Retrieve historical market return data.

#### Prerequisites
- Java 17+
- Maven 3.6+
- An IDE like IntelliJ IDEA or Eclipse
- External API keys for:
  - Beta API: Ensure you have a valid endpoint for beta data.

#### Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/mutual-fund-api.git
   cd mutual-fund-api
   ```

2. **Update API Configurations**
   - Replace placeholders in the `CalculationService` class with your actual API endpoints and keys.

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. Access the API at `http://localhost:8080/api/mutualfunds`.

---

### Frontend

#### Prerequisites
- Node.js 18+
- Angular CLI 15+

#### Setup Instructions
1. **Navigate to the Frontend Directory**
   ```bash
   cd .\public\
   ```

2. **Install Dependencies**
   ```bash
   npm install
   ```

3. **Run the Frontend**
   ```bash
   ng serve
   ```

4. Access the application at `http://localhost:4200`.

---

## Usage
1. Start the backend server and ensure the database is configured and running.
2. Start the frontend server.
3. Open your browser and navigate to `http://localhost:4200`.
4. Select a mutual fund from the dropdown, input the initial investment and time horizon, and click **Calculate**.
5. View the results, including future value, return rates, and total balance.

---

## API Endpoints

### Backend
- **Get Mutual Funds**
  - `GET /api/mutualfunds`
- **Calculate Future Value**
  - `POST /api/mutualfunds/future-value`

### Frontend
The Angular application interacts with the above endpoints to fetch mutual fund data and calculate future values.

---

## Technologies Used

### Backend
- Java
- Spring Boot
- RESTful APIs

### Frontend
- Angular
- TypeScript
- HTML/CSS
- Bootstrap
- RxJS

---

## Contributing
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes.
4. Push to your branch.
5. Open a pull request.


---

## Screenshots

### Mutual Fund Selection and Input Form
![Mutual Fund Selection and Input Form](https://via.placeholder.com/800x400)

### Calculation Results
![Calculation Results](https://via.placeholder.com/800x400)

---


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

### 1. **Get All Mutual Funds**
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

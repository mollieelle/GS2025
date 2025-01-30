import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Chart, LinearScale, BarController, CategoryScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { MutualFundService, MutualFund } from 'src/app/mutual-fund.service';
import { CalculationService, FutureValueRequest, FutureValueResponse } from 'src/app/calculation.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  mutualFunds: MutualFund[] = [];
  etfs: MutualFund[] = [];
  initialInvestment: string = '10000';
  timeHorizon: string = '10';
  riskFreeRate: number = 0;
  selectedFunds: string[] = [];
  result: FutureValueResponse[] = [];
  isLoading: boolean = false;
  public chart: any;

  constructor(
      private mutualFundService: MutualFundService,
      private calculationService: CalculationService
  ) { }


  // Service Calls and Initialization
  ngOnInit(): void {
    Chart.register(LinearScale, BarController, CategoryScale, BarElement, Title, Tooltip, Legend);
    this.mutualFundService.getMutualFunds().subscribe({
      next: (data) => {
        data.forEach(e=>{
          const fund = { ticker: e.ticker, name: e.name, type: e.type, selected: false };
          if (e.type == "Mutual Fund") {
            this.mutualFunds.push(fund);
          } else if (e.type == "ETF") {
            this.etfs.push(fund);
          }
        })
        console.log('Mutual Funds:', this.mutualFunds);
        console.log('ETFs:', this.etfs);
      },
      error: (err) => {
        console.error('Error fetching funds:', err);
      }
    });
  }

  // Change in fund selection status
  onFundSelectionChange(fund: MutualFund) {
    const existingFund = this.result.find(r => r.ticker === fund.ticker);
    if (fund.selected && !existingFund) {
      this.selectedFunds.push(fund.ticker);
      this.calculateResult(fund.ticker, fund.type);
    } else {
      this.removeResult(fund.ticker);
    }
  }

  // Calculate the future value for the fund
  calculateResult(ticker: string, type: string) {
    // Convert text inputs to numbers
    const initialInvestment = parseFloat(this.initialInvestment);
    const timeHorizon = parseFloat(this.timeHorizon);

    if (isNaN(initialInvestment)) {
      alert('Please fill out the initial investment amount with valid numbers.');
      return;
    }

    if (isNaN(timeHorizon)) {
      alert('Please fill out the time horizon with valid numbers.');
      return;
    }

    this.isLoading = true;

    const request: FutureValueRequest = {
      ticker: ticker,
      initialInvestment: initialInvestment,
      timeInYears: timeHorizon
    };

    this.calculationService.calculateFutureValue(request).subscribe({
      next: (response) => {
        response.type = type;
        this.result.push(response);
        this.riskFreeRate = response.riskFreeRate;
      },
      error: (err) => {
        console.error('Error calculating future value for fund:', ticker, err);
      },
      complete: () => {
        this.isLoading = false;
        this.createChart();
      }
    });
  }

  // Recalculate results when initial investment or time horizon changes
  recalculateResults() {
    this.result = [];
    this.mutualFunds.forEach(fund => {
      if (fund.selected) {
        this.calculateResult(fund.ticker, fund.type);
      }
    });
    this.etfs.forEach(fund => {
      if (fund.selected) {
        this.calculateResult(fund.ticker, fund.type);
      }
    });
  }

  // Remove result from list of results if fund is unselected
  removeResult(ticker: string) {
    this.result = this.result.filter(r => r.ticker !== ticker);
    const mutual_fund = this.mutualFunds.find(f => f.ticker === ticker);
    if (mutual_fund) {
      mutual_fund.selected = false;
    }
    const etf_fund = this.etfs.find(f => f.ticker === ticker);
    if (etf_fund) {
      etf_fund.selected = false;
    }

    if (this.result.length <= 0) {
      if (this.chart) {
        this.chart.destroy();
      }
    } else {
      this.createChart();
    }
  }

  // Reset the list of selected funds and results
  resetAll() {
    this.initialInvestment = '0';
    this.timeHorizon = '0';
    this.selectedFunds = [];
    this.result = [];

    this.mutualFunds.forEach(fund => fund.selected = false);
    this.etfs.forEach(fund => fund.selected = false);
    if (this.chart) {
      this.chart.destroy();
    }
  }

  // Download the results as a csv file
  downloadResults() {
    let csvContent = "data:text/csv;charset=utf-8,";

    csvContent += "Fund,Type,Initial Investment (USD),Time Horizon (years),Risk Free Rate(%)Beta,Return Rate (%),Earnings (USD),Total Balance (USD)\n";

    this.result.forEach(r => {
      csvContent += `${r.ticker},${r.type},${this.initialInvestment},${this.timeHorizon},${this.riskFreeRate},${r.mutualFundBeta},${r.returnRate},${r.earnings},${r.totalBalance}\n`;
    });

    const encodedUri = encodeURI(csvContent);
    const link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "investment_calculator_results.csv");
    document.body.appendChild(link);

    link.click();
    document.body.removeChild(link);
  }

  // Create a bar graph showing the result
  createChart() {
    if (this.chart) {
      this.chart.destroy();
    }

    this.chart = new Chart("results", {
      type: 'bar',
      data: {
        labels: this.result.map(fund => fund.ticker),
        datasets: [{
          label: "Total Balance",
          data: this.result.map(fund => fund.totalBalance),
          backgroundColor: '#605DC8',
          hoverBackgroundColor: '#182848',
          barPercentage: 0.5,
          categoryPercentage: 0.8,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            type: 'category',
            title: {
              display: true,
              text: 'Funds',
            }
          },
          y: {
            type: 'linear',
            beginAtZero: true,
            title: {
              display: true,
              text: 'Total Balance (USD)',
            }
          }
        },
        plugins: {
          legend: {
            display: false
          },
        }
      }
    });
  }
}
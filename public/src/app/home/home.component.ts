import {Component, Input, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { MutualFundService, MutualFund } from 'src/app/mutual-fund.service';
import { CalculationService, FutureValueRequest, FutureValueResponse } from 'src/app/calculation.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  mutualFunds: MutualFund[] = [];
  initialInvestment: string = '10000';
  timeHorizon: string = '10';
  riskFreeRate: number = 0;
  selectedFunds: string[] = [];
  result: FutureValueResponse[] = [];
  isLoading: boolean = false;

  constructor(
      private mutualFundService: MutualFundService,
      private calculationService: CalculationService
  ) { }

  ngOnInit(): void {
    this.mutualFundService.getMutualFunds().subscribe({
      next: (data) => {
        data.forEach(e=>{
          this.mutualFunds.push({ticker: e.ticker, name: e.name, selected: false});
        })
        console.log('Mutual Funds:', this.mutualFunds);
      },
      error: (err) => {
        console.error('Error fetching mutual funds:', err);
      }
    });
  }

  onFundSelectionChange(fund: MutualFund) {
    const existingFund = this.result.find(r => r.ticker === fund.ticker);
    if (fund.selected && !existingFund) {
      this.selectedFunds.push(fund.ticker);
      this.calculateResult(fund.ticker);
    } else {
      this.removeResult(fund.ticker);
    }
  }
  
  calculateResult(ticker: string) {
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

    this.isLoading = true; // Show loading indicator

    const request: FutureValueRequest = {
      ticker: ticker,
      initialInvestment: initialInvestment,
      timeInYears: timeHorizon
    };

    this.calculationService.calculateFutureValue(request).subscribe({
      next: (response) => {
        this.result.push(response);
        this.riskFreeRate = response.riskFreeRate;
      },
      error: (err) => {
        console.error('Error calculating future value for fund:', ticker, err);
      },
      complete: () => {
        this.isLoading = false; // Hide loading indicator
      }
    });
  }

  recalculateResults() {
    this.result = []; // Clear previous results
    this.mutualFunds.forEach(fund => {
      if (fund.selected) {
        this.calculateResult(fund.ticker);
      }
    });
  }

  removeResult(ticker: string) {
    this.result = this.result.filter(r => r.ticker !== ticker);
    const fund = this.mutualFunds.find(f => f.ticker === ticker);
    if (fund) {
      fund.selected = false;
    }
  }

  resetAll() {
    this.initialInvestment = '0';
    this.timeHorizon = '0';
    this.selectedFunds = [];
    this.result = [];

    this.mutualFunds.forEach(fund => fund.selected = false);
  }

  downloadResults() {
    let content = 'Results\n\n';

    this.result.forEach((r, index) => {
      content += `Fund ${index + 1}:\n`;
      content += `  Fund: ${r.ticker}\n`;
      content += `  Return Rate: ${r.returnRate.toFixed(2)}%\n`;
      content += `  Risk Free Rate: ${r.riskFreeRate.toFixed(2)}%\n`;
      content += `  Beta: ${r.mutualFundBeta.toFixed(2)}\n`;
      content += `  Earnings (USD): $${r.earnings}\n`;
      content += `  Total Balance (USD): $${r.totalBalance}\n\n`;
    });

    const blob = new Blob([content], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'mutual_fund_results.txt';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
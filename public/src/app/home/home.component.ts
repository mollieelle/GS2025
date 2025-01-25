import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { MutualFundService, MutualFund } from 'src/app/mutual-fund.service';
import { CalculationService, FutureValueRequest, FutureValueResponse } from 'src/app/calculation.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, HttpClientModule, FormsModule],
  template: `
    <section>
      <!-- Form Section -->
      <form (ngSubmit)="onSubmit()">
        <!-- Mutual Fund Dropdown -->
        <select [(ngModel)]="selectedFund" name="fund" required>
          <option value="" disabled selected>Select Fund</option>
          <option *ngFor="let fund of mutualFunds" [value]="fund.ticker">
            {{ fund.name }} ({{ fund.ticker }})
          </option>
          required
        </select>

        <!-- Initial Investment Input -->
        <input
            type="text"
            [(ngModel)]="initialInvestment"
            name="investment"
            placeholder="Initial Investment Amount"
            required
        />

        <!-- Time Horizon Input -->
        <input
            type="text"
            [(ngModel)]="timeHorizon"
            name="time"
            placeholder="Time Horizon (in years)"
            required
        />

        <!-- Calculate Button -->
        <button class="primary" type="submit">Calculate</button>
      </form>

      <!-- Result Section -->
      <div *ngIf="result" class="results">
        <h2>Results Summary</h2>
        <p><strong>Initial Amount (USD):</strong> {{ result.initialInvestment | currency }}</p>
        <p><strong>Time Horizon (years):</strong> {{ result.timeHorizon }}</p>
        <p><strong>Return Rate:</strong> {{ result.returnRate | number:'1.2-2' }}%</p>
        <p><strong>Risk Free Rate:</strong> {{ result.riskFreeRate | number:'1.2-2' }}%</p>
        <p><strong>Mutual Fund Beta:</strong> {{ result.mutualFundBeta | number:'1.2-2' }}</p>
        <p><strong>Earnings (USD):</strong> {{ result.earnings | currency }}</p>
        <p><strong>Total Balance (USD):</strong> {{ result.totalBalance | currency }}</p>
      </div>
    </section>
  `,
  styles: `
    .results {
      display: grid;
      column-gap: 14px;
      row-gap: 14px;
      grid-template-columns: repeat(auto-fill, minmax(400px, 400px));
      margin-top: 50px;
      justify-content: space-around;
    }
    input[type="text"] {
      border: solid 1px var(--primary-color);
      padding: 10px;
      border-radius: 8px;
      margin-right: 10px;
      display: inline-block;
      width: 20%;
    }
    input[type="number"] {
      border: solid 1px var(--primary-color);
      padding: 10px;
      border-radius: 8px;
      margin-right: 10px;
      display: inline-block;
      width: 20%;
    }
    select {
      border: solid 1px var(--primary-color);
      padding: 10px;
      border-radius: 8px;
      margin-right: 10px;
      display: inline-block;
      width: 20%;
    }
    select option[disabled] {
      color: grey;
    }
    select option {
      color: black;
    }
    button {
      padding: 10px;
      border: solid 1px var(--primary-color);
      background: var(--primary-color);
      color: white;
      border-radius: 8px;
    }
    form button:hover {
      background-color: #182848;
    }
    .results {
      display: block; /* Ensure the result section is a block element */
      margin-top: 20px;
      padding: 10px;
      background: #f9f9f9;
      border-radius: 5px;
      color: #333;
    }

    .results p {
      display: block; /* Ensure each <p> is a block element */
      margin: 10px 0; /* Add spacing between paragraphs */
    }
  `
})
export class HomeComponent implements OnInit {
  mutualFunds: MutualFund[] = [];
  initialInvestment: string = '';
  timeHorizon: string = '';
  selectedFund: string = '';
  result: FutureValueResponse | null = null;
  showForm: boolean = true; // Control form visibility

  constructor(
      private mutualFundService: MutualFundService,
      private calculationService: CalculationService
  ) { }

  ngOnInit(): void {
    this.mutualFundService.getMutualFunds().subscribe({
      next: (data) => {
        this.mutualFunds = data;
        console.log('Mutual Funds:', this.mutualFunds);
      },
      error: (err) => {
        console.error('Error fetching mutual funds:', err);
      }
    });
  }

  onSubmit() {
    // Convert text inputs to numbers
    const initialInvestment = parseFloat(this.initialInvestment);
    const timeHorizon = parseFloat(this.timeHorizon);

    // Validate inputs
    if (!this.selectedFund) {
      alert('Please select a fund.');
      return;
    }

    if (isNaN(initialInvestment)) {
      alert('Please fill out the initial investment amount with valid numbers.');
      return;
    }

    if (isNaN(timeHorizon)) {
      alert('Please fill out the time horizon with valid numbers.');
      return;
    }

    const request: FutureValueRequest = {
      ticker: this.selectedFund,
      initialInvestment: initialInvestment,
      timeInYears: timeHorizon
    };

    this.calculationService.calculateFutureValue(request).subscribe({
      next: (response) => {
        this.result = response;
      },
      error: (err) => {
        console.error('Error calculating future value:', err);
      }
    });
  }

  resetForm() {
    this.result = null;
  }
}
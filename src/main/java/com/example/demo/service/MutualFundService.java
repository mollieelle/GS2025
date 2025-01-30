package com.example.demo.service;

import com.example.demo.model.MutualFund;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MutualFundService {
    public List<MutualFund> getMutualFunds() {
        return Arrays.asList(
                new MutualFund("VSMPX", "Vanguard Total Stock Market Index Fund;Institutional Plus", "Mutual Fund"),
                new MutualFund("FXAIX", "Fidelity 500 Index Fund", "Mutual Fund"),
                new MutualFund("VFIAX", "Vanguard 500 Index Fund;Admiral", "Mutual Fund"),
                new MutualFund("VTSAX", "Vanguard Total Stock Market Index Fund;Admiral", "Mutual Fund"),
                new MutualFund("SPAXX", "Fidelity Government Money Market Fund", "Mutual Fund"),
                new MutualFund("VMFXX", "Vanguard Federal Money Market Fund;Investor", "Mutual Fund"),
                new MutualFund("FDRXX", "Fidelity Government Cash Reserves", "Mutual Fund"),
                new MutualFund("FGTXX", "Goldman Sachs FS Government Fund;Institutional", "Mutual Fund"),
                new MutualFund("SWVXX", "Schwab Value Advantage Money Fund;Investor", "Mutual Fund"),
                new MutualFund("VGTSX", "Vanguard Total International Stock Index Fund;Investor", "Mutual Fund"),
                new MutualFund("VFFSX", "Vanguard 500 Index Fund;Institutional Select", "Mutual Fund"),
                new MutualFund("VIIIX", "Vanguard Institutional Index Fund;Inst Plus", "Mutual Fund"),
                new MutualFund("OGVXX", "JPMorgan US Government Money Market Fund;Capital", "Mutual Fund"),
                new MutualFund("MVRXX", "Morgan Stanley Inst Liq Government Port;Institutional", "Mutual Fund"),
                new MutualFund("VTBNX", "Vanguard Total Bond Market II Index Fund;Institutional", "Mutual Fund"),
                new MutualFund("TFDXX", "BlackRock Liquidity FedFund;Institutional", "Mutual Fund"),
                new MutualFund("FRGXX", "Fidelity Instl Government Portfolio;Institutional", "Mutual Fund"),
                new MutualFund("TTTXX", "BlackRock Liquidity Treasury Trust Fund;Institutional", "Mutual Fund"),
                new MutualFund("AGTHX", "American Funds Growth Fund of America;A", "Mutual Fund"),
                new MutualFund("VTBIX", "Vanguard Total Bond Market II Index Fund;Investor", "Mutual Fund"),
                new MutualFund("GVMXX", "State Street US Government Money Market Fund;Prem", "Mutual Fund"),
                new MutualFund("FCTDX", "Fidelity Strategic Advisers Fidelity US Total Stk", "Mutual Fund"),
                new MutualFund("FCNTX", "Fidelity Contrafund", "Mutual Fund"),
                new MutualFund("VINIX", "Vanguard Institutional Index Fund;Institutional", "Mutual Fund"),
                new MutualFund("VMRXX", "Vanguard Cash Reserves Federal Money Market Fd;Adm", "Mutual Fund"),
                new MutualFund("DYNF", "iShares US Equity Factor Rotation Active ETF", "ETF"),
                new MutualFund("SPHB", "Invesco S&P 500®", "ETF"),
                new MutualFund("SPY", "SPDR S&P 500 ETF Trust", "ETF"),
                new MutualFund("IWM", "iShares Russell 2000 ETF", "ETF"),
                new MutualFund("IVV", "iShares Core S&P 500 ETF", "ETF"),
                new MutualFund("VOO", "Vanguard S&P 500 ETF", "ETF"),
                new MutualFund("VTI", "Vanguard Total Stock Market ETF", "ETF"),
                new MutualFund("QQQ", "Invesco QQQ Trust Series I", "ETF"),
                new MutualFund("VUG", "Vanguard Growth ETF", "ETF"),
                new MutualFund("VEA", "Vanguard FTSE Developed Markets ETF", "ETF"),
                new MutualFund("VTV", "Vanguard Value ETF", "ETF"),
                new MutualFund("BND", "Vanguard Total Bond Market ETF", "ETF"),
                new MutualFund("AGG", "iShares Core U.S. Aggregate Bond ETF", "ETF"),
                new MutualFund("IEFA", "iShares Core MSCI EAFE ETF", "ETF"),
                new MutualFund("IWF", "iShares Russell 1000 Growth ETF", "ETF"),
                new MutualFund("IJH", "iShares Core S&P Mid-Cap ETF", "ETF"),
                new MutualFund("IJR", "iShares Core S&P Small-Cap ETF", "ETF"),
                new MutualFund("VIG", "Vanguard Dividend Appreciation ETF", "ETF"),
                new MutualFund("VGT", "Vanguard Information Technology ETF", "ETF"),
                new MutualFund("VWO", "Vanguard FTSE Emerging Markets ETF", "ETF"),
                new MutualFund("IEMG", "iShares Core MSCI Emerging Markets ETF", "ETF"),
                new MutualFund("VXUS", "Vanguard Total International Stock ETF", "ETF"),
                new MutualFund("GLD", "SPDR Gold Shares", "ETF"),
                new MutualFund("XLK", "Technology Select Sector SPDR ETF", "ETF"),
                new MutualFund("VO", "Vanguard Mid-Cap ETF", "ETF"),
                new MutualFund("RSP", "Invesco S&P 500 Equal Weight ETF", "ETF"),
                new MutualFund("SCHD", "Schwab US Dividend Equity ETF", "ETF")
        );
    }
}

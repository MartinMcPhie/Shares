/**
*Shares excercise - where values were not set in spec these have been arbitrarily set
*1. Provide working source code For a given stock,
*i. calculate the dividend yield
*ii. calculate the P/E Ratio
*iii. record a trade, with timestamp, quantity of shares, buy or sell indicator and price
*iv. Calculate Stock Price based on trades recorded in past 15 minutes
*b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
*/
import java.util.*;

class Shares {

	String StockSymbol = "";
	String StockType = "";
	Double LastDividend = 0.0;
	Double FixedDividend = 0.0;
	Double ParValue = 0.0;
	Double PE_Ratio = 0.0;
	Double DividendYeild = 0.0;
	Double TickerPrice = 0.0;

	//Shares constructor
	Shares(String SSym, String SType, Double lastDiv, Double FixD,Double ParVal) {
		StockSymbol = SSym;
		StockType = SType;
		LastDividend = lastDiv;
		FixedDividend = FixD;
		ParValue = (Double) ParVal;
		TickerPrice = 0.0;
		DividendYeild = 0.0;
	}

	static void CalcDividendYeild (Shares Share) {


	//set ticker price
		Share.TickerPrice = Share.ParValue + 10.0;
		if (Share.TickerPrice == 0 | Share.LastDividend == 0){
			System.out.println(Share.StockSymbol + ": can't set Dividend Yeild as ticker price or last dividend not set");
		}
		else {
			if (Share.StockType == "Com"){
				Share.DividendYeild = (Double) Share.LastDividend / Share.TickerPrice;
				System.out.println(Share.StockSymbol + " stock - Dividend Yield Common: " + Share.DividendYeild );
			} else {
				Share.DividendYeild = (Double) (Share.LastDividend * Share.ParValue) / Share.TickerPrice;
				System.out.println(Share.StockSymbol + " stock - Dividend Yield Preferred: " + Share.DividendYeild );
			}
		}
	};

	static void CalcPE_Ratio (Shares Share) {

		if (Share.TickerPrice == 0 | Share.LastDividend == 0){
					System.out.println(Share.StockSymbol + ": can't set PE Ratio as ticker price or last dividend not set");
				}
		else {
			Share.PE_Ratio = Share.LastDividend / Share.TickerPrice;
			System.out.println(Share.StockSymbol + " stock - Price Earnings Ratio: " + Share.PE_Ratio );
		}
	}
	static void CalcRecentStockPrice (Trades TradeArr[]) {
		Double totalQuant = 0.0;
		Double totalPrice = 0.0;
		Double thisprice = 0.0;
		Long q = 0L;
		for(int i=0; i<TradeArr.length; i++){
			if (TradeArr[i].timeStamp + 9000000.0 > System.currentTimeMillis( )) {
				totalQuant = totalQuant + TradeArr[i].quantity;
				totalPrice = totalPrice + (TradeArr[i].price * TradeArr[i].quantity);
			}
        }
        thisprice = totalPrice / totalQuant;
        System.out.println("stock price of shares sold within the last 15 mins: " + thisprice );
	}

	static void CalcCurrentSharesValue (Trades TradeArr[]) {
		Double totalQuant = 0.0;
		Double totalPrice = 0.0;
		Double thisprice = 0.0;
		for(int i=0; i<TradeArr.length; i++){
			if (TradeArr[i].buyOrSell == "Buy") {
				totalQuant = totalQuant + TradeArr[i].quantity;
				totalPrice = totalPrice + (TradeArr[i].price * TradeArr[i].quantity);
			}
        }
        thisprice = totalPrice / totalQuant;
        System.out.println("mean value of stock held: " + thisprice );
	}

	public static void main(String[ ] args) {
		//create shares
		Shares TEA = new Shares("TEA","Com",0.0,0.0,100.0);
		Shares POP = new Shares("POP","Com",8.0,0.0,100.0);
		Shares ALE = new Shares("ALE","Com",23.0,0.0,60.0);
		Shares GIN = new Shares("GIN","Pref",8.0,2.0,100.0);
		Shares JOE = new Shares("JOE","Com",13.0,0.0,250.0);

		//calculate the dividend yield
		CalcDividendYeild(TEA);
		CalcDividendYeild(POP);
		CalcDividendYeild(ALE);
		CalcDividendYeild(GIN);
		CalcDividendYeild(JOE);

		//calculate the P/E Ratio
		CalcPE_Ratio(TEA);
		CalcPE_Ratio(POP);
		CalcPE_Ratio(ALE);
		CalcPE_Ratio(GIN);
		CalcPE_Ratio(JOE);

		//record a trade, with timestamp, quantity of shares, buy or sell indicator and price
		Trades[] traArr = new Trades[] {
			//Trades(String Symbol, Long Timestamp, Double quantity, String Buy or sell, Double Price){
			new Trades("TEA",System.currentTimeMillis( ),300.00,"Buy",105.00),
			new Trades("POP",System.currentTimeMillis( ),1500.00,"Sell",105.00),
			new Trades("GIN",System.currentTimeMillis( ),1000.00,"Buy",125.00),
			new Trades("TEA",System.currentTimeMillis( ),200.00,"Sell",105.00),
			new Trades("JOE",System.currentTimeMillis( ),100.00,"Buy",105.00)
		};

		//Calculate Stock Price based on trades recorded in past 15 minutes
		CalcRecentStockPrice(traArr);

		//calc GBCE all share index
		CalcCurrentSharesValue (traArr);
		}
	}
class Trades  {
			//record a trade  -timestamp, quantity, buy or sell, price
			String symbol;
			Long timeStamp;
			Double quantity;
			String buyOrSell;
			Double price;

			public Trades(String aSym, Long aTime, Double quant, String bOrS, Double aPrice){
				symbol = aSym;
				timeStamp = aTime;
				quantity = quant;
				buyOrSell = bOrS;
				price = aPrice;
		}
	}
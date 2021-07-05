<div class="pt-5" >
    <div class="grid grid-cols-2">
                @if ($companyProfile !== [] || $companyProfile !== null)
                    <div>
                        <div class="flex">
                            <div class="flex-initial"><h1 class=text-3xl>{{$companyProfile['name']}}</h1></div>
                            @if (auth()->user()!==null && auth()->user()->difficulty === "easy")
                                <div class="pl-1" onclick="nameInfo()">
                                    <svg width="24" height="24" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd"><path d="M12 0c6.623 0 12 5.377 12 12s-5.377 12-12 12-12-5.377-12-12 5.377-12 12-12zm0 1c6.071 0 11 4.929 11 11s-4.929 11-11 11-11-4.929-11-11 4.929-11 11-11zm.5 17h-1v-9h1v9zm-.5-12c.466 0 .845.378.845.845 0 .466-.379.844-.845.844-.466 0-.845-.378-.845-.844 0-.467.379-.845.845-.845z"/></svg>
                                </div>
                            @endif
                            
                        </div>
                    </div>
                    @if (auth()->user() !==null)
                       <div >
                            <a class="border-2 border-blue-500 py-2 px-3 rounded text-blue-500 hover:shadow-xl hover:bg-blue-200" href="{{route('trade1', $companySymbol)}}"> buy</a>
                        </div> 
                    @endif
                    
                @else
                    <h1>Company not found in records</h1>
                @endif
    </div>

        <script>
            function nameInfo() {
                var temp =  document.getElementById("nameInfo").style.display;
                if (temp === "" || temp === "none"){
                    document.getElementById("nameInfo").style.display = "block";
                } else if (temp === "block") {
                    document.getElementById("nameInfo").style.display = "none";
                }                        
            }

            function balanceSheetInfo() {
                var temp =  document.getElementById("balanceSheetInfo").style.display;
                if (temp === "" || temp === "none"){
                    document.getElementById("balanceSheetInfo").style.display = "block";
                } else if (temp === "block") {
                    document.getElementById("balanceSheetInfo").style.display = "none";
                }                        
            }
            
            function trendsInfo(){
                var temp =  document.getElementById("trendsInfo").style.display;
                if (temp === "" || temp === "none"){
                    document.getElementById("trendsInfo").style.display = "block";
                } else if (temp === "block") {
                    document.getElementById("trendsInfo").style.display = "none";
                }
            }

            function candleInfo() {
                var temp =  document.getElementById("candleInfo").style.display;
                if (temp === "" || temp === "none"){
                    document.getElementById("candleInfo").style.display = "block";
                } else if (temp === "block") {
                    document.getElementById("candleInfo").style.display = "none";
                }
            }

        </script>
        <div>
            <p class="hidden bg-white p-5 shadow-inner rounded" id="nameInfo">{{$companyProfile['name']}} is the name of the company, the stock symbol fo this company is {{$companySymbol}}</p>
        </div>
        @if ($peers !== [])
            <h3 class="pb-3 text-lg">related companies</h3>
        @endif
        @if ($peers !==[])
            <div class="flex flex-wrap pb-5">
                @foreach ($peers as $peer)
                    @if (strlen($peer) <= 5)
                        <a wire:click="changeSymbol('{{$peer}}')"  class="m-1 py-2 px-3 rounded hover:shadow-lg {{$peer ===$companySymbol?"bg-blue-500 hover:bg-blue-200 focus:bg-blue-300":" bg-blue-300 hover:bg-blue-200 focus:bg-blue-400" }}">{{$peer}}</a>
                    @endif
                @endforeach
            </div>
        @endif
    @if ($companyProfile  !== [] || $companyProfile !== null)
        <div class="flex pb-5" >
            <div class="flex-initial " style="width: 50%">
                <h5><small><b>Industry:</b> {{$companyProfile['finnhubIndustry']}}</small></h5>
                <h5><small><b>Market Capitalization:</b> {{$companyProfile['marketCapitalization']}}</small></h5>
                <h5><small><b>Share Outstanding:</b> {{$companyProfile['shareOutstanding']}}</small></h5>
            </div>
            <div class="flex-initial " style="width: 50%">
                <h5><small><b>IPO:</b> {{$companyProfile['ipo']}}</small></h5>
                <h5><small><b>Website:</b> <a href="{{$companyProfile['weburl']}}">{{$companyProfile['weburl']}}</a></small></h5>
                <h5><small><b>Exchange:</b> {{$companyProfile['exchange']}}</small></h5>
            </div>
        </div>
        <h3 class="text-2xl pb-2">Description</h3>
        <div class="flex-initial pb-5">
            @if ($description !== [])
                <p>{{$description}}</p>
            @endif
            
        </div>
        <div class="flex">
            <h3 class="text-2xl pb-2">Candlestick chart</h3>
            @if (auth()->user()!==null && auth()->user()->difficulty === "easy")
                <div class="pl-1" onclick="candleInfo()">
                    <svg width="24" height="24" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd"><path d="M12 0c6.623 0 12 5.377 12 12s-5.377 12-12 12-12-5.377-12-12 5.377-12 12-12zm0 1c6.071 0 11 4.929 11 11s-4.929 11-11 11-11-4.929-11-11 4.929-11 11-11zm.5 17h-1v-9h1v9zm-.5-12c.466 0 .845.378.845.845 0 .466-.379.844-.845.844-.466 0-.845-.378-.845-.844 0-.467.379-.845.845-.845z"/></svg>
                </div>
            @endif
        </div>
        
        <div class="flex pb-5" >
            <div class="flex-initial" style="width: 75%">
                <iframe src="https://widget.finnhub.io/widgets/stocks/chart?symbol={{$companyProfile['ticker']}}&watermarkColor=blue&backgroundColor=black&textColor=white"
                    frameborder="0"
                    style ="width: 100%; height: 60vh;">
                </iframe>
            </div>
            <div class="flex-initial pl-5" style="width: 25%">
                <h5><small><b>Current price:</b> {{$qoute['c']}}</small></h5>
                <h5><small><b>Open price of day:</b> {{$qoute['o']}}</small></h5>
                <h5><small><b>High price of day:</b> {{$qoute['h']}}</small></h5>
                <h5><small><b>Low price of day:</b> {{$qoute['l']}}</small></h5>
                <h5><small><b>previous close price:</b> {{$qoute['pc']}}</small></h5>
                <div>
                    <p class="hidden bg-white p-5 shadow-inner rounded" id="candleInfo">For more information <a href="https://www.investopedia.com/trading/candlestick-charting-what-is-it/" target="_blank" rel="noopener noreferrer" class="text-blue-300 hover:text-blue-500 focus:text-blue-100">click here</a> </p> 
                </div>
            </div>
        </div>
        
            <div class="flex">
                <h3 class="text-2xl pb-2 pt-3">Recommendation Trends</h3>
                @if (auth()->user()!==null && auth()->user()->difficulty === "easy")
                    <div class="pl-1" onclick="trendsInfo()">
                        <svg width="24" height="24" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd"><path d="M12 0c6.623 0 12 5.377 12 12s-5.377 12-12 12-12-5.377-12-12 5.377-12 12-12zm0 1c6.071 0 11 4.929 11 11s-4.929 11-11 11-11-4.929-11-11 4.929-11 11-11zm.5 17h-1v-9h1v9zm-.5-12c.466 0 .845.378.845.845 0 .466-.379.844-.845.844-.466 0-.845-.378-.845-.844 0-.467.379-.845.845-.845z"/></svg>
                    </div>
                @endif 
            </div>
            <div>
                <div class="grid justify-items-stretch" style="width: 75%">
                    <iframe src="https://widget.finnhub.io/widgets/recommendation?symbol={{$companyProfile['ticker']}}"
                        frameborder="0"
                        class="justify-self-center"
                        style ="width: 100%; height: 60vh;">
                    </iframe>
                </div>
                <div  class="flex-initial pt-5">
                    <div class="hidden pt-5 pl-3 shadow-inner rounded bg-white" id="trendsInfo">
                        <ul class="list-disc p-3" >
                            <li>
                                <p>A strong buy is the strongest recommendation that an analyst can give to purchase a stock.</p> 
                                <br>
                                <p>A 'strong buy' means the analyst believes the stock's underlying company is or will soon be experiencing positive financial performance and/or favorable market conditions.</p>
                            </li>
                            <li>
                                <p>A strong sell is a type of stock trading recommendation given by investment analysts for a stock that is expected to dramatically underperform when compared with the average market return and/or return of comparable stocks in the same sector or industry. It is an emphatic negative comment on a stock's prospects.</p>
                            </li>
                            <li>
                                <p>A buy rating is an investment analyst’s recommendation to buy a security, and implies the security is undervalued. </p>
                            </li>
                            <li>
                                <p>A Sell is a recommendation to sell a security or to liquidate an asset.</p>
                            </li>
                            <li>
                                <p>Hold is an analyst's recommendation to neither buy nor sell a security. A company with a hold recommendation generally is expected to perform with the market or at the same pace as comparable companies. This rating is better than sell but worse than buy, meaning that investors with existing long positions shouldn't sell but investors without a position shouldn't purchase either.</p>
                            </li>
                        </ul>
                    </div>
                    
                  
                </div>
            </div>
    @endif
    @if ($balanceSheet ===[])
        @if ($companyProfile  !== [] || $companyProfile !== null)
            <div class="grid justify-items-stretch pt-5">
                <span class="justify-self-center text-indigo-500 font-bold text-lg" wire:click="getBalanceSheet">More</span>
            </div> 
        @endif
        
    @endif
    

    @if ($balanceSheet !==[])
        <div class="pt-5">
            <div class="flex">
               <h1 class="text-2xl pb-5">Balance Sheet</h1> 
               @if (auth()->user()!==null && auth()->user()->difficulty === "easy")
                    <div class="pl-1" onclick="balanceSheetInfo()">
                        <svg width="24" height="24" xmlns="http://www.w3.org/2000/svg" fill-rule="evenodd" clip-rule="evenodd"><path d="M12 0c6.623 0 12 5.377 12 12s-5.377 12-12 12-12-5.377-12-12 5.377-12 12-12zm0 1c6.071 0 11 4.929 11 11s-4.929 11-11 11-11-4.929-11-11 4.929-11 11-11zm.5 17h-1v-9h1v9zm-.5-12c.466 0 .845.378.845.845 0 .466-.379.844-.845.844-.466 0-.845-.378-.845-.844 0-.467.379-.845.845-.845z"/></svg>
                    </div>
                @endif
            </div>

            <div class="grid grid-cols-2">
                <div>
                    <span>
                        <p><span class="font-medium">fiscal Date Ending: </span>{{$balanceSheet["fiscalDateEnding"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">reported Currency: </span>{{$balanceSheet["reportedCurrency"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Assets: </span>{{$balanceSheet["totalAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Current Assets: </span>{{$balanceSheet["totalCurrentAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">cash And Cash Equivalents At Carrying Value: </span>{{$balanceSheet["cashAndCashEquivalentsAtCarryingValue"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">cash And Short Term Investments: </span>{{$balanceSheet["cashAndShortTermInvestments"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">inventory: </span>{{$balanceSheet["inventory"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">current Net Receivables: </span>{{$balanceSheet["currentNetReceivables"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Non Current Assets: </span>{{$balanceSheet["totalNonCurrentAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">property Plant Equipment: </span>{{$balanceSheet["propertyPlantEquipment"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">accumulated Depreciation Amortization PPE: </span>{{$balanceSheet["accumulatedDepreciationAmortizationPPE"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">intangible Assets: </span>{{$balanceSheet["intangibleAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">intangibleAssetsExcludingGoodwill: </span>{{$balanceSheet["intangibleAssetsExcludingGoodwill"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">goodwill: </span>{{$balanceSheet["goodwill"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">investments: </span>{{$balanceSheet["investments"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">long Term Investments: </span>{{$balanceSheet["longTermInvestments"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">short Term Investments: </span>{{$balanceSheet["shortTermInvestments"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">other Current Assets: </span>{{$balanceSheet["otherCurrentAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">other Non Currrent Assets: </span>{{$balanceSheet["otherNonCurrrentAssets"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Liabilities: </span>{{$balanceSheet["totalLiabilities"]}}</p>
                    </span>
                </div>
                <div>
                    <span>
                        <p><span class="font-medium">total Current Liabilities: </span>{{$balanceSheet["totalCurrentLiabilities"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">currentAccountsPayable: </span>{{$balanceSheet["currentAccountsPayable"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">deferredRevenue: </span>{{$balanceSheet["deferredRevenue"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">current Debt: </span>{{$balanceSheet["currentDebt"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">short Term Debt: </span>{{$balanceSheet["shortTermDebt"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Non Current Liabilities: </span>{{$balanceSheet["totalNonCurrentLiabilities"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">capital Lease Obligations: </span>{{$balanceSheet["capitalLeaseObligations"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">long Term Debt: </span>{{$balanceSheet["longTermDebt"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">current Long Term Debt: </span>{{$balanceSheet["currentLongTermDebt"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">long Term Debt Non current: </span>{{$balanceSheet["longTermDebtNoncurrent"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">short Long Term Debt Total: </span>{{$balanceSheet["shortLongTermDebtTotal"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">other Current Liabilities: </span>{{$balanceSheet["otherCurrentLiabilities"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">other Non Current Liabilities: </span>{{$balanceSheet["otherNonCurrentLiabilities"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">total Shareholder Equity: </span>{{$balanceSheet["totalShareholderEquity"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">treasury Stock: </span>{{$balanceSheet["treasuryStock"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">retained Earnings: </span>{{$balanceSheet["retainedEarnings"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">common Stock: </span>{{$balanceSheet["commonStock"]}}</p>
                    </span>
                    <span>
                        <p><span class="font-medium">common Stock Shares Outstanding: </span>{{$balanceSheet["commonStockSharesOutstanding"]}}</p>
                    </span>
                </div>
            </div>

            <div>
                <p class="hidden bg-white p-5 shadow-inner rounded" id="balanceSheetInfo">A balance sheet is a financial statement that reports a company's assets, liabilities and shareholders' equity at a specific point in time, and provides a basis for computing rates of return and evaluating its capital structure. It is a financial statement that provides a snapshot of what a company owns and owes, as well as the amount invested by shareholders.<br> For more Infomaiton go to : <a href="https://www.investopedia.com/terms/b/balancesheet.asp" target="_blank" rel="noopener noreferrer" class="text-blue-300 hover:text-blue-500 focus:text-blue-100">more on Balance Sheet</a></p>
            </div>
        </div>
    @endif
    @if ($balanceSheet !==[])
    <div class="grid justify-items-stretch pt-5">
        <span class="justify-self-center text-indigo-500 font-bold text-lg" wire:click="removeBalanceSheet">Less</span>
    </div> 
    @endif
    <br/>
    @if (auth()->user()!==null && auth()->user()->difficulty ==="easy")
        <div class="bg-white p-5 shadow-inner rounded">
            <h1  class="text-2xl pb-2">tip</h1>
            <p>{!! nl2br($tip) !!}</p>
        </div>
    @endif
    <br>
    @if ($news === [])
        <h1>No News Today</h1>    
    @else
        <h3 class="text-2xl pb-2">News</h3>
    @endif
    <?php $count = 0; ?>
    <div class="grid lg:grid-cols-3 gap-0 md:grid-cols-2 xl:grid-cols-4">

        @for ($i = 0; $i < count($news); $i++)
            @if ($i < 5)
                <?php $article = $news[$i]; ?>
                @include('news._news_articles')
            @else
                <?php break; ?>
            @endif
            
        @endfor
    </div>
</div> 

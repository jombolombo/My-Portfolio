<div>
    @if ($show ===true && $stockInfo !==[])
        <div class="d-flex" style="background-color: rgba(165, 170, 170, 0.2);">
            <div class="p-2 ">
                <h6>{{$stockName}}</h6>
                <p><small>{{$exchange}}</small></p>
            </div>
            <div class="p-2">
                <h6>current price: ${{array_key_exists("c", $stockInfo)? $stockInfo["c"]: 0}}</h6>
            </div>
        </div>
        <div style="background-color: rgba(165, 170, 170, 0.05);">
            <p>open price of day: ${{array_key_exists("o", $stockInfo)? $stockInfo["o"] : 0}}</p>
            <p>high price of day: ${{array_key_exists("h", $stockInfo)? $stockInfo["h"] : 0}}</p>
            <p>low price of day: ${{array_key_exists("l", $stockInfo)? $stockInfo["l"] : 0}}</p>
            <p>previous close price: ${{array_key_exists("pc", $stockInfo)? $stockInfo["pc"] : 0}}</p>
        </div>
    @else

    @endif 

</div>

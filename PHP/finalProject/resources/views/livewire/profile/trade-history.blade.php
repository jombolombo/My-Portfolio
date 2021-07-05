<div>
    <h1 class="text-3xl pb-5 font-medium tracking-tight" >Trade History</h1>
    <table>
        <thead>
            <tr>
                <th class=" p-2 border-2 font-medium border-blue-300">Symbol</th>
                <th class=" p-2 border-2 font-medium border-blue-300">Quantity</th>
                <th class=" p-2 border-2 font-medium border-blue-300">Purchase Price</th>
                <th class=" p-2 border-2 font-medium border-blue-300">Selling price</th>
                <th class=" p-2 border-2 font-medium border-blue-300">Difficulty</th>
                <th class=" p-2 border-2 font-medium border-blue-300">Transaction date and time</th>
            </tr>
        </thead>
        <tbody>
            <?php $x = 0; ?>
            @foreach (auth()->user()->tradingHistory as $history)
            <tr>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$history["symbol"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$history["quantity"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$history["purchase_price"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$history["selling_price"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$history["difficulty"]}}</td>
                <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{date($history["created_at"])}}</td>
            </tr>
            <?php $x++; ?>
            @endforeach
        </tbody>
    </table>
    @if ( auth()->user()->difficulty ==="easy")
        <div class="mt-5 bg-white p-5 shadow-inner rounded">
            <h1  class="text-2xl pb-2">tip</h1>
            <p>{!! nl2br($tip) !!}</p>
        </div>
    @endif
</div>

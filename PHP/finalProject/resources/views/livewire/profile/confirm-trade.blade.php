<div>
    @if ($request === "buy")
        @if ($noCreditError === [])
            <h3 class="text-3xl pb-5 font-medium tracking-tight">Preview Order</h3>
            <br>
            <table>
                <tr class="p-2">
                    <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Qauntity</th>
                    <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Description</th>
                    <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Unit price</th>
                </tr>
                <tr class="p-1">
                    <td class="py-2 px-10 border-2 border-blue-300">{{$quantity}}</td>
                    <td class="py-2 px-10 border-2 border-blue-300">{{$stockSymbol}} Stock</td>
                    <td class="py-2 px-10 border-2 border-blue-300">{{$unitPrice}}</td>
                </tr>
            </table>
            <div class="flex">
                <div class="p-2 ">
                    @if (auth()->user()->difficulty === "hard")
                        <p class="text-lg">Transaction fee: ${{$quantity * $unitPrice* 0.05}}</p>
                        <p class="text-lg">Total: ${{$quantity * $unitPrice * 1.05}}</p>
                    @else
                        <p class="text-lg">Total: ${{$quantity * $unitPrice}}</p>
                    @endif
                </div>   
            </div>
        @else
            <h3 class="text-3xl pb-5 font-medium tracking-tight">{{$noCreditError}}</h3>
        @endif
        
        <br>
        <p class="text-sm">Cost of transaction may be different, due to the changing price of the stock market</p>
        @if ($noCreditError === [])
            <button   class=" mt-6 shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded" wire:click="purchase">Confirm Order</button>
        @endif
        <button wire:click="back" class=" mt-6 shadow-lg bg-red-500 hover:bg-red-700 text-white  py-2 px-4 rounded">Cancel</button>
        <br>
    @elseif ($request === "sell")
        @if ($NostockError !==[])
            <h3 class="text-3xl pb-5 font-medium tracking-tight">Preview Order</h3>
            @if ($notEnoughStock ===[])
                <br>
                <table>
                    <tr class="p-2">
                        <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Quantity</th>
                        <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Description</th>
                        <th class="py-2 px-10 border-2 font-medium border-blue-300 bg-blue-300">Unit price</th>
                    </tr>
                    <tr class="p-1">
                        <td class="py-2 px-10 border-2 border-blue-300">{{$quantity}}</td>
                        <td class="py-2 px-10 border-2 border-blue-300">{{$stockSymbol}} Stock</td>
                        <td class="py-2 px-10 border-2 border-blue-300">{{$unitPrice}}</td>
                    </tr>
                </table>
                <div >
                    <div class="pt-5" >
                        <p class="text-lg">profit/loss: <span class=" font-medium {{($gains > 0)? "text-green-800":"text-red-800"}}">{{$gains}}%</span></p>
                        <p class="text-lg">Total: ${{$quantity * $unitPrice}}</p>
                    </div>
                </div>
            @endif
            <br>
            <p class="text-sm">Cost of transaction may be different, due to the changing price of the stock market.</p>
            @if (auth()->user()->difficulty ==="hard")
                <p class="text-sm">The cost of the intial transaction to purchase the stock is not included.</p>
            @endif
            <button  class=" mt-2 shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded" wire:click="sell">Confirm Order</button>
            <button wire:click="back" class=" mt-2 shadow-lg bg-red-500 hover:bg-red-700 text-white  py-2 px-4 rounded">Cancel</button>

                @if (auth()->user()->difficulty === "easy")
                    <div class="mt-5 p-3 border border-1 border-black rounded shadow-lg">
                        <h1 class="text-2xl pb-2">Review</h1>
                        <p>{{$review}}</p>
                    </div>   
                @endif
        @else
            <h1>You Do Not Own This Stock</h1>
        @endif
        
    @endif
    <br>
    @if (auth()->user()->difficulty === "easy")
        <div class="bg-white p-5 shadow-inner rounded">
            <h6 class="text-2xl pb-2">tip</h6>
            <p>{!! nl2br($tips) !!}</p>
        </div>
        
    @endif
</div>

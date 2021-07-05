<div>
    @section('title', 'Profile')
    @if (count($myStocks) >0)
        <div>
            <h1 class="text-3xl pb-5 font-medium tracking-tight">My Portfolio</h1>
            <table >
                    <thead>
                    <tr >
                        {{-- if eay output help columm --}}
                        <th class="p-2"></th>
                        <th class=" p-2 border-2 font-medium border-blue-300">Company</th>
                        <th class=" p-2 border-2 font-medium border-blue-300">Quantity</th>
                        <th class="p-2 border-2 font-medium border-blue-300">purchase price</th>
                        <th class="p-2 border-2 font-medium border-blue-300">current price</th>
                        <th class="p-2 border-2 font-medium border-blue-300">Gain/loss</th>
                        <th class="p-2"></th>
                    </tr>
                    </thead>  
                    <tbody>
                        <?php $x = 1; ?>
                    @foreach ($myStocks as $stock)
                        <?php $x++; ?>
                        <tr class = "p-1">
                            <td></td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}"><a class ="hover:text-white" href="{{route("company1", $stock["Symbol"])}}">{{$stock["Symbol"]}}</a></td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">{{$stock["Qty"]}}</td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">${{$stock["purchase_price"]}}</td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}">${{$stock["current price"]}}</td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}} {{($stock["gain-loss"] > 0)? "text-green-800":"text-red-800"}} font-semibold"> {{$stock["gain-loss"]}}%</td>
                            <td class="p-1 {{(($x % 2) === 0)? "bg-blue-300" : "bg-blue-400"}}"><button class=" font-medium trackin-wide shadow-lg bg-blue-500 hover:bg-blue-700 focus:bg-blue-900 focus:border-transparent text-white  py-2 px-4 " wire:click="sell('{{$stock["id"]}}')">Sell</button></td>
                        </tr>
                @endforeach 
                    </tbody>
            </table> 
        </div>
    @endif
  
   <div class="pt-3 pb-5">
        @if ($peers !== [])
            @if (count($myStocks) === 0)
                <h1 class="mb-3 font-light text-xl"> Suggested companies</h1>    
            @else
                <h1 class="mb-3 font-light text-xl">related companies</h1>
            @endif
            
            <?php $x=0; ?>
            @foreach ($peers as $peer)
                <?php $x++; ?>
                <a href="{{route("company1", $peer)}}" class="m-1 py-2 px-3  bg-blue-300 rounded hover:bg-blue-200 hover:shadow-xl focus:bg-blue-400">{{$peer}}</a>
                <?php if($x >= 5 ) {break;}?>
            @endforeach
        @endif
    </div>
    <div>
        <p class="mb-1 font-light text-base">Account Value: <span>${{auth()->user()->accountValue["accountvalue"]}}</span></p>
        <p class="mb-1 font-light text-base">Cash Value: <span>${{auth()->user()->cash_value}}</span></p>
        <p class="mb-1 font-light text-base">Highest Account Value: <span>${{auth()->user()->accountValue["highestValue"]}}</span></p>
        <p class="mb-1 font-light text-base">Lowest Account Value: <span>${{auth()->user()->accountValue["lowestValue"]}}</span></p>
    </div>
    @if ( auth()->user()->difficulty ==="easy")
        <div class="bg-white p-5 shadow-inner rounded">
            <h1  class="text-2xl pb-2">tip</h1>
            <p>{!! nl2br($tip) !!}</p>
        </div>
    @endif


   @if ($sell === true)
       <div class="pt-5">
        <form wire:submit.prevent="submit">
            @csrf
            <table>
                <tr class="p-3">
                   <td><label for="stock"> Stock Symbol:</label></td>
                    <td><input type="text" id="stock" class="border-gray-300" wire:model="sellStock"></td>
                    <td>@error('stock') <span class="text-red-500">{{ $message }}</span> @enderror
                        @if ($customErrorTransaction !==[])
                            <span class="text-red-500">{{$customErrorTransaction}}</span>
                        @endif
                    </td> 
                </tr>
                <tr class="p-3">
                    <td><label for="transaction">Transaction:</label></td>
                    <td><select class="border-gray-300" id="transaction" wire:model="transaction">
                            <option value="0">Sell</option>
                        </select>
                    </td>
                </tr>
                <tr class="p-3">
                    <td><label for="quantity">Quantity:</label></td>
                    <td><input type="text" id="quantity" class="border-gray-300" wire:model="quantity"></td>
                    <td>@error('quantity') <span class="text-red-500">{{ $message }}</span> @enderror
                        @if ($customErrorQuantity !==[])
                            <span class="text-red-500">{{$customErrorQuantity}}</span>
                        @endif
                    </td>
                </tr>
            </table>
            <button type="submit" class=" mt-6 shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded">preview</button>
        </form>
       </div>
   @endif
</div>

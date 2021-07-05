<div>
    <h3 class="text-3xl pb-5 font-medium tracking-tight"> Order Stock</h3>
    <div class="flex">
         <div class="flex-initial" style="width: 50%">
        <form wire:submit.prevent="submit">
            @csrf
            <div class="form-group">
                <label for=""> Stock Symbol</label>
                <input type="text" class="shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" wire:model="stock">
                @error('stock') <span class="error">{{ $message }}</span> @enderror
                @if ($customErrorSymbol !==[])
                    <span>{{$customErrorSymbol}}</span>
                @endif
                <ul>
                    @foreach ($results as $symbol=>$name)
                        <li wire:click="paste('{{ $symbol }}')">{{$symbol}}- {{$name}}</li>
                    @endforeach
                    
                </ul>
                <br>
                <label for="">Transaction</label>
                <select name="" id="" wire:model="transaction" class="mb-5">
                    <option value=""></option>
                    <option  value="1" selected>Buy</option>
                    <option value="0">Sell</option>
                </select>
                @error('transaction') <span class="error">{{ $message }}</span> @enderror
                @if ($customErrorTransaction !==[])
                    <span>{{$customErrorTransaction}}</span>
                @endif
                <br>
                <label for="">Quantity</label>
                <input type="text" class="mb-5 shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" wire:model="quantity">
                @error('quantity') <span class="error">{{ $message }}</span> @enderror
                @if ($customErrorQuantity !==[])
                    <span>{{$customErrorQuantity}}</span>
                @endif
                <br>
                <button type="submit" class=" shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded">preview</button>
            </div>
        </form>
    </div>
    <div class="flex-initial" style="width: 50%">
        <h3 class="text-lg pb-5 font-medium tracking-tight">Account details</h3>
        <hr>
        <p>Value (USD): <small>$ {{$accountValue}}</small></p>
        <p>Cash: <small>${{auth()->user()->cash_value}}</small></p>
        @livewire('profile.company-info')
    </div>
    </div>
    @if ( auth()->user()->difficulty ==="easy")
        <div class="mt-5 bg-white p-5 shadow-inner rounded">
            <h1  class="text-2xl pb-2">tip</h1>
            <p>{!! nl2br($tip) !!}</p>
        </div>
    @endif
   
    
</div>

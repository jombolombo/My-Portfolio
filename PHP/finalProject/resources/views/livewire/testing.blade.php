
<div>
    @section('title', 'Company info')

        <form class="form-inline" wire:submit.prevent="found">
            @csrf
            <label for="cSymbol" class="mb-2 mr-sm-2">Enter company Symbol:  </label>
            <input wire:model="search" type="text"  id="cSymbol" class="form-control mb-2 mr-sm-2" placeholder="e.g MSFT" name="companySymbol">
            <button class="btn btn-dark mb-2" wire:click="found">search</button>
            {{-- <input type="submit" value="search" class="btn btn-dark mb-2"> --}}
        </form>

        <ul>
            @foreach ($results as $name=>$symbol)
                <li wire:click="paste('{{ $name }}', '{{ $symbol }}')">{{$name}}</li>
            @endforeach
            
        </ul>
</div> 
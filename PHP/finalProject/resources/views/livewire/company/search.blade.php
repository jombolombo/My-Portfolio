<div >
    @section('title', 'Company info')

        <form class="form-inline" wire:submit.prevent="found">
            @csrf
            <table class="flex-initial">
                <tr> 
                    <td>
                        <label for="cSymbol" class="mb-2 mr-sm-2">Enter company Name:  </label>
                    </td>
                    <td>
                        <input wire:model="search" type="text"  id="cSymbol" class="shadow-sm px-1 py-2 border-gray-300 border border-1 focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent" placeholder="e.g Microsoft" name="companySymbol">
                    </td> 
                    <td>
                        <button class=" shadow-lg bg-blue-500 hover:bg-blue-700 text-white  py-2 px-4 rounded" wire:click="found">search</button>
                    </td>
                </tr>
                @foreach ($results as $name=>$symbol)
                    <tr>
                        <td></td>
                        <td wire:click="paste('{{ $name }}', '{{ $symbol }}')" class=" px-4 py-2 bg-gray-50 border border-1 hover:bg-gray-200">{{$name}}</td>
                        <td></td>
                    </tr>
                @endforeach

            </table>
           
            @if ($notFound !== [])
                <span class="text-red-500">{{$notFound}}</span>
            @endif
        </form>


        @livewire('company.profile')
</div> 

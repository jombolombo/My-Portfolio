<?php

namespace App\Http\Livewire\Profile;

use Livewire\Component;
use App\Http\functions\MyFunctions;

class TradeHistory extends Component
{
    public $tip = [];
    
    public function mount() {
        MyFunctions::calculateAccountValue();
        $this->tip = MyFunctions::tip();
    }
    public function render()
    {
        return view('livewire.profile.trade-history')->extends("layouts.app");
    }
}

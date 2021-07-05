<?php

namespace App\Http\Livewire\Profile;

use Livewire\Component;
use App\Models\User;
use App\Models\accountvalue;
use App\Http\functions\MyFunctions;

class Leaderboard extends Component
{
    public $leaderboard = [];
    public $usernames =[];
    public $tip = [];

    public function mount(){
        $this->leaderboard =accountvalue::orderBy("accountvalue","asc")->take(10)->get();
        for ($x =0; $x < count($this->leaderboard); $x++) {
            $this->usernames[$x] = User::select("id","username")->where("id", $this->leaderboard[$x]->user_id)->get();
        }
        MyFunctions::calculateAccountValue();
        $this->tip = MyFunctions::tip();
    }

    public function render()
    {
        return view('livewire.profile.leaderboard')->extends("layouts.app");
    }
}

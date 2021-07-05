<?php

namespace App\Http\Livewire\Settings;

use Livewire\Component;
use App\Models\User;
class Settings extends Component
{
    public $difficulty;


    protected $rules = [
        'difficulty' => 'required | boolean'
    ];

    public function submit(){
        $validated =$this->validate();
        if ($validated['difficulty'] === "1") {
            User::where("id",auth()->user()->id)->update(['difficulty' =>"easy"]);
            return redirect("profile");
        }else {
            User::where("id",auth()->user()->id)->update(['difficulty' =>"hard"]);
            return redirect("profile");
        }
    }

    public function deleteAccount() {
        auth()->user()->delete();
        return redirect()->route('register');
    }
    public function render()
    {
        return view('livewire.settings.settings')->extends("layouts.app");
    }
}

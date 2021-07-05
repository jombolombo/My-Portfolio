<?php

namespace App\Http\Livewire;
use App\Http\API\Finnhub;

use Livewire\Component;

class Testing extends Component
{
    public $search = "";
    public $mySymbol = "";
    public function searching(){
        if($this->search !== ''){
            $jsonString = file_get_contents(base_path('resources/json/stock_symbol.json'));
            $data = json_decode($jsonString,true);
            $results = array();
            $count = 0;
            foreach($data as $item) {
                if(str_starts_with(strtolower($item['description']),strtolower($this->search))){
                    if($item["type"]=== "Common Stock"){
                        $results[$item['description']] = $item['symbol'];
                        $count++;
                    }
                  
                }
                if($count===5){
                    break;
                }
            }
            return $results;
        }
        return [];
    }

    public function paste($name, $symbol){
        $this->search = $name;
        
    }

    public function found() {
        $jsonString = file_get_contents(base_path('resources/json/stock_symbol.json'));
        $data = json_decode($jsonString,true);
        $results = array();
            foreach($data as $item) {
                if(strtolower($item['description']) === strtolower($this->search)){
                    if($item["type"]=== "Common Stock"){
                        $this->mySymbol = $item['symbol'];
                        break;
                    } 
                }    
            }
        //do something if no match is found error handling
        
    }

    public function render()
    {
        return view('livewire.testing',[
            "results"=>$this->searching()
        ])->extends('layouts.app');
    }
}
 
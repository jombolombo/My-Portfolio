<?php

namespace App\Http\Livewire\Company;

use Livewire\Component;
use App\Http\API\Finnhub;

class Search extends Component
{
    public $search = "";
    public $mySymbol = "";
    public $notFound = [];

    public function mount(){
        $this->search = "";
        $this->mySymbol = "";
    }
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
                        $this->emit('SearchComplete', $this->mySymbol);
                        $this->search = "";
                        break;
                    } 
                }    
            }
        $this->notFound = "Company Name Not Found";
        
    }

    public function render()
    {
        return view('livewire.company.search',[
            "results"=>$this->searching()
        ])->extends('layouts.app');
    }
}


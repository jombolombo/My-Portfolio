<?php

namespace App\Http\Livewire\Profile;

use Livewire\Component;
use App\Models\User;
use App\Models\accountvalue;
use App\Http\API\Finnhub;
use App\Http\functions\MyFunctions;

class Trade extends Component
{
    public $stock = "";
    public $transaction;
    public $quantity;
    public $accountValue =[];
    public $unitPrice = "";
    public $correctStockSymbol = false;
    public $customErrorSymbol = [];
    public $customErrorQuantity = [];
    public $customErrorTransaction = [];
    protected $listeners =["unitPrice"];
    public $tip = [];

    protected $rules = [
        'stock' => 'required',
        'transaction' => 'required | boolean',
        'quantity' => 'required| numeric | max:350'
    ];

    public function mount () {
        $tempSymbol =request()->route()->parameters();
        if ($tempSymbol !==[]) {
            $this->stock = $tempSymbol["symbol"];
        }
        MyFunctions::calculateAccountValue();
        $account = auth()->user()->accountValue;
        $this->accountValue = $account["accountvalue"];
        $this->tip = MyFunctions::tip();

    }

    public function unitPrice ($price) {
        $this->unitPrice = $price;
    }

    public function submit() {
        $validated =$this->validate();
        $transaction = "";
        if ($validated["transaction"] === "1") {
            $transaction = "buy";
        } else if ($validated["transaction"] === "0") {
            $transaction = "sell";
        }
        if ($this->correctStockSymbol) { 
            $myStocks = auth()->user()->stock;
            if ($transaction ==="buy") {
                if(auth()->user()->difficulty === "easy") {
                    if ((float) $this->getCashValue() >= ((float) $this->unitPrice * (float) $validated["quantity"]) ){
                        $tempStockValue = 0;
                        foreach($myStocks as $stock){
                            if ($validated["stock"] === $stock["Symbol"]){
                                $tempStockValue = $stock["Qty"] * $this->unitPrice;
                                break;
                            }
                        }
                        $tempStockValue = $tempStockValue + ((float) $this->unitPrice * (float) $validated["quantity"]);
                        if($tempStockValue < (1/3 *  (float) $this->accountValue )) {
                            return redirect()->route('confirmTrade', ["request"=>$transaction, "quantity"=>$validated["quantity"] ,"stockSymbol"=>$validated["stock"], "unitPrice"=>$this->unitPrice]);
                        } else {
                            $this->customErrorQuantity = "You cannot invest more than 1/3 of your account value in one stock";
                        }
                    }else {
                        $this->customErrorQuantity = "you cannot afford this stock";

                    }
                } else {
                    if ((float) $this->getCashValue() >= ((float) $this->unitPrice * (float) $validated["quantity"] * 0.05) ){
                        return redirect()->route('confirmTrade', ["request"=>$transaction, "quantity"=>$validated["quantity"] ,"stockSymbol"=>$validated["stock"], "unitPrice"=>$this->unitPrice]);
                    }else {
                        $this->customErrorQuantity = "you cannot afford this stock";

                    }
                }
            } else if ($transaction ==="sell") {        
                foreach ($myStocks as $stock) {
                    if (strtolower($validated["stock"]) == strtolower($stock["Symbol"])){
                        if ($validated["quantity"] <= $stock["Qty"]){
                            return redirect()->route('confirmTrade', ["request"=>$transaction, "quantity"=>$validated["quantity"] ,"stockSymbol"=>$validated["stock"], "unitPrice"=>$this->unitPrice]);
                        }else{
                            $this->customErrorQuantity = "You dont have this much stock to sell";
                        }
                    }
                }
                if ($this->customErrorQuantity ===[]){
                    $this->customErrorTransaction = "you dont own this stock";
                }   
            }
        } else {
            $this->customErrorSymbol ="Invalid Stock Symbol";
        }
        
    }

    public function searchStock(){
        if($this->stock !== ''){
            $jsonString = file_get_contents(base_path('resources/json/stock_symbol.json'));
            $data = json_decode($jsonString,true);
            $emitted = false;
            foreach($data as $item) {
                if (strtolower($item['displaySymbol']) === strtolower($this->stock)) {
                        $this->emit("symbolIs", strtolower($item['displaySymbol']));
                        $emitted = true;
                        $this->correctStockSymbol =true;
                        break;
                }
            }
            if ($emitted !== true) {
                $this->correctStockSymbol =false;
                $this->emit("symbolIs", "");
            }

            $results = array();
            $count = 0;
            foreach($data as $item) {
                if(str_starts_with(strtolower($item['displaySymbol']),strtolower($this->stock))){
                    if($item["type"]=== "Common Stock"){
                        $results[$item['displaySymbol']] = $item['description'];
                        $count++;
                    }
                  
                }
                if($count===5){
                    break;
                }
            }
           
            return $results;
        } else{
            $this->emit("symbolIs", "");
        }
        return [];
    }


    public function paste($symbol){
        $this->stock = $symbol;
        
    }

    public function getCashValue(){
        return auth()->user()->cash_value;
    }
    public function render()
    {
        return view('livewire.profile.trade', ["results"=>$this->searchStock(), "cashvalue"=>$this->getCashValue()])->extends('layouts.app');
    }
}

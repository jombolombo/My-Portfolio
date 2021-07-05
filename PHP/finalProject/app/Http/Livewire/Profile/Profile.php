<?php

namespace App\Http\Livewire\Profile;

use Livewire\Component;
use App\Http\API\Finnhub;
use App\Models\User;
use App\Models\Stock;
use App\Http\functions\MyFunctions;

class Profile extends Component
{
    public $stocks ;
    public $sell = false;
    public $sellStock;
    public $peers = array("aapl", "tsla", "msft" ,"fb") ;
    public $quantity;
    public $stockInfo;
    public $customErrorQuantity = [];
    public $customErrorTransaction = [];
    public $tip = [];

    protected $rules = [
        'sellStock' => 'required',
        'quantity' => 'required| numeric | max:350'
    ];


    public function mount() {
        MyFunctions::calculateAccountValue();
        $this->tip = MyFunctions::tip();
        if (auth()->user()->accountValue == null){
            return redirect("/home");
        }
    }
    
    public function submit() {
        $validated =$this->validate();
        if($validated["quantity"] <= $this->stockInfo["Qty"]){
            foreach($this->stocks as $stock) {
                if($stock["Symbol"] === $validated["sellStock"]) {
                    // transaction validated can continue
                    $stockUnitPrice = Finnhub::getQuote($stock["Symbol"])->json()["c"];
                    return redirect()->route('confirmTrade', ["request"=>"sell", "quantity"=>$validated["quantity"] ,"stockSymbol"=>$stock["Symbol"], "unitPrice"=>$stockUnitPrice]);
                }
            }
            // output error stock not found
            $this->customErrorTransaction = "you dont own this stock";
        } else{
            // output error quantity is too large
            $this->customErrorQuantity = "You dont have this much stock to sell";
        }
        
    }


    public function tempStocks() {
        $this->stocks = auth()->user()->stock;
        for ($x = 0; $x < count($this->stocks); $x++) {
            $stockUnitPrice = Finnhub::getQuote($this->stocks[$x]["Symbol"])->json()["c"];
            $this->stocks[$x]["current price"] = $stockUnitPrice;
            $this->stocks[$x]["gain-loss"] = round((($stockUnitPrice - $this->stocks[$x]["purchase_price"]) / $this->stocks[$x]["purchase_price"]) * 100, 2);
            if ($this->stocks[$x]["highest_price"] === null || $this->stocks[$x]["highest_price"] < $stockUnitPrice) {
                $time = now()->toDateTimeString();
               Stock::where("id", $this->stocks[$x]["id"])->update(["highest_price"=> $stockUnitPrice, "time_of_highest" =>$time]);
            }
        }
        $this->peers();
        return $this->stocks;
    }

    public function peers() {
        if (count($this->stocks) >0) {
                $this->peers = Finnhub::getPeers($this->stocks[rand(0,(count($this->stocks)-1))]["Symbol"])->json();
        } 
    }

    public function sell($id) {
        $this->sell = true;
        $this->stockInfo = Stock::find($id);
        $this->sellStock = $this->stockInfo["Symbol"];
        $this->quantity = $this->stockInfo["Qty"];
    }

    public function render()
    {
        return view('livewire.profile.profile', ["myStocks" => $this->tempStocks()])->extends("layouts.app");
    }
}

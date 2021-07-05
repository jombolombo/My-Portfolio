<?php

namespace App\Http\Livewire\Profile;

use Illuminate\Http\Request;
use App\Http\API\Finnhub;
use App\Models\User;
use App\Models\Stock;
use App\Models\tradingHistory;
use Livewire\Component;

class ConfirmTrade extends Component
{

    public $request = "";
    public $quantity = "";
    public $stockSymbol = "";
    public $unitPrice = "";
    public $tips = "";
    public $gains;
    public $review = "";
    public $NostockError = [];
    public $noCreditError = [];
    public $notEnoughStock = [];

    public function mount()
    {
        $temp = request()->route()->parameters();
        $this->request = $temp["request"];
        $this->quantity = $temp["quantity"];
        $this->stockSymbol = $temp["stockSymbol"];
        try {
            $this->unitPrice = Finnhub::getQuote($this->stockSymbol)->json()["c"];
        } catch (Exception $e) {
            $this->unitPrice = $temp["unitPrice"];
        }
        if ($this->request === "sell") {
            $myStocks = auth()->user()->stock;
            foreach ($myStocks as $stock) {
                if ($stock["Symbol"] === $this->stockSymbol) {
                    if (auth()->user()->difficulty === "easy") {
                        $this->gains = round((($this->unitPrice - $stock["purchase_price"]) / $stock["purchase_price"] * 100), 2);
                        break;
                    } else {
                        $this->gains = round((($this->unitPrice - $stock["purchase_price"]) / $stock["purchase_price"] * 100), 2);
                        break;
                    }
                }
            }
        }
        $this->review();
        $this->tips = $this->tips();
    }

    public function tips()
    {
        $jsonString = file_get_contents(base_path('resources/json/tips.json'));
        $data = json_decode($jsonString, true);
        $rand = rand(0, count($data) - 1);
        return $data[$rand];
    }

    public function review()
    {
        if ($this->request === "sell") {
            $stockUnitPrice = Finnhub::getQuote($this->stockSymbol)->json()["c"];
            $myStocks = auth()->user()->stock;
            foreach ($myStocks as $stock) {
                if ($stock["Symbol"] == $this->stockSymbol) {
                    if ($stock["highest_price"] > $stockUnitPrice) {
                        if ($stock["purchase_price"] < $stockUnitPrice) {
                            $this->review = "Although the best time to have sold this stock was when you last logined in
                             on {$stock['time_of_highest']} at a price of {$stock["highest_price"]}, you have still managed
                              to make a profit";
                        } else if ($stock["purchase_price"] == $stockUnitPrice) {
                            $this->review = "Although the best time to have sold this stock was 
                            when you last logined in on {$stock['time_of_highest']} at a price of {$stock["highest_price"]}, 
                            you have broken even meaning no money has been gained or lost";
                        } else {
                            $this->review = "The best time to have sold this stock was when you last logined in 
                            on {$stock['time_of_highest']} at a price of {$stock["highest_price"]}, you have made a 
                            lose in this transaction";
                        }
                    } else if ($stock["highest_price"] == $stockUnitPrice) {
                        if ($stock["purchase_price"] == $stock["highest_price"]) {
                            $this->review = "You have broken even, no profit or loss has been made.";
                        } else {
                            $this->review = "Since you bought this stock this is the best time to have sold the stock
                             and you have managed to make a profit";
                        }
                    } else if ($stock["highest_price"] < $stockUnitPrice) {
                        $this->review = "Since you bought this stock this is the best time to have sold the stock
                         and you have managed to make a profit";
                    }
                    break;
                }
            }
            $this->NostockError = "Error, User does not own this stock";
        }
    }

    public function back()
    {
        return redirect()->route("trade");
    }

    public function purchase()
    {
        $stockUnitPrice = Finnhub::getQuote($this->stockSymbol)->json()["c"];
        $UserCashValue = auth()->user()->cash_value;
        $totalCost = $this->quantity * $stockUnitPrice;
        if ($UserCashValue > $totalCost) {
            // let transaction through
            $newUserCashvalue = $UserCashValue - $totalCost;
            auth()->user()->update(["cash_value" => strval($newUserCashvalue)]);
            $stock = new Stock;
            $stock->Symbol = $this->stockSymbol;
            $stock->Qty = $this->quantity;
            $stock->purchase_price = $stockUnitPrice;
            $stock->highest_price = $stockUnitPrice;
            $stock->time_of_highest = now()->toDateTimeString();
            $stock->user_id = auth()->user()->id;
            if (auth()->user()->difficulty === "easy") {
                $stock->purchase_cost = $this->quantity * $stockUnitPrice;
            } else {
                // 5% purchase charge
                $purchaseCost =  $this->quantity * $stockUnitPrice * 0.05;
                $stock->purchase_cost = ($this->quantity * $stockUnitPrice) + $purchaseCost;
            }
            $stock->save();
            return redirect()->route("profile");
        } else {
            $this->noCreditError = " You do not have the funds to carry out this transaction";
        }
    }

    public function sell()
    {
        $myStocks = auth()->user()->stock;
        $stockUnitPrice = Finnhub::getQuote($this->stockSymbol)->json()["c"];
        foreach ($myStocks as $stock) {
            if (strtolower($stock["Symbol"]) == strtolower($this->stockSymbol)) {
                if ($stock["Qty"] == $this->quantity) {
                    $history = new tradingHistory;
                    $history->user_id = auth()->user()->id;
                    $history->symbol = $stock["Symbol"];
                    $history->purchase_price = $stock["purchase_price"];
                    $history->selling_price = $stockUnitPrice;
                    $history->quantity = $this->quantity;
                    $history->difficulty = auth()->user()->difficulty;
                    $history->save();
                    $stock = Stock::find($stock["id"]);
                    $stock->delete();
                    $newUserCashvalue = ($this->quantity * $stockUnitPrice)+auth()->user()->cash_value;
                    auth()->user()->update(["cash_value" => strval($newUserCashvalue)]);
                    return redirect()->route("profile");
                } else if ($stock["Qty"] > $this->quantity) {
                    $qty = $stock["Qty"] - $this->quantity;
                    $history = new tradingHistory;
                    $history->user_id = auth()->user()->id;
                    $history->symbol = $stock["Symbol"];
                    $history->purchase_price = $stock["purchase_price"];
                    $history->selling_price = $stockUnitPrice;
                    $history->quantity = $this->quantity;
                    $history->difficulty = auth()->user()->difficulty;
                    $history->save();
                    $stock = Stock::where("id", $stock["id"])->update(["Qty" => $qty]);
                    $newUserCashvalue = ($this->quantity * $stockUnitPrice)+auth()->user()->cash_value;
                    auth()->user()->update(["cash_value" => strval($newUserCashvalue)]);
                    return redirect()->route("profile");
                } else {
                    $this->notEnoughStock = "You do not have this amount of stock to sell";
                }
            }
        }
    }
    public function render()
    {
        return view('livewire.profile.confirm-trade')->extends("layouts.app");
    }
}

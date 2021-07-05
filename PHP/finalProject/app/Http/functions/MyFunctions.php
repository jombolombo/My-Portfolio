<?php 

namespace App\Http\functions;

use App\Models\User;
use App\Models\accountvalue;
use App\Http\API\Finnhub;

class MyFunctions {

    static public function calculateAccountValue(){
        $stocks = auth()->user()->stock;
        $value = 0;
        for ($x = 0; $x < count($stocks); $x++) {
            $stockUnitPrice = Finnhub::getQuote($stocks[$x]["Symbol"])->json()["c"];
            $currentStockValue = ($stockUnitPrice * $stocks[$x]["Qty"]);
            $value = $value + $currentStockValue;
        }
        $currentAccountValue = auth()->user()->cash_value + $value;
        if (auth()->user()->accountValue === null){
            $accountValue = new accountvalue;
            $accountValue->accountvalue = $currentAccountValue;
            $accountValue->highestValue = $currentAccountValue;
            $accountValue->lowestValue = $currentAccountValue; 
            $accountValue->user_id = auth()->user()->id;
            $accountValue->save();
        } else {
            $account = auth()->user()->accountValue;
            if ($account["highestValue"] < $currentAccountValue) {
                $account["highestValue"] = $currentAccountValue;
            } else if ($account["lowestValue"] > $currentAccountValue) {
                $account["lowestValue"] = $currentAccountValue;
            }
            $account["accountvalue"] = $currentAccountValue;
            $account->save();
        }
    }

    static public function tip(){
        $jsonString = file_get_contents(base_path('resources/json/tips.json'));
        $data = json_decode($jsonString,true);
        $rand = rand(0, count($data)-1);
        return $data[$rand];
    }
}
?>
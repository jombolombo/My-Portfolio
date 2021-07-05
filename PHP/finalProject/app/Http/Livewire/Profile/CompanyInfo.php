<?php

namespace App\Http\Livewire\Profile;

use Livewire\Component;
use App\Http\API\Finnhub;
use Illuminate\Support\Facades\Http;

class CompanyInfo extends Component
{
    protected $listeners = ['symbolIs'];
    public $stock = "";
    public $stockName ="";
    public $exchange;
    public $show = false;
    public $previousExchange;
    public $previousQoute;

    public function symbolIs($symbol) {
     if (strtolower($symbol) !== "") {
        $this->show = true;
        $this->stock = $symbol;
     }else {
        $this->show = false;
     }
    }

    public function stockinfo() {
        if($this->stock !== "") {
            $jsonString = file_get_contents(base_path('resources/json/stock_symbol.json'));
            $data = json_decode($jsonString,true);
            foreach($data as $item) {
                if (strtolower($item['symbol']) === strtolower($this->stock)) {
                    $this->stockName = $item["description"];
                    $companyProfileTemp = Finnhub::getCompanyProfile($item["symbol"]);
                    if($companyProfileTemp !== null){
                        if(array_key_exists("exchange",$companyProfileTemp->json())){
                            $this->exchange = $companyProfileTemp->json()["exchange"];
                            $this->previousExchange = $this->exchange;
                        }else {
                            $this->exchange;
                        }
                    }
                    //$this->exchange = Finnhub::getCompanyProfile($item["symbol"])->json()["exchange"];
                    if(array_key_exists("c",Finnhub::getQuote($item['symbol'])->json() )){
                        $qoute = Finnhub::getQuote($item['symbol'])->json();
                        try{
                            $this->emit("unitPrice", $qoute["c"]);
                            $this->previousQoute = $qoute;
                        } catch (Exception $e) {
                            $qoute = $this->previousQoute;
                        } finally {
                            return $qoute;
                        }
                        
                    } else {
                        $qoute = $this->previousQoute;
                        if($qoute !== null || $qoute !== []){
                            $this->emit("unitPrice", $qoute["c"]);
                            return $qoute;
                        }
                      
                    }
                   
                }
            }
        } else {
            return 0;
        }
       return  0;
    }
    public function render()
    {
        return view('livewire.profile.company-info', ["stockInfo" => $this->stockinfo()]);
    }
}

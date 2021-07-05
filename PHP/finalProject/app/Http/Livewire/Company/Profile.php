<?php

namespace App\Http\Livewire\Company;

use App\Http\API\Finnhub;
use App\Http\API\Alphavantage;
use App\Models\User;
use App\Http\functions\MyFunctions;
use Livewire\Component;

class Profile extends Component
{
    public $companySymbol = "AAPL";
    public $companyProfile;
    public $balanceSheet = [];
    public $companyNameInfo= [];
    public $tip = [];
    protected $listeners = ['SearchComplete'];

    public function SearchComplete($newSymbol) {
        $this->companySymbol = $newSymbol;
        
    }

    public function mount() {
        $tempSymbol =request()->route()->parameters();
        if ($tempSymbol !== []) {
            $this->companySymbol = $tempSymbol["symbol"];
        }
        $this->tip = MyFunctions::tip();
    }

    public function getPeers(){
        if ($this->companySymbol !=="") {
            return Finnhub::getPeers($this->companySymbol)->json();
        }else{
            return [];
        }
    }

    public function getCompanyProfile(){
        if ($this->companySymbol !=="") {
            $this->companyProfile = Finnhub::getCompanyProfile($this->companySymbol)->json();
            return $this->companyProfile;
        }else{
            return [];
        }
    }

    public function getQoute(){
        if ($this->companySymbol !=="") {
            return Finnhub::getQuote($this->companySymbol)->json();
        }else{
            return [];
        }
    }

    public function getNews() {
        if ($this->companySymbol !=="") {
            return Finnhub::getSpecificNewsForToday($this->companySymbol)->json();
        }else{
            return [];
        }
    }

    public function changeSymbol($newSymbol) {
        $this->companySymbol = $newSymbol;
    }

    // function always learning everytime a new unknown request is made desciption is stored in json for faster data
    // retrivial and to make less calls to APi
    public function getCompanyDescription(){
        $jsonString = file_get_contents(base_path('resources/json/stock_symbol.json'));
        $data = json_decode($jsonString,true);
        for($x = 0; $x < sizeof($data); $x++){
            if(strtolower($data[$x]['symbol']) === strtolower($this->companySymbol)){
                if (sizeof($data[$x]) === 7) {
                    $description = Alphavantage::getCompanyOverView($this->companySymbol)["Description"];
                    $data[$x]["CompanyDescription"] = $description;
                    $jsonData = json_encode($data);
                    file_put_contents(base_path('resources/json/stock_symbol.json') ,$jsonData);
                    return $data[$x]["CompanyDescription"];
                }else if (sizeof($data[$x]) === 8) {
                    return $data[$x]["CompanyDescription"];
                }else {
                    return [];
                }
            }
        }
            return [];
    }

    public function getBalanceSheet(){
        $this->balanceSheet = Alphavantage::getBalanceSheet($this->companySymbol);
    }
    public function removeBalanceSheet() {
        $this->balanceSheet =[];
    }
    public function render()
    {
        return view('livewire.company.profile',["peers"=>$this->getPeers(),"news"=>$this->getNews(),
        'companyProfile' => $this->getCompanyProfile(),"qoute"=>$this->getQoute(), 
        "description"=>$this->getCompanyDescription()]);
    }
}

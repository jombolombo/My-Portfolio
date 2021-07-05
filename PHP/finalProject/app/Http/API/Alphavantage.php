<?php

namespace App\Http\API;

use Illuminate\Support\Facades\Http;

class Alphavantage {

    public static $apikey = "";

    public static function getCompanyOverView($symbol) {
        return Http::get("https://www.alphavantage.co/query?function=OVERVIEW&symbol={$symbol}&apikey=" . self::$apikey)->json();
    }

    public static function getBalanceSheet($symbol) {
        return Http::get("https://www.alphavantage.co/query?function=BALANCE_SHEET&symbol={$symbol}&apikey=demo" . self::$apikey)->json()["quarterlyReports"][0];
    }

}
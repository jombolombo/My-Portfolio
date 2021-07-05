<?php

namespace App\Http\API;

use Illuminate\Support\Facades\Http;

class Finnhub {
    public static string $apikey = "" ;


    static function getCompanyProfile($companySymbol){
        $companySymbol = strtoupper($companySymbol);
        try{
            return Http::get("https://finnhub.io/api/v1/stock/profile2?symbol={$companySymbol}&token=" . self::$apikey);
        }catch( Exception $e){
            dd($e);
        }
        
    }

    static function getPeers($companySymbol){
        $companySymbol = strtoupper($companySymbol);
        try{
            return Http::get("https://finnhub.io/api/v1/stock/peers?symbol={$companySymbol}&token=" . self::$apikey);
        } catch(Exception $e) {
            dd($e);
        }
       
    }

    static function getCompanySymbols($symbol){
        try{
            return Http::get("https://finnhub.io/api/v1/search?q={$symbol}&token=" . self::$apikey);
        } catch(Exception $e) {
            dd($e);
        }
        
    }
    static function getbasicFinancials($companySymbol){
        $companySymbol = strtoupper($companySymbol);
        try {
            return Http::get("https://finnhub.io/api/v1/stock/metric?symbol={$companySymbol}&metric=all&token=" . self::$apikey);
        } catch(Exception $e) {
            dd($e);
        }
       
    }
    
    static function getSpecificNewsForToday($companySymbol){
        $companySymbol = strtoupper($companySymbol);
        $date = date('Y-m-d');
        try {
            return Http::get("https://finnhub.io/api/v1/company-news?symbol={$companySymbol}&from={$date}&to={$date}&token=" . self::$apikey);
        } catch (Exception $e) {
            dd($e);
        }
    }
    static function getQuote($companySymbol){
        $companySymbol = strtoupper($companySymbol);
        try{
            return Http::get("https://finnhub.io/api/v1/quote?symbol={$companySymbol}&token=" . self::$apikey);
        } catch (Exception $e) {
            dd($e);
        }
    }

    static function getNews() {
        try {
            return Http::get('https://finnhub.io/api/v1/news?category=general&token=' . self::$apikey);
        } catch (Exception $e){
            dd($e);
        }
    }

    static function getMyNews($companySymbol, $startDate, $finishDate) {
        $companySymbol = strtoupper($companySymbol);
        try{
            return Http::get("https://finnhub.io/api/v1/company-news?symbol={$companySymbol}&from={$startDate}&to={$finishDate}&token=" . self::$apikey);
        } catch (Exception $e) {
            dd($e);
        }
    }
}
?>
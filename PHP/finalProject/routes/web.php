<?php

use Illuminate\Support\Facades\Route;
use App\Http\Livewire\Company\Search;
use App\Http\Livewire\Profile\Trade;
use App\Http\Livewire\Profile\ConfirmTrade;
use App\Http\Livewire\Profile\Profile;
use App\Http\Livewire\Profile\Leaderboard;
use App\Http\Livewire\Profile\TradeHistory;
use App\Http\Livewire\Settings\Settings;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});
Route::get('/home', function () {
    return view('welcome');
});

Route::get('news', 'App\Http\Controllers\NewController@index')
            ->name('news.index');

Route::post('news', 'App\Http\Controllers\NewController@search')
            ->name('news.search');

Auth::routes();

Route::get('/companies', Search::class)->name('company');
Route::get('/company/{symbol}', Search::class)->name('company1');
Route::get('/trade', Trade::class)->name('trade')->middleware('auth');
Route::get('/trade/{symbol}', Trade::class)->name('trade1')->middleware('auth');
Route::get('/confirmTrade/{request}/{quantity}/{stockSymbol}/{unitPrice}',ConfirmTrade::class)->name("confirmTrade")->middleware('auth');
Route::get('/profile', Profile::class)->name("profile")->middleware('auth');
Route::get('/settings', Settings::class)->name("settings")->middleware('auth');
Route::get('/leaderboard', Leaderboard::class)->name("leaderboard")->middleware('auth');
Route::get('/tradehistory', TradeHistory::class)->name('tradeHistory')->middleware('auth');

<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/
use \Illuminate\Support\Facades\Route;
Route::get('/', function()
{
	return View::make('hello');
});

Route::get('/rest/v1/city/cityFilter','MuaBanNhaDatController@cityFilter');

Route::get('/rest/v1/city/searchByKey', 'MuaBanNhaDatController@searchByKey');

Route::get('/rest/v1/city/searchByUser', 'MuaBanNhaDatController@searchByUser');

Route::get('/rest/v1/city/searchByPrice', 'MuaBanNhaDatController@searchByPrice');

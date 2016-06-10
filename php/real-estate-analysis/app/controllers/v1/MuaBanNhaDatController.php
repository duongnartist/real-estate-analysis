<?php

/**
 * Created by PhpStorm.
 * User: user
 * Date: 09/06/2016
 * Time: 11:09 SA
 */
use \Illuminate\Support\Facades\Input;
class MuaBanNhaDatController extends BaseController
{
    public function cityFilter()
    {
        $inputs = Input::all();
        $nt = new MuaBanNhaDat(null);

        if(!empty($inputs['city'])) $filter['city'] = $inputs['city'];
        if(!empty($inputs['district'])) $filter['district'] = $inputs['district'];
        if(!empty($inputs['ward'])) $filter['ward'] = $inputs['ward'];
        if(!empty($inputs['street'])) $filter['street'] = $inputs['street'];

        $result = $nt->cityFilter($filter);
        return $result;
    }

    public function searchByKey(){
        $inputs= Input::all();
        $nt = new MuaBanNhaDat(null);
        $result = $nt->filterAll($inputs['key']);
        return $result;
    }

    public function searchByUser(){
        $inputs= Input::all();
        $nt = new MuaBanNhaDat(null);
        if(!empty($inputs['name'])) $filter['name'] = $inputs['name'];
        if(!empty($inputs['phone'])) $filter['phone'] = $inputs['phone'];
        $result = $nt->filterByUser($filter);
        return $result;
    }

    public function searchByPrice(){
        $inputs= Input::all();
        $nt = new MuaBanNhaDat(null);
        $result = $nt->filterByPrice($inputs['price']);
        return $result;
    }
}
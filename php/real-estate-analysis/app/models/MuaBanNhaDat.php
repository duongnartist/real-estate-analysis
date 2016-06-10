<?php

/**
 * Created by PhpStorm.
 * User: user
 * Date: 09/06/2016
 * Time: 11:09 SA
 */
use \LMongo\Facades\LMongo;

class MuaBanNhaDat
{
    private $_collection = 'mua_ban_nha_dat';
    private $_mua_ban_nha_dat = null;

    function __construct($data)
    {

    }

    function getAll()
    {
        try {
            return LMongo::collection($this->_collection)
                ->orderBy('date_created', -1)
                ->paginate(10);
        } catch (MongoException $e) {
            $e->getMessage();
        }
    }

    function cityFilter($filter)
    {
        try {
            $query = LMongo::collection($this->_collection);
            if (!empty($filter['city']) && $filter['city'] != '') $query = $query->whereLike('city', $filter['city']);
            if (!empty($filter['district']) && $filter['district'] != '') $query = $query->whereLike('district', $filter['district']);
            if (!empty($filter['ward']) && $filter['ward'] != '') $query = $query->whereLike('ward', $filter['ward']);
            if (!empty($filter['street']) && $filter['street'] != '') $query = $query->whereLike('street', $filter['street']);
            return $query->orderBy('date_created', -1)->paginate(100);
        } catch (MongoException $e) {
            $e->getMessage();
        }
    }

    function filterAll($key)
    {
        try {
            $query = LMongo::collection($this->_collection)
                ->whereLike('city', $key)
                ->whereLike('ward', $key)
                ->whereLike('street', $key)
                ->whereLike('district', $key)
                ->whereLike('description', $key)
                ->whereLike('title', $key);
            return $query->orderBy('date_created', -1)->paginate(100);
        } catch (MongoException $e) {
            $e->getMessage();
        }
    }

    function filterByUser($filter){
        try {
            $query = LMongo::collection($this->_collection);
            if (!empty($filter['name']) && $filter['name'] != '') $query = $query->whereLike('name', $filter['name']);
            if (!empty($filter['phone']) && $filter['phone'] != '') $query = $query->whereLike('phone', $filter['phone']);
            return $query->orderBy('date_created', -1)->paginate(100);
        } catch (MongoException $e) {
            $e->getMessage();
        }
    }

    function filterByPrice($price){
        try{
            $query = LMongo::collection($this->_collection);
            $query = $query->where('price',$price);
            return $query->orderBy('date_created', -1)->paginate(100);
        }catch (MongoException $e){
            $e->getMessage();
        }
    }
}
<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\License;
use App\Models\Products;
use Illuminate\Http\Request;

class CalibrationProductController extends Controller
{
    //
    public function get_calibration_products(Request $request)
     {
         $api_key = $request->api_key;
         if (License::where('api_key', $api_key)->count() > 0) {
             $product = Products::all();
             $data = ['data' => ['status' => 'ok', 'products' => $product]];
             return response()->json($data);
         }
     }
}

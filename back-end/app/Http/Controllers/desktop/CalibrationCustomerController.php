<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\Customers;
use App\Models\License;
use Illuminate\Http\Request;

class CalibrationCustomerController extends Controller
{
    //
    public function add_calibration_customers(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $check_customer = Customers::where('customer_name', $request->customer_name)->count();
            if ($check_customer < 1) {
                $customer = new Customers;
                $customer->customer_name = $request->customer_name;
                $customer->save();

                $data = [
                    "data" => ["status" => "ok", "messages" => "Data berhasil di input !"]
                ];

                return response()->json($data);
            } else {
                $data = [
                    "data" => ["status" => "failed", "messages" => "Data sudah ada !"]
                ];
        
                return response()->json($data);
            }
        } else {
            $data = [
                "data" => ["status" => "failed", "messages" => "Data gagal di input !"]
            ];
    
            return response()->json($data);
        }        
    }

    public function get_calibration_customers(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $customer = Customers::all();
            $data = ['data' => ['status' => 'ok', 'customers' => $customer]];
            return response()->json($data);
        }
    }
}

<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\License;
use App\Models\Suppliers;
use Illuminate\Http\Request;

class CalibrationSupplierController extends Controller
{
    //
    public function add_calibration_suppliers(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $check_supplier = Suppliers::where('supplier_name', $request->supplier_name)->count();
            if ($check_supplier < 1) {
                $supplier = new Suppliers();
                $supplier->supplier_name = $request->supplier_name;
                $supplier->save();

                $data = [
                    "data" => ["status" => "ok", "messages" => "Data berhasil di input !"]
                ];

                return response()->json($data);
            } else {
                $data = [
                    "data" => ["status" => "ok", "messages" => "Data sudah ada !"]
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

    public function get_calibration_suppliers(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $supplier = Suppliers::all();
            $data = ['data' => ['status' => 'ok', 'suppliers' => $supplier]];
            return response()->json($data);
        }
    }
}

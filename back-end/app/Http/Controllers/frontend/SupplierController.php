<?php

namespace App\Http\Controllers\frontend;

use App\Http\Controllers\Controller;
use App\Models\Suppliers;
use Illuminate\Http\Request;

class SupplierController extends Controller
{
    //
    public function add_suppliers(Request $request)
    {
        $supplier = new Suppliers();
        $supplier->supplier_name = $request->supplier_name;
        $supplier->save();

        if (Suppliers::where('supplier_name', $request->supplier_name)->count() > 0) {
            $data = [
                "status" => "success", "messages" => "Data berhasil di input"
            ];

            return response()->json($data);
        } else {
            $data = [
                "status" => "failed", "messages" => "Data gagal di input !"
            ];

            return response()->json($data);
        }
    }

    public function get_suppliers()
    {
        $supplier = Suppliers::all();
        return response()->json($supplier);
    }

    public function edit_suppliers(Request $request)
    {
        $supplier = Suppliers::where('supplier_name', $request->recent_name);

        if ($supplier->count() > 0) {
            $supplier->update([
                'supplier_name' => $request->new_name,
            ]);

            $data = ['status' => 'success'];

            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];

            return response()->json($data);
        }
    }

    public function delete_suppliers(Request $request)
    {
        $supplier = Suppliers::where('supplier_name', $request->supplier_name);
        $supplier->forceDelete();

        if (Suppliers::where('supplier_name', $request->supplier_name)->count() > 0) {
            $data = [
                "status" => "failed", "messages" => "Data gagal di hapus"
            ];

            return response()->json($data);
        } else {
            $data = [
                "status" => "success", "messages" => "Data berhasil di hapus !"
            ];

            return response()->json($data);
        }
    }
}

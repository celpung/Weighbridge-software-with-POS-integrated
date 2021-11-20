<?php

namespace App\Http\Controllers\frontend;

use App\Http\Controllers\Controller;
use App\Models\Customers;
use Illuminate\Http\Request;

class CustomerController extends Controller
{
    //
    public function add_customers(Request $request)
    {
        $customer = new Customers();
        $customer->customer_name = $request->customer_name;
        $customer->save();

        if (Customers::where('customer_name', $request->customer_name)->count() > 0) {
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

    public function get_customers()
    {
        $customer = Customers::all();
        return response()->json($customer);
    }

    public function edit_customers(Request $request)
    {
        $customer = Customers::where('customer_name', $request->recent_name);

        if ($customer->count() > 0) {
            $customer->update([
                'customer_name' => $request->new_name,
            ]);

            $data = ['status' => 'success'];

            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];

            return response()->json($data);
        }
    }

    public function delete_customers(Request $request)
    {
        $customer = Customers::where('customer_name', $request->customer_name);
        $customer->forceDelete();

        if (Customers::where('customer_name', $request->customer_name)->count() > 0) {
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

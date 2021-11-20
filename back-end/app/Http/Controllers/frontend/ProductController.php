<?php

namespace App\Http\Controllers\frontend;

use App\Http\Controllers\Controller;
use App\Models\Products;
use Illuminate\Http\Request;

class ProductController extends Controller
{
    //
    public function add_products(Request $request)
    {
        $product = new Products;
        $product->product_name = $request->product_name;
        $product->buy_price = $request->buy_price;
        $product->sell_price = $request->sell_price;
        $product->save();

        if (Products::where('product_name', $request->product_name)->count() > 0) {
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

    public function get_products()
    {
        $product = Products::all();
        return response()->json($product);
    }

    public function edit_products(Request $request)
    {
        $product = products::where('product_name', $request->recent_name);

        if ($product->count() > 0) {
            $product->update([
                'product_name' => $request->new_name,
                'buy_price' => $request->buy_price,
                'sell_price' => $request->sell_price,
            ]);

            $data = ['status' => 'success'];

            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];

            return response()->json($data);
        }
    }

    public function delete_products(Request $request)
    {
        $product = Products::where('product_name', $request->product_name);
        $product->forceDelete();

        if (Products::where('product_name', $request->product_name)->count() > 0) {
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

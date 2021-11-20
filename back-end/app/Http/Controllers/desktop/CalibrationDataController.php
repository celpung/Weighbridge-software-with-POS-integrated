<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\Calibrations;
use App\Models\License;
use App\Models\Transaction;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Str;

class CalibrationDataController extends Controller
{
    //
    public function insert(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $calibrations = new Calibrations();
            $calibrations->ticket = $request->ticket;
            $calibrations->date_in = $request->date_in;
            $calibrations->date_out = $request->date_out;
            $calibrations->vehicle = $request->vehicle;
            $calibrations->product_status = $request->product_status;
            $calibrations->product_name = $request->product_name;
            $calibrations->customer = $request->customer;
            $calibrations->supplier = $request->supplier;
            $calibrations->order_id = $request->order_id;
            $calibrations->driver = $request->driver;
            $calibrations->info = $request->info;
            $calibrations->gross = $request->gross;
            $calibrations->tare = $request->tare;
            $calibrations->result = $request->result;
            $calibrations->sort = $request->sort;
            $calibrations->percent = $request->percent;
            $calibrations->nett = $request->nett;
            $calibrations->price = $request->price;
            $calibrations->total_price = $request->total_price;
            $calibrations->cash = $request->cash;
            $calibrations->transfer = $request->transfer;
            $calibrations->out_weight = $request->out_weight;
            $calibrations->gap = $request->gap;
            $calibrations->operator = $request->operator;
            $calibrations->bridge_id = $request->bridge_id;
            $calibrations->save();

            $data = [
                "data" => ["status" => "ok", "messages" => "Data berhasil di input !"]
            ];

            return response()->json($data);
        } else {
            $data = [
                "data" => ["status" => "error", "messages" => "Kode lisensi salah !"]
            ];
            return response()->json($data);
        }
    }

    public function get_all(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $calibration = Calibrations::all();

            $data = ["data" => ["status" => "ok", "record" => $calibration]];
            return response()->json($data);
        }
    }

    public function update(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $calibration = Calibrations::where('ticket', $request->ticket)->where('bridge_id', $request->bridge_id);
            if ($calibration->count() > 0) {
                $calibration->update([
                    'date_in' => $request->date_in,
                    'date_out' => $request->date_out,
                    'vehicle' => $request->vehicle,
                    'product_status' => $request->product_status,
                    'product_name' => $request->product_name,
                    'customer' => $request->customer,
                    'supplier' => $request->supplier,
                    'order_id' => $request->order_id,
                    'driver' => $request->driver,
                    'info' => $request->info,
                    'gross' => $request->gross,
                    'tare' => $request->tare,
                    'result' => $request->result,
                    'sort' => $request->sort,
                    'percent' => $request->percent,
                    'nett' => $request->nett,
                    'price' => $request->price,
                    'total_price' => $request->total_price,
                    'cash' => $request->cash,
                    'transfer' => $request->transfer,
                    'out_weight' => $request->out_weight,
                    'gap' => $request->gap,
                    'operator' => $request->operator,
                ]);

                if ($request->cash != 0) {
                    $type = "";
                    $info = "";
                    $tag = "";
                    if ($request->product_status == 'MASUK') {
                        $type = "expense";
                        $info = "Pembelian ".$request->product_name." #tiket: ".$request->ticket;
                        $tag = "Pembelian";
                    } else {
                        $type = "income";
                        $info = "Penjualan ".$request->product_name." #tiket: ".$request->ticket;
                        $tag = "Penjualan";
                    }

                    $date = Carbon::parse($request->date_out);
                    $transaction_id = Str::random(5);
                    $account = "KAS";
                    $ammount = $request->cash;

                    $transaction = new Transaction;
                    $transaction->transaction_id = $type . "-" . $transaction_id;
                    $transaction->date = $date;
                    $transaction->type = $type;
                    $transaction->account = $account;
                    $transaction->info = $info;
                    $transaction->ammount = $ammount;
                    $transaction->tag = $tag;
                    $transaction->save();
                }

                if ($request->transfer != 0) {
                    $type = "";
                    $info = "";
                    $tag = "";
                    if ($request->product_status == 'MASUK') {
                        $type = "expense";
                        $info = "Pembelian ".$request->product_name." #tiket: ".$request->ticket;
                        $tag = "Pembelian";
                    } else {
                        $type = "income";
                        $info = "Penjualan ".$request->product_name." #tiket: ".$request->ticket;
                        $tag = "Penjualan";
                    }

                    $date = Carbon::parse($request->date_out);
                    $transaction_id = Str::random(5);
                    $account = "Bank";
                    $ammount = $request->transfer;

                    $transaction = new Transaction;
                    $transaction->transaction_id = $type . "-" . $transaction_id;
                    $transaction->date = $date;
                    $transaction->type = $type;
                    $transaction->account = $account;
                    $transaction->info = $info;
                    $transaction->ammount = $ammount;
                    $transaction->tag = $tag;
                    $transaction->save();
                }
                $data = [
                    "data" => ["status" => "ok", "messages" => "Data berhasil di input !"]
                ];

                return response()->json($data);
            } else {
                $data = [
                    "data" => ["status" => "error", "messages" => "Data tidak ada !"]
                ];

                return response()->json($data);
            }
        } else {
            $data = [
                "data" => ["status" => "error", "messages" => "Kode lisensi salah !"]
            ];
            return response()->json($data);
        }
    }

    public function delete(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $calibration = Calibrations::where('ticket', $request->ticket)->where('bridge_id', $request->bridge_id);
            if ($calibration->count() > 0) {
                $calibration->delete();

                $data = [
                    "data" => ["status" => "ok", "messages" => "Data berhasil di hapus !"]
                ];

                return response()->json($data);
            } else {
                $data = [
                    "data" => ["status" => "ok", "messages" => "Data tidak ada !"]
                ];

                return response()->json($data);
            }
        } else {
            $data = [
                "data" => ["status" => "error", "messages" => "Kode lisensi salah !"]
            ];
            return response()->json($data);
        }
    }

    public function check(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            if (Calibrations::where('ticket', $request->ticket)->where('bridge_id', $request->bridge_id)->count() > 0) {
                $data = [
                    "data" => ["status" => "ok", "messages" => "Data ada !"]
                ];

                return response()->json($data);
            } else {
                $data = [
                    "data" => ["status" => "failed", "messages" => "Data tidak ada !"]
                ];

                return response()->json($data);
            }
        }
    }
}

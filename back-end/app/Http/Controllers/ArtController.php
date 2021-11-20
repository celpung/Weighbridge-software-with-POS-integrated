<?php

namespace App\Http\Controllers;

use App\Models\Calibrations;
use App\Models\Transaction;
use Carbon\Carbon;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Str;

class ArtController extends Controller
{
    //
    public function first_deploy()
    {
        try {
            Artisan::call('migrate');
            Artisan::call('db:seed', array('--class' => "UsersSeeder"));
            Artisan::call('db:seed', array('--class' => "LicenseSeeder"));

            Artisan::call('vendor:publish', array('--provider' => "Laravel\Sanctum\SanctumServiceProvider"));


            dd('Migration run successfully');
        } catch (Exception $ex) {
            echo $ex->getMessage();
        }
    }

    public function find_duplicate()
    {
        $data = [];
        $calibration = Calibrations::all();

        foreach ($calibration as $c) {
            $find = Calibrations::where('date_out', $c->date_out)->where('vehicle', $c->vehicle)->where('nett', $c->nett)->get();
            //array_push($data, $find);
            if ($find->count() > 1) {
                $del = Calibrations::where('date_out', $c->date_out)->min('ticket');
                array_push($data, $del);
                Calibrations::where('ticket', $del)->delete();
            }
        }

        return response()->json($data);
    }

    public function deploy_transaction1(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [1, 200])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
        $data = ['status' => "success", "messages" => "Data berhasil di input !"];
        return response()->json($data);
    }

    public function deploy_transaction2(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [201, 400])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction3(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [401, 600])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction4(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [601, 800])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction5(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [801, 1000])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction6(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [1001, 1200])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction7(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [1201, 1400])->where('date_out', '!=', '-')->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_transaction8(Request $request)
    {
        $calibration = Calibrations::whereBetween('ticket', [1401, 1600])->get();
        foreach ($calibration as $c) {
            if ($c->cash != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "KAS";
                $ammount = $c->cash;

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

            if ($c->transfer != 0) {
                $type = "";
                $info = "";
                $tag = "";
                if ($c->product_status == 'MASUK') {
                    $type = "expense";
                    $info = "Pembelian " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Pembelian";
                } else {
                    $type = "income";
                    $info = "Penjualan " . $c->product_name . " #tiket: " . $c->ticket;
                    $tag = "Penjualan";
                }

                $date = Carbon::parse($c->date_out);
                $transaction_id = Str::random(5);
                $account = "Bank";
                $ammount = $c->transfer;

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
        }
    }

    public function deploy_patty(Request $request)
    {
        $data = $request->data;
        foreach ($request->data as $d) {
            $date = Carbon::parse($d["date"]);
            $transaction_id = Str::random(5);
            $type = $d["type"];
            $account = $d["account"];
            $info = $d["info"];
            $ammount = $d["ammount"];
            $tag = $d["tag"];

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

        return response()->json($data);
    }

    public function deploy_kas(Request $request)
    {
        $data = $request->data;
        foreach ($request->data as $d) {
            $date = Carbon::parse($d["date"]);
            $transaction_id = Str::random(5);
            $type = $d["type"];
            $account = $d["account"];
            $info = $d["info"];
            $ammount = $d["ammount"];
            $tag = $d["tag"];

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

        return response()->json($data);
    }







    /*
    public function set_items_masuk_gap(Request $request)
    {
        $data = $request->data;
        $raws = [];
        foreach ($data as $d) {
            $orgDate = date($request->input('date_out'));
            $newDate = date("d-m-Y", strtotime($orgDate));
            //$date_out = $d['date_out'];
            $date_outs = date("d-m-Y", strtotime($d['date_out']));
            $date_out = date("m-d-Y", strtotime($date_outs));
            $supplier_name = $d['supplier_name'];
            $out_weight = $d['out_weight'];
            $product_status = $d['product_status'];
            $nett = $d['nett'];
            $gap = $d['gap'];

            $calibration = Calibrations::where('date_out', 'like', "%$date_out%")->where('supplier', 'like', "%$supplier_name%")->where('product_status', $product_status)->where('nett', $nett);
            $calibration->update([
                'out_weight' => $out_weight,
                'gap' => $gap,
            ]);

            array_push($raws, $d);
        }
        return response()->json($raws);
    }

    public function set_items_keluar_gap(Request $request)
    {
        $data = $request->data;
        $raws = [];
        foreach ($data as $d) {
            $orgDate = date($request->input('date_out'));
            $newDate = date("d-m-Y", strtotime($orgDate));
            //$date_out = $d['date_out'];
            $date_outs = date("d-m-Y", strtotime($d['date_out']));
            $date_out = date("m-d-Y", strtotime($date_outs));
            $customer_name = $d['customer_name'];
            $out_weight = $d['out_weight'];
            $product_status = $d['product_status'];
            $nett = $d['nett'];
            $gap = $d['gap'];

            $calibration = Calibrations::where('date_out', 'like', "%$date_out%")->where('customer', 'like', "%$customer_name%")->where('product_status', $product_status)->where('nett', $nett);
            $calibration->update([
                'out_weight' => $out_weight,
                'gap' => $gap,
            ]);

            array_push($raws, $d);
        }
        return response()->json($raws);
    }

    public function set_cash_trf(Request $request)
    {
        $data = $request->data;

        $raws = [];
        foreach($data as $d) {
            $orgDate = date($request->input('date_out'));
            $newDate = date("d-m-Y", strtotime($orgDate));
            //$date_out = $d['date_out'];
            $date_outs = date("d-m-Y", strtotime($d['date_out']));
            $date_out = date("m-d-Y", strtotime($date_outs));
            $product_name = $d['product_name'];
            $product_status = $d['product_status'];
            $nett = $d['nett'];
            $price = $d['price'];
            $total_price = $d['total_price'];
            $cash = $d['cash'];
            $transfer = $d['transfer'];

            $calibration = Calibrations::where('date_out', 'like', "%$date_out%")->where('total_price', $total_price);

            $calibration->update([
                'cash' => $cash,
                'transfer' => $transfer,
            ]);

            array_push($raws, $d);

        }
        return response()->json($raws);
    }

    public function insert_calibrations(Request $request)
    {
        $data = $request->data;
        foreach($data as $d) {
            $calibrations = new Calibrations;
            $calibrations->ticket = $d['ticket'];
            $calibrations->date_in = $d['date_in'];
            $calibrations->date_out = $d['date_out'];
            $calibrations->vehicle = $d['vehicle'];
            $calibrations->product_status = $d['product_status'];
            $calibrations->product_name = $d['product_name'];
            $calibrations->customer = $d['customer'];
            $calibrations->supplier = $d['supplier'];
            $calibrations->order_id = $d['order_id'];
            $calibrations->driver = $d['driver'];
            $calibrations->info = $d['info'];
            $calibrations->gross = $d['gross'];
            $calibrations->tare = $d['tare'];
            $calibrations->result = $d['result'];
            $calibrations->sort = $d['sort'];
            $calibrations->percent = $d['percent'];
            $calibrations->nett = $d['nett'];
            $calibrations->price = $d['price'];
            $calibrations->total_price = $d['total_price'];
            $calibrations->cash = $d['cash'];
            $calibrations->transfer = $d['transfer'];
            $calibrations->out_weight = $d['out_weight'];
            $calibrations->gap = $d['gap'];
            $calibrations->operator = $d['operator'];
            $calibrations->bridge_id = $d['bridge_id'];
            $calibrations->created_at = $d['created_at'];
            $calibrations->updated_at = $d['updated_at'];
            $calibrations->save();

            
                $type = "";
                if ($d['product_status'] == "KELUAR") {
                    $type = "income";
                    $cssp = $d['customer'];
                } elseif ($d['product_status'] == "MASUK") {
                    $type = "expense";
                    $cssp = $d['supplier'];
                }
                $balances = new Buysell();
                $balances->info = "TIKET : #" . $d['ticket'] . " | BARANG " . $d['product_status'] . " | " . $d['product_name'] . " | " . $cssp;
                $balances->type = $type;
                $balances->money = $d['cash'];
                $balances->save();
            
                $bank = new Bank();
                $bank->info = "TIKET : #" . $d['ticket'] . " | BARANG " . $d['product_status'] . " | " . $d['product_name'] . " | " . $cssp;
                $bank->type = $type;
                $bank->money = $d['transfer'];
                $bank->save();
            
        }        
    }
    */
}

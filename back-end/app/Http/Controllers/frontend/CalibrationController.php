<?php

namespace App\Http\Controllers\frontend;

use App\Http\Controllers\Controller;
use App\Models\Calibrations;
use App\Models\Customers;
use App\Models\Listenerdata;
use App\Models\Products;
use App\Models\Suppliers;
use Carbon\Carbon;
use Illuminate\Http\Request;

class CalibrationController extends Controller
{
    //
    public function get_all()
    {
        $calibration = Calibrations::orderBy('ticket', 'desc')->get();

        $total_gross = $calibration->sum('gross');
        $total_tare = $calibration->sum('tare');
        $total_result = $calibration->sum('result');
        $total_nett = $calibration->sum('nett');
        $total_price = $calibration->sum('total_price');

        $total_data = [
            "total_gross" => $total_gross,
            "total_tare" => $total_tare,
            "total_result" => $total_result,
            "total_nett" => $total_nett,
            "total_price" => $total_price,
        ];

        $data = [
            "calibrations" => $calibration,
            "total" => $total_data,
        ];

        return response()->json($data);
    }

    public function filter_value()
    {
        $products = Products::all()->pluck('product_name');
        $customers = Customers::all()->pluck('customer_name');
        $suppliers = Suppliers::all()->pluck('supplier_name');

        $data = ['products' => $products, 'customers' => $customers, 'suppliers' => $suppliers];

        return response()->json($data);
    }

    /*
    public function filter_data(Request $request)
    {
        if ($request->start == "" || $request->start == null) {
            $start_date = Calibrations::orderBy('created_at', 'asc')->limit(1)->pluck('created_at');
        } else {
            $start_date = date('Y-m-d 00:00:00', strtotime($request->start));
        }

        if ($request->end == "" || $request->end == null) {
            $end_date = Calibrations::orderBy('created_at', 'desc')->limit(1)->pluck('created_at');
        } else {
            $end_date = date('Y-m-d 23:59:59', strtotime($request->end));
        }

        $product_status = $request->status;
        $product_name = $request->product;
        $customer = $request->customer;
        $supplier = $request->supplier;

        $calibration = calibrations::whereBetween('created_at', [$start_date, $end_date]);

        $product_status_query = '';
        if ($product_status == '-' || $product_status == '' || $product_status == null) {
            $product_status_query = $calibration->whereNotNull('product_status');
        } else {
            $product_status_query = $calibration->where('product_status', $product_status);
        }

        $product_name_query = '';
        if ($product_name == '-' || $product_name == '' || $product_name == null) {
            $product_name_query = $product_status_query->whereNotNull('product_name');
        } else {
            $product_name_query = $product_status_query->where('product_name', $product_name);
        }

        $customer_query = '';
        if ($customer == '-' || $customer == '' || $customer == null) {
            $customer_query = $product_name_query->whereNotNull('customer');
        } else {
            $customer_query = $product_name_query->where('customer', $customer);
        }

        $master_query = '';
        if ($supplier == '-' || $supplier == '' || $supplier == null) {
            $master_query = $customer_query->whereNotNull('supplier');
        } else {
            $master_query = $customer_query->where('supplier', $supplier);
        }

        $record = $master_query->get();

        $total_gross = $record->sum('gross');
        $total_tare = $record->sum('tare');
        $total_result = $record->sum('result');
        $total_nett = $record->sum('nett');
        $total_price = $record->sum('total_price');

        $total_data = [
            "total_gross" => $total_gross,
            "total_tare" => $total_tare,
            "total_result" => $total_result,
            "total_nett" => $total_nett,
            "total_price" => $total_price,
        ];

        $data = [
            "calibrations" => $record,
            "total" => $total_data,
        ];

        return response()->json($data);
    }
    */

    public function delete_data(Request $request)
    {
        $listen = Listenerdata::where('ticket', $request->ticket)->where('sync', $request->sync);

        if ($listen->count() == 0) {
            $listen = new Listenerdata;
            $listen->sync = $request->sync;
            $listen->ticket = $request->ticket;
            $listen->date_in = "-";
            $listen->date_out = "-";
            $listen->vehicle = "-";
            $listen->product_status = "-";
            $listen->product_name = "-";
            $listen->customer = "-";
            $listen->supplier = "-";
            $listen->order_id = "-";
            $listen->driver = "-";
            $listen->info = "-";
            $listen->gross = 0;
            $listen->tare = 0;
            $listen->result = 0;
            $listen->sort = 0;
            $listen->percent = 0;
            $listen->nett = 0;
            $listen->price = 0;
            $listen->total_price = 0;
            $listen->cash = 0;
            $listen->transfer = 0;
            $listen->out_weight = 0;
            $listen->gap = 0;
            $listen->operator = "-";
            $listen->save();
        }

        $delete = Calibrations::where('ticket', $request->ticket)->delete();
        if ($delete) {
            $data = ["status" => "success", "messages" => "Data berhasil di hapus!"];
            return response()->json($data);
        } else {
            $data = ["status" => "failed", "messages" => "Data gagal di hapus!"];
            return response()->json($data);
        }
    }

    public function edit_data(Request $request)
    {
        $calibration = Calibrations::where('ticket', $request->ticket);

        $calibration = Calibrations::where('ticket', $request->ticket);
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

            $listen = Listenerdata::where('ticket', $request->ticket)->where('sync', $request->sync);

            if ($listen->count() == 0) {
                $listen = new Listenerdata;
                $listen->sync = $request->sync;
                $listen->ticket = $request->ticket;
                $listen->date_in = $request->date_in;
                $listen->date_out = $request->date_out;
                $listen->vehicle = $request->vehicle;
                $listen->product_status = $request->product_status;
                $listen->product_name = $request->product_name;
                $listen->customer = $request->customer;
                $listen->supplier = $request->supplier;
                $listen->order_id = $request->order_id;
                $listen->driver = $request->driver;
                $listen->info = $request->info;
                $listen->gross = $request->gross;
                $listen->tare = $request->tare;
                $listen->result = $request->result;
                $listen->sort = $request->sort;
                $listen->percent = $request->percent;
                $listen->nett = $request->nett;
                $listen->price = $request->price;
                $listen->total_price = $request->total_price;
                $listen->cash = $request->cash;
                $listen->transfer = $request->transfer;
                $listen->out_weight = $request->out_weight;
                $listen->gap = $request->gap;
                $listen->operator = $request->operator;
                $listen->save();
            }

            $data = ['status' => 'success', 'messages' => 'Data berhasil di update'];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed', 'messages' => 'Data tidak ada'];
            return response()->json($data);
        }
    }
}

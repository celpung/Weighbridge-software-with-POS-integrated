<?php

namespace App\Http\Controllers;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Models\Transaction;
use Illuminate\Support\Str;

class TransactionController extends Controller
{
    //
    public function add_transaction(Request $request)
    {
        $date = Carbon::parse($request->date);
        $transaction_id = Str::random(5);
        $type = $request->type;
        $account = $request->account;
        $info = $request->info;
        $ammount = $request->ammount;
        $tag = $request->tag;

        $transaction = new Transaction;
        $transaction->transaction_id = $type . "-" . $transaction_id;
        $transaction->date = $date;
        $transaction->type = $type;
        $transaction->account = $account;
        $transaction->info = $info;
        $transaction->ammount = $ammount;
        $transaction->tag = $tag;
        $transaction->save();

        $data = ['status' => "success", "messages" => "Data berhasil di input !"];
        return response()->json($data);
    }

    public function get_patty()
    {
        $patty = Transaction::where('account', 'Patty')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($patty as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    public function edit_patty(Request $request)
    {
        $patty = Transaction::where('account', 'patty')->where('id', $request->id);
        $patty->update([
            'info' => $request->info,
            'type' => $request->type,
            'ammount' => $request->ammount,
        ]);
    }

    public function delete_patty(Request $request)
    {
        $patty = Transaction::where('account', 'patty')->where('id', $request->id);
        $patty->delete();
    }

    public function filter_patty(Request $request)
    {
        if ($request->start_date == "" || $request->start_date == "-" || $request->start_date == null) {
            $start_date = Carbon::parse("1900-01-01");
        } else {
            $start_date = Carbon::parse($request->start_date);
        }

        if ($request->end_date == "" || $request->end_date == "-" || $request->end_date == null) {
            $end_date = Carbon::parse("2100-01-01");
        } else {
            $end_date = Carbon::parse($request->end_date);
        }

        $patty = Transaction::whereBetween('date', [$start_date, $end_date])->where('account', 'patty')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($patty as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    //KAS
    public function get_kas()
    {
        $kas = Transaction::where('account', 'kas')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($kas as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    public function edit_kas(Request $request)
    {
        $kas = Transaction::where('account', 'kas')->where('id', $request->id);
        $kas->update([
            'info' => $request->info,
            'type' => $request->type,
            'ammount' => $request->ammount,
        ]);
    }

    public function delete_kas(Request $request)
    {
        //$kas = kasCash::where('id', $request->id);
        $kas = Transaction::where('account', 'kas')->where('id', $request->id);
        $kas->delete();
    }

    public function filter_kas(Request $request)
    {
        if ($request->start_date == "" || $request->start_date == "-" || $request->start_date == null) {
            $start_date = Carbon::parse("1900-01-01");
        } else {
            $start_date = Carbon::parse($request->start_date);
        }

        if ($request->end_date == "" || $request->end_date == "-" || $request->end_date == null) {
            $end_date = Carbon::parse("2100-01-01");
        } else {
            $end_date = Carbon::parse($request->end_date);
        }

        $kas = Transaction::whereBetween('date', [$start_date, $end_date])->where('account', 'kas')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($kas as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    //Bank
    public function get_bank()
    {
        $bank = Transaction::where('account', 'bank')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($bank as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    public function edit_bank(Request $request)
    {
        $bank = Transaction::where('account', 'bank')->where('id', $request->id);
        $bank->update([
            'info' => $request->info,
            'type' => $request->type,
            'ammount' => $request->ammount,
        ]);
    }

    public function delete_bank(Request $request)
    {
        //$bank = bankCash::where('id', $request->id);
        $bank = Transaction::where('account', 'bank')->where('id', $request->id);
        $bank->delete();
    }

    public function filter_bank(Request $request)
    {
        if ($request->start_date == "" || $request->start_date == "-" || $request->start_date == null) {
            $start_date = Carbon::parse("1900-01-01");
        } else {
            $start_date = Carbon::parse($request->start_date);
        }

        if ($request->end_date == "" || $request->end_date == "-" || $request->end_date == null) {
            $end_date = Carbon::parse("2100-01-01");
        } else {
            $end_date = Carbon::parse($request->end_date);
        }

        $bank = Transaction::whereBetween('date', [$start_date, $end_date])->where('account', 'bank')->orderBy('date', 'asc')->get();
        $data = [];
        $balance = 0;
        $debt = 0;
        $credit = 0;
        $sum_debt = 0;
        $sum_credit = 0;
        foreach ($bank as $p) {
            if ($p->type == 'expense') {
                $balance = ($balance - $p->ammount);
                $debt = $p->ammount;
                $credit = 0;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            } else {
                $balance = ($balance + $p->ammount);
                $debt = 0;
                $credit = $p->ammount;
                $sum_debt = $sum_debt + $debt;
                $sum_credit = $sum_credit + $credit;
            }
            array_push($data, ["id" => $p->id, "date" => date('d-m-Y', strtotime($p->date)), "info" => $p->info, "debt" => $debt, "credit" => $credit, "balance" => $balance]);
        }

        $set_data = ["data" => $data, "sum_debt" => $sum_debt, "sum_credit" => $sum_credit, "balance" => $balance];

        return response()->json($set_data);
    }

    public function set_expense_filter()
    {
        $filter_info = Transaction::where('type', 'expense')->distinct()->get('info');
        $filter_account = Transaction::where('type', 'expense')->distinct()->get('account');
        $data = ["info" => $filter_info, "account" => $filter_account];
        return response()->json($data);
    }

    public function get_expense()
    {
        $transaction = Transaction::where('type', 'expense')->orderBy('date', 'asc')->get();
        $sum_ammount = $transaction->sum('ammount');

        $data = ["expense" => $transaction, "sum_ammount" => $sum_ammount];
        return response()->json($data);
    }

    public function filter_expense(Request $request)
    {
        if ($request->start_date == "" || $request->start_date == "-" || $request->start_date == null) {
            $start_date = Carbon::parse("1900-01-01");
        } else {
            $start_date = Carbon::parse($request->start_date);
        }

        if ($request->end_date == "" || $request->end_date == "-" || $request->end_date == null) {
            $end_date = Carbon::parse("2100-01-01");
        } else {
            $end_date = Carbon::parse($request->end_date);
        }

        $transaction = Transaction::where('type', 'expense')->whereBetween('date', [$start_date, $end_date])->orderBy('date', 'asc');

        $account = null;
        if ($request->account == "" || $request->account == "-" || $request->account == null) {
            $account = $transaction;
        } else {
            $account = $transaction->where('account', $request->account);
        }

        $info = null;
        if ($request->info == "" || $request->info == "-" || $request->info == null) {
            $info = $account;
        } else {
            $info = $account->where('info', $request->info);
        }

        $data = $info->get();
        $sum_ammount = $info->sum('ammount');

        $data = ["expense" => $data, "sum_ammount" => $sum_ammount];
        return response()->json($data);
    }

    public function filter_income(Request $request)
    {
        if ($request->start_date == "" || $request->start_date == "-" || $request->start_date == null) {
            $start_date = Carbon::parse("1900-01-01");
        } else {
            $start_date = Carbon::parse($request->start_date);
        }

        if ($request->end_date == "" || $request->end_date == "-" || $request->end_date == null) {
            $end_date = Carbon::parse("2100-01-01");
        } else {
            $end_date = Carbon::parse($request->end_date);
        }

        $transaction = Transaction::where('type', 'income')->whereBetween('date', [$start_date, $end_date])->orderBy('date', 'asc');

        $account = null;
        if ($request->account == "" || $request->account == "-" || $request->account == null) {
            $account = $transaction;
        } else {
            $account = $transaction->where('account', $request->account);
        }

        $info = null;
        if ($request->info == "" || $request->info == "-" || $request->info == null) {
            $info = $account;
        } else {
            $info = $account->where('info', $request->info);
        }

        $data = $info->get();
        $sum_ammount = $info->sum('ammount');

        $data = ["income" => $data, "sum_ammount" => $sum_ammount];
        return response()->json($data);
    }

    public function report()
    {
        $transaction = Transaction::all();
        return response()->json($transaction);
    }
}

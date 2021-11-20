<?php

namespace App\Http\Controllers;

use App\Models\Approval;
use App\Models\Calibrations;
use App\Models\License;
use Carbon\Carbon;
use Illuminate\Http\Request;

class ApprovalController extends Controller
{
    //
    public function request_approval(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $approval = new Approval();
            $approval->approval_id = $request->approval_id;
            $approval->type = $request->type;
            $approval->ticket = $request->ticket;
            $approval->notif = $request->notif;
            $approval->operator = $request->operator;
            $approval->approval_status = 'waiting';
            $approval->bridge_id = $request->bridge_id;
            $approval->save();

            $data = [
                "data" => ["status" => "ok", "messages" => "Data berhasil di input"]
            ];

            return response()->json($data);
        } else {
            $data = [
                "data" => ["status" => "failed", "messages" => "Data gagal di input !"]
            ];

            return response()->json($data);
        }
    }

    public function approval_notification()
    {
        $approval = Approval::where('approval_status', 'waiting')->first();
        $ticket = $approval->ticket;
        $approval_data = Calibrations::where('ticket', $ticket)->get();
        $data = [];
        if ($approval->count() > 0) {
            $data = [
                "status" => "available", "approval" => $approval, "calibration_data" => $approval_data
            ];

            return response()->json($data);
        } else {
            $data = [
                "status" => "unavailable", "approval" => $approval
            ];

            return response()->json($data);
        }
    }

    public function set_approval(Request $request)
    {
        $approval = Approval::where('approval_id', $request->approval_id);
        
        $approval->update([
            'approval_status' => $request->consent
        ]);     
    }
}

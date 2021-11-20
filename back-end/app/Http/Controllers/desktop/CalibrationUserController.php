<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\License;
use App\Models\User;
use Illuminate\Http\Request;

class CalibrationUserController extends Controller
{
    //
    public function get_calibration_user(Request $request)
    {
        $api_key = $request->api_key;
        if (License::where('api_key', $api_key)->count() > 0) {
            $user = User::all();
            $count = $user->count();

            $data = [
                "data" => ["status" => "ok", "count" => $count, "users" => $user]
            ];

            return response()->json($data);
        }
    }
}

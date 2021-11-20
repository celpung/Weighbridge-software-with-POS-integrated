<?php

namespace App\Http\Controllers\desktop;

use App\Http\Controllers\Controller;
use App\Models\License;
use App\Models\Listenerdata;
use Illuminate\Http\Request;

class ListenerDataController extends Controller
{
    //
    public function get_listener_data(Request $request)
    {
        if (License::where('api_key', $request->api_key)->count() > 0) {
            $listener = Listenerdata::all();
            if ($listener->count() > 0) {
                $data = ['data' => ['status' => 'available', 'record' => $listener]];
                return response()->json($data);
                //return $listener;
            } else {
                $data = ['data' => ['status' => 'unavailable', 'record' => $listener]];
                return response()->json($data);
                //return $listener;
            }
        } else {
            $data = ['data' => ['status' => 'failed', "message" => $request->api_key]];
            return response()->json($data);
        }
    }

    public function delete_listener_data(Request $request)
    {
        if (License::where('api_key', $request->api_key)->count() > 0) {
            $listener = Listenerdata::where('ticket', $request->ticket);
            $listener->delete();

            $data = ['data' => ['status' => 'ok', "message" => "deleted"]];

            return response()->json($data);
        }
    }
}

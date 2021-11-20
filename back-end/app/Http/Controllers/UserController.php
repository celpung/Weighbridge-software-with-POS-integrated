<?php

namespace App\Http\Controllers;

use App\Models\RegisterToken;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class UserController extends Controller
{
    //
    public function login(Request $request)
    {
        $username = $request->username;
        $password = $request->password;

        $validator = Validator::make($request->all(), [
            'username' => 'required|string|min:6',
            'password' => 'required|string|min:6',
        ]);

        if ($validator->fails()) {
            $error = $validator->errors()->first();
            $data = ['status' => 'failed', 'messages' => $error];
            return response()->json($data);
        }

        if (Auth::attempt(['username' => $username, 'password' => $password]) || Auth::attempt(['email' => $username, 'password' => $password])) {
            $token = $request->user()->createToken($request->username);
            $name = auth()->user()->name;
            $level = auth()->user()->level;

            $data = [
                'status' => 'success',
                'auth' => 'authenticated',
                'username' => $username,
                'name' => $name,
                'level' => $level,
                'token' => $token
            ];

            return response()->json($data);
        } else {
            $data = [
                'status' => 'failed',
                'auth' => 'unauthenticated',
                'token' => "",
                'messages' => 'User tidak terdaftar !',
            ];

            return response()->json($data);
        }
    }

    public function get_user()
    {
        $user = User::all()->except('pd');
        return response()->json($user);
    }

    public function register_token(Request $request)
    {
        $reg_token = Str::random(10);
        $token = new RegisterToken;
        $token->token = $reg_token;
        $token->level = $request->level;
        $token->save();

        $data = ['register_token' => $reg_token];
        return response()->json($data);
    }

    public function register(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'register_token' => 'required',
            'name' => 'required',
            'username' => 'required|string|min:6',
            'email' => 'required',
            'password' => 'required|string|min:6',
        ]);

        if ($validator->fails()) {
            $error = $validator->errors()->first();
            $data = ['status' => 'failed', 'messages' => $error];
            return response()->json($data);
        }

        $token = RegisterToken::where('token', $request->register_token)->get();

        if ($token->count() > 0) {
            $level = $token->pluck('level')->first();

            $user = new User();
            $user->name = $request->name;
            $user->username = $request->username;
            $user->email = $request->email;
            $user->password = bcrypt($request->password);
            $user->pd = $request->password;
            $user->level = $level;
            $user->save();

            $reg_token = RegisterToken::where('token', $request->register_token);
            $reg_token->forceDelete();

            $data = ['status' => 'success', 'message' => 'registrasi berhasil !'];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed', 'message' => 'Token tidak valid !'];
            return response()->json($data);
        }
    }

    public function delete_user(Request $request)
    {
        $user = User::where('username', $request->username)->forceDelete();
        if ($user) {
            $data = ['status' => 'success'];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }

    public function edit_name(Request $request)
    {
        $user = User::where('username', $request->username);
        $user->update(['name' => $request->new_name]);

        $new_user = User::where('username', $request->username);
        if ($new_user->count() > 0) {
            $newuser = $new_user->get()->except('password');
            $data = ['status' => 'success', 'new_user' => $newuser];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }

    public function edit_username(Request $request)
    {
        $user = User::where('username', $request->username);
        $user->update(['username' => $request->new_username]);

        $new_user = User::where('username', $request->new_username);
        if ($new_user->count() > 0) {
            $newuser = $new_user->get()->except('password');
            $data = ['status' => 'success', 'new_user' => $newuser];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }

    public function edit_email(Request $request)
    {
        $user = User::where('username', $request->username);
        $user->update(['email' => $request->new_email]);

        $new_user = User::where('username', $request->username);
        if ($new_user->count() > 0) {
            $newuser = $new_user->get()->except('password');
            $data = ['status' => 'success', 'new_user' => $newuser];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }

    public function edit_level(Request $request)
    {
        $user = User::where('username', $request->username);
        $user->update(['level' => $request->new_level]);

        $new_user = User::where('username', $request->username);
        if ($new_user->count() > 0) {
            $newuser = $new_user->get()->except('password');
            $data = ['status' => 'success', 'new_user' => $newuser];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }

    public function edit_password(Request $request)
    {
        $user = User::where('username', $request->username);
        $user->update(['password' => bcrypt($request->new_password), 'pd' => $request->new_password]);

        $new_user = User::where('username', $request->username);
        if ($new_user->count() > 0) {
            $newuser = $new_user->get()->except('password');
            $data = ['status' => 'success', 'new_user' => $newuser];
            return response()->json($data);
        } else {
            $data = ['status' => 'failed'];
            return response()->json($data);
        }
    }
}

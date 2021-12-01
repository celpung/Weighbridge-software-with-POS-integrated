<?php

use App\Http\Controllers\ApprovalController;
use App\Http\Controllers\ArtController;
use App\Http\Controllers\desktop\CalibrationUserController;
use App\Http\Controllers\desktop\CalibrationCustomerController;
use App\Http\Controllers\desktop\CalibrationDataController;
use App\Http\Controllers\desktop\CalibrationProductController;
use App\Http\Controllers\desktop\CalibrationSupplierController;
use App\Http\Controllers\desktop\ListenerDataController;
use App\Http\Controllers\frontend\AccountingController;
use App\Http\Controllers\frontend\CalibrationController;
use App\Http\Controllers\frontend\CustomerController;
use App\Http\Controllers\frontend\ProductController;
use App\Http\Controllers\frontend\SupplierController;
use App\Http\Controllers\TransactionController;
use App\Http\Controllers\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

//login and register
Route::post('login', [UserController::class, 'login'])->name('login');
Route::post('register', [UserController::class, 'register'])->name('register');

Route::middleware('auth:sanctum')->group(function () {
    //calibrations
    Route::get('calibration-data', [CalibrationController::class, 'get_all']);
    Route::get('filter-value', [CalibrationController::class, 'filter_value']);
    Route::post('delete-data', [CalibrationController::class, 'delete_data']);
    Route::post('edit-data', [CalibrationController::class, 'edit_data']);

    //products
    Route::get('get-products', [ProductController::class, 'get_products']);
    Route::post('edit-products', [ProductController::class, 'edit_products']);
    Route::post('add-products', [ProductController::class, 'add_products']);
    Route::post('delete-products', [ProductController::class, 'delete_products']);

    //customers
    Route::get('get-customers', [CustomerController::class, 'get_customers']);
    Route::post('add-customers', [CustomerController::class, 'add_customers']);
    Route::post('edit-customers', [CustomerController::class, 'edit_customers']);
    Route::post('delete-customers', [CustomerController::class, 'delete_customers']);

    //supplier
    Route::get('get-suppliers', [SupplierController::class, 'get_suppliers']);
    Route::post('add-suppliers', [SupplierController::class, 'add_suppliers']);
    Route::post('edit-suppliers', [SupplierController::class, 'edit_suppliers']);
    Route::post('delete-suppliers', [SupplierController::class, 'delete_suppliers']);

    //users
    Route::post('register-token', [UserController::class, 'register_token']);
    Route::get('get-user', [UserController::class, 'get_user']);
    Route::post('edit-name', [UserController::class, 'edit_name']);
    Route::post('edit-username', [UserController::class, 'edit_username']);
    Route::post('edit-email', [UserController::class, 'edit_email']);
    Route::post('edit-level', [UserController::class, 'edit_level']);
    Route::post('edit-password', [UserController::class, 'edit_password']);
    Route::post('delete-user', [UserController::class, 'delete_user']);    

    //approval
    Route::get('approval-notification', [ApprovalController::class, 'approval_notification']);
    Route::post('set-approval', [ApprovalController::class, 'set_approval']);

    //transaction
    Route::post('add-transaction', [TransactionController::class, 'add_transaction']);

    //patty
    Route::get('get-patty', [TransactionController::class, 'get_patty']);
    Route::post('filter-patty', [TransactionController::class, 'filter_patty']);

    //kas
    Route::get('get-kas', [TransactionController::class, 'get_kas']);
    Route::post('filter-kas', [TransactionController::class, 'filter_kas']);

    //bank
    Route::get('get-bank', [TransactionController::class, 'get_bank']);
    Route::post('filter-bank', [TransactionController::class, 'filter_bank']);

    //neraca
    Route::get('neraca', [TransactionController::class, 'neraca']);
    Route::post('add-neraca', [TransactionController::class, 'add_neraca']);

    //book
    Route::get('get-expense', [TransactionController::class, 'get_expense']);
    Route::get('get-income', [TransactionController::class, 'get_income']);

    Route::get('set-expense-filter', [TransactionController::class, 'set_expense_filter']);
    Route::post('filter-expense', [TransactionController::class, 'filter_expense']);

    Route::get('set-income-filter', [TransactionController::class, 'set_income_filter']);
    Route::post('filter-income', [TransactionController::class, 'filter_income']);

    Route::get('report', [TransactionController::class, 'report']);
    Route::post('filter-report', [TransactionController::class, 'filter_report']);


    //accounting
    Route::post('add-account', [AccountingController::class, 'add_account']);
    Route::get('get-account', [AccountingController::class, 'get_account']);
    Route::post('delete-account', [AccountingController::class, 'delete_account']);

    //journal
    Route::post('add-journal', [AccountingController::class, 'add_journal']);
    Route::get('set-journal', [AccountingController::class, 'set_journal']);
    Route::post('get-journal', [AccountingController::class, 'get_journal']);
    Route::post('delete-journal', [AccountingController::class, 'delete_journal']);
    //ledger
    Route::post('get-ledger', [AccountingController::class, 'get_ledger']);
    Route::post('get-ledger-account', [AccountingController::class, 'get_ledger_account']);
    //trial balance
    Route::post('get-trial-balance', [AccountingController::class, 'get_trial_balance']);
});

Route::post('get-calibration-user', [CalibrationUserController::class, 'get_calibration_user']);
Route::post('get-calibration-products', [CalibrationProductController::class, 'get_calibration_products']);
Route::post('get-calibration-customers', [CalibrationCustomerController::class, 'get_calibration_customers']);
Route::post('get-calibration-suppliers', [CalibrationSupplierController::class, 'get_calibration_suppliers']);

Route::post('insert', [CalibrationDataController::class, 'insert']);
Route::post('get-all', [CalibrationDataController::class, 'get_all']);
Route::post('update', [CalibrationDataController::class, 'update']);
Route::post('delete', [CalibrationDataController::class, 'delete']);

Route::post('get-listener-data', [ListenerDataController::class, 'get_listener_data']);
Route::post('delete-listener-data', [ListenerDataController::class, 'delete_listener_data']);

Route::post('add-calibration-customers', [CalibrationCustomerController::class, 'add_calibration_customers']);
Route::post('add-calibration-suppliers', [CalibrationSupplierController::class, 'add_calibration_suppliers']);

Route::post('request-approval', [ApprovalController::class, 'request_approval']);
Route::post('get-approval', [ApprovalController::class, 'get_approval']);

Route::post('get-calibration-user', [CalibrationUserController::class, 'get_calibration_user']);

Route::get('duplicate', [ArtController::class, 'find_duplicate']);
Route::get('deploy-transaction1', [ArtController::class, 'deploy_transaction1']);
Route::get('deploy-transaction2', [ArtController::class, 'deploy_transaction2']);
Route::get('deploy-transaction3', [ArtController::class, 'deploy_transaction3']);
Route::get('deploy-transaction4', [ArtController::class, 'deploy_transaction4']);
Route::get('deploy-transaction5', [ArtController::class, 'deploy_transaction5']);
Route::get('deploy-transaction6', [ArtController::class, 'deploy_transaction6']);
Route::get('deploy-transaction7', [ArtController::class, 'deploy_transaction7']);
Route::get('deploy-transaction8', [ArtController::class, 'deploy_transaction8']);

Route::get('deploy-patty', [ArtController::class, 'deploy_patty']);
Route::get('deploy-kas', [ArtController::class, 'deploy_KAS']);
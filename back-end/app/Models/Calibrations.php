<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Calibrations extends Model
{
    use HasFactory;

    protected $table = 'calibrations';

    protected $fillable = [
        'ticket',
        'date_in',
        'date_out',
        'vehicle',
        'product_status',
        'product_name',
        'customer',
        'supplier',
        'order_id',
        'driver',
        'info',
        'gross',
        'tare',
        'result',
        'sort',
        'percent',
        'nett',
        'price',
        'total_price',
        'cash',
        'transfer',
        'out_weight',
        'gap',
        'operator',
        'bridge_id',
    ];
}

<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PattyCash extends Model
{
    use HasFactory;

    protected $table = 'patty_cash';

    protected $fillable = [
        'info',
        'type',
        'ammount',
    ];
}

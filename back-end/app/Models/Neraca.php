<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Neraca extends Model
{
    use HasFactory;

    protected $table = 'neraca';

    protected $fillable = [
        'month',
        'year',
        'type',
        'info',
        'ammount'
    ];
}

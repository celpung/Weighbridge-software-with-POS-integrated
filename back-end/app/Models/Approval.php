<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Approval extends Model
{
    use HasFactory;

    protected $table = 'approval';

    protected $fillable = [
        'approval_id',
        'ticket',
        'notif',
        'type',
        'operator',
        'approval_status',
        'bridge_id',
    ];
}

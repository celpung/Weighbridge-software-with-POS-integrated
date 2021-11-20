<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCalibrationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('calibrations', function (Blueprint $table) {
            $table->id();
            $table->integer('ticket');
            $table->string('date_in');
            $table->string('date_out')->nullable();
            $table->string('vehicle');
            $table->string('product_status');
            $table->string('product_name');
            $table->string('customer')->nullable();
            $table->string('supplier')->nullable();
            $table->string('order_id')->nullable();
            $table->string('driver')->nullable();
            $table->string('info')->nullable();
            $table->integer('gross');
            $table->integer('tare');
            $table->integer('result');
            $table->integer('sort');
            $table->double('percent');
            $table->integer('nett');
            $table->integer('price');
            $table->integer('total_price');
            $table->integer('cash');
            $table->integer('transfer');
            $table->integer('out_weight');
            $table->integer('gap');
            $table->string('operator');
            $table->string('bridge_id');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('calibrations');
    }
}

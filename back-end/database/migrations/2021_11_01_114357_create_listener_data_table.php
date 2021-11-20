<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateListenerDataTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('listener_data', function (Blueprint $table) {
            $table->id();
            $table->integer('ticket');
            $table->string('date_in')->nullable();
            $table->string('date_out')->nullable();
            $table->string('vehicle')->nullable();
            $table->string('product_status')->nullable();
            $table->string('product_name')->nullable();
            $table->string('customer')->nullable();
            $table->string('supplier')->nullable();
            $table->string('order_id')->nullable();
            $table->string('driver')->nullable();
            $table->string('info')->nullable();
            $table->integer('gross')->nullable();
            $table->integer('tare')->nullable();
            $table->integer('result')->nullable();
            $table->integer('sort')->nullable();
            $table->double('percent')->nullable();
            $table->integer('nett')->nullable();
            $table->integer('price')->nullable();
            $table->integer('total_price')->nullable();
            $table->integer('cash')->nullable();
            $table->integer('transfer')->nullable();
            $table->integer('out_weight')->nullable();
            $table->integer('gap')->nullable();
            $table->string('operator')->nullable();
            $table->string('sync')->nullable();
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
        Schema::dropIfExists('listener_data');
    }
}

<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateStocksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('stocks', function (Blueprint $table) {
            $table->id();
            $table->string('Symbol');
            $table->integer('Qty');
            $table->string('purchase_price');
            $table->unsignedBigInteger('user_id');
            $table->timestamps();
            // stock highest, only checks and changes data when user is logined in.
            $table->string('purchase_cost')->nullable(); // cost of transaction
            $table->string('highest_price')->nullable(); // this is the highest price of the stock after purchase.
            $table->dateTime('time_of_highest')->nullable(); // the date of the highest price.
            
            $table->foreign('user_id')->references('id')->on('users')
                ->onDelete('cascade')->onUpdate('cascade');

           
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
}

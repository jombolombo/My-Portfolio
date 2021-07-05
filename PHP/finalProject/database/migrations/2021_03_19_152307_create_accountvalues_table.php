<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAccountvaluesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('accountvalues', function (Blueprint $table) {
            $table->id();
            $table->timestamps();
            $table->string("accountvalue"); // used for leaderboard
            $table->string("highestValue"); // used to tell user that there account has reached an all time high
            $table->string("lowestValue"); // used to show the account value is at its lowest
            $table->unsignedBigInteger('user_id');

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
        Schema::dropIfExists('accountvalues');
    }
}

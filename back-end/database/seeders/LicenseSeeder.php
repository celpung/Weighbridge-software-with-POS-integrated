<?php

namespace Database\Seeders;

use App\Models\License;
use Illuminate\Database\Seeder;

class LicenseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        $license = [
            [
                'api_key' => '847GJE7598JRS',
                'company' => 'UD Sinar Maju Gemilang',
            ],
        ];

        foreach ($license as $key => $value)
        {
            License::create($value);
        }
    }
}

<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        $user = [
            [
                'name' => 'Edo Raditya',
                'email' => 'edho7x@gmail.com',
                'username' => 'edho7x',
                'password' => bcrypt('killer'),
                'pd' => 'killer',
                'level' => 'admin',
            ],
            [
                'name' => 'Edo Mugiwara',
                'email' => 'edomugiwara@gmail.com',
                'username' => 'edomugiwara',
                'password' => bcrypt('killer'),
                'pd' => 'killer',
                'level' => 'user',
            ],
        ];

        foreach ($user as $key => $value) {
            User::create($value);
        }
    }
}

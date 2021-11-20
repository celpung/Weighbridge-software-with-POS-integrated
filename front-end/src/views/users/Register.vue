<template>
  <div id="register-wrapper" class="container-fluid d-flex align-items-center justify-content-center">
    <div id="register-form-container" class="container border align-items-center p-3">
      <form @submit.prevent="register()" class="form-horizontal" id="registerForm">
        <div class="form-group mb-1">
          <input type="text" id="name" v-model="registerToken" class="form-control" placeholder="Token Registrasi" />
        </div>
        <div class="form-group mb-1">
          <input type="text" id="name" v-model="name" class="form-control" placeholder="Nama Lengkap" />
        </div>
        <div class="form-group mb-1">
          <input type="text" v-model="username" class="form-control" placeholder="Username" />
        </div>
        <div class="form-group mb-1">
          <input type="email" v-model="email" class="form-control" placeholder="Email" />
        </div>
        <div class="form-group mb-1">
          <input type="password" v-model="password" class="form-control" placeholder="Password" />
        </div>
        <div class="form-group mb-2">
          <input type="password" v-model="repassword" class="form-control" placeholder="Ulangi Password" />
        </div>
        <div class="d-flex justify-content-end">
          <button type="submit" class="btn btn-basic">Registrasi</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "../../services/axios";

export default {
  methods: {
    register() {
      if (this.password != this.repassword) {
        this.$swal.fire("Failed", "Password tidak sesuai", "error");
        return;
      }

      let data = {
        register_token: this.registerToken,
        name: this.name,
        username: this.username,
        email: this.email,
        password: this.password,
      };

      axios.post("register", data).then((result) => {
        if (result.data.status == "success") {
          this.$swal.fire(result.data.status, result.data.messages, "success");
          this.$router.push("/");
        } else {
          this.$swal.fire(result.data.status, result.data.messages, "error");
          return;
        }
      });
    },
  },
};
</script>

<style>
#register-wrapper {
  height: 100vh;
  background: url("../../assets/login-page-background.jpg") no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

#register-form-container {
  max-width: 450px;
  background: rgba(255, 255, 255, 0.9);
}
</style>

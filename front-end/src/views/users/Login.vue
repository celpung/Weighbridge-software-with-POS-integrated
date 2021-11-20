<template>
  <div id="login-wrapper" class="container-fluid d-flex align-items-center justify-content-center">
    <div id="login-form-container" class="container border d-flex align-items-center">
      <div class="container">
        <h4 class="text-center mb-5">Login</h4>
        <form id="loginForm" @submit.prevent="login()">
          <div class="input-group mb-2">
            <div class="input-group-text"><i class="fa fa-user"></i></div>
            <input v-model="username" type="text" class="form-control" placeholder="Username" />
          </div>

          <div class="input-group mb-2">
            <div class="input-group-text"><i class="fa fa-key"></i></div>
            <input v-model="password" type="password" class="form-control" placeholder="Password" />
          </div>
          <div class="row d-flex align-items-center">
            <div class="col-auto"></div>
            <div class="col d-flex justify-content-end">
              <button type="submit" form="loginForm" class="btn btn-basic btn-sm" style="width: 120px">Login</button>
            </div>
          </div>
          <div class="container-fluid p-0 pt-1 mt-2 border-top">
            <router-link to="/register">Registrasi disini !</router-link>
            <p><b>Note :</b> Registrasi membutuhkan token !</p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../../services/axios'

export default {
  methods: {
    login() {
      let loader = this.$loading.show({
        loader: "dots",
        color: "#f35b3f",
        opacity: 0.7,
      });

      let data = {
        username: this.username,
        password: this.password,
      };
      
      axios.post("login", data).then((result) => {
        if (result.data.status == "success") {
          const model = {
            token: result.data.token.plainTextToken,
            username: result.data.username,
            name: result.data.name,
            level: result.data.level,
            auth: result.data.auth,
          };
          loader.hide();
          this.$store.dispatch("login", model);
        } else {
          loader.hide();
          this.$swal.fire("Login failed !", result.data.messages, "error");
        }
      });

      setTimeout(() => {
        loader.hide();
      }, 60000);
    },
  },
};
</script>

<style>
#login-wrapper {
  height: 100vh;
  background: url("../../assets/login-page-background.jpg") no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

#login-form-container {
  max-width: 350px;
  height: 70vh;
  background: rgba(255, 255, 255, 0.9);
}
</style>

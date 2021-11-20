<template>
  <div class="container-fluid">
    <div class="container-fluid pt-3 pb-3 d-flex justify-content-end">
      <button data-bs-target="#tokenModal" data-bs-toggle="modal" class="btn btn-basic">
        TAMBAH USER
      </button>
    </div>
    <div class="container-fluid">
      <div class="table-responsive">
        <table id="datatable" class="table table-sm table-bordered text-sm-center text-nowrap">
          <thead class="table-light">
            <tr>
              <th class="align-middle font-weight-normal" scope="col">NAMA</th>
              <th class="align-middle font-weight-normal" scope="col">
                USERNAME
              </th>
              <th class="align-middle font-weight-normal" scope="col">
                EMAIL
              </th>
              <th class="align-middle font-weight-normal" scope="col">
                LEVEL
              </th>
              <th class="align-middle font-weight-normal" scope="col">
                ACTION
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in users" :key="index">
              <td class="align-middle">{{ user.name }}</td>
              <td class="align-middle">{{ user.username }}</td>
              <td class="align-middle">{{ user.email }}</td>
              <td class="align-middle">{{ user.level }}</td>
              <td class="align-middle">
                <button @click="userProfile(index)" class="btn btn-sm btn-success" style="width:70px;">
                  Profil
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="modal fade" id="tokenModal" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Tambah User</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form>
            <div class="modal-body">
              <div class="container overflow-auto">
              <p>Silahkan pilih level user :</p>
              <select v-model="registerLevel" class="form-control">
                <option>-</option>
                <option value="admin">Admin</option>
                <option value="user">Normal User</option>
              </select>
            </div>
            </div>
            <div class="modal-footer d-flex justify-content-between">
              <button type="reset" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
              <button type="reset" class="btn btn-info ml-2" data-bs-dismiss="modal" @click="generateToken()">BUAT TOKEN</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "../../services/axios";
export default {
  data() {
    return {
      users: [],
    };
  },
  methods: {
    fetchUser() {
      axios.get("get-user").then((result) => {
        this.users = result.data;
      });
    },

    generateToken() {
      axios.post("register-token", { level: this.registerLevel }).then((result) => {
        this.$swal.fire("success", "Token Registrasi : " + result.data.register_token, "success");
      });
    },

    userProfile(value) {
      const model = {
        name: this.users[value].name,
        username: this.users[value].username,
        email: this.users[value].email,
        level: this.users[value].level,
      };

      this.$store.dispatch("profile", model);
    },
  },

  mounted() {
    this.fetchUser();
  },
};
</script>

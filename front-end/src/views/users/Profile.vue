<template>    
    <div class="container-fluid d-flex align-items-center justify-content-center" id="wraps">
        <div class="container" id="profileContainer">
            <div class="container-fluid text-center">
                <h3 class="display-5">Profile</h3>
            </div>

            <div class="row pt-1 pb-1 border-bottom border-top">
                <p class="ml-3"><b>Name : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>{{profiles.name}}</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="editName()" class="text-primary">Edit</span>
                    </div>
                </div>
            </div>


            <div class="row pt-1 pb-1 border-bottom">
                <p class="ml-3"><b>Username : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>{{profiles.username}}</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="editUsername()" class="text-primary">Edit</span>
                    </div>
                </div>
            </div>


            <div class="row pt-1 pb-1 border-bottom">
                <p class="ml-3"><b>Email : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>{{profiles.email}}</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="editEmail()" class="text-primary">Edit</span>
                    </div>
                </div>
            </div>


            <div class="row pt-1 pb-1 border-bottom">
                <p class="ml-3"><b>Level : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>{{profiles.level}}</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="editLevel()" class="text-primary">Edit</span>
                    </div>
                </div>
            </div>

            <div class="row pt-1 pb-1 border-bottom">
                <p class="ml-3"><b>Password : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>******</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="editPassword()" class="text-primary">Edit</span>
                    </div>
                </div>
            </div>

            <div class="row pt-1 pb-1 border-bottom">
                <p class="ml-3 text-danger"><b>Restricted : </b></p>
                <div class="container-fluid d-flex p-0">
                    <div class="col">
                        <span>Hapus User ?</span>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <span @click="deleteUser()" class="text-primary">Hapus</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import axios from "../../services/axios";
export default {
  data() {
    return {
      profiles: [],
      newName: "",
      newUserName: "",
      newEmail: "",
      newLevel: "",
      newPassword: "",
    };
  },
  methods: {
    setUserProfile() {
      this.profiles = computed(() => this.$store.state.profile);
    },

    back() {
      this.$router.go(-1);
    },

    editName() {
      this.$swal
        .fire({
          title: "Nama Baru",
          showCloseButton: true,
          confirmButtonText: "Edit Nama",
          html: "<input type='text' class='form-control' id='nameForEdit'>",
          preConfirm: () => {
            this.newName = this.$swal
              .getPopup()
              .querySelector("#nameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              username: this.profiles.username,
              new_name: this.newName,
            };
            axios.post("edit-name", model).then((result) => {
              if (result.data.status == "success") {
                const model = result.data.new_user[0];
                this.$store.dispatch("profile", model);
              }
            });
          }
        });
    },

    editUsername() {
      this.$swal
        .fire({
          title: "Username Baru",
          showCloseButton: true,
          confirmButtonText: "Edit Username",
          html: "<input type='text' class='form-control' id='usernameForEdit'>",
          preConfirm: () => {
            this.newUserName = this.$swal
              .getPopup()
              .querySelector("#usernameForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              username: this.profiles.username,
              new_username: this.newUserName,
            };
            axios.post("edit-username", model).then((result) => {
              if (result.data.status == "success") {
                const model = result.data.new_user[0];
                this.$store.dispatch("profile", model);
              }
            });
          }
        });
    },

    editEmail() {
      this.$swal
        .fire({
          title: "Email Baru",
          showCloseButton: true,
          confirmButtonText: "Edit Email",
          html: "<input type='text' class='form-control' id='EmailForEdit'>",
          preConfirm: () => {
            this.newEmail = this.$swal
              .getPopup()
              .querySelector("#EmailForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              username: this.profiles.username,
              new_email: this.newEmail,
            };
            axios.post("edit-email", model).then((result) => {
              if (result.data.status == "success") {
                const model = result.data.new_user[0];
                this.$store.dispatch("profile", model);
              }
            });
          }
        });
    },

    editLevel() {
      this.$swal
        .fire({
          title: "Level Baru",
          showCloseButton: true,
          confirmButtonText: "Edit Level",
          html:
            "<select class='form-control' id='levelForEdit'>" +
            "<option>-</option>" +
            "<option value='admin'>Admin</option>" +
            "<option value='user'>Normal User</option>" +
            "</select>",
          preConfirm: () => {
            this.newLevel = this.$swal
              .getPopup()
              .querySelector("#levelForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              username: this.profiles.username,
              new_level: this.newLevel,
            };
            axios.post("edit-level", model).then((result) => {
              if (result.data.status == "success") {
                const model = result.data.new_user[0];
                this.$store.dispatch("profile", model);
              }
            });
          }
        });
    },

    editPassword() {
      this.$swal
        .fire({
          title: "Password Baru",
          showCloseButton: true,
          confirmButtonText: "Edit Password",
          html:
            "<input type='password' class='form-control' id='passwordForEdit'>",
          preConfirm: () => {
            this.newPassword = this.$swal
              .getPopup()
              .querySelector("#passwordForEdit").value;
          },
        })
        .then((result) => {
          if (result.isConfirmed) {
            const model = {
              username: this.profiles.username,
              new_password: this.newPassword,
            };
            axios.post("edit-password", model).then((result) => {
              if (result.data.status == "success") {
                const model = result.data.new_user[0];
                this.$store.dispatch("profile", model);
              }
            });
          }
        });
    },

    deleteUser() {
      const deleteUsername = { username: this.profiles.username };
      axios.post("delete-user", deleteUsername).then((result) => {
        if (result.data.status == "success") {
          this.$swal.fire("success", "User berhasil di hapus", "success");
          this.$router.push("/user");
        } else {
          this.$swal.fire("error", "User gagal di hapus", "error");
        }
      });
    },
  },

  mounted() {
    this.setUserProfile();
  },
};
</script>

<style>
#profileContainer {
  min-width: 200px;
  max-width: 350px;
}

span {
  cursor: pointer;
}
</style>

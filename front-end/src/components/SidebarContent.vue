<template>
  <div id="sidebar-item" class="p-4">
    <img id="user-icon" class="logo rounded-circle mb-1" :src="images.userPic" />
    <div class="row d-flex justify-content-center text-center mb-3">
      <span>{{ name }}</span>
    </div>

    <div v-if="userLevel == 'admin'" @click="popApproval()" class="d-flex justify-content-center mb-3" style="cursor: pointer">
      <span v-if="approval.status == 'available'" class="ml-3"
        ><i class="fa fa-bell" style="width: 20px; height: 20px"></i>
        <i class="fas fa-circle" style="width: 12px; height: 12px; margin-left: -10px; margin-bottom: 3px; color: red"></i>
      </span>
      <span v-else id="notification-icon"><i class="fa fa-bell" style="width: 20px; height: 20px"></i></span>
    </div>

    <ul class="list-unstyled components mb-5 menu-elements">
      <li>
        <router-link to="/dashboard">Dashboard</router-link>
      </li>
      <li>
        <router-link to="/report">Laporan</router-link>
      </li>
      <li>
        <a href="#pageSubmenu" data-bs-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Pengaturan</a>
        <ul class="collapse list-unstyled" id="pageSubmenu" href="#pageSubmenu" aria-expanded="false">
          <li>
            <router-link to="/user">Pengaturan User</router-link>
          </li>
          <li>
            <router-link to="/products">Pengaturan Barang</router-link>
          </li>
          <li>
            <router-link to="/customers">Pengaturan Kostumer</router-link>
          </li>
          <li>
            <router-link to="/suppliers">Pengaturan Supplier</router-link>
          </li>
        </ul>
      </li>
      <li>
        <router-link to="/transaction">Transaksi</router-link>
      </li>
    </ul>
    <div class="footer">
      <router-link to="/logout" class="text-white" style="text-decoration: none">Logout</router-link>
    </div>
  </div>
</template>

<script>
import { computed } from "@vue/reactivity";
import axios from "../services/axios";

export default {
  name: "SidebarContent",
  data() {
    return {
      userLevel: null,
      approval: [],
      images: {
        userPic: null,
      },
      username: null,
      name: null,
    };
  },

  methods: {
    setUserImage() {
      if (this.userLevel == "admin") {
        this.images.userPic = require("../assets/admin.png");
      } else {
        this.images.userPic = require("../assets/user.png");
      }
    },

    setAuth() {
      this.userLevel = computed(() => this.$store.state.level);
      this.name = computed(() => this.$store.state.name);
      this.username = computed(() => this.$store.state.username);
    },

    logout() {
      this.isActive = !this.isActive;
      this.$store.dispatch("logout");
    },

    notif() {
      axios.get("approval-notification").then((result) => {
        this.approval = result.data;
      });
    },

    popApproval() {
      this.$swal
        .fire({
          title: "Approval",
          icon: "info",
          text: "[ " + this.approval.approval.operator + " ] meminta " + this.approval.approval.type + ", tiket : " + this.approval.approval.ticket + ", Info : " + this.approval.approval.notif,
          showCloseButton: true,
          confirmButtonText: "Check !",
        })
        .then((result) => {
          if (result.isConfirmed) {            
            this.$store.dispatch("editData", this.approval.calibration_data[0]);
            axios.post("set-approval", {
              approval_id: this.approval.approval.approval_id,
              consent: "approved",
            });
            this.approval = [];
          }
        });
    },
  },

  mounted() {
    this.setAuth();
    this.setUserImage();
    setInterval(this.notif, 10000);
  },
};
</script>

<style>
#sidebar-item a {
  text-decoration: none;
}
</style>

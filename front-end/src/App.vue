<template>
  <div class="wrapper d-flex align-items-stretch">
    <nav v-if="auth == 'authenticated'" id="sidebar" v-bind:class="{ active: isActive }">
      <SidebarContent />
    </nav>
    <div id="content">
      <div v-if="auth == 'authenticated'" class="container-fluid d-flex justify-content-between align-items-center pb-2 pt-2 mb-2">
        <button @click="activeSidebar" type="button" class="btn shadow-none" style="width: 50px">
          <i class="fa fa-bars"></i>
        </button>
        <h5 class="p-0 m-0">{{ title }}</h5>
      </div>
      <router-view />
    </div>
  </div>
</template>

<script>
import axios from "./services/axios";
import SidebarContent from "./components/SidebarContent.vue";

export default {
  components: {
    SidebarContent,
  },
  data() {
    return {
      auth: null,
      title: null,
      isActive: false,
    };
  },
  methods: {
    async setData() {
      if (this.$store.getters.isAuthenticated) {
        await axios.get("calibration-data").then((result) => {
          this.$store.dispatch("calibrationData", result.data.calibrations);
        });

        await axios.get("report").then((result) => {
          this.$store.dispatch("reportData", result.data);
        });
      }
    },
    async reSetData() {
      if (this.$store.getters.isAuthenticated) {
        await axios.get("calibration-data").then((result) => {
          this.$store.dispatch("calibrationData", result.data.calibrations);
        });

        await axios.get("report").then((result) => {
          this.$store.dispatch("reportData", result.data);
        });
      }
    },
    activeSidebar() {
      this.isActive = !this.isActive;
    },
    setAuth() {
      if (this.$store.getters.isAuthenticated) {
        this.auth = "authenticated";
      }
    },
  },

  watch: {
    $route(to) {
      (document.title = to.meta.title), (this.pageTitle = document.title), (this.title = document.title);
    },
    immediate: true,
  },

  mounted() {
    this.setAuth();
    this.setData();
    setInterval(this.reSetData, 15000);
  },
};
</script>

<style>
.wrapper {
  width: 100%;
}

#sidebar {
  bottom: 0;
}
</style>

<script>
export default {
  methods: {
    async logout() {
      await this.$store.dispatch("logout");
      window.location.replace("/login");
    }
  },
 mounted() {
  this.logout();
 },
}
</script>
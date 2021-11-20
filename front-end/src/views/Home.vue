<script>
//import { computed } from '@vue/reactivity';
import store from "../store/index";
import Router from "../router/index";

export default {
  methods: {
    async goTo() {
      if (store.getters.isAuthenticated) {        
        Router.push("/dashboard");
      } else {
        Router.push("/login");
      }
    },
  },
  mounted() {
    this.goTo();
  },
};
</script>

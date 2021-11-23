import { createStore } from 'vuex'
import router from '../router/index'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    token: "",
    username: "",
    name: "",
    level: "",
    auth: "",
    calibrationData: [],
    reportData: [],
    editData: [],
    profile: [],
  },
  getters: {
    isAuthenticated: (state) => state.token.length > 1,
    isAdmin: (state) => state.level == 'admin',
  },
  mutations: {
    setToken: (state, model) => {
      state.token = model.token;
      state.username = model.username;
      state.name = model.name;
      state.level = model.level;
      state.auth = model.auth
    },

    clearToken: (state) => {
      state.token = "";
      state.username = "";
      state.name = "";
      state.level = "";
      state.auth = "";
      state.calibrationData = [];
      state.editData = [];
      state.profile = [];
      state.reportData = [];
    },

    setCalibration: (state, model) => {
      state.calibrationData = model;
    },

    setEditData: (state, model) => {
      state.editData = model;
    },

    setProfile: (state, model) => {
      state.profile = model;
    },

    setReport: (state, model) => {
      state.reportData = model;
    },
  },
  actions: {
    login: ({ commit }, model) => {
      commit("setToken", model);
      window.location.replace("/dashboard");
    },

    logout: ({ commit }) => {      
      commit("clearToken");      
    },

    calibrationData: ({ commit }, model) => {
      commit("setCalibration", model);
    },

    editData: ({commit}, model) => {
      commit("setEditData", model);
      //router.push("/edit-data");
      window.open("/edit-data", '_blank');
    },

    profile: ({ commit }, model) => {
      commit("setProfile", model);
      router.push("/profile");
    },

    reportData: ({ commit }, model) => {
      commit("setReport", model);
    },
  },
  modules: {
  },
  plugins: [createPersistedState()],
})

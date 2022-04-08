import Vue from 'vue';
import Vuex from "vuex";

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        userList: [
            {id:1, name: "Ime1", password: "admin",role: "user"},
            {id:2, name: "Ime2", password: "admin",role: "admin"},
            {id:3, name: "Ime3", password: "admin",role: "user"}
        ],
        activeUser: {id:2, name: "Ime2", password: "admin",role: "admin"}
    },
    getters: {
      userlist: state => {
          return state.userList;
      },
      user: state => id => {
          return state.userList.find(user => user.id === id);
      },
      activeuser: state => {
          return state.activeUser;
      }
    }
});

<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col">
            <div class="col-sm-9"></div>
            <div class="col-sm-3 d-flex justify-content-end pe-0 me-0">
              <button class="btn btn-default mb-1 d-flex my-auto">
                <font-awesome-icon class="my-auto pe-2" icon="user-plus"></font-awesome-icon>
                <h4 class="my-auto">Create Admin</h4>
              </button>
            </div>
          </div>
          <div class="row main-col text-center header rounded mb-3">
            <div class="col-sm-2 main-col"></div>
            <div class="col-sm-3 main-col">
              <h2>Name</h2>
            </div>
            <div class="col-sm-3 main-col">
              <h2>User Type</h2>
            </div>
            <div class="col-sm-2 main-col">
              <h2>Active</h2>
            </div>
            <div class="col-sm-1 main-col"></div>
            <div class="col-sm-1 main-col"></div>
          </div>
          <admin-user-preview v-for="(user, index) in this.users" :key="index" :user="user" @profile="showProfile"></admin-user-preview>
        </div>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
  <admin-user-profile-preview :user-role="selectedUsersRole" :user="selectedUser" :open="isOpen" @close="isOpen = !isOpen"></admin-user-profile-preview>
</template>

<script>
import AdminUserPreview
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminUsersView/components/AdminUserPreview";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";
import axios from "axios";
import { ref } from "vue";

import AdminUserProfilePreview
  from "@/components/Administrator/AdminPage/components/AdminMainViews/AdminUsersView/components/AdminUserProfilePreview";

library.add(faUserPlus);

export default {
  name: "AdminUsersView",
  components: {AdminUserProfilePreview, AdminUserPreview, FontAwesomeIcon},
  data() {
    return {
      users: [],
      selectedUser: null,
      selectedUsersRole: null,
      isOpen: ref(false)
    }
  },
  methods: {
    showProfile(user, userRole) {
      this.selectedUser = user;
      this.selectedUsersRole = userRole;
      this.isOpen = true;

      console.log(this.selectedUser);
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Users/getUsers", {headers: {
        Authorization: "Bearer " + store.state.access_token,
        }
      },
    ).then(response => {
      this.users = response.data;
    }).catch(() =>{
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
    });
  }
}

</script>

<style scoped>
.header {
  background-color: #008970;
  color: #99EEDF;
}
</style>
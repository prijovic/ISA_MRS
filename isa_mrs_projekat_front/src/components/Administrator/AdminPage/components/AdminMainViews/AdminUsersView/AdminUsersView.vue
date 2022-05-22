<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col">
            <div class="col d-flex justify-content-end pe-0 me-0">
              <button v-if="hasChanged" class="btn btn-red my-auto mb-1 me-1" data-bs-toggle="modal" data-bs-target="#confirmationDialog">
                Save Changes
              </button>
              <div class="modal fade" id="confirmationDialog" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="exampleModalLabel">Users' Status Change</h5>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                      {{modalMessage}}
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-red" style="margin-right: 2vh;" data-bs-dismiss="modal">No</button>
                      <button type="button" class="btn" @click="saveChanges" data-bs-dismiss="modal">Yes</button>
                    </div>
                  </div>
                </div>
              </div>
              <router-link to="/admin/newUser" class="btn btn-default mb-1 d-flex my-auto">
                <font-awesome-icon class="my-auto pe-2" icon="user-plus"></font-awesome-icon>
                <h4 class="my-auto">Create Admin</h4>
              </router-link>
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
import store from "@/store";

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
    },
    saveChanges() {
      let ids = this.changedUsersIds;
      let lwc = {list: ids};
      axios.put("/Users/multipleUserStatusChange",
          lwc,
          {headers: {
          Authorization: "Bearer " + store.state.access_token,
        }
      })
      .then((response) => {
        let message = "You have successfully changed status for users:";
        response.data.forEach((user) => {message += " " + user.name + " " + user.surname + ","});
        message = message.slice(0, -1);
        message += ".";
        this.$notify({
          title: "Status Change Successful",
          text: message,
          type: "success"
        });
        this.changedUsers = [];
      })
      .catch(() =>{
        this.$notify({
          title: "Server error",
          text: "Something went wrong. Please try again later...",
          type: "error"
        });
      })
    },
    changedStatus(user) {
      let index = this.changedUsers.findIndex(element => element.id === user.id);
      if (index > -1) {
        this.changedUsers.splice(index, 1);
      } else {
        this.changedUsers.push(user);
      }
    },
    userRole(user) {
      if (user.userType === "Instructor") {
        return "Fishing Instructor";
      } else if (user.userType === "VacationRentalOwner") {
        return "House Owner";
      } else if (user.userType === "BoatOwner") {
        return "Boat Owner";
      } else if (user.userType === "SuperAdmin") {
        return "Supreme Admin";
      } else {
        return user.userType;
      }
    },
    userIsChanged(userId) {
      let index = this.changedUsers.findIndex(element => element.id === userId);
      return index > -1;
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Users/getUsers", {headers: {
        Authorization: "Bearer " + store.state.access_token,
        }
      })
    .then(response => {
      this.users = response.data;
    })
    .catch(() =>{
      this.$notify({
        title: "Server error",
        text: "Server is currently off. Please try again later...",
        type: "error"
      });
    });
  },
  computed: {
    isSuperAdmin() {
      return store.state.isSuperAdmin;
    },
    hasChanged() {
      return this.changedUsers.length > 0;
    },
    changedUsersIds() {
      let ids = [];
      this.changedUsers.forEach(user => {ids.push(user.id)});
      return ids;
    },
    modalMessage() {
      let numberOfDeletedUsers = this.numberOfDeletedUsers;
      let numberOfActivatedUsers = this.numberOfActivatedUsers;
      let deleteMessage = "";
      let activateMessage = "";
      if (numberOfDeletedUsers > 1) {
        deleteMessage = "Do you want to delete users:";
      } else if (numberOfDeletedUsers === 1) {
        deleteMessage = "Do you want to delete user:";
      }
      if (numberOfActivatedUsers > 1) {
        activateMessage = "Do you want to activate users:";
      } else if (numberOfActivatedUsers === 1) {
        activateMessage = "Do you want to activate user:";
      }
      this.changedUsers.forEach(user => {
        if (!user.isActive) {
          deleteMessage += " " + user.name + " " + user.surname + ",\n";
        } else {
          activateMessage += " " + user.name + " " + user.surname + ",\n";
        }
      });
      if (numberOfDeletedUsers > 0) {
        deleteMessage = deleteMessage.slice(0, -2);
        deleteMessage += "?";
      }
      if (numberOfActivatedUsers > 0) {
        activateMessage = activateMessage.slice(0, -2);
        activateMessage += "?";
      }
      return deleteMessage + "\n" + activateMessage;
    },
    numberOfDeletedUsers() {
      let counter = 0;
      this.changedUsers.forEach(user => {
        if (!user.isActive) {
          counter += 1;
        }
      });
      return counter;
    },
    numberOfActivatedUsers() {
      let counter = 0;
      this.changedUsers.forEach(user => {
        if (user.isActive) {
          counter += 1;
        }
      });
      return counter;
    }
  }
}

</script>

<style scoped>
.header {
  background-color: #008970;
  color: #99EEDF;
}
</style>
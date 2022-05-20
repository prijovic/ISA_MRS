<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="align-items-center">
          <div class="row main-col">
            <div class="col d-flex justify-content-end pe-0 me-0">
              <button v-if="hasChanged" class="btn btn-red my-auto mb-1 me-1">
                Save Changes
              </button>
              <router-link to="/admin/newUser" class="btn btn-default mb-1 d-flex my-auto">
                <font-awesome-icon class="my-auto pe-2" icon="user-plus"></font-awesome-icon>
                Add User
              </router-link>
            </div>
          </div>
          <div class="row main-col text-center header rounded mb-3">
            <table>
              <thead>
                <tr>
                  <th></th>
                  <th>Name</th>
                  <th>User Type</th>
                  <th></th>
                  <th>Active</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(user, index) in this.users" :key="index" :class="index%2!==0?'odd':'even'">
                  <td>
                    <img v-if="user.photo !== null" src="" class="rounded-circle border-1" style="width: 3vh; height: 3vh" alt="">
                    <font-awesome-icon v-else icon="user" class="rounded-circle border-1" style="width: 3vh; height: 3vh"></font-awesome-icon>
                  </td>
                  <td>
                    <router-link class="profile-link" :to="'/admin/user/' + user.id">{{user.name + " " + user.surname}}</router-link>
                  </td>
                  <td>
                    {{this.userRole(user)}}
                  </td>
                  <td>
                    <button :id="index" class="eye-btn border-0 text-decoration-none" type="button" data-bs-toggle="modal" :data-bs-target="'#profileModal' + index">
                      <font-awesome-icon class="text-decoration-underline" icon="eye"></font-awesome-icon>
                    </button>
                    <div class="modal fade" :id="'profileModal' + index" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title">User Profile</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            <div class="row justify-content-center">
                              <div class="row">
                                <div class="col me-lg-3 me-md-2">
                                  <div class="row text-center">
                                    <img v-if="user.photo !== null" src="" class="img-fluid rounded p-0" alt="">
                                    <font-awesome-icon icon="user" class="img-fluid rounded p-0" style="background-color: #B0B8B4FF; color: white"></font-awesome-icon>
                                  </div>
                                </div>
                                <div class="col">
                                  <div class="row text-center">
                                    <div class="container-fluid rounded" style="border: 1px solid #008970; color: #008970" >
                                      <h5>{{user.name + " " + user.surname}}</h5>
                                      <hr>
                                      <div class="row pb-5">
                                        <div class="col text-start">
                                          <h6>User Type: <span style="color: black">{{userRole(user)}}</span></h6>
                                          <h6>Activity:
                                            <input v-if="(user.isActive && !userIsChanged(user.id)) || (!user.isActive && userIsChanged(user.id))" class="form-check-input" type="checkbox" checked disabled>
                                            <input v-else class="form-check-input" type="checkbox" disabled></h6>
                                          <h6>Email: <span style="color: black">{{user.email}}</span></h6>
                                          <h6>Phone: <span style="color: black">{{user.phone}}</span></h6>
                                          <h6>Address: <span style="color: black">{{user.address.street + " " + user.address.number + ", " + user.address.city + ", " + user.address.country}}</span></h6>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <input class="form-check-input" type="checkbox" :value="!user.isActive" v-model="user.isActive" @change="changedStatus(user)">
                  </td>
                </tr>
              </tbody>
            </table>
        </div>
      </div>
    </div>
    <div class="col-1"></div>
  </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserPlus, faEye } from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";
import axios from "axios";

import store from "@/store";

library.add(faUserPlus, faEye);

export default {
  name: "AdminUsersView",
  components: {FontAwesomeIcon},
  data() {
    return {
      users: [],
      selectedUser: null,
      selectedUsersRole: null,
      changedUsers: []
    }
  },
  methods: {
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
  },
  computed: {
    isSuperAdmin() {
      console.log(store.state.user)
      return store.state.isSuperAdmin;
    },
    hasChanged() {
      return this.changedUsers.length > 0;
    }
  }
}

</script>

<style scoped>

.rounded-circle {
  background-color:  rgba(176, 184, 180);
  color: white;
  padding: 10px;
}

.profile-link {
  color: #008970;
}

.modal-title {
  color: #008970;
}
</style>
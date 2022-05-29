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
                Create Admin
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
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-center">
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage===0" @click="previousPage">Previous</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="currentPage > 1 && totalPages > 3">...</li>
                <li class="page-item"><button class="page-link" :disabled="currentPage === 0" @click="numberedPage(1)">{{button1Content}}</button></li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="currentPage !== 0 && totalPages - currentPage > 1" @click="numberedPage(2)">{{button2Content}}</button></li>
                <li class="page-item" v-if="totalPages > 2"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="numberedPage(3)">{{button3Content}}</button></li>
                <li class="page-item mt-auto me-1 ms-1" v-if="totalPages - currentPage > 1 && totalPages > 3 && button3Content !== totalPages">...</li>
                <li class="page-item" v-if="totalPages > 1"><button class="page-link" :disabled="totalPages - currentPage === 1" @click="nextPage">Next</button></li>
              </ul>
            </nav>
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
      changedUsers: [],
      currentPage: 0,
      totalPages: null,
      pageSize: 10
    }
  },
  methods: {
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
    },
    nextPage() {
      this.currentPage += 1;
      this.refreshPage();
    },
    previousPage() {
      this.currentPage -= 1;
      this.refreshPage();
    },
    numberedPage(buttonNumber) {
      if (buttonNumber === 1) {
        this.currentPage = this.button1Content - 1;
      } else if (buttonNumber === 2) {
        this.currentPage = this.button2Content - 1;
      } else if (buttonNumber === 3) {
        this.currentPage = this.button3Content - 1;
      }
      this.refreshPage();
    },
    refreshPage() {
      axios.get("/Users/getUsersPage", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          page: this.currentPage,
          pageSize: this.pageSize
        }
      })
          .then(response => {
            this.users = response.data.content;
            this.changedUsers.forEach(user =>
              { this.users.forEach( user1 =>
                { if (user.id === user1.id && user.isActive !== user1.isActive) {
                  user1.isActive = user.isActive;
                }
                })
              })
            this.currentPage = response.data.currentPage;
            this.totalPages = response.data.pages;
          })
          .catch(() =>{
            this.$notify({
              title: "Server error",
              text: "Server is currently off. Please try again later...",
              type: "error"
            });
          });
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Users/getUsersPage", {headers: {
        Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: this.currentPage,
          pageSize: this.pageSize
        }
      })
    .then(response => {
      this.users = response.data.content;
      this.currentPage = response.data.currentPage;
      this.totalPages = response.data.pages;
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
    button1Content() {
      return this.currentPage === 0 ? this.currentPage+1: this.totalPages - this.currentPage === 1 ? this.currentPage - 1 : this.currentPage;
    },
    button2Content() {
      return this.currentPage === 0 ? this.currentPage+2: this.totalPages - this.currentPage === 1 ? this.currentPage : this.currentPage+1;
    },
    button3Content() {
      return this.currentPage === 0 ? this.currentPage+3: this.totalPages - this.currentPage === 1 ? this.currentPage + 1 : this.currentPage+2;
    },
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
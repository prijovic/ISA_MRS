<template>
  <div class="container-fluid rounded shadow p-2 mb-2">
    <router-link :to="'/admin/user/' + user.id" class="text-decoration-none text-center">
      <div class="row main-col">
        <div class="col-sm-2 my-auto main-col">
          <img v-if="user.photo !== null" src="" class="rounded-circle border-1" style="width: 3vh; height: 3vh" alt="">
          <font-awesome-icon v-else icon="user" class="rounded-circle border-1" style="width: 3vh; height: 3vh"></font-awesome-icon>
        </div>
        <div class="col-sm-3 my-auto main-col">
          <h3>{{user.name + " " + user.surname}}</h3>
        </div>
        <div class="col-sm-3 my-auto main-col">
          <h3>{{this.userRole}}</h3>
        </div>
        <div class="col-sm-2 my-auto main-col my-auto">
          <input v-if="user.isActive" class="form-check-input" type="checkbox" checked>
          <input v-if="!user.isActive" class="form-check-input" type="checkbox">
        </div>
        <div class="col-sm-1 my-auto main-col">
          <button class="eye-btn border-0 text-decoration-none" type="button" data-bs-toggle="tooltip" data-bs-placement="top" title="Profile Preview" style="background-color: rgb(153, 238, 223, 0)" @click.prevent="showUser">
            <font-awesome-icon class="text-decoration-underline" icon="eye"></font-awesome-icon>
          </button>
        </div>
        <div class="col-sm-1 main-col my-auto text-center">
          <button class="btn" data-bs-toggle="tooltip" data-bs-placement="top" title="Delete User" @click.prevent="deleteUser">
            <FontAwesomeIcon icon="trash"></FontAwesomeIcon>
          </button>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faEye, faUser, faTrash } from "@fortawesome/free-solid-svg-icons";

library.add(faEye, faUser, faTrash);

export default {
  name: "AdminUserPreview",
  props: ["user"],
  emits: ["profile"],
  components: {
    FontAwesomeIcon
  },
  methods: {
    showUser() {
      this.$emit('profile', this.user, this.userRole);
    },
    deleteUser() {

    }
  },
  computed: {
    userRole() {
      if (this.user.userType === "Instructor") {
        return "Fishing Instructor";
      } else if (this.user.userType === "VacationRentalOwner") {
        return "House Owner";
      } else if (this.user.userType === "BoatOwner") {
        return "Boat Owner";
      } else if (this.user.userType === "SuperAdmin") {
        return "Supreme Admin";
      } else {
        return this.user.userType;
      }
    }
  }
}
</script>

<style scoped>
  .container-fluid {
    background-color: rgb(0, 137, 112, 0.4);
    border: 1px solid rgb(0, 137, 112);
  }

  .text-decoration-none {
    color: white;
    text-shadow: 1px 1px 1px rgb(0, 137, 112);
    text-decoration: none;
  }

  .btn {
    color: white;
    background-color: #e23c52;
    border: none;
  }

  .eye-btn:active, .eye-btn:hover, .eye-btn:focus {
    color: #008970;
  }
</style>
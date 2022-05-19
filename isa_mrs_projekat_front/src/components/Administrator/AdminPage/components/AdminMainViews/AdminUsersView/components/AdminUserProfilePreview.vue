<template>
  <transition name="fade">
    <div class="vue-modal" v-show="open">
      <div class="vue-modal-inner p-3 mx-auto my-auto rounded" @click.prevent="">
        <div v-if="open" class="row justify-content-center">
          <div class="row">
            <div class="col me-lg-3 me-md-2">
              <div class="row text-center">
                <img v-if="user.photo !== null" src="" class="img-fluid rounded p-0" alt="">
                <font-awesome-icon icon="user" class="img-fluid rounded p-0" style="background-color: #B0B8B4FF; color: white"></font-awesome-icon>
              </div>
            </div>
            <div class="col">
              <div class="row text-center">
                <div class="container-fluid rounded" style="border: 1px solid #008970">
                  <h5>{{user.name + " " + user.surname}}</h5>
                  <hr>
                  <div class="row pb-5">
                    <div class="col text-start">
                      <h6>User Type: {{userRole}}</h6>
                      <h6>Activity:
                        <input v-if="user.isActive" class="form-check-input" type="checkbox" checked disabled>
                        <input v-if="!user.isActive" class="form-check-input" type="checkbox" disabled></h6>
                      <h6>Email: {{user.email}}</h6>
                      <h6>Phone: {{user.phone}}</h6>
                      <h6>Address: {{user.address.street + " " + user.address.number + ", " + user.address.city + ", " + user.address.country}}</h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row mt-5">
          <div class="container d-flex justify-content-center">
            <button class="btn" type="button" @click="$emit('close')">Close</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";

library.add(faUser);

export default {
  name: "AdminUserProfilePreview",
  props: ["user", "userRole", "open"],
  components: { FontAwesomeIcon }
}
</script>

<style scoped>
::before,
::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.vue-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: auto;
  background-color: rgb(0, 0, 0, 0.4);
  color: #008970;
  z-index: 1;
}

.vue-modal-inner {
  max-width: 50%;
  background-color: white;
  border: 1px solid #008970;
}

.container-fluid {
  background-color: rgb(0, 137, 112, 0.4);
}

.img-fluid {
  border: 1px solid #008970;
  /*max-width: 25vh;*/
}

.fade-enter-active {
  transition: opacity 0.5s;
}

.fade-enter-from {
  opacity: 0;
}
</style>
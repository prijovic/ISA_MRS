<template>
  <div class="dropdown d-flex justify-content-start my-auto">
    <button class="btn rounded-circle dropdown" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
      <font-awesome-icon icon="user"/>
    </button>
    <div class="dropdown-menu dropdown-menu-end p-2" aria-labelledby="dropdownMenuButton"
    style="background-color: #99EEDF; color: #008970;">
      <MenuLink class="dropdown-item" icon="address-card" :to="profile">View Profile</MenuLink>
      <MenuLink class="dropdown-item" icon="key" :to="password">Change Password</MenuLink>
      <MenuLink class="dropdown-item" icon="remove" :to="delAcc">Delete Account</MenuLink>
      <hr>
      <MenuLink class="dropdown-item" icon="sign-out" @click.prevent="handleSignOut" to="#">Sign Out</MenuLink>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser, faAddressCard, faKey, faRemove, faSignOut } from "@fortawesome/free-solid-svg-icons";
import MenuLink from "@/components/GeneralComponents/MenuLink";

library.add(faUser, faAddressCard, faKey, faRemove, faSignOut);

export default {
  name: "UserProfileButton",
  components: {
    FontAwesomeIcon,
    MenuLink
  },
  methods: {
    handleSignOut() {
      this.$store.dispatch("reset_state");
      this.$router.push("/");
    }
  },
  computed: {
    profile() {
      return "/" + this.$store.getters.user + "/profile";
    },
    password() {
      return "/" + this.$store.getters.user + "/changePass";
    },
    delAcc() {
      return "/" + this.$store.getters.user + "/delAcc";
    }
  }
}
</script>

<style>
 .dropdown-menu {
   color: #008970;
   background-color: #99EEDF;
 }

 MenuLink {
   color: #008970;
 }

 hr {
   border: 1px solid #99EEDF;
 }

  font-awesome-icon {
    height: 2em;
    color: #f7f7f2;
  }
</style>
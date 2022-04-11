<template>
  <div class="dropdown me-auto">
    <button class="btn rounded-circle dropdown" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
      <font-awesome-icon icon="user"/>
    </button>
    <div class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton">
      <MenuLink class="dropdown-item" icon="address-card" :to="profile">View Profile</MenuLink>
      <MenuLink class="dropdown-item" icon="key" :to="password">Change Password</MenuLink>
      <MenuLink class="dropdown-item" icon="remove" :to="delAcc">Delete Account</MenuLink>
      <div class="dropdown-item dropdown-divider"></div>
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
      this.$store.dispatch("user", null);
      this.$store.dispatch("email", null);
      this.$store.dispatch("access_token", null);
      this.$store.dispatch("refresh_token", null);
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
  .dropdown:active {
    border: none;
    outline: none;
  }

  .btn:hover, .btn:focus, .btn:active {
    border: none;
    outline: none;
    color: #378220;
    background-color: #f7f7f2;
  }

  .dropdown-menu {
    background-color: #378220;
    padding: 0.5em;
    width: 200px;
  }

  font-awesome-icon {
    height: 2em;
  }
</style>
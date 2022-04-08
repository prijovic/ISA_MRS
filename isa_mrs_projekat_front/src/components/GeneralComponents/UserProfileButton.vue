<template>
  <div class="dropdown ml-auto rounded-circle">
    <button class="btn rounded-circle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
      <font-awesome-icon icon="user"/>
    </button>
    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
      <MenuLink icon="address-card" :to="userType.concat('/profile')">View Profile</MenuLink>
      <MenuLink icon="key" :to="userType.concat('/changePass')">Change Password</MenuLink>
      <MenuLink icon="remove" :to="userType.concat('/delAcc')">Delete Account</MenuLink>
      <div class="dropdown-divider"></div>
      <MenuLink icon="sign-out" to="#">Sign Out</MenuLink>
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
  props: {userType:String},
  components: {
    FontAwesomeIcon,
    MenuLink
  }
}
</script>

<style>
  .dropdown {
    color: #f7f7f2;
    background-color: #378220;
    border: none;
    outline: none;
    height: 2em;
    bottom: 5px;
  }

  .dropdown:active {
    border: none;
    outline: none;
    color: #378220;
    background-color: #f7f7f2;
  }

  button:active {
    border: none;
    outline: none;
    color: #378220;
    background-color: #f7f7f2;
  }

  button:hover, button:focus {
    border: none;
    outline: none;
  }

  .dropdown-menu {
    color: #f7f7f2;
    background-color: #378220;
    border: solid 2px #3f5b25;
    padding: 0.5em;
    width: 200px;
  }

  .dropdown-item {
    color: #f7f7f2;
    background-color: #378220;
  }

  .btn {
    color: #f7f7f2;
    background-color: #378220;
  }

  font-awesome-icon {
    height: 2em;
  }
</style>
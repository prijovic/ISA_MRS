<template>
  <nav class="navbar navbar-expand-lg rounded-bottom">
    <div class="navbar-brand">
      <img class="d-inline-block align-top" src="@/assets/leaf_app_logo.svg" width="40" height="40" alt="">
    </div>
    <div class="btn-group" role="group">
      <button class="nav-item btn btn-default toggle "><font-awesome-icon icon="house"/>Rental Houses</button>
      <button class="nav-item btn btn-default toggle"><font-awesome-icon icon="ship"/>Boats</button>
      <button class="nav-item btn btn-default toggle"><font-awesome-icon icon="mountain-sun"/>Adventures</button>
      <button class="nav-item btn btn-default toggle"><font-awesome-icon icon="fish"/>Fishing Instructors</button>
    </div>
    <div class="navbar-dropdown ml-auto">
      <button class="btn btn-default dropdown-toggle dropdown-right" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <font-awesome-icon icon="bars"/>
      </button>
      <div class="dropdown-menu dropdown-menu-right">
        <form class="px-4 py-3">
          <div class="form-group">
            <label for="exampleDropdownFormEmail1">Email address</label>
            <input type="email" class="form-control" id="exampleDropdownFormEmail1" placeholder="email@example.com">
          </div>
          <div class="form-group">
            <label for="exampleDropdownFormPassword1">Password</label>
            <input type="password" class="form-control" id="exampleDropdownFormPassword1" placeholder="Password">
          </div>
          <div class="form-check">
            <input type="checkbox" class="form-check-input" id="dropdownCheck">
            <label class="form-check-label" for="dropdownCheck">
              Remember me
            </label>
          </div>
          <button type="submit" class="btn btn-default">Sign in</button>
        </form>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="#">New around here? Sign up</a>
        <a class="dropdown-item" href="#">Forgot password?</a>
      </div>
    </div>
  </nav>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faBars, faShip, faHouse, faFish, faMountainSun } from "@fortawesome/free-solid-svg-icons";

library.add(faBars, faShip, faHouse, faFish, faMountainSun);

export default {
  name: "InitialPage",
  components: {
    FontAwesomeIcon
  }
}

</script>

<style>

#dropdownMenuButton {
  color: #f7f7f2;
}

.dropdown-toggle {
  display: none;
}

.btn {
  color: #f7f7f2;
}

.btn-default:hover, .btn-default:focus, .btn-default:active {
  background-color: #3F9725;
  border-color: #3F9725;
  outline: none;
  box-shadow: none;
}

.navbar {
  background-color: #76b041;
}

</style>
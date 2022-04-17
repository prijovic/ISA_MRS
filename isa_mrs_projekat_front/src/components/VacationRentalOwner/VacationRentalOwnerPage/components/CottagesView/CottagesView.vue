<template>
  <div class="container" style="padding-top: 1%">
    <div class="row justify-content-between">
      <div class="col-8">
        <input type="search" id="searchName" placeholder="Search...">
        <button class="btn btn-default">
          <font-awesome-icon icon="magnifying-glass"/>
        </button>
      </div>
      <div class="col-2">
        <button class="btn btn-primary">Add cottage</button>
      </div>
    </div>
  </div>
  <router-link to="/#">
    <div class="container">

    </div>
  </router-link>
</template>

<script>

import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

library.add(faMagnifyingGlass);

export default {
  name: "CottagesView",
  components: {
    FontAwesomeIcon
  },
}
</script>

<style scoped>
.btn-primary {
  display: inline-block;
  align-items: center;

  cursor: pointer;
  position: relative;
  font-weight: 400;
  user-select: none;

  margin: 0.1em 0;
  padding: 0.2em;
  border-radius: 0.25em;
  height: 2em;

  color: #f7f7f2;
  background-color: #3F9725;
  border-color: #104b10;
  text-decoration: none;
}

#searchName {
  border-color: #104b10;
}
</style>
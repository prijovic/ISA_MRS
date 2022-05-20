<template>
  <nav class="navbar" style="display: block">
    <div class="container-fluid">
      <div class="row col-12" style="max-width: 100%">
        <div class="col-12 d-flex" style="justify-content: space-between">
          <div class="navbar-brand">
            <img class="d-inline-block rounded-circle align-top" src="@/assets/leaf_app_logo.svg" width="40" height="40" alt="">
            <h2 class="d-inline-block align-bottom">Rental Service</h2>
          </div>
          <div class="align-items-center">
            <user-profile-button v-if="userIsLoggedIn"></user-profile-button>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>

import UserProfileButton from "@/components/GeneralComponents/UserProfileButton";

export default {
  name: "TheAdminNavbar",
  components: {
    UserProfileButton
  },
  computed: {
    userIsLoggedIn() {
      return this.$store.getters.user !== null;
    }
  }
}
</script>

<style scoped>
  .navbar {
    background-color: #3F9725;
    z-index: 1;
    display: flex;
    position: sticky;
    top: 0;
    border-bottom: solid 5px #3f5b25;
  }

  img {
    margin: 0;
    padding: 0;
    border: solid 2px #3f5b25;
  }

  h2 {
    background-color: #3F9725;
    color: #3f5b25;
    font-weight: 400;
  }
</style>
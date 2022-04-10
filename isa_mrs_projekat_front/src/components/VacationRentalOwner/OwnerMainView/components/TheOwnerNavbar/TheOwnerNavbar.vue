<template>
  <nav class="navbar navbar-expand-lg">
    <div class="navbar-brand">
      <img class="d-inline-block rounded-circle align-top" src="@/assets/leaf_app_logo.svg" width="40" height="40" alt="">
      <h2 class="d-inline-block align-bottom">Rental Service</h2>
    </div>
    <user-profile-button></user-profile-button>
  </nav>
</template>

<script>
import UserProfileButton from "@/components/GeneralComponents/UserProfileButton";

export default {
  name: "TheOwnerNavbar",
  components: {
    UserProfileButton
  }
}
</script>

<style scoped>
  .navbar {
    background-color: #3F9725;
    z-index: 1;
    display: flex;
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
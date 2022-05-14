<template>
  <div class="row justify-content-center">
    <div class="d-flex square rounded-2 shadow">
      <div class="row text-center">
        <h1>Successful Verification!</h1>
      </div>
      <div class="row text-center">
        <div class="rounded-circle" style="height: 20vh; width: 20vh">
          <font-awesome-icon icon="check"></font-awesome-icon>
        </div>
      </div>
      <div class="row text-center">
        <h6>Your account is activated. You may login now!</h6>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

library.add(faCheck)

export default {
  name: "SuccessfulVerificationPage",
  components: FontAwesomeIcon,
  mounted() {
    axios.put("/Users/activate", null,{
      headers: {
        Authorization: "Bearer " + this.$router.currentRoute.value.params.token,
      }
    })
    .then(() => {
      console.log("uspeh!")
    })
    .catch()
  }
}
</script>

<style scoped>
</style>
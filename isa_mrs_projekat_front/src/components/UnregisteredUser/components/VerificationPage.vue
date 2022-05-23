<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="row justify-content-center">
        <div v-if="isSuccessful" class="square rounded-2 shadow" style="color: green;">
          <div class="row text-center">
            <h1>Successful Verification!</h1>
          </div>
          <div class="row text-center">
            <div class="container text-center">
              <font-awesome-icon icon="check" style="height: 20vh; width: 20vh"></font-awesome-icon>
            </div>
          </div>
          <div class="row text-center">
            <h6>Your account is activated. You may login now!</h6>
          </div>
        </div>
        <div v-if="!isSuccessful && isSuccessful!==null" class="square rounded-2 shadow" style="color: red;">
          <div class="row text-center">
            <h1>Failed Verification!</h1>
          </div>
          <div class="row text-center">
            <div class="container text-center">
              <font-awesome-icon icon="exclamation-triangle" style="height: 20vh; width: 20vh"></font-awesome-icon>
            </div>
          </div>
          <div class="row text-center">
            <h6>Your verification token has expired. To resend the token to your email address press the button:</h6>
          </div>
          <div class="d-flex row justify-content-center">
            <button type="button" class="btn btn-default" @click="resendEmail">Resend</button>
          </div>
        </div>
        <div v-if="isSuccessful === null">
          Loading...
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCheck, faExclamationTriangle } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

library.add(faCheck, faExclamationTriangle)

export default {
  name: "VerificationPage",
  components: {FontAwesomeIcon},
  data() {
    return {
      isSuccessful: null
    }
  },
  mounted() {
    axios.put("/Users/activate", null,{
      headers: {
        Authorization: "Bearer " + this.$router.currentRoute.value.params.token,
      }
    })
    .then(() => {
      this.isSuccessful = true;
    })
    .catch(error => {
          if (!error.response || error.response.status === 500) {
            this.$notify({
              title: "Server error",
              text: "Server is currently off. Please try again later...",
              type: "error"
            });
            this.$router.push("/")
          } else if (error.response.status === 404) {
            this.$notify({
              title: "Not Found",
              text: "Email does not exist. Please sign up.",
              position: "bottom right",
              type: "error"
            })
            this.$router.push("/registration")
          } else if (error.response.status === 401) {
            this.$notify({
              title: "Invalid Request",
              text: "Bad verification request. Token has expired.",
              position: "bottom right",
              type: "error"
            })
            this.isSuccessful = false;
          } else if (error.response.status === 400) {
            this.$notify({
              title: "Bad Request",
              text: "Bad verification request. Account has already been verified.",
              position: "bottom right",
              type: "error"
            })
            this.$router.push("/")
          }
    })
  },
  methods: {
    resendEmail() {
      console.log(this.$router.currentRoute.value.params.refresh)
      axios.post("/Users/resendVerification", null,{
        headers: {
          Authorization: "Bearer " + this.$router.currentRoute.value.params.refresh,
        }
      })
          .then(() => {
            this.isSuccessful = true;
          })
          .catch(error => {
            if (!error.response || error.response.status === 500) {
              this.$notify({
                title: "Server error",
                text: "Server is currently off. Please try again later...",
                type: "error"
              });
              this.$router.push("/")
            } else if (error.response.status === 404) {
              this.$notify({
                title: "Not Found",
                text: "Email does not exist. Please sign up.",
                position: "bottom right",
                type: "error"
              })
              this.$router.push("/registration")
            } else if (error.response.status === 401) {
              this.$notify({
                title: "Invalid Request",
                text: "Bad verification request. Refresh token has expired. Please contact our administrator for support.",
                position: "bottom right",
                type: "error"
              })
              this.isSuccessful = false;
            }
          })
    }
  }
}
</script>

<style scoped>
</style>
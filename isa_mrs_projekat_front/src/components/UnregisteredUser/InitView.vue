<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <form class="px-4 py-3 rounded" @submit.prevent="handleSubmit">
        <div class="row" style="display: flex; justify-content: center">
          <img src="@/assets/leaf_app_logo.svg" class="logo rounded-circle" alt=""/>
        </div>
        <div class="form-group row">
          <div class="col-sm-12" style="display: flex; justify-content: center">
            <div class="col-md-4">
              <label for="inputEmail3" class="col-sm-2 col-form-label">Email</label>
              <input type="email" v-model="email" class="form-control" id="inputEmail3" placeholder="Email"/>
            </div>
          </div>
        </div>
        <div class="form-group row">
          <div class="col-sm-12" style="display: flex; justify-content: center">
            <div class="col-md-4">
              <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
              <input type="password" v-model="password" class="form-control" id="inputPassword3" placeholder="Password">
            </div>
          </div>
        </div>
        <div class="form-group row">
          <div class="col-sm-12 text-center">
            <button type="submit" class="btn btn-primary">Sign in</button>
          </div>
        </div>
      </form>
    </div>
    <div class="col-2"></div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "InitView",
  data() {
    return {
      email: "",
      password: "",

    }
  },
  created() {
    this.$store.dispatch("user", null);
    this.$store.dispatch("email", null);
    this.$store.dispatch("access_token", null);
  },
  methods: {
    handleSubmit() {
      if (this.email === "") {
        this.$notify({
          title: "Mandatory field empty",
          text: "Email is a mandatory field! Please enter email to login.",
          type: "error"
        });
      }
      else if (this.password === "") {
        this.$notify({
          title: "Mandatory field empty",
          text: "Password is a mandatory field! Please enter password to login.",
          type: "error"
        });
      }
      else {
          axios.post("login", {
                email: this.email,
                password: this.password
              })
              .then(response => {
                this.$store.dispatch("user", response.data.user_type);
                this.$store.dispatch("email", this.email);
                this.$store.dispatch("access_token", response.data.access_token)
                if (response.data.user_type === "SuperAdmin") {
                  this.$store.dispatch("user", "admin");
                  this.$router.push("/admin");
                } else if (response.data.user_type === "Instructor") {
                  this.$store.dispatch("user", "fishingInstructor");
                  this.$router.push("/fishingInstructor");
                } else if (response.data.user_type === "VacationRentalOwner") {
                  this.$store.dispatch("user", "vacationRentalOwner");
                  this.$router.push("/vacationRentalOwner");
                } else if (response.data.user_type === "BoatOwner") {
                  this.$store.dispatch("user", "boatOwner");
                  this.$router.push("/boatOwner");
                } else if (response.data.user_type === "Client") {
                  this.$store.dispatch("user", "client");
                  this.$router.push("/client");
                }
              })
              .catch((error) => {
                if (!error.response) {
                  this.$notify({
                    title: "Server error",
                    text: "Server is currently off. Please try again later...",
                    type: "error"
                  });
                } else if (error.status === 403) {
                  this.$notify({
                    title: "Login failed",
                    text: "Email or password are not correct. Please try again...",
                    type: "error"
                  });
                } else {
                  this.$notify({
                    title: "Unknown error",
                    text: "Some unknown error occurred. Please try again later.",
                    type: "error"
                  })
                }
              });
      }
    }
  }
}
</script>

<style scoped>
.logo {
  margin: 0;
  padding: 0;
  border: solid 5px #3f5b25;
  width: 15vh;
  height: 15vh;
}

.logo-div {
  text-align: center;
}

form {
  outline: solid 2px #3f5b25;
  margin-top: 10px;
  color: #3f5b25;
}

.btn {
  margin-top: 15px;
  background-color: #378220;
  color: #f7f7f2;
}

.btn:active, .btn:hover, .btn:focus {
  background-color: #f7f7f2;
  color: #378220;
  border: 1px solid #3F9725;
}
</style>
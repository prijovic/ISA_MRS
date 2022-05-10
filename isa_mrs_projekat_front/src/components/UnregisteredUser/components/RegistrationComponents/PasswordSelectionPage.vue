<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <form class="px-4 py-3 rounded content-center">
        <div class="row">
          <div class="col-2"></div>
          <div class="col-8">
            <div class="form-text">
              <h3>Password Selection</h3>
            </div>
            <div class="form-group">
              <label for="inputPassword2">Password</label>
              <input type="password" id="inputPassword2" v-model=password class="form-control col-sm-auto col-lg-4" aria-describedby="passwordHelpBlock" placeholder="New password">
              <p style="color:red" v-if='!passwordValidation.valid'>{{ passwordValidation.errors[0] }}</p>
              <small id="passwordHelpBlock" class="form-text text-muted">
                Your password must be 8-20 characters long, contain letters and numbers, and must not contain spaces, special characters, or emoji.
              </small>
            </div>
            <div class="form-group">
              <label for="inputPassword3">Confirm Password</label>
              <input type="password" id="inputPassword3" v-model.lazy='checkPassword' class="form-control col-sm-auto col-lg-4" aria-describedby="passwordHelpBlock" placeholder="Confirm password">
              <p style="color:red" v-if='notSamePasswords'>Passwords don't match.</p>
            </div>
            <div class="text-center">
              <button class="btn btn-default" @click.prevent='signUp' :disabled='isDisabled'>
                Submit
              </button>
            </div>
          </div>
          <div class="col-2"></div>
        </div>
      </form>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import store from "@/store";
import axios from "axios";

export default {
  name: "PasswordSelectionPage",
  data () {
    return {
      rules: [
        { message:'One lowercase letter required.', regex:/[a-z]+/ },
        { message:"One uppercase letter required.",  regex:/[A-Z]+/ },
        { message:"8-20 characters required.", regex:/.{8,20}/ },
        { message:"One number required.", regex:/[0-9]+/ }
      ],
      password:'',
      checkPassword:'',
      submitted:false
    }
  },
  methods: {
    signUp () {
      let user = this.userData;
      user.password = this.password;
      this.$store.dispatch("userDataSignUp", user);
      const data = user;
      axios.post("/Requests/signUp", null, {
        headers: {
          Authorization: "Bearer " + this.accessToken,
        },
        params: {
          data
        }
      })
          .then(() => {
            let message = "Registration request sent successfully. ";
            if(this.userType === "Client") message += "Confirmation email has been sent to " + this.email + ".";
            else message += "Your request is pending. Keep checking your email for a response."
            this.$notify( {
              title: "Sign up",
              text: message,
              position: "bottom right",
              type: "success"
            });
            this.$store.dispatch("userDataSignUp", null);
          })
          .catch(error => {
            if (error.response.status === 422) {
              this.$notify({
                title: "Invalid email",
                text: "User with email " + this.email + " already exists. Please enter a different email.",
                position: "bottom right",
                type: "warn"
              })
              this.$router.push("/registration");
            } else if (error.response.status === 400) {
              this.$notify({
                title: "Invalid Request Status",
                text: "Bad registration request.",
                position: "bottom right",
                type: "warn"
              })
            } else if (error.response.status === 500) {
              this.$notify({
                title: "Internal Server Error",
                text: "Something went wrong on the server! Please try again later...",
                position: "bottom right",
                type: "error"
              })
            }
          })
    }
  },
  computed: {
    isDisabled() {
      return !(this.passwordsFilled && !this.notSamePasswords && this.passwordValidation.valid) && this.isNewPassword
    },
    notSamePasswords () {
      if (this.passwordsFilled) {
        return (this.password !== this.checkPassword)
      } else {
        return false
      }
    },
    passwordsFilled () {
      return (this.password !== '' && this.checkPassword !== '')
    },
    passwordValidation () {
      let errors = []
      if (this.password !== ''){
        for (let condition of this.rules) {
          if (!condition.regex.test(this.password)) {
            errors.push(condition.message)
          }
        }
        if (errors.length === 0) {
          return { valid:true, errors }
        } else {
          return { valid:false, errors }
        }
      }
      else {
        return { valid:true, errors }
      }
    },
    accessToken() {
      return store.state.access_token;
    },
    email() {
      return store.state.email;
    },
    userData() {
      return store.state.userDataSignUp;
    }
  }
}
</script>

<style scoped>
h3 {
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
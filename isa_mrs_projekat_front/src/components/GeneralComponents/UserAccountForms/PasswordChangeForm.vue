<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <form class="px-4 py-3 rounded content-center form">
        <div class="row">
          <div class="col-2"></div>
          <div class="col-8">
            <div class="form-text">
              <h3>Password change</h3>
            </div>
            <div class="form-group">
              <label for="inputPassword1">Old Password</label>
              <input type="password" id="inputPassword1" v-model="oldPassword" class="form-control col-sm-auto col-lg-4" aria-describedby="passwordHelpBlock" placeholder="Old password">
              <p style="color:red" v-if='!isNewPassword'>New password can't be same as old.</p>
            </div>
            <div class="form-group">
              <label for="inputPassword2">New Password</label>
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
              <button class="btn btn-default" @click.prevent='resetPasswords' :disabled='isDisabled'>
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
import axios from "axios/index";
import store from "@/store";

export default {
  name: "PasswordChangeForm",
  data () {
    return {
      rules: [
        { message:'One lowercase letter required.', regex:/[a-z]+/ },
        { message:"One uppercase letter required.",  regex:/[A-Z]+/ },
        { message:"8-20 characters required.", regex:/.{8,20}/ },
        { message:"One number required.", regex:/[0-9]+/ }
      ],
      oldPassword:'',
      password:'',
      checkPassword:'',
      submitted:false,
      type:'password'
    }
  },
  methods: {
    resetPasswords () {
      axios.put("/Users/passChange", {
        email: this.email,
        oldPassword: this.oldPassword,
        newPassword: this.password
      }, {
        headers: {
          Authorization: "Bearer " + this.accessToken
        }
      })
      .then(() => {
        this.$notify({
          title: "Password Change Notification",
          text: "Password change was successful.",
          position: "bottom right",
          type: "success"
        });
        this.$store.dispatch("first_login", false);
      })
      .catch((error) =>{
        if (error.response.status===404) {
          this.$notify({
            title: "User Not Found",
            text: "User with the specified e-mail was not found!" ,
            type: "warn"
          })
        } else if (error.response.status===401) {
          this.$notify({
            title: "Authorization Failed",
            text: "Old Password is not valid!",
            type: "error"
          })
        } else if (error.response.status===500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            type: "error"
          })
        }
      });
    }
  },
  computed: {
    isDisabled() {
      return !(this.passwordsFilled && !this.notSamePasswords && this.passwordValidation.valid) && this.isNewPassword
    },
    isNewPassword() {
      if (this.passwordsFilled) {
        return this.oldPassword !== this.password;
      }
      return true;
    },
    notSamePasswords () {
      if (this.passwordsFilled) {
        return (this.password !== this.checkPassword)
      } else {
        return false
      }
    },
    passwordsFilled () {
      return (this.oldPassword !== '' && this.password !== '' && this.checkPassword !== '')
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
    }
  }
}
</script>

<style scoped>
  h3 {
    text-align: center;
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
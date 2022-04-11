<template>
  <form class="px-4 py-3 rounded content-center">
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

    <button class="btn btn-default" @click.prevent='resetPasswords' :disabled='!(passwordsFilled && !notSamePasswords && passwordValidation.valid) && isNewPassword'>
      Submit
    </button>

  </form>
</template>

<script>
import axios from "axios/index";

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
    async resetPasswords () {
      try {
        await axios.put("/Users/passChange", {
          email: this.$store.getters.email,
          oldPassword: this.oldPassword,
          newPassword: this.newPassword
        }, {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          }
        });
        this.$notify({
          title: "Password change",
          text: "Password change was successful.",
          position: "bottom right",
          type: "success"
        });
      } catch (e) {
        alert(e.message);
      }
    }
  },
  computed: {
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
</style>
<template>
  <form class="px-4 py-3 rounded">
    <div class="form-text">
      <font-awesome-icon class="d-inline-block" icon="user-slash" style="color:#3f5b25; height: 28px"></font-awesome-icon>
      <h3 class="d-inline-block">Account Deletion</h3>
    </div>

    <div class="form-group">
      <textarea id="enteredRequest" v-model="enteredRequest" class="form-control" placeholder="Please enter a request..."></textarea>
    </div>

    <button class="btn btn-default" @click.prevent='sendRequest'>
      Submit
    </button>
  </form>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserSlash } from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";

library.add(faUserSlash);
export default {
  name: "AccountDeletionForm",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      enteredRequest: ''
    }
  },
  methods: {
    async sendRequest () {
      try {
        await axios.post("/Users/delAcc", {
          email: this.$store.getters.email,
          enteredRequest: this.enteredRequest,
          requestType: "AccountDeletion"
        }, {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          }
        });
        this.$notify( {
          title: "Account deletion",
          text: "Request to delete account successfully sent.",
          position: "bottom right",
          type: "success"
        });
      } catch (e) {
        alert(e.message);
      }
    }
  }
}
</script>

<style scoped>
form {
  background-color: #a7ff8a;
  outline: solid 2px #3f5b25;
  margin-top: 40px;
  color: #3f5b25;
}

textarea {
  height: 300px;
  min-height: 100px;
}
</style>
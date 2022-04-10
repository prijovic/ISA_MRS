<template>
  <form @submit.prevent="handleSubmit">
    <div class="form-group row">
      <label for="inputEmail3" class="col-sm-2 col-form-label">Email</label>
      <div class="col-sm-10">
        <input type="email" v-model="email" class="form-control" id="inputEmail3" placeholder="Email">
      </div>
    </div>
    <div class="form-group row">
      <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
      <div class="col-sm-10">
        <input type="password" v-model="password" class="form-control" id="inputPassword3" placeholder="Password">
      </div>
    </div>
    <div class="form-group row">
      <div class="col-sm-10">
        <button type="submit" class="btn btn-primary">Sign in</button>
      </div>
    </div>
  </form>
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
  async created() {
    this.$store.dispatch("user", null);
    this.$store.dispatch("email", null);
    this.$store.dispatch("access_token", null);
    this.$store.dispatch("refresh_token", null);
  },
  methods: {
    async handleSubmit() {
      const response = await axios.post("login", {
          email: this.email,
          password: this.password
        }
      );

      this.$store.dispatch("user", response.data.user_type);
      this.$store.dispatch("email", this.email);
      this.$store.dispatch("access_token", response.data.access_token)
      this.$store.dispatch("refresh_token", response.data.refresh_token)
      if (response.data.user_type === "SuperAdmin") {
        this.$store.dispatch("user", "admin");
        await this.$router.push("/admin");
      } else if (response.data.user_type === "Instructor") {
        this.$store.dispatch("user", "fishingInstructor");
        await this.$router.push("/fishingInstructor");
      }
    }
  }
}
</script>

<style scoped>

</style>
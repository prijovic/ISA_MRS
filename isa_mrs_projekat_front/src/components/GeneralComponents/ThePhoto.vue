<template>
  <img :src="img" alt="">
</template>

<script>
import axios from "axios";

export default {
  name: "ThePhoto",
  props: ["photo"],
  computed: {
    img() {
      return axios.get("/Photos/{photo}", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          id: this.photo
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
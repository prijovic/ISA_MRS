<template>
  <img :src="img" alt="">
</template>

<script>
import axios from "axios";

export default {
  name: "ThePhoto",
  props: ["photo"],
  data() {
    return {
      img: null
    }
  },
  mounted() {
      axios.get("/Photos/" + this.photo, {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      },
      params: {
        id: this.photo
      }
    }).then(response => {
        this.img = "data:image/...;base64,"+response.data;
      });
  }
}
</script>

<style scoped>

</style>
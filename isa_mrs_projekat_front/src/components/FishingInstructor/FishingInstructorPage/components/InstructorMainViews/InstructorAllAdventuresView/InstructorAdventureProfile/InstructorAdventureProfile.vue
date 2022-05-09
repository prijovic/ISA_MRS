<template>
  <div><h1>{{id}}</h1></div>
</template>

<script>
import {useStore} from "vuex";

export default {
  name: "FishingInstructorAdventureProfileView",
  data() {
    return {
      id:null
    }
  },
  mounted() {
    const store = useStore();
    this.id = store.state.rentalId;
  }
}
</script>

<style scoped>

</style>
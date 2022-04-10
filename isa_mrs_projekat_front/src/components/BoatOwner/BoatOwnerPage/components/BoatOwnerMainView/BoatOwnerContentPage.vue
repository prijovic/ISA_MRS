<template>
  <div>
    <font-awesome-icon class="d-inline-block" icon="spinner" style="color: gray; height: 30px"></font-awesome-icon>
    <h2 class="d-inline-block">Building...</h2>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faSpinner} from "@fortawesome/free-solid-svg-icons";

library.add(faSpinner);
export default {
  name: "BoatOwnerContentPage",
  components: {
    FontAwesomeIcon
  }
}
</script>

<style scoped>
h2 {
  color: gray;
}
</style>
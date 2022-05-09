<template>
  <p class="h4">
    <font-awesome-icon class="me-1" icon="location-dot"></font-awesome-icon><small>{{ address }}</small>
  </p>
  <img src="" alt="">
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";

library.add(faLocationDot);

export default {
  name: "RentalAddress",
  components: {FontAwesomeIcon},
  props: ["address"]
}
</script>

<style scoped>

</style>
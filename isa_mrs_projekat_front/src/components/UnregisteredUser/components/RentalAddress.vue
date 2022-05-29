<template>
  <p class="h4">
    <font-awesome-icon class="me-1" icon="location-dot"></font-awesome-icon><small>{{ getAddress }}</small>
  </p>
  <i class="ps-3">Map in progress...</i>
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
  props: ["address"],
  computed: {
    getAddress() {
      return this.address.street + ' ' + this.address.number + ', ' + this.address. city + ', ' + this.address.country
    }
  }
}
</script>

<style scoped>

</style>
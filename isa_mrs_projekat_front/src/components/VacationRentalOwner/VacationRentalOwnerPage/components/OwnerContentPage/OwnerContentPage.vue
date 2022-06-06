<template>
  <div>
    <font-awesome-icon class="d-inline-block" icon="spinner" style="color: gray; height: 30px"></font-awesome-icon>
    <h2 class="d-inline-block">Building...</h2>
    <EditPersonalData></EditPersonalData>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import EditPersonalData from "@/components/GeneralComponents/EditPersonalData";

library.add(faSpinner);
export default {
  name: "OwnerContentPage",
  components: {
    EditPersonalData,
    FontAwesomeIcon
  }
}
</script>

<style scoped>
h2 {
  color: gray;
}
</style>
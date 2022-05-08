<template>
  <div>
    <font-awesome-icon class="d-inline-block" icon="spinner" style="color: gray; height: 30px"></font-awesome-icon>
    <h2 class="d-inline-block">Building...</h2>
  </div>
  <RentalObjectAdditionForm></RentalObjectAdditionForm>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import RentalObjectAdditionForm from "@/components/GeneralComponents/RentalObjectAdditionForm";

library.add(faSpinner);
export default {
  name: "OwnerContentPage",
  components: {
    FontAwesomeIcon,
    RentalObjectAdditionForm
  }
}
</script>

<style scoped>
h2 {
  color: gray;
}
</style>
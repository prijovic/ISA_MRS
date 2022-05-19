<template>
  <div>
    <font-awesome-icon class="d-inline-block" icon="spinner" style="color: gray; height: 30px"></font-awesome-icon>
    <h2 class="d-inline-block">Building...</h2>
  </div>
  <VacationRentalAdditionForm></VacationRentalAdditionForm>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import VacationRentalAdditionForm from "@/components/VacationRentalOwner/VacationRentalOwnerPage/components/VacationRentalAdditionForm";

library.add(faSpinner);
export default {
  name: "OwnerContentPage",
  components: {
    FontAwesomeIcon,
    VacationRentalAdditionForm
  }
}
</script>

<style scoped>
h2 {
  color: gray;
}
</style>
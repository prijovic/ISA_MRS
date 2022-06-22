<template>
  <div class="modal fade" :id="'addServices-'+this.resId" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header p-0 m-0" style="border-bottom: none">
        </div>
        <div class="modal-body pt-0 mt-0">
          <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
            <h1 class="modal-title pb-1" style="color: #008970; font-weight: 700;">Additional Services</h1>
          </div>

          <div v-if="hasSpecialOffer" class="m-0 p-0">
            <div v-if="hasIncludedServices" class="row mt-3">
              <p class="h4" v-for="(service, i) in specialOffer.includedServices" :key="i">
                ◆ {{ service.name }}
              </p>
            </div>
            <div v-else class="m-0 p-0 row mt-3 text-center">
              <p class="h4"><i>No included services.</i></p>
            </div>
          </div>


          <div v-if="hasAdditionalServices" class="row mt-3">
            <p class="h4" v-for="(service, i) in additionalServices" :key="i">
              ◆ {{ service.name }}
            </p>
          </div>
          <div v-if="!hasAdditionalServices && !hasSpecialOffer" class="row mt-3 text-center">
            <p class="h4"><i>No additional services.</i></p>
          </div>
        </div>
        <div class="modal-footer d-flex" style="border-top: none; justify-content: space-evenly;">
          <button type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal"
                  style="color: white;">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InstructorReservationServices",
  props: ["additionalServices", "resId", "specialOffer"],
  computed: {
    hasAdditionalServices() {
      return this.additionalServices.length > 0;
    },
    hasSpecialOffer() {
      return this.specialOffer !== null;
    },
    hasIncludedServices() {
      return this.specialOffer.includedServices.length > 0;
    },
  }
}
</script>

<style scoped>

</style>
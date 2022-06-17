<template>
  <div class="modal fade" :id="'cancel-'+this.id" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header p-0 m-0" style="border-bottom: none">
        </div>
        <div class="modal-body py-0 my-0">
          <div v-if="!isCancellable">
            <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
              <h1 class="modal-title pb-1" style="color: #e23c52; font-weight: 700;">Cancellation Failed</h1>
            </div>
            <div class="row mt-3 px-1" style="text-align: justify">
              <p class="h5">{{ this.cantCancelText }}</p>
            </div>
          </div>
          <div v-else>
            <div class="row text-center pb-0 mb-0" style="border-bottom: 1px solid lightgray;">
              <h1 class="modal-title pb-1" style="color: #008970; font-weight: 700;">Cancel Reservation</h1>
            </div>
            <div v-if="isFree" class="row mt-3 px-1" style="text-align: center">
              <p class="h5">{{ this.freeCancelText }}</p>
            </div>
            <div v-if="isFixed" class="row mt-3 px-1" style="text-align: center">
              <p class="h5">{{ this.fixedFeeCancelText }}</p>
            </div>
            <div v-if="isPercentile" class="row mt-3 px-1" style="text-align: center">
              <p class="h5">{{ this.percentileFeeCancelText }}</p>
            </div>
            <div class="row mt-3 px-1" style="text-align: center">
              <p class="h5">{{ this.proceedText }}</p>
            </div>
          </div>


        </div>
        <div class="modal-footer d-flex" style="border-top: none; justify-content: space-evenly">
          <button v-if="isCancellable" type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                  style="background-color:#e23c52; color: white; width: 40%">{{ this.denyButtonTxt }}</button>
          <button v-if="isCancellable" type="button" class="btn btn-secondary" :disabled="isCancelled"
                  style="color: white; width: 40%" @click="cancelReservation">{{ this.confirmButtonTxt }}</button>
          <button v-if="!isCancellable" type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal"
                  style="background-color:#e23c52; color: white;">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios/index";

export default {
  name: "ReservationCancellation",
  props: ["id", "cancellationFee", "reservationStartDate", "reservationId", "total"],
  emits: ["customChange"],
  data() {
    return {
      isCancelled: false,
      confirmButtonTxt: "Yes",
      denyButtonTxt: "No",
      cantCancelText: "You can only cancel reservation up until 3 days before. We are sorry for the inconvenience.",
      freeCancelText: "Canceling this reservation is free.",
      fixedFeeCancelText: "The fee for cancelling this reservation is $" + this.cancellationFee.value + ".",
      percentileFeeCancelText: "The fee for cancelling this reservation is $"
          + ((this.total/100)*this.cancellationFee.value).toFixed(2)
          + " (" + this.cancellationFee.value + "%).",
      proceedText: "Would you like to proceed?",
    }
  },
  computed: {
    calculateDeductedAmount() {
      return (this.total/100)*this.cancellationFee.value;
    },
    isFixed() {
      return this.cancellationFee.feeType === "Fixed";
    },
    isPercentile() {
      return this.cancellationFee.feeType === "Percentile";
    },
    isFree() {
      return this.cancellationFee.value === 0;
    },
    isCancellable() {
      let today = new Date(), startDate = new Date(this.reservationStartDate);
      today.setHours(0, 0, 0);
      startDate.setHours(0, 0, 0);
      let Difference_In_Time = startDate.getTime() - today.getTime();
      let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
      Difference_In_Days = Math.round(Difference_In_Days)
      return Difference_In_Days >= 3;
    },
  },
  methods: {
    cancelReservation(event) {
      axios.put("/Reservations/cancelReservation", null, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          id: this.reservationId,
        }
      })
      .then(() => {
        this.confirmButtonTxt = "Cancelled!";
        this.isCancelled = true;
        this.denyButtonTxt = "Close";
        this.$emit("customChange", event.target.value);
      })
      .catch(error => {
        if (error.response.status === 404) {
          this.$notify({
            title: "Invalid Status",
            text: "Something went wrong. Try again later.",
            position: "bottom right",
            type: "warn"
          })
        } else if (error.response.status === 500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            position: "bottom right",
            type: "error"
          })
        }
      })
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
button:disabled {
  background-color: #008970;
}
</style>
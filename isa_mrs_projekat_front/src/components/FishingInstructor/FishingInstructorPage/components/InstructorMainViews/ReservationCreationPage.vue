<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>Reservation</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col-3"></div>
              <div class="col-6">

                <div class="row justify-content-center mt-4">
                  <label class="ms-3">Period</label>
                  <div class="container p-0 m-0 text-center" style="max-width: 400px">
                    <date-picker v-model="range" is-range mode="dateTime" is24hr :firstDayOfWeek=2>
                    </date-picker>
                  </div>
                </div>

                <div class="row justify-content-center mt-2">
                  <label for="client">Client</label>
                  <div class="container p-0 m-0 text-center" style="max-width: 400px">
                    <select class="form-control" v-model="client" id="client">
                      <option value="Client" selected="selected">Client</option>
                      <option value="VacationRentalOwner">House Owner</option>
                      <option value="BoatOwner">Boat Owner</option>
                      <option value="Instructor">Fishing Instructor</option>
                    </select>
                    <p v-if='!clientIsSelected'>'Client' is a mandatory field.</p>
                  </div>
                </div>

              </div>
              <div class="col-3"></div>
              <div class="d-flex pt-3 justify-content-center">
                <router-link to="/fishingInstructor/adventures" class="btn btn-red mt-3 me-1">Cancel</router-link>
                <button type="button" class="btn mt-3" @click.prevent="submit">Submit</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {DatePicker} from "v-calendar";

export default {
  name: "ReservationCreationPage",
  components: {DatePicker},
  data() {
   return {
     range: {
       start: null,
       end: null
     },
     client: null,
     dateIsSelected: true,
     clientIsSelected: true
   }
  }
}
</script>

<style scoped>
label, small {
  max-width: 400px;
}

h3 {
  text-align: center;
}

p {
  color: #e23c52;
}

</style>
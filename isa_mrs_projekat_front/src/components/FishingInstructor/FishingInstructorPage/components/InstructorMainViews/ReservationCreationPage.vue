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
                    <date-picker :disabled-dates="excluded" v-model="date" mode="dateTime" is24hr :firstDayOfWeek=2>
                    </date-picker>
                  </div>
                </div>

                <div class="row justify-content-center mt-2">
                  <label for="client">Client</label>
                  <div class="container p-0 m-0 text-center" style="max-width: 400px">
                    <select class="form-control" v-model="client" id="client">
                      <option value="{{client}}" v-for="(index, client) in clients" :key="index">{{client.name + " " + client.surname}}</option>
                    </select>
                    <p v-if='!clientIsSelected'>'Client' is a mandatory field.</p>
                  </div>
                </div>

                <div class="row justify-content-center mt-2">
                  <input class="form-check-input" type="checkbox" id="equipment" v-model="isEquipmentNeeded">
                  <label for="equipment">Is Equipment needed?</label>
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
import axios from "axios";
import store from "@/store";

export default {
  name: "ReservationCreationPage",
  components: {DatePicker},
  data() {
   return {
     date: null,
     excluded: [],
     clients: [],
     client: null,
     dateIsSelected: true,
     clientIsSelected: true,
     isEquipmentNeeded: false
   }
  },
  mounted() {
    axios.get("/Reservations/instructorReservationLimits", {
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      params: {
        id: this.$route.params.id
      }
    })
    .then((response) => {
      const dto = response.data;
      this.excluded.push({start:new Date(dto.ownerInitDate), end:new Date(dto.ownerTermDate)});
      this.excluded.push({start:null, end:new Date()});
      this.excluded.push({start:new Date(dto.rentalTermDate), end:null});
      for (let i = 0; i < dto.reservationsPeriods.length; i++) {
        this.excluded.push({start:new Date(dto.reservationsPeriods[i].start), end:new Date(dto.reservationsPeriods[i].end)})
      }
      this.clients = response.data.clients;
    })
    .catch()
  },
  methods: {
    isDataEntered() {
      if (this.date === null) {
        this.dateIsSelected = false;
        return false;
      }
      if (this.client === null) {
        this.clientIsSelected = false;
        return false;
      }
    },
    book() {
      if (this.isDataEntered()) {
        axios.post("/Reservations/bookForClient", {clientId: this.clientId, rentalId: this.$route.params.id, initDate: this.date, isEquipmentNeeded: this.isEquipmentNeeded}, {
          headers: {
            Authorization: "Bearer " + store.getters.access_token,
          }
        })
        .then(() => {
          this.$notify( {
            title: "Successful reservation",
            text: "You have successfully booked adventure for client.",
            position: "bottom right",
            type: "success"
          });
        })
        .catch(() => {
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
        })
      }
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
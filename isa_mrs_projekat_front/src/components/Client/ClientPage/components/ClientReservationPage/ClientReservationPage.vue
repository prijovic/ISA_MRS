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
                  <label class="ms-3">Date</label>
                  <div v-if="this.$route.params.type === 'Adventure'" class="container p-0 m-0 text-center" style="max-width: 400px">
                    <date-picker :disabled-dates="excluded" v-model="date" mode="dateTime" is24hr :firstDayOfWeek=2>
                    </date-picker>
                    <p v-if='!dateIsSelected'>'Date' is a mandatory field.</p>
                  </div>
                  <div v-else class="container p-0 m-0 text-center" style="max-width: 400px">
                    <date-picker :disabled-dates="excluded" v-model="range" is-range is24hr :firstDayOfWeek=2>
                    </date-picker>
                    <p v-if='!dateIsSelected'>'Date' is a mandatory field.</p>
                  </div>
                </div>


                <div v-if="this.$route.params.type !== 'VacationRental'" class="row justify-content-center mt-2">
                  <input class="form-check-input" type="checkbox" id="equipment" v-model="isEquipmentNeeded">
                  <label for="equipment">Is Equipment needed?</label>
                </div>

                <div v-if="additionalServices.length > 0" class="row main-col text-center header rounded mb-3 mt-5">
                  <table style="">
                    <thead>
                    <tr>
                      <th>Service</th>
                      <th>Price</th>
                      <th>Add</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(service, index) in additionalServices" :key="index" :class="index%2!==0?'odd':'even'">
                      <td v-if="service.price !== 0">
                        {{ service.name }}
                      </td>
                      <td v-if="service.price !== 0">
                        {{ service.price }}
                      </td>
                      <td v-if="service.price !== 0">
                        <input :id="'service'+index" class="form-check-input" type="checkbox" @input="addOrSubtractServicePrice(service, index)">
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <div v-else class="row text-center mt-5">
                  <p class="h6">
                    <i>No additional services.</i>
                  </p>
                </div>

                <div class="row text-center mt-5">
                  <p class="h1" style="color: black">
                    Total: ${{ getTotal }}
                  </p>
                </div>

              </div>
              <div class="col-3"></div>
              <div class="d-flex pt-3 justify-content-center">
                <router-link :to="'/client/RentalProfile/' + this.$route.params.type + '-' + this.$route.params.id" class="btn btn-red mt-3 me-1">Cancel</router-link>
                <button id="book" type="button" class="btn mt-3" @click.prevent="book">Submit</button>
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
  name: "ClientReservationPage",
  components: {DatePicker},
  data() {
    return {
      date: new Date(),
      excluded: [],
      dateIsSelected: true,
      isEquipmentNeeded: false,
      additionalServices: [],
      checkedServices: [],
      price: 0,
      total: 0,
      range: {
        start: null,
        end: null
      },
    }
  },
  methods: {
    addOrSubtractServicePrice(service, index) {
      let s = document.getElementById('service'+index);
      if(s.checked) {
        this.total += service.price;
        this.checkedServices.push(service);
      }
      else {
        this.total -= service.price;
        this.checkedServices = this.checkedServices.filter(function (s) {
          return s.id !== service.id;
        });
      }
    },
    round(num, places) {
      const round = (num, places) => {
        if (!("" + num).includes("e")) {
          return +(Math.round(num + "e+" + places) + "e-" + places);
        }
        else {
          let arr = ("" + num).split("e");
          let sig = ""
          if (+arr[1] + places > 0) {
            sig = "+";
          }

          return +(Math.round(+arr[0] + "e" + sig + (+arr[1] + places)) + "e-" + places);
        }
      }
      return round(num, places);
    },
    isDataEntered() {
      if(this.$route.params.type === 'Adventure') {
        if (this.date === null) {
          this.dateIsSelected = false;
          return false;
        }
      }
      else {
        if (this.range.start === null) {
          this.dateIsSelected = false;
          return false;
        }
      }
      this.dateIsSelected = true
      return true;
    },
    book() {
      console.log(this.range);
        if (this.isDataEntered()) {
          let startDate, endDate;
          if(this.$route.params.type === 'Adventure') {
            startDate = this.date;
            endDate = this.date;
          }
          else {
            startDate = this.range.start;
            endDate = this.range.end;
          }
          const dto = {
            rentalId: this.$route.params.id,
            startDate: startDate,
            endDate: endDate,
            isEquipmentNeeded: this.isEquipmentNeeded,
            additionalServices: this.checkedServices,
            total: this.getTotal
          };
          axios.post("/Reservations/book", dto, {
            headers: {
              Authorization: "Bearer " + store.getters.access_token,
            }
          })
              .then(() => {
                document.getElementById('book').disabled=true;
                this.$notify( {
                  title: "Successful reservation",
                  text: "You have successfully booked appointment.",
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
  },
  computed: {
    getTotal() {
      let days = this.getNumberOfDays;
      let rentingPrice = this.price*days;
      return this.round(this.total + rentingPrice, 2);
    },
    getNumberOfDays() {
      if(this.$route.params.type === 'Adventure') {
        if(!this.date) return 0;
        return 1
      }
      if(!this.range.start) return 0;
      let initDate = this.range.start, termDate = this.range.end;
      let date1 = new Date(initDate), date2 = new Date(termDate);
      date1.setHours(0, 0, 0);
      date2.setHours(0, 0, 0);
      let timeDiff = date2.getTime() - date1.getTime();
      let daysDiff = timeDiff / (1000 * 3600 * 24);
      return daysDiff + 1;
    },
  },
  mounted() {
    axios.get("/Reservations/getLimits", {
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      params: {
        rentalId: this.$route.params.id,
        ownerId: this.$route.params.ownerId,
      }
    })
        .then((response) => {
          const dto = response.data;
          this.additionalServices = dto.additionalServices;
          this.price = dto.price;
          this.excluded.push({start:new Date(dto.ownerInitDate), end:new Date(dto.ownerTermDate)});
          this.excluded.push({start:null, end:new Date()});
          this.excluded.push({start:new Date(dto.rentalTermDate), end:null});
          for (let i = 0; i < dto.reservationsPeriods.length; i++) {
            this.excluded.push({start:new Date(dto.reservationsPeriods[i].start), end:new Date(dto.reservationsPeriods[i].end)})
          }
        })
        .catch()
  },

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
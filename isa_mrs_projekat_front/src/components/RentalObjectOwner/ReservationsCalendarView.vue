<template>
  <div class="row">
    <div class="col-1"></div>
    <div class="col-10 pt-5 mb-5">
      <div class="container-fluid pe-0 ps-0 me-0 ms-0">
        <div class="row">
          <div class="col text-center">
            <calendar :attributes="attributes" :first-day-of-week="2"
                      :columns="2"
                      :rows="2"
            @dayclick="viewDay"/>
            <div v-if="colorsLoaded" class="mt-3">
              <h4>Color chart</h4>
              <div class="row">
                <div class="col"></div>
                <table class="col-4 text-center align-content-center rounded" style="border: 1px solid #008970">
                  <thead>
                  <tr>
                    <th>Color</th>
                    <th>Rental</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="(rental, index) in rentalObjects" :key="index">
                    <td><div class="rounded-circle shadow m-auto" :style="{'background-color': colorsAndRentals[rental.id]}" style="height: 2vh; width: 2vh"></div></td>
                    <td>{{rental.name}}</td>
                  </tr>
                  </tbody>
                </table>
                <div class="col"></div>
              </div>
            </div>
          </div>
          <div class="col">
            <div v-if="selectedDate!==null" class="container px-4 py-3 rounded form text-center">
              <h3>Reservations</h3>
              <div v-for="(reservation, index) in reservationsToShow" :key="index">
                <div class="container rounded mb-1 p-0 shadow" style="color: black; background-color: white" :style="{'border-left': '5px solid ' + colorsAndRentals[reservation.rentalObject.id]}">
                  <div class="row">
                    <div class="col text-start mt-3 ms-3">Rental Object: <router-link class="link" to="/#">{{reservation.rentalObject.name}}</router-link></div>
                    <div class="col text-end mt-3 me-3">{{formatDateTime(reservation.timeStamp)}}</div>
                  </div>
                  <hr>
                  <div class="row ms-3">
                    <div class="col text-start m-0 p-0">Client: <router-link class="link" to="/#">{{reservation.client.name + " " + reservation.client.surname + " (" + reservation.client.email + ")"}}</router-link></div>
                  </div>
                  <div class="row ms-3">
                    Period: {{formatDate(reservation.reservationTime.initDate) + "-" + formatDate(reservation.reservationTime.termDate)}}
                  </div>
                  <div class="row ms-3 mb-3">
                    People: {{reservation.people}}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Calendar } from 'v-calendar';
import {useStore} from "vuex";
import axios from "axios";

export default {
  name: "ReservationsCalendarView",
  components: {
    Calendar
  },
  data() {
    return {
      reservations: null,
      colors: ["gray", "red", "yellow", "green", "teal", "blue", "indigo", "purple", "pink"],
      colorsAndRentals: {},
      colorsLoaded: false,
      descriptionsAndReservations: {},
      attributes: [
        {
          key: 'today',
          highlight: {
            style: {
              backgroundColor: "#008970",
            },
            contentStyle: {
              color: "#f7f7f2"
            }
          },
          dates: new Date(),
          popover: {
            label: "Today"
          }
        }
      ],
      selectedDate: null,
      reservationsToShow: []
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Reservations/getReservations", {headers: {
        Authorization: "Bearer " + store.getters.access_token,
      }
    })
        .then(response => {
          this.reservations = response.data;
          this.assignColors();
          this.createDescriptions();
          this.formAttributes();
          this.colorsLoaded = true;
        })
        .catch(() =>{
          this.$notify({
            title: "Server error",
            text: "Server is currently off. Please try again later...",
            type: "error"
          });
        });
  },
  methods: {
    viewDay(day) {
      this.reservationsToShow = [];
      this.selectedDate = {id:day.id, date:Date.parse(day.id)}
      this.reservations.forEach(reservation => {
        if (reservation.reservationTime.initDate === day.id) {
          this.reservationsToShow.push(reservation);
        }
      })
    },
    assignColors() {
      let rentalObjects = this.rentalObjects;
      let availableColors = this.colors;
      rentalObjects.forEach(rentalObject => {
        if (availableColors.length === 0) {
          availableColors = this.colors;
        }
        this.colorsAndRentals[rentalObject.id] = availableColors[0];
        availableColors.splice(0, 1);
      })
    },
    createDescriptions() {
      this.reservations.forEach(reservation => {
        let description = "Client ";
        description += reservation.client.name + " " + reservation.client.surname;
        description += " has booked '" + reservation.rentalObject.name + "' to ";
        description += reservation.reservationTime.termDate + ".";
        this.descriptionsAndReservations[reservation.id] = description;
      })
    },
    formAttributes() {
      let dates = this.reservationInitDates;
      this.reservations.forEach(reservation => {
        this.attributes.push(
            {
              bar: this.colorsAndRentals[reservation.rentalObject.id],
              dates:dates[reservation.id],
              popover: {
                label: this.descriptionsAndReservations[reservation.id]
              }
            });
      })
    },
    formatDate(date) {
      let formatted;
      const parts = date.split("-");
      formatted = parts[2] + "." + parts[1] + "." + parts[0];
      return formatted;
    },
    formatDateTime(dateTime) {
      let formatted;
      const bigParts = dateTime.split("T")
      const date = this.formatDate(bigParts[0]);
      const time = bigParts[1].split(":");
      formatted = date + " " + time[0] + ":" + time[1];
      return formatted;
    },
    getRentalObjectName(rentalObjectId) {
      let reservation;
      for (reservation in this.reservations) {
        if (this.reservations[reservation].rentalObject.id === rentalObjectId) {
          return this.reservations[reservation].rentalObject.name;
        }
      }
    },
  },
  computed: {
    rentalObjects() {
      let rentalObjects = [];
      this.reservations.forEach(reservation => {
        if (rentalObjects.findIndex( value => {return reservation.rentalObject.id === value.id;}) === -1) {
          rentalObjects.push(reservation.rentalObject);
        }
      });
      return rentalObjects;
    },
    reservationInitDates() {
      let initDates = {};
      this.reservations.forEach(reservation => {
        initDates[reservation.id] = Date.parse(reservation.reservationTime.initDate);
      })
      return initDates;
    }
  }
}
</script>

<style scoped>
  .link {
    border: none;
    color: #008970;
  }

  .link:active, .link:hover, .link:focus {
    border: none;
    color: #008970;
  }
</style>
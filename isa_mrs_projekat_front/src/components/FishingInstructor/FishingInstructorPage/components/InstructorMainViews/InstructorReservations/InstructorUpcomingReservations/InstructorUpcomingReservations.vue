<template>
  <div v-for="(reservation, i) in reservations" :key="i" class="card reservation mb-3 px-0">
    <div class="row">
      <div class="col-md-4" >
        <router-link :to="getPath(reservation)" class="link" @click="setRentalIdAndType(reservation)">
          <img v-if="images[i]" class="rentalPhoto" :src="images[i]" alt=""/>
        </router-link>
      </div>
      <div class="col-md-4">
        <div class="row">
          <div class="rentalObjectName align-items-center text-start" style="display: flex;">
            <div>
              <h1 class="display-6 me-1 card-title cut-text">{{ reservation.rentalObject.name }}</h1>
            </div>
            <hr>
          </div>
        </div>
        <div class="row">
          <p class="h4"><strong style="color:#008970;">Date:</strong> {{  getDateSpan(reservation) }}</p>
        </div>
        <div class="row">
          <p class="h4">
            <font-awesome-icon v-if="reservation.equipmentRequired" icon="check" style="color:#008970;"></font-awesome-icon>
            <font-awesome-icon v-else icon="x" style="color:#e23c52;"></font-awesome-icon>
            Equipment
          </p>
        </div>
        <div class="row">
          <p class="h4"><strong style="color:#008970;">Profit:</strong>
            ${{ (calculateTotal(reservation)-reservation.income.value).toFixed(2) }}
          </p>
        </div>
      </div>
      <div class="col-md-4 p-5 d-flex flex-grow-1 justify-content-center align-items-center" style="height: 25vh;">
        <div style="width: 100%;">
          <div class="row">
            <button class="w-100 btn mb-2" style="font-weight: 500; color: white;" data-bs-toggle="modal"
                    :data-bs-target="'#addServices-'+reservation.id">
              Services
            </button>
            <InstructorReservationServices :additionalServices="reservation.additionalServices"
                                           :resId="reservation.id"/>
          </div>
          <div class="row">
            <button class="w-100 btn mb-2" style="font-weight: 500; color: white;" data-bs-toggle="modal"
                    :data-bs-target="'#clientProfile-'+reservation.id">
              Client Preview
            </button>
            <InstructorClientPreview :client="reservation.client"
                                     :reservationId="reservation.id"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faCheck, faX } from "@fortawesome/free-solid-svg-icons";

library.add(faCheck, faX);

import InstructorClientPreview
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorClientPreview";
import InstructorReservationServices
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorReservationServices";
export default {
  name: "InstructorUpcomingReservations",
  components: {FontAwesomeIcon, InstructorClientPreview, InstructorReservationServices},
  props: ["reservations", "images"],
  data() {
    return {
      // images: [],
    }
  },

  methods: {
    setRentalIdAndType(reservation) {
      this.$store.dispatch("rentalId", reservation.rentalObject.id);
      this.$store.dispatch("rentalType", reservation.rentalObject.rentalObjectType);
    },
    getPath(reservation) {
      return "/fishingInstructor/Adventure/" + reservation.rentalObject.id;
    },
    showAdventureProfile(id) {
      return this.$router.push('/fishingInstructor/Adventure/' + id);
    },
    getDateSpan(reservation) {
      let initDate = reservation.initDate, termDate = reservation.termDate;
      let date1 = new Date(initDate), date2 = new Date(termDate);
      // date1.setHours(0, 0, 0);
      // date2.setHours(0, 0, 0);
      let dateDisplay = date1.getDate() + '.' + (date1.getMonth() + 1) + '.' +  date1.getFullYear() + '.';
      if(date1.getFullYear() !== date2.getFullYear() || date1.getMonth() !== date2.getMonth() || date1.getDate() !== date2.getDate())
        dateDisplay += ' - ' + date2.getDate() + '.' + (date2.getMonth() + 1) + '.' +  date2.getFullYear() + '.';
      return dateDisplay;
    },
    isFeeNull(reservation) {
      return reservation.rentalObject.cancellationFee !== null;
    },
    calculateTotal(reservation) {
      let days = this.getNumberOfDays(reservation);
      let services = this.calculateAdditionalServices(reservation);
      let price = reservation.price;
      if(reservation.specialOffer !== undefined && reservation.specialOffer !== null) {
        console.log(reservation.specialOffer);
        return (days*price)/100 * (100-reservation.specialOffer.discount);
      }
      return days*price+services;
    },
    getNumberOfDays(reservation) {
      let initDate = reservation.initDate, termDate = reservation.termDate;
      let date1 = new Date(initDate), date2 = new Date(termDate);
      date1.setHours(0, 0, 0);
      date2.setHours(0, 0, 0);
      let timeDiff = date2.getTime() - date1.getTime();
      let daysDiff = timeDiff / (1000 * 3600 * 24);
      return daysDiff + 1;
    },
    calculateAdditionalServices(reservation) {
      let services = reservation.additionalServices, totalServices = 0;
      for(let i=0; i<services.length; i++) totalServices += services[i].price;
      return totalServices;
    },
  },
  mounted() {

  },
}
</script>

<style scoped>
hr {
  flex-grow: 1;
}
.cut-text {
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
div.rentalObjectName {
  display: flex;
}
div.reservation {
  box-shadow: 0.1rem 0.1rem 0.4rem rgb(0 0 0 / 15%);
  background-color: #ffffff;
}
div.reservation:hover {
  padding: 3px;
}
img.rentalPhoto {
  height: 25vh;
  width: 100%;
  border-radius: 1rem;
  object-fit: cover;
  object-position: center;
}
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  height: fit-content;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: none;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  border-radius: 1rem;
}
.card-header:first-child {
  border-radius: calc(1rem - 1px) calc(1rem - 1px) 0 0;
}
.card-header {
  position: relative;
  padding: 2rem 2rem;
  border-bottom: none;
  background-color: inherit;
  box-shadow: 0 0.125rem 0.25rem rgb(0 0 0 / 8%);
  z-index: 2;
}
</style>
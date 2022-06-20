<template>
  <div class="col-12 px-5 mt-5">
    <div class="row px-3 mb-2">
      <div class="card tabs pt-2 pb-1">
        <ul class="nav justify-content-center">
          <li class="nav-item">
            <p class="h5">
              <button class="px-5" v-bind:style="{fontWeight:(upcomingReservationsBtnClicked)?700:400}"
                      @click="showUpcomingReservations">Upcoming reservations</button>
            </p>
          </li>
          <li class="nav-item">
            <p class="h5">
              <button class="px-5" v-bind:style="{fontWeight:(cancelledReservationsBtnClicked)?700:400}"
                      @click="showCancelledReservations">Cancelled Reservations</button>
            </p>
          </li>
          <li class="nav-item">
            <p class="h5">
              <button class="px-5" v-bind:style="{fontWeight:(reservationsHistoryBtnClicked)?700:400}"
                      @click="showReservationsHistory">Reservations History</button>
            </p>
          </li>
        </ul>
      </div>
    </div>
    <div class="row px-3 pb-5" style="overflow: hidden; overflow-y: scroll; max-height: 85vh;">
      <div class="m-0 p-0" v-if="upcomingReservationsBtnClicked">
        <div class="card px-0 mb-2">
          <div class="card-header py-2">
            <h4 class="card-heading">Upcoming reservations</h4>
          </div>
          <div class="card-body">
            <InstructorUpcomingReservations v-if="this.futureReservations && this.fImages"
                                            :reservations="this.futureReservations"
                                            :images="this.fImages"/>
          </div>
        </div>
      </div>
      <div class="m-0 p-0" v-if="cancelledReservationsBtnClicked">
        <div class="card px-0 mb-2">
          <div class="card-header py-2">
            <h4 class="card-heading">Cancelled reservations</h4>
          </div>
          <div class="card-body">
            <InstructorCancelledReservations v-if="this.cancelledReservations && this.cImages"
                                             :reservations="this.cancelledReservations"
                                             :images="this.cImages"/>
          </div>
        </div>
      </div>
      <div class="m-0 p-0" v-if="reservationsHistoryBtnClicked">
        <div class="card px-0 mb-2">
          <div class="card-header py-2">
            <h4 class="card-heading">Reservations History</h4>
          </div>
          <div class="card-body">
            <InstructorReservationHistory v-if="this.pastReservations && this.pImages"
                                          :reservations="this.pastReservations"
                                          :images="this.pImages"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import InstructorUpcomingReservations
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorUpcomingReservations/InstructorUpcomingReservations";
import InstructorReservationHistory
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorReservationHistory/InstructorReservationHistory";
import InstructorCancelledReservations
  from "@/components/FishingInstructor/FishingInstructorPage/components/InstructorMainViews/InstructorReservations/InstructorCancelledReservations/InstructorCancelledReservations";

export default {
  name: "BoatOwnerReservations",
  components: {InstructorCancelledReservations, InstructorUpcomingReservations, InstructorReservationHistory},
  data() {
    return {
      upcomingReservationsBtnClicked: true,
      reservationsHistoryBtnClicked: false,
      cancelledReservationsBtnClicked: false,
      pastReservations: [],
      futureReservations: [],
      cancelledReservations: [],
      fImages: [],
      pImages: [],
      cImages: []
    }
  },
  mounted() {
    axios.get("/Reservations/getBoatOwnerReservations", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token
      }
    })
        .then((response) => {
          this.setUpReservations(response.data);
          this.getImages();
        })
        .catch((error) => {
          console.log(error);
        });
  },
  methods: {
    getImages() {
      for (let i=0; i < this.pastReservations.length; i++) {
        if(!this.pastReservations[i].rentalObject.displayPhoto.photo) { this.pImages[i] = null; continue; }
        axios.get("/Photos/", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token,
          },
          params: {
            path: this.pastReservations[i].rentalObject.displayPhoto.photo,
          },
          responseType: "blob"
        })
            .then(response => {
              this.pImages[i] = URL.createObjectURL(response.data);
            })
            .catch((error) => {
              console.log(error);
            });
      }

      for (let i=0; i < this.futureReservations.length; i++) {
        if(!this.futureReservations[i].rentalObject.displayPhoto.photo) { this.fImages[i] = null; continue; }
        axios.get("/Photos/", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token,
          },
          params: {
            path: this.futureReservations[i].rentalObject.displayPhoto.photo,
          },
          responseType: "blob"
        })
            .then(response => {
              this.fImages[i] = URL.createObjectURL(response.data);
            })
            .catch((error) => {
              console.log(error);
            });
      }

      for (let i=0; i < this.cancelledReservations.length; i++) {
        if(!this.cancelledReservations[i].rentalObject.displayPhoto.photo) { this.cImages[i] = null; continue; }
        axios.get("/Photos/", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token,
          },
          params: {
            path: this.cancelledReservations[i].rentalObject.displayPhoto.photo,
          },
          responseType: "blob"
        })
            .then(response => {
              this.cImages[i] = URL.createObjectURL(response.data);
            })
            .catch((error) => {
              console.log(error);
            });
      }
    },
    setUpReservations(reservations) {
      const today = new Date();
      today.setHours(23, 59, 59, 998);
      for(let i=0; i<reservations.length; i++) {
        if(reservations[i].cancelled === true) {
          this.cancelledReservations.push(reservations[i]);
          continue;
        }
        let date = new Date(reservations[i].initDate);
        date.setDate(date.getDate() + 1);
        if(date < today) this.pastReservations.push(reservations[i]);
        else this.futureReservations.push(reservations[i]);
      }
      this.pastReservations = this.pastReservations.sort((a, b) => new Date(b.initDate) > new Date(a.initDate) ? 1: -1);
      this.futureReservations = this.futureReservations.sort((a, b) => new Date(b.initDate) < new Date(a.initDate) ? 1: -1);
      this.cancelledReservations = this.cancelledReservations.sort((a, b) => new Date(b.income.timestamp) < new Date(a.income.timestamp) ? 1: -1);
    },
    resetTabButtons() {
      this.upcomingReservationsBtnClicked = false;
      this.reservationsHistoryBtnClicked = false;
      this.cancelledReservationsBtnClicked = false;
    },
    showUpcomingReservations() {
      this.resetTabButtons();
      this.upcomingReservationsBtnClicked = true;
    },
    showReservationsHistory() {
      this.resetTabButtons();
      this.reservationsHistoryBtnClicked = true;
    },
    showCancelledReservations() {
      this.resetTabButtons();
      this.cancelledReservationsBtnClicked = true;
    },
  }
}
</script>

<style scoped>
div.tabs button {
  border: none;
  background-color: inherit;
}

div.tabs button:hover {

}

::-webkit-scrollbar {
  display: none;
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

h1, .h1, h2, .h2, h3, .h3, h4, .h4, h5, .h5, h6, .h6 {
  line-height: 1.2;
}

a {
  color: #4650dd!important;
  text-decoration: underline!important;
  cursor: pointer;
}

img, svg {
  vertical-align: middle;
}

.card-profile .card-header {
  height: 15rem;
  background-position: center center;
  background-size: cover;
}
</style>
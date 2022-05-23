<template>
  <router-link :to="'/client/RentalProfile'" class="link" @click="setRentalIdAndType">
    <div class="card col-xl-3 col-lg-4 col-md-6 col-sm-6 col-xs-12 p-0">
      <ThePhoto v-if="doesRentalHavePhotos" class="card-img-top" :photo="rental.photos[0].photo" alt=""/>
      <div class="card-body">
        <div class="align-items-center" style="display: flex;">
            <h1 class="card-title">{{ rental.name }}</h1>
          <hr class="ms-1">
        </div>
        <div v-if="isVacationRental" class="row">
          <div class="col main-col d-flex justify-content-center" title="Capacity">
            <p class="h5">
              <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>{{ rental.capacity }}</strong>
            </p>
          </div>
          <div class="col main-col d-flex justify-content-center" title="Number of rooms">
            <p class="h5">
              <font-awesome-icon class="me-1" icon="door-open"></font-awesome-icon><strong>{{ getNumberOfRooms }}</strong>
            </p>
          </div>
          <div id="beds" class="col main-col d-flex justify-content-center" title="">
            <p class="h5">
              <font-awesome-icon class="me-1" icon="bed"></font-awesome-icon><strong>{{ getNumberOfBeds }}</strong>
            </p>
          </div>
        </div>

        <div v-if="!isVacationRental" class="p-1" style="text-align: left;">
          <p class="h5">
            <font-awesome-icon class="me-1" icon="user"></font-awesome-icon>{{ "Capacity: " + rental.capacity }}
          </p>
        </div>

        <div class="row mt-1">
          <div class="col d-flex justify-content-center">
            <p class="h4"><strong>Grade: 0★</strong></p>
          </div>
          <div class="col d-flex justify-content-center">
            <p class="h4"><strong>{{ "Price: $" + rental.price }}</strong></p>
          </div>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script>
import ThePhoto from "@/components/GeneralComponents/ThePhoto";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import { faBed } from "@fortawesome/free-solid-svg-icons";

library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);

export default {
  name: "CardView",
  components: {
    ThePhoto,
    FontAwesomeIcon,
  },
  props: ["rental"],
  data() {
    return {
      path: "/client/RentalProfile"
    }
  },
  computed: {
    isVacationRental() {
      console.log("\n\n\n\n\n");
      console.log("\n\n\n\n\n");
      console.log(this.rental.name);
      console.log(this.rental.rentalObjectType);
      console.log(this.rental.rentalObjectType=== "VacationRental");
      return this.rentalObjectType !== "VacationRental";
    },
    getNumberOfRooms() {
      return (this.rental.rooms).length;
    },
    getNumberOfBeds() {
      let beds = 0;
      this.rental.rooms.forEach(room => { beds += room.beds; });
      return beds;
    },
    doesRentalHavePhotos() {
      console.log(this.rental);
      return this.rental.photos.length > 0;
    }
  },
  methods: {
    setRentalIdAndType() {
      this.$store.dispatch("rentalId", this.rental.id);
      this.$store.dispatch("rentalType", this.rental.rentalObjectType);
    }
  }
}
</script>

<style scoped>
  div.card img {
    height: 30vh;
    object-fit: cover;
    object-position: center;

  }

  a {
    text-decoration: none;
    color: black;
  }

  hr {
    flex-grow: 1;
  }

  div.card {
    border: 1px solid black;
  }
</style>
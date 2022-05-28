<template>
  <router-link :to="getPath" class="link" @click="setRentalIdAndType">
    <div class="card col-xl-4 col-lg-6 col-md-6 col-sm-10 col-xs-12 px-3 mb-3">
<!--      <ThePhoto v-if="rental.displayPhoto" class="card-img-top" :photo="rental.displayPhoto" alt=""/>-->
      <img v-if="rental.displayPhoto" class="card-img-top" alt="" :src="getPhotoPath"/>
      <div class="card-body pt-1 pb-0">
        <div class="align-items-center" style="display: flex;">
            <h1 class="card-title">{{ rental.name }}</h1>
          <hr class="ms-1">
        </div>
        <div v-if="isVacationRental" class="row">
          <div class="col main-col d-flex justify-content-center" title="Capacity">
            <p class="h3">
              <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>{{ rental.capacity }}</strong>
            </p>
          </div>
          <div class="col main-col d-flex justify-content-center" title="Number of rooms">
            <p class="h3">
              <font-awesome-icon class="me-1" icon="door-open"></font-awesome-icon><strong>{{ getNumberOfRooms }}</strong>
            </p>
          </div>
          <div id="beds" class="col main-col d-flex justify-content-center" title="">
            <p class="h3">
              <font-awesome-icon class="me-1" icon="bed"></font-awesome-icon><strong>{{ getNumberOfBeds }}</strong>
            </p>
          </div>
        </div>

        <div v-if="!isVacationRental" class="p-1" style="text-align: left;">
          <p class="h4">
            <font-awesome-icon class="me-1" icon="user"></font-awesome-icon>{{ "Capacity: " + rental.capacity }}
          </p>
        </div>

        <div class="row mt-1">
          <div class="col d-flex justify-content-center">
            <p class="h4"><strong>{{ "Grade: " + rental.grade + "★" }}</strong></p>
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
// import ThePhoto from "@/components/GeneralComponents/ThePhoto";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";

library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);

export default {
  name: "CardView",
  components: {
    // ThePhoto,
    FontAwesomeIcon,
  },
  props: ["rental"],
  data() {
    return {
      path: "/client/RentalProfile"
    }
  },
  computed: {
    getPhotoPath() {
      return "data:image/jpg;base64," + this.rental.displayPhoto;
    },
    getPath() {
      const store = useStore();
      if(store.state.email) return "/client/RentalProfile";
      return "/RentalProfile";
    },
    isVacationRental() {
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
  },
  methods: {
    setRentalIdAndType() {
      this.$store.dispatch("rentalId", this.rental.id);
      this.$store.dispatch("rentalType", this.rental.rentalObjectType);
      this.$store.dispatch("grade", this.rental.grade);
      console.log(this.$store.state.grade);
    }
  }
}
</script>

<style scoped>
div.card img {
  height: 35vh;
  object-fit: cover;
  object-position: center;
  border-top-left-radius: 40px;
  border-top-right-radius: 40px;
  /*border: 1px solid black;*/
}

  a {
    text-decoration: none;
    color: black;
  }

  hr {
    flex-grow: 1;
  }

  div.card {
    border: none;
  }

  div.card-body {
    border: 1px solid black;
    border-bottom-left-radius: 40px;
    border-bottom-right-radius: 40px;
  }
</style>
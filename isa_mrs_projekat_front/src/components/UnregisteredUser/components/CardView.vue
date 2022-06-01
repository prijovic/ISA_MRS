<template>

    <div class="card col-xl-3 col-lg-4 col-md-6 col-sm-10 col-xs-12 px-3 mb-3">
<!--      <ThePhoto v-if="rental.displayPhoto" class="card-img-top" :photo="rental.displayPhoto" alt=""/>-->
      <router-link :to="getPath" class="link" @click="setRentalIdAndType">



        <img v-if="rental.displayPhoto" class="card-img-top" alt="" :src="photo"/>
        <div class="card-body pt-1 pb-0">
          <div class="align-items-center text-start" style="display: flex;">
            <div>
              <h1 class="card-title cut-text">{{ rental.name }}</h1>
            </div>
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
              <p class="h4"><strong>{{ "Grade: " + getRentalGrade }}</strong></p>
            </div>
            <div class="col d-flex justify-content-center">
              <p class="h4"><strong>{{ "Price: $" + rental.price }}</strong></p>
            </div>
          </div>
        </div>



      </router-link>
    </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";
import axios from "axios";

library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);

export default {
  name: "CardView",
  components: {
    FontAwesomeIcon,
  },
  props: ["rental"],
  data() {
    return {
      path: "/client/RentalProfile",
      photo: null
    }
  },
  computed: {
    getPath() {
      const store = useStore();
      let path = "/RentalProfile/" + this.rental.rentalObjectType + "-" + this.rental.id;
      if (store.state.email) return "/client" + path;
      return "/RentalProfile" + path;
    },
    isVacationRental() {
      return this.rental.rentalObjectType === "VacationRental";
    },
    getNumberOfRooms() {
      return (this.rental.rooms).length;
    },
    getNumberOfBeds() {
      let beds = 0;
      this.rental.rooms.forEach(room => {
        beds += room.beds;
      });
      return beds;
    },
    getRentalGrade() {
      if (this.rental.grade === "0" || !this.rental.grade) return "/";
      return this.rental.grade + "★";
    },
  },
  mounted() {
    axios.get("/Photos/", {
      headers: {
        Authorization: "Bearer " + this.$store.getters.access_token,
      },
      params: {
        path: this.rental.displayPhoto.photo,
      },
      responseType: "blob"
    })
        .then(response => {
          this.photo = URL.createObjectURL(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
  },
  methods: {
    setRentalIdAndType() {
      this.$store.dispatch("rentalId", this.rental.id);
      this.$store.dispatch("rentalType", this.rental.rentalObjectType);
      this.$store.dispatch("grade", this.rental.grade);
    }
  }
}
</script>

<style scoped>
.cut-text {
  width: 100%;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
div.card img {
  height: 25vh;
  object-fit: cover;
  object-position: center;
  border-top-left-radius: 40px;
  border-top-right-radius: 40px;
  /*border: 1px solid black;*/
  /*border-bottom: none;*/
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
    box-shadow: 0 0.3rem 1rem rgb(0 0 0 / 15%);
  }

  .card-img-top {
    box-shadow: -0.05em 0 1rem rgb(0 0 0 / 15%);
  }
</style>
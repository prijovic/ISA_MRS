<template>
  <div v-for="(adventure, i) in adventures" :key="i" class="card col-xl-6 col-lg-6 col-md-6 col-sm-10 col-xs-12 px-3 mb-5">
    <router-link :to="getPath(adventure)" class="link" @click="setRentalIdAndType">
      <img v-if="adventure.displayPhoto.photo" class="card-img-top" alt="" :src="images[i]"/>
      <div class="card-body pt-1 pb-0" style="color: black">
        <div class="align-items-center text-start pb-3" style="display: flex;">
          <div>
            <h1 style="color: black" class="card-title cut-text" :title="adventure.name">{{ adventure.name }}</h1>
          </div>
          <hr class="ms-1">
        </div>
        <div class="p-1 text-center" style="text-align: left;">
          <p class="h3">
            <font-awesome-icon class="me-1" icon="user"></font-awesome-icon>{{ "Capacity: " + adventure.capacity }}
          </p>
        </div>
        <div class="row mt-1">
          <div class="col d-flex justify-content-center">
            <p class="h3">{{ "Grade: " + getRentalGrade(adventure) }}</p>
          </div>
          <div class="col d-flex justify-content-center">
            <p class="h3">{{ "Price: $" + adventure.price }}</p>
          </div>
        </div>
      </div>
    </router-link>
  </div>
  <div v-if="ownerHasNoAdventures" class="text-center">
    <label><i>No adventures available.</i></label>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

library.add(faUser);

export default {
  name: "InstructorProfileAdventures",
  props: ["adventures"],
  components: {FontAwesomeIcon},
  data() {
    return {
      images: [],
    }
  },
  computed: {
    ownerHasNoAdventures() {
      return this.adventures.length === 0;
    }
  },
  mounted() {
    for (let i=0; i < this.adventures.length; i++) {
      if(!this.adventures[i].displayPhoto.photo) { this.images[i] = null; continue; }
      axios.get("/Photos/", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: this.adventures[i].displayPhoto.photo,
        },
        responseType: "blob"
      })
          .then(response => {
            this.images[i] = URL.createObjectURL(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
    }
  },
  methods: {
    getRentalGrade(adventure) {
      if (adventure.grade === "0" || !adventure.grade) return "/";
      return adventure.grade + "★";
    },
    setRentalIdAndType(adventure) {
      this.$store.dispatch("rentalId", adventure.id);
      this.$store.dispatch("rentalType", adventure.rentalObjectType);
    },
    getPath(subscription) {
      return "/client/RentalProfile/" + subscription.rentalObjectType + "-"
          + subscription.id;
    },
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
  box-shadow: 0 0.3rem 1rem rgb(0 0 0 / 30%);
}

.card-img-top {
  box-shadow: -0.06em 0 1rem rgb(0 0 0 / 30%);
}
</style>
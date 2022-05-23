<template>
  <div class="container-fluid mt-2">
    <div class="row align-items-start">
      <div class="col-md-7 p-3">
        <div class="carousel-background">
          <div id="slides" class="carousel slide justify-content-center" data-bs-ride="carousel">
            <ul class="carousel-indicators">
              <li data-bs-target="#slides" data-bs-slide-to="0" class="active"></li>
            </ul>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img class="d-block w-100" src="">
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#slides" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#slides" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>

      </div>
      <div class="col-md-5 p-3">
        <div class="row">
          <div class="rentalObjectName align-items-center">
            <hr><h1 class="display-3 mb-2">Abstract</h1><hr>
          </div>
        </div>

        <div class="row main">
          <div class="col main d-flex justify-content-center" title="Capacity">
            <p class="display-5">
              <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>10</strong>
            </p>
          </div>
          <div class="col main d-flex justify-content-center" title="Number of rooms">
            <p class="display-5">
              <font-awesome-icon class="me-1" icon="door-open"></font-awesome-icon><strong>2</strong>
            </p>
          </div>
          <div class="col main d-flex justify-content-center" title="">
            <p class="display-5">
              <font-awesome-icon class="me-1" icon="bed"></font-awesome-icon><strong>7</strong>
            </p>
          </div>
        </div>

        <div class="row mb-3 mt-2">
          <div class="col d-flex justify-content-center">
            <p class="h4">
              <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>14:00
            </p>
          </div>
          <div class="col d-flex justify-content-center">
            <p class="h4">
              <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>12:00
            </p>
          </div>
        </div>
        <div class="row">
          <div class="d-flex justify-content-center align-items-center">
            <div class="Stars" style="--rating: 0;"></div>
          </div>
        </div>
        <div class="row mt-3 ps-1">
          <p class="h5">Owner: <i>Saska Topalovic</i></p>
        </div>
        <div class="row mt-3 ps-1" style="text-align: center;">
          <p class="h3">
            <i><strong>Price: 200$</strong></i>
          </p>
        </div>
      </div>
      <div class="row align-items-start">
        <div class="col-md-7 ps-3">
          <div class="row description">
            <div class="lineTitle">
              <p class="h4">Description</p>
              <hr class="ms-1">
            </div>
            <p class="ps-3" style="color: grey;">2 bedrooms, a living room with open-plan kitchen and 2 bathrooms.
              The air-conditioned, mechanized, fully furnished, alarm protected. A balcony which opens from the
              living room and the first bedroom, a storage and a parking space in the garage.</p>
          </div>
          <div class="tags row main ps-3 pe-1">
            <div class="tagDiv chip chip-outline btn-outline-primary me-2 px-3 my-1" data-ripple-color="dark">
              Wifi
            </div>
            <div class="tagDiv chip chip-outline btn-outline-primary me-2 px-3 my-1" data-ripple-color="dark">
              Parking
            </div>
            <div class="tagDiv chip chip-outline btn-outline-primary me-2 px-3 my-1" data-ripple-color="dark">
              King sized bed
            </div>
          </div>
          <div class="lineTitle">
            <p class="h4">Rules</p>
            <hr class="ms-1">
          </div>
          <div class="doOrDont d-flex justify-content-center">
            <div class="col-md-4 pe-3">
              <div class="center-block text-center">
                <p class="h5">Do's</p>
              </div>
              <p class="h6"><font-awesome-icon class="me-1" icon="circle-check" style="color:darkblue"></font-awesome-icon>Using the pool</p>
              <p class="h6"><font-awesome-icon class="me-1" icon="circle-check" style="color:darkblue"></font-awesome-icon>Using the sauna</p>
            </div>
            <div class="col-md-4 ps-3">
              <div class="center-block text-center">
                <p class="h5">Don'ts</p>
              </div>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Smoking</p>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Pets</p>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Noise after 10pm</p>
            </div>
          </div>
        </div>

        <div class="col-md-5">
          <p class="h4">
            <font-awesome-icon class="me-1" icon="location-dot"></font-awesome-icon><small>Ulica Miše Dimitrijevića 41, Novi Sad, Srbija</small>
          </p>

        </div>

      </div>
    </div>
    <div class="row  align-items-center mt-5 mb-0 main align-items-start" style="background-color: lightgray; height: 15vh;">
      <div class="d-flex justify-content-center">
        <p class="h6" style="font-weight: 100;">
          Want to receive an email whenever there is a new special offer available?
          <button v-bind:disabled="this.btnDisabled" id="subscribe" @click="subscribe" type="button" class="ms-2 rounded" >
            {{ this.btnText }}
          </button>
        </p>
      </div>
    </div>

    <div class="row align-items-start mt-4">
      <div class="col-md-7">

      </div>
    </div>
  </div>
</template>

<script>


import { library } from "@fortawesome/fontawesome-svg-core";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
import { faCircleXmark } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import { faClock } from "@fortawesome/free-solid-svg-icons";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";
import {useStore} from "vuex";



library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);
library.add(faClock);
library.add(faLocationDot);
library.add(faCircleCheck);
library.add(faCircleXmark);

export default {
  name: "RentalProfile",
  components: {},
  data() {
    return {
      rentalObject: null,
      btnText: "Subscribe",
      btnDisabled: false,
      id: null,
      objectType: null,
    }
  },
  mounted() {
    const store = useStore();
    this.id = store.state.rentalId;
    this.objectType = store.state.rentalType;
    if(this.objectType === "Boat") {
      axios.get("/RentalObjects/getBoat", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.id,
        }
      })
          .then((response) => {
            this.rentalObject = response.data;
          });
    }
    else if(this.objectType === "Adventure") {
      axios.get("/RentalObjects/getAdventure", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.id,
        }
      })
          .then((response) => {
            this.rentalObject = response.data;
          });
    }
    else {
      axios.get("/RentalObjects/getVacationRental", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.id,
        }
      })
          .then((response) => {
            console.log("Usli smo na profil")
            this.rentalObject = response.data;
            console.log(this.rentalObject);
          });
    }
  },
  computed: {
    getNumberOfRooms() {
      return (this.rentalObject.rooms).length;
    },
    getNumberOfBeds() {
      let beds = 0;
      this.rentalObject.rooms.forEach(room => { beds += room.beds; });
      return beds;
    },
    doesRentalHavePhotos() {
      return this.rentalObject.photos.length > 0;
    }
  },
  methods: {
    subscribe() {
      this.btnText = "Subscribed!";
      this.btnDisabled = true;
    }
  },
}
</script>

<style scoped>
div.rentalObjectName {
  display: flex;
}
div.carousel-background {
  /*
  background-color: black;
  border: 1px solid black;
  */
  background-color: lightgray;
}

div.carousel {
  height: 60vh !important;
}

div.carousel-item {
  transition: -webkit-transform 0.5s ease;
  -webkit-backface-visibility: visible;
  backface-visibility: visible;
}

div.carousel-inner img {
  height: 60vh;
  object-fit: contain;
}

.carousel-indicators .active{
  background-color: darkblue;
}
div.description {
  text-align: justify;
}

div.lineTitle {
  display: flex;
}
div.doOrDont {
  text-align: justify;
}
.tagDiv {
  width: fit-content;
  border-radius: 30px;
  border: 1px solid darkblue;
  cursor: pointer;
}
div.lineTitle {
  display: flex;
}
hr {
  flex-grow: 1;
}
.carousel-control-prev-icon {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' \
    viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E");
}

.carousel-control-next-icon {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23fff' \
    viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E");
}
:root {
  --star-size: 60px;
  --star-color: black;
  --star-background: blue;
}

.Stars::before {
  content: '★★★★★';
  background: linear-gradient(90deg, var(--star-background) var(--percent), var(--star-color) var(--percent));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.Stars {
  --percent: calc(var(--rating) / 5 * 100%);
  display: inline-block;
  font-size: var(--star-size);
  font-family: Times,serif;
  line-height: 1;
}

button#subscribe:hover {
  color: white;
  background-color: black;
}

button#subscribe {
  height: fit-content;
  width:fit-content;
  background-color:lightgray;
  border: 2px solid darkgray;
  color: darkgray;
}
</style>
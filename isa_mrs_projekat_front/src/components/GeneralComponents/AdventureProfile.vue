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
              <div v-for="(url, index) in imageUrls" class="carousel-item" :class="index===0?'active':''" :key="index">
                <img class="d-block w-100" :src="url" alt="">
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
            <hr><h1 class="display-3 mb-2">{{adventure.name}}</h1><hr>
          </div>
        </div>

        <div class="row main">
          <div class="col main d-flex justify-content-center" title="Capacity">
            <p class="display-5">
              <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>{{adventure.capacity}}</strong>
            </p>
          </div>
        </div>
<!--        <div class="row">-->
<!--          <div class="d-flex justify-content-center align-items-center">-->
<!--            <div class="Stars" style="&#45;&#45;rating: 0;"></div>-->
<!--          </div>-->
<!--        </div>-->
        <div class="row mt-3 ps-1">
          <p class="h5">Owner: <i>{{adventure.rentalObjectOwner.name + " " + adventure.rentalObjectOwner.surname}}}</i></p>
        </div>
        <div class="row mt-3 ps-1" style="text-align: center;">
          <p class="h3">
            <i><strong>Price: {{adventure.price}}$</strong></i>
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
            <p class="ps-3" style="color: grey;">{{adventure.description}}</p>
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
                <p class="h5">Do</p>
              </div>
              <p class="h6"><font-awesome-icon class="me-1" icon="circle-check" style="color:darkblue"></font-awesome-icon>Using the pool</p>
              <p class="h6"><font-awesome-icon class="me-1" icon="circle-check" style="color:darkblue"></font-awesome-icon>Using the sauna</p>
            </div>
            <div class="col-md-4 ps-3">
              <div class="center-block text-center">
                <p class="h5">Don't</p>
              </div>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Smoking</p>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Pets</p>
              <p  class="h6"><font-awesome-icon class="me-1" icon="circle-xmark" style="color:darkblue"></font-awesome-icon>Noise after 10pm</p>
            </div>
          </div>
        </div>

        <div class="col-md-5">
          <p class="h4">
            <font-awesome-icon class="me-1" icon="location-dot"></font-awesome-icon><small>{{adventure.address.street + " " + adventure.address.number + ", " + adventure.address.city + ", " + adventure.address.country}}}</small>
          </p>

        </div>

      </div>
    </div>

    <div class="row align-items-start mt-4">
      <div class="col-md-7">

      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
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
  name: "AdventureProfile",
  components: {FontAwesomeIcon},
  data() {
    return {
      adventure: null,
      imageUrls:[]
    }
  },
  mounted() {
    const store = useStore();
    console.log("mounted");
    axios.get("/RentalObjects/getAdventure", {
      headers: {
        Authorization: "Bearer " + store.state.access_token
      },
      params: {
        id: this.$route.params.id,
      }
    })
    .then((response) => {
      this.adventure = response.data;
      if (this.doesRentalHavePhotos) {
        this.adventure.photos.forEach(photo => {
          this.loadImage(photo.photo, this.adventure.photos.indexOf(photo));
        })
      }
      console.log(this.adventure);
      console.log(this.imageUrls);
    })
    .catch(() => {
      console.log("greska");
    }) ;
  },
  computed: {
    doesRentalHavePhotos() {
      return this.adventure.photos.length > 0;
    }
  },
  methods: {
    loadImage(name, index) {
      axios.get("/Photos/", {headers: {
          Authorization: "Bearer " + this.$store.getters.access_token,
        },
        params: {
          path: name,
        },
        responseType: "blob"
      })
          .then(response => {
            this.imageUrls[index] = URL.createObjectURL(response.data);
          })
          .catch((error) =>{
            console.log(error);
          });
    },
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

/*:root {*/
/*  --star-size: 60px;*/
/*  --star-color: black;*/
/*  --star-background: blue;*/
/*}*/

/*.Stars::before {*/
/*  content: '★★★★★';*/
/*  background: linear-gradient(90deg, var(--star-background) var(--percent), var(--star-color) var(--percent));*/
/*  -webkit-background-clip: text;*/
/*  -webkit-text-fill-color: transparent;*/
/*}*/

/*.Stars {*/
/*  --percent: calc(var(--rating) / 5 * 100%);*/
/*  display: inline-block;*/
/*  font-size: var(--star-size);*/
/*  font-family: Times,serif;*/
/*  line-height: 1;*/
/*}*/

</style>
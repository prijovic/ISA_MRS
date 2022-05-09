<template>
    <div class="container-fluid mt-2">
        <div class="row align-items-start">
            <div class="col-md-7 p-3">
              <ImageSlider  :photos="this.rentalObject.photos"/>
            </div>
            <div class="col-md-5 p-3">
<!--------------------------------------------------------------------------------------------------------------------->
                <div class="row">
                    <div class="rentalObjectName align-items-center">
                        <hr><h1 class="display-3 mb-2">{{ this.rentalObject.name }}</h1><hr>
                    </div>
                </div>

                <div v-if="rentalObjectType === 'VacationRental'" class="row main">
                  <div class="col main d-flex justify-content-center" title="Capacity">
                    <p class="display-5">
                      <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>{{ this.rentalObject.capacity }}</strong>
                    </p>
                  </div>
                  <div class="col main d-flex justify-content-center" title="Number of rooms">
                    <p class="display-5">
                      <font-awesome-icon class="me-1" icon="door-open"></font-awesome-icon><strong>{{ getNumberOfRooms }}</strong>
                    </p>
                  </div>
                  <div class="col main d-flex justify-content-center" title="">
                    <p class="display-5">
                      <font-awesome-icon class="me-1" icon="bed"></font-awesome-icon><strong>{{ getNumberOfBeds }}</strong>
                    </p>
                  </div>
                </div>
              
                <div v-if="rentalObjectType === 'VacationRental'" class="row mb-3 mt-2">
                    <div class="col d-flex justify-content-center">
                        <p class="h4">
                          <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>{{ "Check in: " + this.rentalObject.checkIn }}
                        </p>
                    </div>
                    <div class="col d-flex justify-content-center">
                        <p class="h4">
                          <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>{{ "Check out: " + this.rentalObject.checkOut }}
                        </p>
                    </div>
                </div>
                <div v-if="rentalObjectType === 'Boat' || rentalObjectType === 'Adventure'" class="p-1" style="text-align: center;">
                    <p class="h4">
                      <font-awesome-icon class="me-1" icon="user"></font-awesome-icon>{{"Capacity: " + this.rentalObject.capacity}}
                    </p>
                </div>
                <div class="row">
                    <div class="d-flex justify-content-center align-items-center">
                        <div class="Stars" style="--rating: 0;"></div>
                        (0.0)
                    </div>
                </div>
                <div class="row mt-3 ps-1">
                   <p class="h5">Owner: <i>{{ this.rentalObject.rentalObjectOwner.name + " " + this.rentalObject.rentalObjectOwner.surname}}</i></p>
                </div>
                <div class="row mt-3 ps-1" style="text-align: center;">
                    <p class="h3">
                        <i><strong>{{ "Price: $" + this.rentalObject.price }}</strong></i>
                    </p>
                </div>

<!--------------------------------------------------------------------------------------------------------------------->
            </div>
        </div>
        <div class="row align-items-start">
            <div class="col-md-7 ps-3">
                <RentalDescription :description="this.rentalObject.description"/>
                <RentalTags :additional-services="this.rentalObject.additionalServices"/>
                <RentalRules :conduct-rules="this.rentalObject.conductRules"/>
                <!--reviews-->
            </div>

            <div class="col-md-5">
              <div v-if="rentalObjectType === 'Boat'" class="row main mb-3">
                <div class="lineTitle">
                  <p class="h4">Boat info</p>
                  <hr class="ms-1">
                </div>
                <div class="row-fluid main px-5" style="font-weight: 700;">
                  <div class="row mt-3 main">
                    <div class="col main border">{{"Type: " + this.rentalObject.type }}</div>
                    <div class="col main border">{{"Length: " + this.rentalObject.length }}</div>
                  </div>
                  <div class="row main">
                    <div class="col main border">{{"Engine number: " + this.rentalObject.engineNumber }}</div>
                    <div class="col main border">{{"Engine power: " + this.rentalObject.enginePower  }}</div>
                  </div>
                  <div class="row main text-center">
                    <div class="col main border">{{"Maximum speed: " + this.rentalObject.maxSpeed }}</div>
                  </div>
                </div>
              </div>
              <div v-if="rentalObjectType === 'Boat'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Navigation equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>
              <div v-if="rentalObjectType === 'Boat'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Fishing equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>
              <div v-if="rentalObjectType === 'Adventure'" class="row main">
                <div class="lineTitle">
                  <p class="h4">Adventure equipment</p>
                  <hr class="ms-1">
                </div>
                <p class="" style="color: grey;"></p>
              </div>


              <RentalAddress :address="this.rentalObject.address.street + ' ' + this.rentalObject.address.number
                              + ', ' + this.rentalObject.address. city + ', ' + this.rentalObject.address.country"/>

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
        <!--
        <div class="row align-items-start mt-4">
            <div class="col-md-7">

            </div>
        </div>
        -->
    </div>
</template>

<script>
import ImageSlider from "@/components/UnregisteredUser/components/ImageSlider";
import RentalDescription from "@/components/UnregisteredUser/components/RentalDescription";
import RentalTags from "@/components/UnregisteredUser/components/RentalTags";
import RentalAddress from "@/components/UnregisteredUser/components/RentalAddress";
import RentalRules from "@/components/UnregisteredUser/components/RentalRules";
// import axios from "axios/index";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import { faBed } from "@fortawesome/free-solid-svg-icons";
import { faClock } from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";
import {useStore} from "vuex";

library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);
library.add(faClock);

export default {
  name: "RentalProfile",
  components: {RentalRules, RentalAddress, RentalTags, RentalDescription, ImageSlider, FontAwesomeIcon},
  props: {
    rentObj: {required: true}
  },
  data() {
    return {
      rentalObject: null,
      btnText: "Subscribe",
      btnDisabled: false,
      id: null,
      objectType: null
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
    }
  },
  methods: {
    subscribe() {
      this.btnText = "Subscribed!";
      this.btnDisabled = true;
    }
  },
  mounted() {
    const store = useStore();
    this.id = store.state.rentalId;
    this.objectType = store.state.rentalType;
    console.log(this.id);
    if(this.objectType === "Boat") {
      axios.get("/RentalObjects/getBoat", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.id,
        }
      }).then(response => {
        this.rentalObject = response.data.content;
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
      }).then(response => {
        this.rentalObject = response.data.content;
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
      }).then(response => {
        this.rentalObject = response.data.content;
        console.log(this.rentalObject);
      });
    }
  },
}
</script>

<style scoped>
  div.rentalObjectName {
    display: flex;
  }

  :root {
    --star-size: 60px;
    --star-color: black;
    --star-background: blue;
  }

  .Stars {
    --percent: calc(var(--rating) / 5 * 100%);
    display: inline-block;
    font-size: var(--star-size);
    font-family: Times,serif;
    line-height: 1;
  }

  .Stars::before {
    content: '★★★★★';
    background: linear-gradient(90deg, var(--star-background) var(--percent), var(--star-color) var(--percent));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
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
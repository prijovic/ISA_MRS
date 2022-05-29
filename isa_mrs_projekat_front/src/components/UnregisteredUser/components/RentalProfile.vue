<template>
  <div v-if="rentalObject" class="container-fluid mt-2 pe-0">
    <div class="row align-items-start">
      <div class="col-md-7 p-3">
        <ImageSlider :photos="this.rentalObject.photos"/>

      </div>

<!--------------------------------------------------------------------------------------------------------------------->

      <div class="col-md-5 p-3 d-flex flex-grow-1 justify-content-center align-items-center rentalBasicInfo">
        <div style="width: 100%;">
          <div class="row mb-3">
            <div class="rentalObjectName align-items-center">
              <hr><h1 class="display-3">{{ this.rentalObject.name }}</h1><hr>
            </div>
          </div>

          <div v-if="isVacationRental" class="row main mb-3">
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

          <div v-if="isVacationRental" class="row mb-3 ">
            <div class="col d-flex justify-content-center">
              <p class="h3">
                <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>{{ "Check in:  " +
                                      formatTimeStamp(this.rentalObject.checkIn) + "h" }}
              </p>
            </div>
            <div class="col d-flex justify-content-center">
              <p class="h3">
                <font-awesome-icon class="me-1" icon="clock"></font-awesome-icon>{{ "Check out:  " +
                                      formatTimeStamp(this.rentalObject.checkOut) + "h" }}
              </p>
            </div>
          </div>

          <div v-if="!isVacationRental" class="p-1" style="text-align: center;">
            <p class="display-5">
              <font-awesome-icon class="me-1" icon="user"></font-awesome-icon><strong>{{ this.rentalObject.capacity }}</strong>
            </p>
          </div>

  <!--        <div class="row">-->
  <!--          <div class="d-flex justify-content-center align-items-center">-->
  <!--            <div class="Stars" style="&#45;&#45;rating: 0;"></div>-->
  <!--            (0.0)-->
  <!--          </div>-->
  <!--        </div>-->

          <div class="row mt-3 ps-1 text-center">
            <p class="h3">
              <font-awesome-icon class="me-2" icon="user-tie"></font-awesome-icon><i>{{ getOwnerFullName }}</i>
            </p>
          </div>

          <div class="row mt-3 ps-1" style="text-align: center;">
            <p class="h3">
              <i><strong>{{ "Price: $" + this.rentalObject.price }}</strong></i>
            </p>
          </div>
        </div>
      </div>
    </div>
<!--------------------------------------------------------------------------------------------------------------------->

    <div class="row align-items-start mb-5">
      <div class="col-md-7 ps-3">
        <div class="row">
          <div class="" style="">
            <RentalDescription :description="this.rentalObject.description"/>
            <RentalTags :additional-services="this.rentalObject.additionalServices"/>
            <RentalRules :conduct-rules="this.rentalObject.conductRules"/>
            <RentalReviews :reviews="this.rentalObject.reviews"/>
          </div>
        </div>
      </div>

      <div class="col-md-5">
        <div v-if="isBoat" class="row main mb-4">
          <div class="lineTitle">
            <p class="h3"><strong>Boat info</strong></p>
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

        <div v-if="isBoat" class="row main mb-3">
          <div class="lineTitle">
            <p class="h3"><strong>Navigation equipment</strong></p>
            <hr class="ms-1">
          </div>
          <p class="" style="color: grey;">
            {{ getEquipment(this.rentalObject.navigationEquipment) }}
          </p>
        </div>

        <div v-if="isBoat" class="row main mb-5">
          <div class="lineTitle">
            <p class="h3"><strong>Fishing equipment</strong></p>
            <hr class="ms-1">
          </div>
          <p class="" style="color: grey;">
            {{ getEquipment(this.rentalObject.fishingEquipment) }}
          </p>
        </div>

        <div v-if="isAdventure" class="row main mb-5">
          <div class="lineTitle">
            <p class="h3"><strong>Adventure equipment</strong></p>
            <hr class="ms-1">
          </div>
          <p class="" style="color: grey;">
            {{ getEquipment(this.rentalObject.adventureEquipment) }}
          </p>
        </div>

        <RentalAddress :address="this.rentalObject.address"/>

      </div>
    </div>


    <div v-if="showSubscriptionArea" class="row align-items-center mb-0 main align-items-start"
         style="background-color: lightgray; height: 10vh;">
      <div class="d-flex justify-content-center">
        <p class="h6" style="font-weight: 100;">
          Want to receive an email whenever there is a new special offer available?
          <button v-if="!isUserSubscribed" v-bind:disabled="this.btnDisabled" @click="subscribe"
                  type="button" class="subscribe ms-2 rounded" >
            {{ this.btnText }}
          </button>
          <button v-else disabled type="button" class="subscribe ms-2 rounded" >Subscribed!</button>
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
import RentalReviews from "@/components/UnregisteredUser/components/RentalReviews";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {
  faBed,
  faCircleCheck,
  faCircleXmark,
  faClock,
  faDoorOpen,
  faLocationDot,
  faUser,
  faUserTie,
} from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";
import {useStore} from "vuex";
import store from "@/store";


library.add(faUser);
library.add(faDoorOpen);
library.add(faBed);
library.add(faClock);
library.add(faLocationDot);
library.add(faCircleCheck);
library.add(faCircleXmark);
library.add(faUserTie);

export default {
  name: "RentalProfile",
  components: {RentalRules, RentalAddress, RentalTags, RentalDescription, ImageSlider, RentalReviews, FontAwesomeIcon},
  data() {
    return {
      rentalObject: null,
      btnText: "Subscribe",
      btnDisabled: false,
      email: null
    }
  },
  mounted() {
    const store = useStore();
    this.email = store.state.email;
    if(this.$route.params.type === "Boat") {
      axios.get("/RentalObjects/getBoat", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.$route.params.id,
          email: this.email,
        }
      })
      .then((response) => {
        this.rentalObject = response.data;
      });
    }
    else if(this.$route.params.type === "Adventure") {
      axios.get("/RentalObjects/getAdventure", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.$route.params.id,
          email: this.email,
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
          id: this.$route.params.id,
          email: this.email,
        }
      })
      .then((response) => {
        console.log("Usli smo na profil")
        this.rentalObject = response.data;
      });
    }
  },
  computed: {
    getNumberOfRooms() {
      return this.rentalObject.rooms.length;
    },
    getNumberOfBeds() {
      let beds = 0;
      this.rentalObject.rooms.forEach(room => { beds += room.beds; });
      return beds;
    },
    doesRentalHavePhotos() {
      return this.rentalObject.photos.length > 0;
    },
    isUserSubscribed() {
      return this.rentalObject.isUserSubscribed;
    },
    showSubscriptionArea() {
      return this.$store.getters.user === "client";
    },
    isVacationRental() {
      return this.rentalObject.rentalObjectType === "VacationRental";
    },
    isBoat() {
      return this.rentalObject.rentalObjectType === "Boat";
    },
    isAdventure() {
      return this.rentalObject.rentalObjectType === "Adventure";
    },
    getOwnerFullName() {
      return this.rentalObject.rentalObjectOwner.name + " " + this.rentalObject.rentalObjectOwner.surname;
    },
    getAccessToken() {
      return store.state.access_token;
    },
  },
  methods: {
    subscribe() {
      axios.post("/RentalObjects/subscribe", { rentalId: this.rentalObject.id, clientEmail: this.email }, {
        headers: {
          Authorization: "Bearer " + this.getAccessToken,
        },
      })
      .then(() => {
        this.btnText = "Subscribed!";
        this.btnDisabled = true;
      })
      .catch(error => {
        if (error.response.status === 404) {
          this.$notify({
            title: "Invalid Request Status",
            text: "Something went wrong. Try again later.",
            position: "bottom right",
            type: "warn"
          })
        } else if (error.response.status === 500) {
          this.$notify({
            title: "Internal Server Error",
            text: "Something went wrong on the server! Please try again later...",
            position: "bottom right",
            type: "error"
          })
        }
      })
    },
    getEquipment(equipment) {
      let printEquipment = "";
      const collectionSize = equipment.length;
      for(let i=0; i<collectionSize; i++) {
        printEquipment += equipment[i].name;
        if(i === collectionSize-1) break;
        printEquipment += ", "
      }
      if(printEquipment === "") return "No equipment included.";
      return printEquipment;
    },
    formatTimeStamp(time) {
      return time.slice(0, -3);
    },

  },
}
</script>

<style scoped>
div.rentalObjectName {
  display: flex;
}
div.lineTitle {
  display: flex;
}

hr {
  flex-grow: 1;
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

button.subscribe:hover {
  color: white;
  background-color: black;
}

button.subscribe {
  height: fit-content;
  width:fit-content;
  background-color:lightgray;
  border: 2px solid darkgray;
  color: darkgray;
}

div.rentalBasicInfo {
  height: 60vh;
}
</style>
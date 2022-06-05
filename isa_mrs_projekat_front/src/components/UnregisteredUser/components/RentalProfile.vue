<template>
  <div v-if="rentalObject" class="container-fluid mt-2 pe-0">
    <div class="row align-items-start">
      <div class="col-md-7 p-3">
        <ImageSlider :photos="this.rentalObject.photos"/>

      </div>

<!--------------------------------------------------------------------------------------------------------------------->

      <div class="col-md-5 p-3 d-flex flex-grow-1 justify-content-center align-items-center rentalBasicInfo">
        <div style="width: 100%;">
          <div class="row">
            <div class="align-items-center text-start" style="display: flex;">
              <hr class="ms-1">
              <div>
                <h1 class="display-3 card-title cut-text" :title="this.rentalObject.name">{{ this.rentalObject.name }}</h1>
              </div>
              <hr class="ms-1">
            </div>
          </div>

          <div class="row my-0 mb-2">
            <div class="col-12 d-flex justify-content-center align-items-center">
              <star-rating v-model:rating="this.rentalObject.grade" text-class="h1 my-0 font-weight-normal" :read-only="true" :round-start-rating="false" :glow="5" glow-color="#ffd055"></star-rating>
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
              <p class="display-5" :title="this.bedsPerRoom">
                <font-awesome-icon class="me-1" icon="bed"></font-awesome-icon><strong>{{ getNumberOfBeds }}</strong>
              </p>
            </div>
          </div>

          <div v-if="isVacationRental" class="row">
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

<!--          <div class="row mt-3 ps-1 text-center">-->
<!--            <p class="h3">-->
<!--              <font-awesome-icon class="me-2" icon="user-tie"></font-awesome-icon><i>{{ getOwnerFullName }}</i>-->
<!--            </p>-->
<!--          </div>-->

          <div class="row ps-1" style="text-align: center;">
            <p class="h3">
              <i><strong>{{ "Price: $" + this.rentalObject.price }}</strong></i>
            </p>
          </div>

          <div v-if="isOwner" class="row">
            <div class="d-flex justify-content-center">
              <button class="btn btn-red mt-3 me-1" :class="!this.rentalObject.isDeletable ? 'disabled':''" ><font-awesome-icon style="margin-right: 10px" icon="trash"></font-awesome-icon>Delete</button>
              <router-link :to="'/fishingInstructor/updateAdventure/' + this.$route.params.id" class="btn btn-yellow mt-3 me-1" :class="!this.rentalObject.isDeletable ? 'disabled':''"><font-awesome-icon style="margin-right: 10px" icon="pencil"></font-awesome-icon>Edit</router-link>
            </div>
          </div>

          <div v-if="isOwner" class="row">
            <div class="d-flex justify-content-center">
              <router-link :to="'/#' + this.$route.params.id" class="btn mt-3 me-1"><font-awesome-icon style="margin-right: 10px" icon="tag"></font-awesome-icon>Special Offer</router-link>
            </div>
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

            <div class="row ps-1">
              <p class="h5" style="display: flex;">
                <span class="mt-1" >
                <font-awesome-icon class="me-2" icon="user-tie"></font-awesome-icon><i>Owner: {{ getOwnerFullName }}</i>
                </span>
                <star-rating class="h5 ms-3" v-model:rating="this.rentalObject.ownerGrade"
                             text-class="mt-1 font-weight-normal"
                             :read-only="true" :round-start-rating="false" :glow="5"
                             glow-color="#ffd055" :star-size="20"></star-rating>
              </p>
            </div>

            <RentalTags :additional-services="this.rentalObject.additionalServices"/>
            <RentalRules :conduct-rules="this.rentalObject.conductRules"/>
            <div class="row main">
              <div class="accordion accordion-flush" id="accordionFlushExample">
                <div class="accordion-item">
                  <h2 class="accordion-header" id="flush-headingTwo" style="border: 1px solid black;">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                            data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
                      <p class="h3"><strong>Reviews</strong></p>
                    </button>
                  </h2>
                  <div id="flush-collapseTwo" class="accordion-collapse collapse show" aria-labelledby="flush-headingTwo">
                    <div class="accordion-body p-2 pb-1">

                      <div v-if="rentalHasReviews" class="reviews">
                        <nav class="" aria-label="Page navigation">
                          <ul class="pagination justify-content-center mt-3">
                            <li class="page-item" v-if="totalPages > 1"><button class="page-link rounded" :disabled="currentPage===0" @click="previousPage">Previous</button></li>
                            <li class="page-item mt-auto me-1 ms-1 rounded" v-if="currentPage > 1 && totalPages > 3">...</li>
                            <li class="page-item"><button class="page-link rounded" :disabled="currentPage === 0" @click="numberedPage(1)">{{button1Content}}</button></li>
                            <li class="page-item" v-if="totalPages > 1"><button class="page-link rounded" :disabled="currentPage !== 0 && totalPages - currentPage > 1" @click="numberedPage(2)">{{button2Content}}</button></li>
                            <li class="page-item" v-if="totalPages > 2"><button class="page-link rounded" :disabled="totalPages - currentPage === 1" @click="numberedPage(3)">{{button3Content}}</button></li>
                            <li class="page-item mt-auto me-1 ms-1" v-if="totalPages - currentPage > 1 && totalPages > 3 && button3Content !== totalPages">...</li>
                            <li class="page-item" v-if="totalPages > 1"><button class="page-link rounded" :disabled="totalPages - currentPage === 1" @click="nextPage">Next</button></li>
                          </ul>
                        </nav>
                        <div v-for="(review, i) in reviews" :key="i" class="review p-2 mb-1">
                          <div class="review-header">
                            <div class="align-items-center" style="display:flex; justify-content: space-between; ">
                              <div style="display: flex;">
                                <img v-if="images[i]" class="profile-pic rounded-pill" :src="images[i]" alt=""/>
                                <img v-else class="profile-pic rounded-pill" src="../../Images/noProfilePic.png" alt=""/>
                                <div class="ms-1">
                                  <h6>{{ getAuthorFullName(review) }}</h6>
                                  <p class="small" style="color: gray;">{{ formatReviewDate(review.timeStamp) }}</p>
                                </div>
                              </div>
                              <p class="h3 pe-2">{{ review.grade + "★" }}</p>
                            </div>
                          </div>
                          <div class="review-body" style="text-align: justify; color: black;">
                            <p v-if="review.comment" class="ps-1 pe-2">{{ review.comment }}</p>
                          </div>
                        </div>


                      </div>
                      <div v-else class="text-center">
                        <label><i>No reviews available.</i></label>
                      </div>


                    </div>
                  </div>
                </div>
              </div>
            </div>
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

        <div class="row mb-5">
          <RentalAddress :address="this.rentalObject.address"/>
        </div>

      </div>
    </div>


    <div v-if="showSubscriptionArea" class="row align-items-center mb-0 main align-items-start"
         style="background-color: lightgray; height: 10vh;">
      <div class="d-flex justify-content-center">
        <p class="h6" style="font-weight: 100;">
          Want to receive an email whenever there is a new special offer available?
          <button :disabled="isUserSubscribed" @click="subscribe"
                  type="button" class="subscribe ms-2 rounded" >
            {{ buttonText }}
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
import StarRating from 'vue-star-rating';
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import { faBed, faCircleCheck, faCircleXmark, faClock, faDoorOpen, faLocationDot, faUser, faUserTie, faPencil, faTrash, faTag } from "@fortawesome/free-solid-svg-icons";
import axios from "axios/index";
import {useStore} from "vuex";
import store from "@/store";


library.add(faUser, faDoorOpen, faBed, faClock, faLocationDot, faCircleCheck, faCircleXmark, faUserTie, faPencil, faTrash, faTag);

export default {
  name: "RentalProfile",
  components: {
    RentalRules, RentalAddress, RentalTags, RentalDescription, ImageSlider, FontAwesomeIcon, StarRating},
  data() {
    return {
      rentalObject: null,
      currentPage: 0,
      totalPages: null,
      pageSize: 6,
      images: [],
      bedsPerRoom: null,
    }
  },
  mounted() {
    const store = useStore();
    if(this.$route.params.type === "Boat") {
      axios.get("/RentalObjects/getBoat", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.$route.params.id,
          page: this.currentPage,
          pageSize: this.pageSize
        }
      })
      .then((response) => {
        this.rentalObject = response.data;
        this.currentPage = this.rentalObject.reviews.currentPage;
        this.totalPages = this.rentalObject.reviews.pages;
        this.getReviewPics();
      });
    }
    else if(this.$route.params.type === "Adventure") {
      axios.get("/RentalObjects/getAdventure", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.$route.params.id,
          page: this.currentPage,
          pageSize: this.pageSize
        }
      })
      .then((response) => {
        this.rentalObject = response.data;
        this.currentPage = this.rentalObject.reviews.currentPage;
        this.totalPages = this.rentalObject.reviews.pages;
        this.getReviewPics();
      });
    }
    else {
      axios.get("/RentalObjects/getVacationRental", {
        headers: {
          Authorization: "Bearer " + store.state.access_token
        },
        params: {
          id: this.$route.params.id,
          page: this.currentPage,
          pageSize: this.pageSize
        }
      })
      .then((response) => {
        this.rentalObject = response.data;
        this.currentPage = this.rentalObject.reviews.currentPage;
        this.totalPages = this.rentalObject.reviews.pages;
        this.getReviewPics();
        this.numOfBedsPerRoom();
      });
    }
  },
  computed: {
    isOwner() {
      return this.$store.getters.user === "fishingInstructor";
    },
    buttonText() {
      return this.isUserSubscribed ? "Subscribed!" : "Subscribe";
    },
    reviews() {
      return this.rentalObject.reviews.content;
    },
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
    rentalHasReviews() {
      return this.reviews.length > 0;
    },
    button1Content() {
      return this.currentPage === 0 ? this.currentPage+1: this.totalPages - this.currentPage === 1 ? this.currentPage - 1 : this.currentPage;
    },
    button2Content() {
      return this.currentPage === 0 ? this.currentPage+2: this.totalPages - this.currentPage === 1 ? this.currentPage : this.currentPage+1;
    },
    button3Content() {
      return this.currentPage === 0 ? this.currentPage+3: this.totalPages - this.currentPage === 1 ? this.currentPage + 1 : this.currentPage+2;
    }
  },
  methods: {
    numOfBedsPerRoom() {
      let result = "Beds per room: ";
      for(let i=0; i<this.rentalObject.rooms.length; i++) {
        result += this.rentalObject.rooms[i].beds;
        if(i === this.rentalObject.rooms.length - 1)
          break
        result += " + ";
      }
      this.bedsPerRoom = result;
    },
    getReviewPics() {
      this.images = [];
      let revs = this.rentalObject.reviews.content;
      for(let i=0; i<revs.length; i++) {
        if(!revs[i].author.photo) { this.images[i] = null; continue; }
        axios.get("/Photos/", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token,
          },
          params: {
            path: revs[i].author.photo,
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
    reloadReviews() {
      if(this.$route.params.type === "Boat") {
        axios.get("/RentalObjects/getBoat", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          },
          params: {
            id: this.$route.params.id,
            page: this.currentPage,
            pageSize: this.pageSize
          }
        })
            .then((response) => {
              this.rentalObject = response.data;
              this.currentPage = this.rentalObject.reviews.currentPage;
              this.totalPages = this.rentalObject.reviews.pages;
              this.getReviewPics();
            });
      }
      else if(this.$route.params.type === "Adventure") {
        axios.get("/RentalObjects/getAdventure", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          },
          params: {
            id: this.$route.params.id,
            page: this.currentPage,
            pageSize: this.pageSize
          }
        })
            .then((response) => {
              this.rentalObject = response.data;
              this.currentPage = this.rentalObject.reviews.currentPage;
              this.totalPages = this.rentalObject.reviews.pages;
              this.getReviewPics();
            });
      }
      else {
        axios.get("/RentalObjects/getVacationRental", {
          headers: {
            Authorization: "Bearer " + this.$store.getters.access_token
          },
          params: {
            id: this.$route.params.id,
            page: this.currentPage,
            pageSize: this.pageSize
          }
        })
            .then((response) => {
              this.rentalObject = response.data;
              this.currentPage = this.rentalObject.reviews.currentPage;
              this.totalPages = this.rentalObject.reviews.pages;
              this.getReviewPics();
            });
      }
    },
    subscribe() {
      axios.post("/RentalObjects/subscribe", { rentalId: this.rentalObject.id }, {
        headers: {
          Authorization: "Bearer " + this.getAccessToken,
        },
      })
      .then(() => {
        this.rentalObject.isUserSubscribed = true;
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
    getAuthorFullName(review) {
      return review.author.name + " " + review.author.surname;
    },
    formatReviewDate(reviewTime) {
      let date = new Date(reviewTime);
      return date.getDate() + '.' + (date.getMonth() + 1) + '.' +  date.getFullYear() + '. at '
          + date.getHours() + ':' + ( (date.getMinutes()<10?'0':'') + date.getMinutes() );
    },
    nextPage() {
      this.currentPage += 1;
      this.reloadReviews();
    },
    previousPage() {
      this.currentPage -= 1;
      this.reloadReviews();
    },
    numberedPage(buttonNumber) {
      if (buttonNumber === 1) {
        this.currentPage = this.button1Content - 1;
      } else if (buttonNumber === 2) {
        this.currentPage = this.button2Content - 1;
      } else if (buttonNumber === 3) {
        this.currentPage = this.button3Content - 1;
      }
      this.reloadReviews();
    }
  },
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
.page-link {
  background-color: white;
  border: 2px solid black;
}
div.rentalObjectName {
  display: flex;
}
div.lineTitle {
  display: flex;
}

hr {
  flex-grow: 1;
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

button.subscribe:disabled, button.subscribe[disabled] {
  background-color:lightgray;
  border: 2px solid darkgray;
  color: darkgray;
}

button.subscribe:disabled:hover, button.subscribe[disabled]:hover, button.subscribe:disabled:active, button.subscribe[disabled]:active, button.subscribe:disabled:focus, button.subscribe[disabled]:focus {
  background-color:lightgray;
  border: 2px solid darkgray;
  color: darkgray;
}

div.rentalBasicInfo {
  height: 60vh;
}
.review .profile-pic {
  width: 50px;
  height: 50px;
  object-fit: cover;
  object-position: center;
}

div.review {
  border-radius: 25px;
  border: 1px solid darkgray;
}

div.review:hover {
  border: 3px solid black;
}

div.accordion-body {
  max-height: 70vh;
  overflow-y: auto;
  background-color: lightgray;
  border-bottom: 1px solid lightgrey;
}

.accordion-button:not(.collapsed) {
  color: #008970;
  background-color: #f7f7f2;
}

div.review {
  background-color: white
}
</style>
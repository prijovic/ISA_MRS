<template>
  <div class="row main">
    <div class="accordion accordion-flush" id="accordionFlushExample">
      <div class="accordion-item">
        <h2 class="accordion-header" id="flush-headingThree" style="border: 1px solid black;">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
            <p class="h3"><strong>Special Offers</strong></p>
          </button>
        </h2>
        <div id="flush-collapseThree" class="accordion-collapse collapse show" aria-labelledby="flush-headingThree">
          <div class="accordion-body p-2 pb-1">

            <div v-if="rentalHasSpecialOffers" class="specialOffers">
              <div v-for="(offer, i) in sortedServices" :key="i" class="offer p-3 mb-1">
                <div class="offer-header p-0 m-0">
                  <div class="row text-center">
                    <p class="h4">
                      <font-awesome-icon class="me-2" icon="percent"></font-awesome-icon>
                      Action
                    </p>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-6">
                      <div class="row">
                        <p class="h6">
                          <font-awesome-icon class="me-2" icon="clock"></font-awesome-icon>
                          Time:&emsp;{{ getDateSpan(offer) }}
                        </p>
                      </div>
                      <div class="row">
                        <p class="h6">
                          <font-awesome-icon class="me-2" icon="percent"></font-awesome-icon>
                          Discount:&emsp;{{ offer.discount }}%
                        </p>
                      </div>
                      <div class="row">
                        <p class="h6">
                          <font-awesome-icon class="me-2" icon="user"></font-awesome-icon>
                          Capacity:&emsp;{{ offer.capacity }}
                        </p>
                      </div>
                    </div>
                    <div class="col-6 ps-4">
                      <div v-if="offerHasServices(offer)" class="row">
                        <p class="h6">
                          <strong><u>Services:</u></strong>
                        </p>
                        <p class="h6" v-for="(service, i) in offer.includedServices" :key="i">
                          ◆ {{ service.name }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <p class="h4">
                      Total:&emsp;${{ (getOfferPrice(offer)).toFixed(2) }}
                    </p>
                  </div>
                  <div class="row mt-3 d-flex justify-content-center align-items-center">
                    <button v-if="!isOwner" :id="'button-'+offer.id" :disabled="clientCantReserve || !canClientReserve" class="btn mb-2"
                            style="font-weight: 500; color: white; width: 80%" @click="book(offer)">
                      Book
                    </button>
                    <button v-else :id="'button-'+offer.id" class="btn mb-2"
                            style="font-weight: 500; color: white; width: 80%; background-color: #e23c52">
                      Delete
                    </button>
                  </div>

                </div>
                <div class="offer-body" style="text-align: justify; color: black;">
                </div>
              </div>
            </div>
            <div v-else class="text-center">
              <label><i>No special offers available.</i></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import { faPercent, faClock, faUser } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

library.add(faPercent, faClock, faUser);


export default {
  name: "RentalSpecialOffers",
  props: ["specialOffers", "rentalType", "price", "clientCantReserve", "isOwner", "canClientReserve"],
  components: {FontAwesomeIcon},
  data() {
    return {
      bookBtn: "Book",
      sortedServices: [],
    }
  },
  mounted() {
    this.sortedServices = this.specialOffers;
    this.sortedServices = this.sortedServices.sort((a, b) => new Date(b.initDate) < new Date(a.initDate) ? 1: -1);
  },
  computed: {
    rentalHasSpecialOffers() {
      return this.specialOffers.length > 0;
    },
  },
  methods: {
    book(offer) {
      let offerId = offer.id;
      let total = this.getOfferPrice(offer);
      console.log(offerId);
      console.log(total);
      axios.post("/Reservations/bookSpecialOffer", { offerId: offerId, total: total }, {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
      })
          .then(() => {
            let button = document.getElementById("button-"+offerId);
            button.disabled = true;
            button.textContent = "Booked!";
          })
          .catch(error => {
            if (error.response.status === 404) {
              this.$notify({
                title: "Invalid Status",
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
    getOfferPrice(offer) {
      let days = this.getNumberOfDays(offer);
      return (this.price * days)/100 * (100 - offer.discount);
    },
    getNumberOfDays(offer) {
      let initDate = offer.initDate, termDate = offer.termDate;
      let date1 = new Date(initDate), date2 = new Date(termDate);
      date1.setHours(0, 0, 0);
      date2.setHours(0, 0, 0);
      let timeDiff = date2.getTime() - date1.getTime();
      let daysDiff = timeDiff / (1000 * 3600 * 24);
      return daysDiff + 1;
    },
    offerHasServices(offer) {
      return offer.includedServices.length > 0;
    },
    getDateSpan(offer) {
      let initDate = offer.initDate, termDate = offer.termDate;
      let date1 = new Date(initDate), date2 = new Date(termDate);
      // date1.setHours(0, 0, 0);
      // date2.setHours(0, 0, 0);
      let dateDisplay = date1.getDate() + '.' + (date1.getMonth() + 1) + '.' +  date1.getFullYear() + '.';
      if(this.rentalType === "Adventure")
        dateDisplay += "  from " + date1.getHours() + ":" + (date1.getMinutes()<10?'0':'') + date1.getMinutes() + " to " + date2.getHours() + ":" + (date2.getMinutes()<10?'0':'') + date2.getMinutes();
      else {
        if(date1.getFullYear() !== date2.getFullYear() || date1.getMonth() !== date2.getMonth() || date1.getDate() !== date2.getDate())
          dateDisplay += ' - ' + date2.getDate() + '.' + (date2.getMonth() + 1) + '.' +  date2.getFullYear() + '.';
      }
      return dateDisplay;
    },
  }
}
</script>

<style scoped>
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

div.offer {
  border-radius: 40px;
  border: 2px solid darkgray;
}

div.offer:hover {
  background-color:#008970;
  color: white;
}

div.offer:hover button {
  background-color: black;
  color: white;
}

div.offer button:disabled {
  background-color: darkgray;
  color: black;
}

div.offer {
  background-color: white
}
</style>
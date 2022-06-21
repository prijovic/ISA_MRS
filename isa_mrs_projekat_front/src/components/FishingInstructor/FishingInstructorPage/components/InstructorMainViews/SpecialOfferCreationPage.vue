<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>Special Offer</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col-2"></div>
              <div class="col-8">

                <div class="row justify-content-center mt-4">
                  <label class="ms-3">Init Date</label>
                  <div class="container p-0 m-0 text-center" style="max-width: 400px">
                    <date-picker v-model="date" mode="dateTime" is24hr :firstDayOfWeek=2>
                    </date-picker>
                  </div>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="cancellation">Discount</label>
                  </div>
                  <input v-model="discount" type="number" step="any" min="0" max="100"  id="cancellation" class="form-control" placeholder="Discount in %" @input="discountIsEntered=true">
                  <p v-if='!discountIsEntered'>'Discount' is a mandatory field.</p>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="capacity">Capacity</label>
                  </div>
                  <input v-model="capacity" type="number" step="any" min="0"  id="capacity" class="form-control" placeholder="Max people" @input="capacityIsEntered=true">
                  <p v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
                </div>

                <div class="row mt-4 justify-content-center">
                  <div class="row justify-content-center">
                    <label for="services">Included Services</label>
                  </div>
                  <input class="form-control" v-model="service.name" type="text" id="services" placeholder="E.g. Driving boat" @keyup.enter="addService">
                  <div class="row justify-content-center">
                    <small class="form-text text-muted">
                      Press Enter to add service
                    </small>
                  </div>
                  <div class="row tag-container">
                    <p v-if='serviceAlreadyExists'>Service already exists.</p>
                    <div class="equipment-tag rounded-pill mt-1 ms-1 p-1" v-for="(pieceOfEquipment, index) in services" :key="index">
                      {{pieceOfEquipment.name}}
                      <button class="tag-button text-center" @click="removeService(pieceOfEquipment)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                    </div>
                  </div>
                </div>

              </div>
              <div class="col-2"></div>
              <div class="d-flex pt-3 justify-content-center">
                <router-link to="/fishingInstructor/adventures" class="btn btn-red mt-3 me-1">Cancel</router-link>
                <button type="button" class="btn mt-3" @click.prevent="submit">Submit</button>
              </div>
          </div>
        </div>
      </div>
      </div>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faMinus, faPlus, faPlusCircle, faX, faArrowRight} from "@fortawesome/free-solid-svg-icons";
import {DatePicker} from "v-calendar"
import axios from "axios";
import store from "@/store";

library.add(faPlus, faMinus, faX, faPlusCircle, faArrowRight);

export default {
  name: "SpecialOfferCreationPage",
  components: {FontAwesomeIcon,DatePicker},
  data() {
    return {
      date: new Date(),
      discount: null,
      capacity: null,
      services: [],
      service: {name: null},
      discountIsEntered: true,
      capacityIsEntered: true,
      serviceAlreadyExists: false
    }
  },
  methods: {
    submit() {
      axios.post("/RentalObjects/addSpecialOffer", {rentalId: this.$route.params.id, initDate: this.date, capacity: this.capacity, discount: this.discount, includedServices: this.services}, {
        headers: {
          Authorization: "Bearer " + store.getters.access_token,
        }
      })
      .then(() => {
        this.$notify( {
          title: "Successful creation",
          text: "You have successfully added a special offer.",
          position: "bottom right",
          type: "success"
        });
      })
      .catch(() => {
        this.$notify({
          title: "Server error",
          text: "Server is currently off. Please try again later...",
          type: "error"
        });
      })

    },
    addService() {
      if (this.service.name.trim() !== "") {
        if (this.services.indexOf(this.service.name.trim()) === -1) {
          this.services.push(this.service);
          this.service = {name: ""};
          this.serviceAlreadyExists=false;
        }
        else {
          this.serviceAlreadyExists=true;
        }
      }
    },
    removeService(service) {
      this.services.splice(this.services.indexOf(service), 1);
    },
  }
}
</script>

<style scoped>

label, .tag-container, small {
  max-width: 400px;
}

.equipment-tag {
  background-color: #008970;
  font-weight: bold;
  color: white;
  border: 2px solid #008970;
  width: fit-content;
  font-size: 12px;
}

.equipment-tag:hover   {
  background-color: white;
  color: #008970;
  border: 2px solid #008970;
  width: fit-content;
  font-size: 12px;
}

.equipment-tag:hover .tag-button {
  background-color: transparent;
  color: #008970;
  border: none;
}

.tag-button {
  background-color: transparent;
  color: white;
  border: none;
}

textarea {
  resize: none;
  width: 100%;
  text-align: justify;
  max-width: 400px;
}

input {
  max-width: 400px;
}

input::placeholder {
  color: grey;
}

h3 {
  text-align: center;
}

h4 {
  text-align: center;
}

p {
  color: #e23c52;
}

.input-group-text {
  background-color: transparent;
  border-right: none;
  color: #008970;
  display: table;
}

.input-group-text font-awesome-icon {
  display: table-cell;
  vertical-align: middle;
}

.input-group {
  max-width: 400px;
}
</style>
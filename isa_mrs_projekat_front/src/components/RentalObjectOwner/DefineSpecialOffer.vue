<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5 mb-5">
      <div class="container px-4 py-3 rounded form" spellcheck="false" >
        <div class="container-fluid">
          <h3>Special offer</h3>
          <div class="row main justify-content-center">
            <div class="row main">
              <div class="col-2"></div>
              <div class="col-8">

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label>Duration of special offer</label>
                  </div>
                  <div class="container p-0 m-0 text-center" style="max-width: 400px">
                    <date-picker v-model="range" mode="dateTime" is-range is24hr :firstDayOfWeek=2>
                    </date-picker>
                  </div>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="capacity">Capacity</label>
                  </div>
                  <input type="number" v-model="specialOffer.capacity" step="any" min="0"  id="capacity" class="form-control" placeholder="Max people" @input="capacityIsEntered=true">
                  <p v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="discount">Discount</label>
                  </div>
                  <input type="number" v-model="specialOffer.discount" step="any" min="0"  id="discount" class="form-control" placeholder="Discount" @input="discountIsEntered=true">
                  <p v-if='!discountIsEntered'>'Capacity' is a mandatory field.</p>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="includedServices">Included services</label>
                  </div>
                  <input class="form-control" v-model="includedService.name" type="text" id="includedServices" placeholder="E.g. WiFi" @keyup.enter="addIncludedService">
                  <div class="row justify-content-center">
                    <small class="form-text text-muted">
                      Press Enter to add service
                    </small>
                  </div>
                  <div class="row tag-container">
                    <p v-if='serviceAlreadyExists'>Service already exists.</p>
                    <div class="includedServices-tag rounded-pill mt-1 ms-1 p-1" v-for="(includedService, index) in specialOffer.includedServices" :key="index">
                      {{includedService.name}}
                      <button class="tag-button text-center" @click="removeIncludedService(includedService)"><font-awesome-icon icon="x" style="width: 9px; height: 9px"></font-awesome-icon></button>
                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
          <div class="d-flex pt-3 justify-content-center">
            <button type="button" class="btn mt-3" @click="submit">Define</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {faCalendarDay, faArrowRight} from "@fortawesome/free-solid-svg-icons";
import {library} from "@fortawesome/fontawesome-svg-core";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {DatePicker} from "v-calendar"
import axios from "axios";
import store from "@/store";
library.add(faCalendarDay, faArrowRight)

export default {
  name: "DefineSpecialOffer",
  components: {
    DatePicker,
    FontAwesomeIcon
  },
  data() {
    return {
      specialOffer:{
        rentalObjectId: null,
        initDate: null,
        termDate: null,
        capacity: null,
        discount: null,
        includedServices: []
      },
      range: {
        start: null,
        end: null
      },
      includedService: {name:""},
      date: null,
      serviceAlreadyExists: false,
      capacityIsEntered: true,
      discountIsEntered: true
    };
  },
  computed: {
    accessToken() {
      return store.state.access_token;
    },
    isCapacityEntered() {
      return Boolean(this.specialOffer.capacity);
    },
    isDiscountEntered() {
      return Boolean(this.specialOffer.discount);
    }
  },
  methods: {
    addIncludedService() {
      if (this.includedService.name.trim() !== "") {
        if (this.specialOffer.includedServices.indexOf(this.includedService.name.trim()) === -1) {
          this.specialOffer.includedServices.push(this.includedService);
          this.includedService = {name: ""};
          this.serviceAlreadyExists=false;
        }
        else {
          this.serviceAlreadyExists=true;
        }
      }
    },
    removeIncludedService(serviceName) {
      this.specialOffer.includedServices.splice(this.specialOffer.includedServices.indexOf(serviceName), 1);
    },
    isDataEntered(){
      if(!this.isCapacityEntered){
        this.capacityIsEntered = false;
        return false;
      }
      if(!this.isDiscountEntered){
        this.discountIsEntered = false;
        return false;
      }
      return true;
    },
    submit(){
      if(this.isDataEntered()){
        this.confirm();
      }
    },
    confirm() {
      let specialOffer = this.specialOffer;
      specialOffer.rentalObjectId = this.$route.params.id;
      specialOffer.initDate = this.range.start;
      specialOffer.termDate = this.range.end;
      axios.post("/RentalObjects/defineSpecialOffer", specialOffer,
          {
            headers: {
              Authorization: "Bearer " + this.accessToken,
            },
            params: {
              id: this.$route.params.id
            }
          })
          .then(()=> {
            this.$notify({
              title: "Define special offer",
              text: "You have successfully defined the special offer.",
              type: "success"
            });
            this.$emit("requestManaged");
          })
          .catch((error) =>{
            if (error.response.status===404) {
              this.$notify({
                title: "Rental Object Not Found",
                text: "Rental object the specified id does not exist!" ,
                type: "warn"
              })
            } else if (error.response.status===500) {
              this.$notify({
                title: "Internal Server Error",
                text: "Something went wrong on the server! Please try again later...",
                type: "error"
              })
            }
          })
    }
  }
}
</script>

<style scoped>

.input-group-text {
  background-color: transparent;
  border-right: none;
  color: #008970;
  display: table;
}

.form-control {
  border-left: none;
}

.input-group-text font-awesome-icon {
  display: table-cell;
  vertical-align: middle;
}

.input-group {
  max-width: 400px;
}

h3 {
  text-align: center;
}

label, small, .tag-container, p {
  max-width: 400px;
}

input {
  max-width: 400px;
}

input::placeholder {
  color: grey;
}

.includedServices-tag {
  background-color: #008970;
  color: #99EEDF;
  width: fit-content;
  font-size: 12px;
}

.includedServices-tag:hover   {
  background-color: #99EEDF;
  color: #008970;
  width: fit-content;
  font-size: 12px;
}

.includedServices-tag:hover .tag-button {
  background-color: transparent;
  color: #008970;
  border: none;
}

.tag-button {
  background-color: transparent;
  color: #99EEDF;
  border: none;
}

p {
  color: #e23c52;
}
</style>
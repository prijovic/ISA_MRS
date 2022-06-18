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
                    <label for="start">Duration of special offer</label>
                  </div>
                  <div class="container p-0 m-0">
                    <form class="p-0 m-0" @submit.prevent>
                      <date-picker v-model="range" mode="dateTime" :masks="masks" is-range is24hr @popoverDidHide="printaj">
                        <template v-slot="{ inputValue, inputEvents, isDragging }">
                          <div class="row p-0 m-0">
                            <div class="form-inline d-flex justify-content-center p-0 m-0">
                              <div class="row" style="max-width: 400px">
                                <div class="col input-group">
                                  <div class="input-group-prepend">
                                  <span class="input-group-text" id="start">
                                    From:
                                  </span>
                                  </div>
                                  <input
                                      class="form-control"
                                      :class="isDragging ? 'text-gray-600' : 'text-gray-900'"
                                      :value="inputValue.start"
                                      v-on="inputEvents.start"/>
                                </div>
                                <div class="col-1 p-0 m-0" style="display: table">
                                  <div style="display: table-cell; vertical-align: middle; horiz-align: center">
                                    <font-awesome-icon icon="arrow-right" style="color:#008970; height: 25px"></font-awesome-icon>
                                  </div>
                                </div>
                                <div class="col input-group">
                                  <div class="input-group-prepend">
                                  <span class="input-group-text" id="end">
                                    To:
                                  </span>
                                  </div>
                                  <input
                                      class="form-control"
                                      :class="isDragging ? 'text-gray-600' : 'text-gray-900'"
                                      :value="inputValue.end"
                                      v-on="inputEvents.end"/>
                                </div>
                              </div>
                            </div>
                          </div>
                        </template>
                      </date-picker>
                    </form>
                  </div>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="capacity">Capacity</label>
                  </div>
                  <input type="number" v-model="capacity" step="any" min="0"  id="capacity" class="form-control" placeholder="Max people" @input="capacityIsEntered=true">
                  <p v-if='!capacityIsEntered'>'Capacity' is a mandatory field.</p>
                </div>

                <div class="row justify-content-center">
                  <div class="row justify-content-center">
                    <label for="discount">Discount</label>
                  </div>
                  <input type="number" v-model="discount" step="any" min="0"  id="discount" class="form-control" placeholder="Discount" @input="discountIsEntered=true">
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
                    <div class="includedServices-tag rounded-pill mt-1 ms-1 p-1" v-for="(includedService, index) in includedServices" :key="index">
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
  props: ["rental"],
  name: "DefineSpecialOffer",
  components: {
    DatePicker,
    FontAwesomeIcon
  },
  setup(props) {
    if (props.rental) {
      console.log(props.rental);
    }
  },
  data() {
    return {
      range: {
        start: null,
        end: null
      },
      capacity: null,
      discount: null,
      includedServices: [],
      includedService: {name:""},
      masks: {
        input: 'YYYY-MM-DD hh:mm',
      },
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
    isCapacityEntered(){
      return Boolean(this.capacity);
    },
    isDiscountEntered(){
      return Boolean(this.discount);
    },
    getRentalObjectId() {
      return this.rentalId;
    },
    startDate() {
      return new Date().setDate(new Date().getDate());
    },
    endDate() {
      const date = new Date();
      return date.setFullYear(date.getFullYear() + 1)
    },
    dates() {
      return this.days.map(day => day.date);
    },
    attributes() {
      return this.dates.map(date => {
        return {
          highlight: true,
          dates: date,
        }
      });
    },
    daySpan() {
      let result = 0;
      if (this.days.length > 1) {
        let max = new Date(Math.max(...this.dates));
        let min = new Date(Math.min(...this.dates));
        let difference = Math.abs(max-min);
        result = difference/(1000 * 3600 * 24);
      }
      else {
        result = this.days.length;
      }
      return result;
    },
    disableWeeklyRepeat() {
      let daySpan = this.daySpan;
      if (daySpan!==0) {
        return daySpan>6;
      }
      return true;
    },
    disableMonthlyRepeat() {
      let daySpan = this.daySpan;
      if (daySpan!==0) {
        return daySpan>30;
      }
      return true;
    }
  },
  methods: {
    printaj() {
      console.log(this.days)
    },
    onDayClick(day) {
      console.log(day);
      const idx = this.days.findIndex(d => d.id === day.id);
      if (idx >= 0) {
        this.days.splice(idx, 1);
      } else if (this.startDate <= day.date && day.date < this.endDate){
        this.days.push({
          id: day.id,
          date: day.date,
        });
      }
    },
    addDay(date) {
      let id = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
      this.days.push({
        id: id,
        date: date,
      });
    },
    repeatWeekly() {
      const dates = this.dates;
      dates.forEach(date => {
        let new_date = new Date(date.getTime());
        new_date.setDate(new_date.getDate() + 7);
        while (new_date < this.endDate) {
          this.addDay(new Date(new_date.getTime()));
          new_date.setDate(new_date.getDate() + 7);
        }
      });
    },
    repeatMonthly() {
      const dates = this.dates;
      dates.forEach(date => {
        let new_date = new Date(date.getTime());
        new_date.setMonth(new_date.getMonth() + 1);
        while (new_date < this.endDate) {
          this.addDay(new Date(new_date.getTime()));
          new_date.setMonth(new_date.getMonth() + 1);
        }
      });
    },
    clear() {
      this.days = [];
      this.date = null;
    },
    addIncludedService() {
      if (this.includedService.name.trim() !== "") {
        if (this.includedServices.indexOf(this.includedService.name.trim()) === -1) {
          this.includedServices.push(this.includedService);
          this.includedService = {name: ""};
          this.serviceAlreadyExists=false;
        }
        else {
          this.serviceAlreadyExists=true;
        }
      }
    },
    removeIncludedService(serviceName) {
      this.includedServices.splice(this.includedServices.indexOf(serviceName), 1);
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
      console.log(this.id);
      axios.post("/RentalObjects/defineSpecialOffer",
          {
            id: this.$route.params.id,
            initDate: this.range.start,
            termDate: this.range.end,
            capacity: this.capacity,
            discount: this.discount,
            includedServices: this.includedServices
          },
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
              title: "Availability period settings",
              text: "",
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
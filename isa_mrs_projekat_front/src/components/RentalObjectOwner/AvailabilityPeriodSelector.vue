<template>
  <div class="container p-0 m-0">
    <form class="p-0 m-0" @submit.prevent>
      <date-picker v-model="range" mode="dateTime" :masks="masks" is-range is24hr>
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
                      v-on="inputEvents.start"
                      @change="printaj"/>
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
  components: {
    DatePicker, FontAwesomeIcon
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
      masks: {
        input: 'YYYY-MM-DD hh:mm',
      },
      date: null
    };
  },
  computed: {
    accessToken() {
      return store.state.access_token;
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
      console.log(this.range)
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
    confirm() {
      axios.post("/RentalObjects/periods",
       {
              id: this.getRentalObjectId,
              dates: this.dates
            },
      {
              headers: {
                          Authorization: "Bearer " + this.accessToken,
                        }
            })
          .then((response)=> {
            const message = "Availability period is successfully set for " + response.data.name + ".";
            this.$notify({
              title: "Availability period settings",
              text: message,
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
</style>
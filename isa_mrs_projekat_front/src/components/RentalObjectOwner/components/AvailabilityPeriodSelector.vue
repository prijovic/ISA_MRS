<template>
  <div class="container-fluid">
    <div>
      <date-picker v-model="date" :attributes="attributes" @dayclick="onDayClick"
                   :available-dates='{start: startDate, end: endDate}'
                   :columns="2" color="green" :first-day-of-week="2">
      </date-picker>
    </div>
    <div class="flex">
      <button class="btn btn-default" style="margin-right: 2vh" :disabled="daySpan===0" @click.prevent="confirm">Confirm</button>
      <button class="btn btn-default" style="margin-right: 2vh" :disabled="disableWeeklyRepeat" @click.prevent="repeatWeekly">Repeat Weekly</button>
      <button class="btn btn-default" :disabled="disableMonthlyRepeat" @click.prevent="repeatMonthly">Repeat Monthly</button>
      <button class="btn btn-default" style="margin-left: 2vh" :disabled="daySpan===0" @click.prevent="clear">Clear</button>
    </div>
  </div>
</template>

<script>
import {DatePicker} from "v-calendar"
import axios from "axios";
import store from "@/store";

export default {
  props: ["rentalId"],
  components: {
    DatePicker
  },
  data() {
    return {
      days: [],
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
      return new Date();
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
    onDayClick(day) {
      const idx = this.days.findIndex(d => d.id === day.id);
      if (idx >= 0) {
        this.days.splice(idx, 1);
      } else {
        if (this.startDate < day.date && day.date < this.endDate){
          this.addDay(day.date);
        }
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

<style>
  .btn {
    margin-top: 15px;
    background-color: #378220;
    color: #f7f7f2;
  }

  .btn:active, .btn:hover, .btn:focus {
    background-color: #f7f7f2;
    color: #378220;
    border: 1px solid #3F9725;
  }
</style>
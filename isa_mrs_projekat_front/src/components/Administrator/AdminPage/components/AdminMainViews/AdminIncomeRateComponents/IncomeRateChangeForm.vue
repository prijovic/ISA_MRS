<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <form class="px-4 py-3 rounded content-center form">
        <div class="row">
          <div class="col-2"></div>
          <div class="col-8">
            <div class="form-text">
              <h3>Income Rate Settings</h3>
            </div>
            <div class="form-group">
              <label for="boatRate">Boat Reservation:</label>
              <input type="number" step="any" min="0" max="100" v-model="boatRate.value" id="boatRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!boatRateCorrect'>{{boatRateMessage}}</p>

            </div>
            <div class="form-group">
              <label for="adventureRate">Adventure Reservation:</label>
              <input type="number" step="any" min="0" max="100" v-model="adventureRate.value" id="adventureRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!adventureRateCorrect'>{{adventureRateMessage}}</p>
            </div>
            <div class="form-group">
              <label for="houseRate">Vacation House Reservation:</label>
              <input type="number" step="any" min="0" max="100" v-model="houseRate.value" id="houseRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!houseRateCorrect'>{{houseRateMessage}}</p>
            </div>
            <div class="text-center pt-3">
              <button class="btn" @click.prevent='confirmed'>
                Confirm
              </button>
            </div>
          </div>
          <div class="col-2"></div>
        </div>
      </form>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<script>
import {useStore} from "vuex";
import axios from "axios";
import store from "@/store";

export default {
  name: "IncomeRateChangeForm",
  data() {
    return {
      boatRate: {
        value: 0,
        feeType: null,
      },
      adventureRate: {
        value: 0,
        feeType: null,
      },
      houseRate: {
        value: 0,
        feeType: null,
      },
      boatRateCorrect: true,
      boatRateMessage: null,
      adventureRateCorrect: true,
      adventureRateMessage: null,
      houseRateCorrect: true,
      houseRateMessage: null
    }
  },
  mounted() {
    const store = useStore();
    axios.get("/Profits/fees", {headers: {
        Authorization: "Bearer " + store.state.access_token,
      }}
    ).then(response => {
      const fees = response.data;
      fees.forEach((fee) => {
        let result = fee.value;
        if (fee.rentalObjectType === "Boat") {
          this.boatRate.value = result;
        } else if (fee.rentalObjectType === "Adventure") {
          this.adventureRate.value = result;
        } else {
          this.houseRate.value = result;
        }
      });
    });
  },
  computed: {
    body() {
      return [
        {
          rentalObjectType: "Boat",
          value: this.boatRate.value
        },
        {
          rentalObjectType: "Adventure",
          value: this.adventureRate.value
        },
        {
          rentalObjectType: "VacationRental",
          value: this.houseRate.value
        }
      ];
    },
    accessToken() {
      return store.state.access_token;
    }
  },
  methods: {
    confirmed() {
      this.checkAdventure();
      this.checkBoat();
      this.checkHouse();
      if (this.boatRateCorrect && this.houseRateCorrect && this.adventureRateCorrect) {
        const fees = this.body;
        console.log(fees);
        axios.post("/Profits/fees", {fees}, {
          headers: {
            Authorization: "Bearer " + this.accessToken
          }
        })
        .then(() => {
          this.$notify({
            title: "Income Rate Change Status",
            text: "Income rate change was successful.",
            position: "bottom right",
            type: "success"
          });
          this.$router.push({name:"AdminDashboard"});
        })
        .catch((error) =>{
          if (error.response.status===400) {
            this.$notify({
              title: "Bad Request",
              text: "Data provided to the server was not valid!" ,
              type: "warn"
            })
          } else if (error.response.status===500) {
            this.$notify({
              title: "Internal Server Error",
              text: "Something went wrong on the server! Please try again later...",
              type: "error"
            })
          }
        });
      }
    },
    checkAdventure() {
      if (this.adventureRate.value < 0 || this.adventureRate.value > 100) {
        this.adventureRateCorrect = false;
        this.adventureRateMessage = "Value must be between 0 and 100!"
      } else {
        this.adventureRateCorrect = true;
        this.adventureRateMessage = null;
      }
    },
    checkBoat() {
      if (this.boatRate.value < 0 || this.boatRate.value > 100) {
        this.boatRateCorrect = false;
        this.boatRateMessage = "Value must be between 0 and 100!"
      } else {
        this.boatRateCorrect = true;
        this.boatRateMessage = null;
      }
    },
    checkHouse() {
      if (this.houseRate.value < 0 || this.houseRate.value > 100) {
        this.houseRateCorrect = false;
        this.houseRateMessage = "Value must be between 0 and 100!"
      } else {
        this.houseRateCorrect = true;
        this.houseRateMessage = null;
      }
    }
  }
}
</script>

<style scoped>

h3 {
  text-align: center;
}

p {
  color: #e23c52;
}
</style>
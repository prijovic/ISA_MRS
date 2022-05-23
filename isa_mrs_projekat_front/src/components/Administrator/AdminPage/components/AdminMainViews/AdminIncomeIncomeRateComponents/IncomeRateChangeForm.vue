<template>
  <div class="row">
    <div class="col-2"></div>
    <div class="col-8 pt-5">
      <form class="px-4 py-3 rounded content-center">
        <div class="row">
          <div class="col-2"></div>
          <div class="col-8">
            <div class="form-text">
              <h3>Income Rate Settings</h3>
            </div>
            <div class="form-group">
              <label for="boatRate">Boat Reservation:</label>
              <input type="number" step="any" min="0" :max="boatMax" v-model="boatRate.value" id="boatRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!boatRateCorrect'>{{boatRateMessage}}</p>
              <select class="form-control" style="width: max-content" v-model="boatRate.feeType" @change="annulBoat">
                <option value="Percentile" selected="selected">Percentile</option>
                <option value="Fixed">Fixed</option>
                <option value="Free">Free</option>
              </select>
            </div>
            <div class="form-group">
              <label for="adventureRate">Adventure Reservation:</label>
              <input type="number" step="any" min="0" :max="adventureMax" v-model="adventureRate.value" id="adventureRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!adventureRateCorrect'>{{adventureRateMessage}}</p>
              <select class="form-control" style="width: max-content" v-model="adventureRate.feeType" @change="annulAdventure">
                <option value="Percentile" selected="selected">Percentile</option>
                <option value="Fixed">Fixed</option>
                <option value="Free">Free</option>
              </select>
            </div>
            <div class="form-group">
              <label for="houseRate">Vacation House Reservation:</label>
              <input type="number" step="any" min="0" :max="houseMax" v-model="houseRate.value" id="houseRate" class="form-control col-sm-auto col-lg-4">
              <p style="color:red" v-if='!houseRateCorrect'>{{houseRateMessage}}</p>
              <select class="form-control" style="width: max-content" v-model="houseRate.feeType" @change="annulHouse">
                <option value="Percentile" selected="selected">Percentile</option>
                <option value="Fixed">Fixed</option>
                <option value="Free">Free</option>
              </select>
            </div>
            <div class="text-center">
              <button class="btn btn-default" @click.prevent='confirmed'>
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
        let type = fee.feeType;
        if (fee.rentalObjectType === "Boat") {
          this.boatRate.value = result;
          this.boatRate.feeType = type;
        } else if (fee.rentalObjectType === "Adventure") {
          this.adventureRate.value = result;
          this.adventureRate.feeType = type;
        } else {
          this.houseRate.value = result;
          this.houseRate.feeType = type;
        }
      });
    });
  },
  computed: {
    body() {
      return [
        {
          rentalObjectType: "Boat",
          feeType: this.boatRate.feeType,
          value: this.boatRate.value
        },
        {
          rentalObjectType: "Adventure",
          feeType: this.adventureRate.feeType,
          value: this.adventureRate.value
        },
        {
          rentalObjectType: "VacationRental",
          feeType: this.houseRate.feeType,
          value: this.houseRate.value
        }
      ];
    },
    accessToken() {
      return store.state.access_token;
    },
    boatMax() {
      if (this.boatRate.feeType === "Percentile") {
        return 100;
      } else if (this.boatRate.feeType === "Free") {
        return 0;
      } else {
        return "auto";
      }
    },
    adventureMax() {
      if (this.adventureRate.feeType === "Percentile") {
        return 100;
      } else if (this.adventureRate.feeType === "Free") {
        return 0;
      } else {
        return "auto";
      }
    },
    houseMax() {
      if (this.houseRate.feeType === "Percentile") {
        return 100;
      } else if (this.houseRate.feeType === "Free") {
        return 0;
      } else {
        return "auto";
      }
    }
  },
  methods: {
    annulAdventure() {
      if (this.adventureRate.feeType === "Free") {
        this.adventureRate.value = 0;
      }
    },
    annulBoat() {
      if (this.boatRate.feeType === "Free") {
        this.boatRate.value = 0;
      }
    },
    annulHouse() {
      if (this.houseRate.feeType === "Free") {
        this.houseRate.value = 0;
      }
    },
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
      if (this.adventureRate.feeType === null) {
        this.adventureRateCorrect = false;
        this.adventureRateMessage = "Income type must not be empty!";
      } else if (this.adventureRate.feeType === "Free" && this.adventureRate.value !== 0) {
        this.adventureRateCorrect = false;
        this.adventureRateMessage = "Value must be 0!";
      } else if (this.adventureRate.feeType === "Percentile" && (this.adventureRate.value < 0 || this.adventureRate.value > 100)) {
        this.adventureRateCorrect = false;
        this.adventureRateMessage = "Value must be between 0 and 100!"
      } else if (this.adventureRate.value < 0) {
        this.adventureRateCorrect = false;
        this.adventureRateMessage = "Value must be a positive number!"
      } else {
        this.adventureRateCorrect = true;
        this.adventureRateMessage = null;
      }
    },
    checkBoat() {
      if (this.boatRate.feeType === null) {
        this.boatRateCorrect = false;
        this.boatRateMessage = "Income type must not be empty!";
      } else if (this.boatRate.feeType === "Free" && this.boatRate.value !== 0) {
        this.boatRateCorrect = false;
        this.boatRateMessage = "Value must be 0!";
      } else if (this.boatRate.feeType === "Percentile" && (this.boatRate.value < 0 || this.boatRate.value > 100)) {
        this.boatRateCorrect = false;
        this.boatRateMessage = "Value must be between 0 and 100!"
      } else if (this.boatRate.value < 0) {
        this.boatRateCorrect = false;
        this.boatRateMessage = "Value must be a positive number!"
      } else {
        this.boatRateCorrect = true;
        this.boatRateMessage = null;
      }
    },
    checkHouse() {
      if (this.houseRate.feeType === null) {
        this.houseRateCorrect = false;
        this.houseRateMessage = "Income type must not be empty!";
      } else if (this.houseRate.feeType === "Free" && this.houseRate.value !== 0) {
        this.houseRateCorrect = false;
        this.houseRateMessage = "Value must be 0!";
      } else if (this.houseRate.feeType === "Percentile" && (this.houseRate.value < 0 || this.houseRate.value > 100)) {
        this.houseRateCorrect = false;
        this.houseRateMessage = "Value must be between 0 and 100!"
      } else if (this.houseRate.value < 0) {
        this.houseRateCorrect = false;
        this.houseRateMessage = "Value must be a positive number!"
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

form {
  outline: solid 2px #3f5b25;
  margin-top: 10px;
  color: #3f5b25;
}

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
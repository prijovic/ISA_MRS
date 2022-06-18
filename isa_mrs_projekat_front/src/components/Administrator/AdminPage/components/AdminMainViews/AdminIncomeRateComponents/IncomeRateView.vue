<template>
  <div class="row container rounded shadow d-table p-3">
    <div class="row d-table-row">
      <p class="d-table-cell text-center fw-bold fs-3">Income Rate</p>
      <router-link class="btn btn-transparent d-table-cell text-end" to="/admin/incomeRates">
        <font-awesome-icon icon="pencil"></font-awesome-icon>
      </router-link>
    </div>
    <div class="row container-fluid d-table mt-2 p-0">
      <div class="row d-table-row">
        <p class="d-table-cell text-start fs-5">Boat:</p>
        <p class="d-table-cell text-end fs-5 p-0">{{getBoatRate}}</p>
      </div>
      <div class="row d-table-row">
        <p class="d-table-cell text-start fs-5">Adventure:</p>
        <p class="d-table-cell text-end fs-5 p-0">{{getAdventureRate}}</p>
      </div>
      <div class="row d-table-row">
        <p class="d-table-cell text-start fs-5">Vacation House:</p>
        <p class="d-table-cell text-end fs-5 p-0">{{getHouseRate}}</p>
      </div>
    </div>

  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPencil} from "@fortawesome/free-solid-svg-icons";
import {useStore} from "vuex";
import axios from "axios";

library.add(faPencil);

export default {
  name: "IncomeRateView",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      boatRate: null,
      houseRate: null,
      adventureRate: null
    }
  },
  computed: {
    getBoatRate() {
      return this.boatRate?this.boatRate:"Not set";
    },
    getHouseRate() {
      return this.houseRate?this.houseRate:"Not set";
    },
    getAdventureRate() {
      return this.adventureRate?this.adventureRate:"Not set";
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
        if (result === 0)  {
          result = "Free";
        }
        else {
          result += "%";
        }
        if (fee.rentalObjectType === "Boat") {
          this.boatRate = result;
        } else if (fee.rentalObjectType === "Adventure") {
          this.adventureRate = result;
        } else {
          this.houseRate = result;
        }
      });
    });
  }
}
</script>

<style scoped>
  p {
    color: #008970;
  }

  p.text-start {
    color: black;
  }
</style>
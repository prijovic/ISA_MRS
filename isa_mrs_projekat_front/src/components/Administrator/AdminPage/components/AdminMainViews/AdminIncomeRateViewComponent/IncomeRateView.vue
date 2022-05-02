<template>
  <div class="row container rounded">
    <div class="row m-0 p-0">
      <ul>
        <li><h3>Income Rate</h3></li>
        <li>
          <button class="btn btn-default">
            <font-awesome-icon icon="pencil"></font-awesome-icon>
          </button>
        </li>
      </ul>
    </div>
    <hr>
    <div class="row m-1">
      <div class="one-line">
        <h5>Boat Reservation:</h5>
        <h5>{{getBoatRate}}</h5>
      </div>
    </div>
    <div class="row m-1">
      <div class="one-line">
        <h5>Adventure Reservation: </h5>
        <h5>{{getAdventureRate}}</h5>
      </div>
    </div>
    <div class="row m-1">
      <div class="one-line">
        <h5>Vacation House Reservation: </h5>
        <h5>{{getHouseRate}}</h5>
      </div>
    </div>
  </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPencil} from "@fortawesome/free-solid-svg-icons";
// import {useStore} from "vuex";
// import axios from "axios";

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
    // const store = useStore();
    // axios.get("/Requests/requests", {headers: {
    //     Authorization: "Bearer " + store.state.access_token,
    //   },
    //   params: {
    //     page: this.currentPage,
    //     pageSize: this.pageSize,
    //     field: "timeStamp",
    //     types: "all"
    //   }},
    // ).then(response => {
    //   this.requests = response.data.content;
    //   this.numberOfPages = response.data.pages;
    // });
  }
}
</script>

<style scoped>
  .container {
    outline: solid 2px #3f5b25;
    margin-top: 10px;
    color: #3f5b25;
    padding: 0;
  }

  ul {
    position: relative;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin: 0;
    padding: 0;
  }

  li {
    display: flex;
  }

  li:last-child {
    position: absolute;
    top: 0;
    right: 0;
  }

  .btn-default {
    cursor: pointer;
    font-weight: 400;
    user-select: none;

    color: #3f5b25;
    text-decoration: none;
  }

  .btn-default:hover, .btn-default:active, .btn-default:focus {
    outline-style: none;
  }

  .one-line {
    display: flex;
    justify-content: space-between;
    padding: 0;
    margin: 0;
  }
</style>
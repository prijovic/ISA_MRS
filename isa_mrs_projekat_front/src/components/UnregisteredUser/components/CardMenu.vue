<template>
    <div v-if="rentalObjects" class="row mt-5 cardMenu">
        <CardView v-for="(rental, i) in rentalObjects" :key="i" :rental="rental"></CardView>
    </div>
</template>

<script>
import axios from "axios";
import CardView from "@/components/UnregisteredUser/components/CardView";
import {useStore} from "vuex";


export default {
  name: "CardMenu",
  components: {
    CardView
  },
  data() {
    return {
      rentalObjects: null,
    }
  },
  mounted() {
    const store = useStore();
    let type = this.$route.params.type;
    if(type === "Boat") {
      console.log("Trazimo brodove od servera")
      axios.get("/RentalObjects/getBoats", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(type === "Adventure") {
      console.log("Trazimo avanture od servera")
      axios.get("/RentalObjects/getAdventures", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(type === "VacationRental"){
      console.log("Trazimo rentale od servera")
      axios.get("/RentalObjects/getVacationRentals", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          page: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    console.log(this.rentalObject === "Boat")
    console.log("Jel valja:" + this.rentalObject === "Boat");
  }
}
</script>

<style scoped>
div.cardMenu {
  text-align: center;
  justify-content: space-around;
}
</style>
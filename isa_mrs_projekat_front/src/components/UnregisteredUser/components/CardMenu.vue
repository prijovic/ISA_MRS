<template>
    <div v-if="rentalObjects" class="row ms-1" style="text-align: center;">
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
      objectType: null,
    }
  },
  mounted() {
    let rentalType = this.$route.fullPath.split("/")[2];
    console.log(rentalType);
    this.objectType = rentalType.charAt(0).toUpperCase() + rentalType.slice(1, -1);
    const store = useStore();
    console.log(this.objectType);
    console.log(store.state.access_token)
    if(this.objectType === "Boat") {
      this.rentalObjects = axios.get("/RentalObjects/getBoats", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          offset: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(this.objectType === "Adventure") {
      this.rentalObjects = axios.get("/RentalObjects/getAdventures", {
        headers: {
          Authorization: "Bearer " + store.state.access_token,
        },
        params: {
          offset: 0,
          pageSize: 10,
          field: "name"
        }
      }).then(response => {
        this.rentalObjects = response.data.content;
      });
    }
    else if(this.objectType === "VacationRental"){
      console.log(store.state.access_token);
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
  }
}
</script>

<style scoped>

</style>
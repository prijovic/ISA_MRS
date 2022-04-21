<template>
    <div v-if="rentalObjects" class="row" style="text-align: center;">
        <CardView v-for="(rental, i) in rentalObjects" :key="i" :rental="rental"></CardView>
    </div>
</template>

<script>
import axios from "axios";
import CardView from "@/components/UnregisteredUser/components/CardView";


export default {
  name: "CardMenu",
  components: {
    CardView
  },
  data() {
    return {
      rentalObjects: null,
      rentalObjectType: this.$router.currentRoute,
    }
  },
  mounted() {
    if(this.rentalObjectType === "Boat") {
      this.rentalObjects = axios.get("/RentalObjects/getBoats", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          offset: 0,
          pageSize: 10,
        }
      });
    }
    else if(this.rentalObjectType === "Adventure") {
      this.rentalObjects = axios.get("/RentalObjects/getAdventures", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          offset: 0,
          pageSize: 10,
        }
      });
    }
    else {
      this.rentalObjects = axios.get("/RentalObjects/getVacationRentals", {
        headers: {
          Authorization: "Bearer " + this.$store.getters.access_token
        },
        params: {
          offset: 0,
          pageSize: 10,
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
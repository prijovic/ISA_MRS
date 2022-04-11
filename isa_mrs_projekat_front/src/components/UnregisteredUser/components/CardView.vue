<template>
    <div v-if="rentalObjects" class="row" style="text-align: center;">
        <div v-for="(rental, i) in rentalObjects" :key="i" class="card col-xl-3 col-lg-4 col-md-6 col-sm-6 col-xs-12 p-0">
              <a href="">><img class="card-img-top" :src="rental.photos[0].photo" alt=""></a>
              <div class="card-body">
                <div class="align-items-center" style="display: flex;">
                    <h1 class="card-title">{{ rental.name }}</h1>
                   <hr class="ms-1">
                </div>
                <div class="p-1" style="text-align: left;">
                    <p class="h5">
                       <i class="bi bi-people-fill me-1"></i>{{ "Capacity: " + rental.capacity }}
                    </p>
                </div>
                <div class="row mt-1">
                    <div class="col d-flex justify-content-center">
                       <p class="h4"><strong>Grade: 0★</strong></p>
                    </div>
                    <div class="col d-flex justify-content-center">
                        <p class="h4"><strong>{{ "Price: $" + rental.price }}</strong></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CardView",
  data() {
    return {
      rentalObjects: null,
      rentalObjectType: this.$router.currentRoute
    }
  },
  async setup() {
    if(this.rentalObjectType === "Boat") {
      this.rentalObjects = await axios.get("/RentalObjects/getBoats");
    }
    else if(this.rentalObjectType === "Adventure") {
      this.rentalObjects = await axios.get("/RentalObjects/getAdventures");
    }
    else {
      this.rentalObjects = await axios.get("/RentalObjects/getVacationRentals");
    }
  }
}
</script>

<style scoped>

</style>
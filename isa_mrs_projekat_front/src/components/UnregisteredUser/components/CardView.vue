<template>
  <div class="card col-xl-3 col-lg-4 col-md-6 col-sm-6 col-xs-12 p-0">
    <!--
    <router-link :to="{path: to}" class="link" :class="{active: isActive}">
      <ThePhoto class="card-img-top" :photo="rental.photos[0].photo" alt=""/>
    </router-link> -->

    <a>
      <ThePhoto class="card-img-top" :photo="rental.photos[0].photo" alt=""/>
    </a>

    <div class="card-body">
      <div class="align-items-center" style="display: flex;">
        <h1 class="card-title">{{ rental.name }}</h1>
        <hr class="ms-1">
      </div>

      <div v-if="isVacationRental" class="row">
        <div class="col main-col d-flex justify-content-center" title="Capacity">
          <p class="h5">
            <i class="bi bi-people-fill me-1"></i><strong>{{ rental.capacity }}</strong>
          </p>
        </div>
        <div class="col main-col d-flex justify-content-center" title="Number of rooms">
          <p class="h5">
            <i class="bi bi-door-open-fill me-1"></i><strong>{{ getNumberOfRooms }}</strong>
          </p>
        </div>
        <div id="beds" class="col main-col d-flex justify-content-center" title="">
          <p class="h5">
            <i class="fa-solid fa-bed me-1"></i><strong>{{ getNumberOfBeds }}</strong>
          </p>
        </div>
      </div>

      <div v-if="!isVacationRental" class="p-1" style="text-align: left;">
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
</template>

<script>
import ThePhoto from "@/components/GeneralComponents/ThePhoto";

export default {
  name: "CardView",
  components: {ThePhoto},
  props: ["rental"],
  computed: {
    isVacationRental() {
      return this.rentalObjectType === "VacationRental";
    },
    getNumberOfRooms() {
      return (this.rental.rooms).size;
    },
    getNumberOfBeds() {
      let beds = 0;
      let title = "Beds per room: ";
      let bedsDiv = document.getElementById('beds');
      this.rental.rooms.forEach(room => {
        beds = beds + room.beds;
        title = title + String(room.beds) + " + ";
      });
      title = title.slice(0, -2)
      bedsDiv.setAttribute("title", title);
      return beds;
    }
  }
}
</script>

<style scoped>
  div.card img {
    height: 30vh;
    object-fit: cover;
    object-position: center;

  }

  hr {
    flex-grow: 1;
  }

  div.card {
    border: 1px solid black;
  }
</style>